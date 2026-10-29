# Cadastro de Funcionários API

API REST de estudo: CRUD, filtros, DTOs, validação, Mapper, tratamento de erros e injeção por construtor. Projeto independente, sem banco externo e sem Lombok.

## Executar no IntelliJ

1. Use **Code > Download ZIP**, extraia e abra a pasta com `pom.xml` em **File > Open**.
2. Selecione JDK **17 ou 21 instalado** em **File > Project Structure > Project**. O projeto compila para 17 e roda no JDK 21 das aulas. O diretório do JDK deve conter `bin/java.exe` no Windows.
3. Importe como Maven e espere baixar as dependências. Se necessário, use **Add as Maven Project** no `pom.xml` e **Reload All Maven Projects** no painel Maven.
4. Execute `main` em `src/main/java/br/com/estudo/funcionariosapi/Application.java`.
5. Espere `Started Application` e abra `http://localhost:8080/funcionarios`. Inicialmente retorna `[]`.

Tecnologias: Spring Boot 3.5.15, Spring Web, Validation, Java, Maven Wrapper 3.9.9. O Wrapper baixa Maven no primeiro uso; precisa de internet.

```powershell
.\mvnw.cmd spring-boot:run
.\mvnw.cmd test
```

No Linux/macOS, use `./mvnw spring-boot:run` e `./mvnw test`.

## Dados e regras

| Campo | Tipo |
|---|---|
| `id` | `Long`, gerado no servidor |
| `nome` | `String` |
| `cargo` | `String` |
| `salario` | `BigDecimal` |

Salário obrigatório e maior que zero.

Textos obrigatórios não podem estar vazios. Espaços nas pontas são removidos. Valores monetários usam BigDecimal, até 9 inteiros e 2 casas decimais, não negativos (salário deve ser positivo). ID não é aceito no DTO de entrada. Propriedades JSON desconhecidas são rejeitadas.

Exemplo para POST e PUT:

```json
{"nome": "Bruno Souza", "cargo": "Desenvolvedor", "salario": 3500.0}
```

## Rotas

| Método | Caminho | Sucesso |
|---|---|---|
| POST | `/funcionarios` | 201, objeto e Location |
| GET | `/funcionarios` | 200 e lista |
| GET | `/funcionarios?cargo=desenvolvedor` | 200 e lista filtrada |
| GET | `/funcionarios/{id}` | 200 e objeto |
| PUT | `/funcionarios/{id}` | 200, substitui todos os campos |
| DELETE | `/funcionarios/{id}` | 204 sem corpo |

Filtros: `nome` (contains), `cargo` (equals). `contains` busca parte do texto, `equals` compara texto completo, ambos sem distinguir maiúsculas; `bool` recebe true/false; `min`/`max` são limites numéricos. Filtros combinam com E. Lista sem resultados: 200 e `[]`.



No Postman: **Body > raw > JSON**, `Content-Type: application/json` e `Accept: application/json`. Use **Params** para filtros com espaços ou acentos. Há exemplos em `requisicoes.http` para Postman ou cliente HTTP do IntelliJ.

Erros: 400 para entrada inválida, 404 para ID inexistente, 406 para Accept incompatível, 415 para formato de entrada incompatível. Corpo padronizado com `status`, `mensagem` e `campos`.

## Como estudar as classes

1. `Funcionario`: entidade interna e getters.
2. `FuncionarioRequest`/`FuncionarioResponse`: DTOs de entrada e saída. São records, que fornecem acessores como `request.nome()`.
3. `FuncionarioMapper`: converte DTO em entidade e entidade em resposta.
4. `FuncionarioModel`: interface das operações.
5. `ArmazenamentoEmMemoria`: armazenamento e filtros.
6. `FuncionarioModelMemoria`/`FuncionarioModelDemo`: duas implementações com `@Service` e `@Profile`.
7. `FuncionarioController`: recebe requisições e devolve respostas HTTP.
8. `ApiExceptionHandler`: centraliza os erros com `@RestControllerAdvice`.

Fluxo: JSON > DTO > validação > Controller > Mapper > Model > entidade > DTO > JSON.

`@PathVariable`: ID do caminho. `@RequestParam`: filtros após ?. `@RequestBody`: dados JSON. `@Valid`: ativa validação. `ResponseEntity.created(...)`: 201 e Location.

## Trocar implementação

Em `application.properties`, use `spring.profiles.active=demo` e reinicie para obter um registro inicial. O perfil padrão `memoria` começa vazio. Use só um desses perfis por vez. Ambas as implementações usam memória; nenhuma usa SQL. O Spring fornece a dependência pelo construtor: o controller conhece a interface e não cria implementações com `new`.

## Testes e limites

Testes cobrem ciclo CRUD e filtros, regras específicas, erros HTTP/validação e seleção do perfil demo. A configuração de Mockito evita exigir anexação de agente à JVM; os testes usam o contexto real, sem mocks.

Dados desaparecem quando o processo encerra. Sem autenticação, persistência ou paginação; é um exercício didático. Todos os projetos usam porta 8080: execute um por vez ou altere `server.port`.

## Exercícios

- Refaça GET por ID e explique 200, 400 e 404.
- Acrescente um filtro e teste um valor sem resultado.
- Explique por que Request DTO não recebe ID.
- Troque o perfil sem alterar o controller.
- Explique o que muda entre PUT e PATCH.
