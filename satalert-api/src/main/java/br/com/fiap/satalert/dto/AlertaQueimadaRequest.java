// AlertaQueimadaRequest.java
package br.com.fiap.satalert.dto;

import jakarta.validation.constraints.*;

public record AlertaQueimadaRequest(
    @NotNull(message = "ID da região é obrigatório") Long idRegiao,
    @NotNull(message = "Temperatura é obrigatória")
    @DecimalMin("0.0") @DecimalMax("120.0") Double temperatura,
    @NotNull @DecimalMin("0.0") @DecimalMax("100.0") Double nivelFumaca
) {}
