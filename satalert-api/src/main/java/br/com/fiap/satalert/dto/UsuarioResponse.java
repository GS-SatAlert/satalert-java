package br.com.fiap.satalert.dto;

import java.time.LocalDate;

public record UsuarioResponse(
    Long id,
    String nome,
    String email,
    String telefone,
    String role,
    LocalDate dtCadastro
) {}
