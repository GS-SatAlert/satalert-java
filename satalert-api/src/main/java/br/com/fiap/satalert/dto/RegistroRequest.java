package br.com.fiap.satalert.dto;

import jakarta.validation.constraints.*;

public record RegistroRequest(
    @NotBlank(message = "Nome é obrigatório") String nome,
    @NotBlank(message = "E-mail é obrigatório") @Email(message = "E-mail inválido") String email,
    @NotBlank(message = "Senha é obrigatória") @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres") String senha,
    String telefone
) {}
