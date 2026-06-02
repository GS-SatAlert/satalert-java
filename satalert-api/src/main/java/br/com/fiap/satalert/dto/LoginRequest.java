package br.com.fiap.satalert.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank String email,
    @NotBlank String senha
) {}
