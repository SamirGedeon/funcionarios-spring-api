package br.com.estudo.funcionariosapi;

import java.math.BigDecimal;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
@Service
@Profile("demo")
public class FuncionarioModelDemo extends ArmazenamentoEmMemoria { public FuncionarioModelDemo() { criar(new Funcionario(null, "Bruno Souza", "Desenvolvedor", new BigDecimal("3500.0"))); } }
