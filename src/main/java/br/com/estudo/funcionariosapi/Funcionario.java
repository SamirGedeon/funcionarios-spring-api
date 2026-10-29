package br.com.estudo.funcionariosapi;

import java.math.BigDecimal;

public class Funcionario {
    private final Long id;
    private final String nome;
    private final String cargo;
    private final BigDecimal salario;
    public Funcionario(Long id, String nome, String cargo, BigDecimal salario) {
        this.id = id;
        this.nome = nome;
        this.cargo = cargo;
        this.salario = salario;
    }
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getCargo() { return cargo; }
    public BigDecimal getSalario() { return salario; }
}
