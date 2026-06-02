package br.com.fiap.satalert.dto;

public record RegiaoResponse(
    Long id,
    String nome,
    String estado,
    String bioma,
    Double latitude,
    Double longitude,
    String nivelRisco
) {}
