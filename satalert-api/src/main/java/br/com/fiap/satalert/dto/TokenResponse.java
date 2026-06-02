package br.com.fiap.satalert.dto;

public record TokenResponse(
    String token,
    String tipo,
    Long expiracao
) {}
