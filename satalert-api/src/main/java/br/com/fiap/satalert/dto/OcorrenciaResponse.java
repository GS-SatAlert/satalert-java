package br.com.fiap.satalert.dto;

import java.time.LocalDate;

public record OcorrenciaResponse(
    Long id,
    String nomeRegiao,
    String estado,
    String tipoAlerta,
    String nivelRisco,
    String status,
    LocalDate dtOcorrencia,
    LocalDate dtResolucao
) {}
