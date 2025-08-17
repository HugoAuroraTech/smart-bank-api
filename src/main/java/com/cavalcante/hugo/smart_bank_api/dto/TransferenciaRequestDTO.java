package com.cavalcante.hugo.smart_bank_api.dto;



import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransferenciaRequestDTO(
        @NotNull
        Long idContaOrigem,
        @NotNull
        Long idContaDestino,
        @NotNull
        BigDecimal valor
) {
}
