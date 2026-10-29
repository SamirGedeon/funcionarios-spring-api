package br.com.estudo.funcionariosapi;

import java.math.BigDecimal;

public record FuncionarioResponse(Long id, String nome, String cargo, BigDecimal salario) {}
