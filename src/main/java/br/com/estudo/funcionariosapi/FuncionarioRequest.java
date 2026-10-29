package br.com.estudo.funcionariosapi;

import java.math.BigDecimal;
import jakarta.validation.constraints.*;

// DTO de entrada; ID é gerado no servidor.
public record FuncionarioRequest(
    @NotBlank(message="nome é obrigatório") @Size(max=120) String nome,
    @NotBlank(message="cargo é obrigatório") @Size(max=120) String cargo,
    @NotNull @DecimalMin("0.01") @Digits(integer=9, fraction=2) BigDecimal salario
) {}
