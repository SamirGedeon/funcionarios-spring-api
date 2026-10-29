package br.com.estudo.funcionariosapi;

import java.util.*;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public abstract class ArmazenamentoEmMemoria implements FuncionarioModel {
    private final Map<Long, Funcionario> itens = new LinkedHashMap<>();
    private final AtomicLong sequencia = new AtomicLong();
    public synchronized Funcionario criar(Funcionario entrada) {
        Long id = sequencia.incrementAndGet();
        Funcionario criado = new Funcionario(id, entrada.getNome(), entrada.getCargo(), entrada.getSalario());
        itens.put(id, criado);
        return criado;
    }
    public synchronized List<Funcionario> listar(String nome, String cargo) {
        
        return itens.values().stream()
                        .filter(item -> nome == null || nome.isBlank() || item.getNome().toLowerCase(Locale.ROOT).contains(nome.strip().toLowerCase(Locale.ROOT)))
            .filter(item -> cargo == null || cargo.isBlank() || item.getCargo().equalsIgnoreCase(cargo.strip()))
            .toList();
    }
    public synchronized Optional<Funcionario> buscar(Long id) { return Optional.ofNullable(itens.get(id)); }
    public synchronized Optional<Funcionario> atualizar(Long id, Funcionario entrada) {
        if (!itens.containsKey(id)) return Optional.empty();
        Funcionario atualizado = new Funcionario(id, entrada.getNome(), entrada.getCargo(), entrada.getSalario());
        itens.put(id, atualizado);
        return Optional.of(atualizado);
    }
    public synchronized boolean excluir(Long id) { return itens.remove(id) != null; }
}
