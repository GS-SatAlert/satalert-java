package br.com.fiap.satalert.dto;

import jakarta.validation.constraints.*;

public record AlertaDesmatamentoRequest(
    @NotNull(message = "ID da região é obrigatório") Long idRegiao,
    @NotNull @DecimalMin("0.1") Double areaHectares,
    @DecimalMin("0.0") @DecimalMax("100.0") Double coberturaVegetal
) {}
