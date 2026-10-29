package br.com.estudo.funcionariosapi;

import java.util.*;
import java.math.BigDecimal;
public interface FuncionarioModel {
    Funcionario criar(Funcionario entrada);
    List<Funcionario> listar(String nome, String cargo);
    Optional<Funcionario> buscar(Long id);
    Optional<Funcionario> atualizar(Long id, Funcionario entrada);
    boolean excluir(Long id);
}
