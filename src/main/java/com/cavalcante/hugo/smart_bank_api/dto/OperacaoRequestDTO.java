package com.cavalcante.hugo.smart_bank_api.dto;


import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record OperacaoRequestDTO(
        @NotNull
        BigDecimal valor
) {
}
