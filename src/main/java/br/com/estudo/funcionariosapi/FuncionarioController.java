package br.com.estudo.funcionariosapi;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.math.BigDecimal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/funcionarios", produces="application/json")
public class FuncionarioController {
    private final FuncionarioModel model;
    public FuncionarioController(FuncionarioModel model) { this.model = model; }
    @PostMapping(consumes="application/json")
    public ResponseEntity<FuncionarioResponse> criar(@Valid @RequestBody FuncionarioRequest request) {
        Funcionario criado = model.criar(FuncionarioMapper.toEntity(request, null));
        return ResponseEntity.created(URI.create("/funcionarios/"+criado.getId())).body(FuncionarioMapper.toResponse(criado));
    }
    @GetMapping
    public List<FuncionarioResponse> listar(@RequestParam(name="nome", required=false) String nome,
            @RequestParam(name="cargo", required=false) String cargo) { return model.listar(nome, cargo).stream().map(FuncionarioMapper::toResponse).toList(); }
    @GetMapping("/{id}")
    public FuncionarioResponse buscar(@PathVariable("id") Long id) { return FuncionarioMapper.toResponse(model.buscar(id).orElseThrow(() -> new RecursoNaoEncontradoException(id))); }
    @PutMapping(value="/{id}", consumes="application/json")
    public FuncionarioResponse atualizar(@PathVariable("id") Long id, @Valid @RequestBody FuncionarioRequest request) {
        return FuncionarioMapper.toResponse(model.atualizar(id, FuncionarioMapper.toEntity(request,id)).orElseThrow(() -> new RecursoNaoEncontradoException(id)));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") Long id) {
        if (!model.excluir(id)) throw new RecursoNaoEncontradoException(id);
        return ResponseEntity.noContent().build();
    }
    
}
