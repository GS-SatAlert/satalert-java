package br.com.fiap.satalert.dto;

import jakarta.validation.constraints.*;

public record RegiaoRequest(
    @NotBlank(message = "Nome é obrigatório") @Size(max = 100) String nome,
    @NotBlank(message = "Estado é obrigatório") String estado,
    String bioma,
    @NotNull Double latitude,
    @NotNull Double longitude
) {}
