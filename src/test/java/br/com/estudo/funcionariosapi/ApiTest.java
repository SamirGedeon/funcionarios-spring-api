package br.com.estudo.funcionariosapi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
class ApiTest {
    @Autowired MockMvc mvc;
    @Autowired com.fasterxml.jackson.databind.ObjectMapper json;
    @Test void crudFiltroERegras() throws Exception {
        String body="{\"nome\": \"Bruno Souza\", \"cargo\": \"Desenvolvedor\", \"salario\": 3500.0}";
        String response=mvc.perform(post("/funcionarios").contentType(MediaType.APPLICATION_JSON).content(body)).andExpect(status().isCreated()).andExpect(header().exists("Location")).andReturn().getResponse().getContentAsString();
        long id=json.readTree(response).get("id").asLong();
        mvc.perform(get("/funcionarios/"+id)).andExpect(status().isOk()).andExpect(jsonPath("$.nome").value("Bruno Souza"));
        mvc.perform(get("/funcionarios").param("cargo","desenvolvedor")).andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(id));
        
        mvc.perform(put("/funcionarios/"+id).contentType(MediaType.APPLICATION_JSON).content("{\"nome\": \"Bruno Souza Atualizado\", \"cargo\": \"Desenvolvedor\", \"salario\": 3500.0}")).andExpect(status().isOk()).andExpect(jsonPath("$.nome").value("Bruno Souza Atualizado"));
        mvc.perform(delete("/funcionarios/"+id)).andExpect(status().isNoContent()).andExpect(content().string(""));
        mvc.perform(get("/funcionarios/"+id)).andExpect(status().isNotFound());
        mvc.perform(delete("/funcionarios/"+id)).andExpect(status().isNotFound());
    }
    @Test void validacaoEContratoHttp() throws Exception {
        mvc.perform(post("/funcionarios").contentType(MediaType.APPLICATION_JSON).content("{}")).andExpect(status().isBadRequest()).andExpect(jsonPath("$.campos").isArray());
        mvc.perform(post("/funcionarios").contentType(MediaType.APPLICATION_JSON).content("{")).andExpect(status().isBadRequest());
        mvc.perform(get("/funcionarios/abc")).andExpect(status().isBadRequest());
        mvc.perform(post("/funcionarios").contentType(MediaType.TEXT_PLAIN).content("teste")).andExpect(status().isUnsupportedMediaType());
        mvc.perform(get("/funcionarios").accept(MediaType.TEXT_HTML)).andExpect(status().isNotAcceptable());
    }
}
