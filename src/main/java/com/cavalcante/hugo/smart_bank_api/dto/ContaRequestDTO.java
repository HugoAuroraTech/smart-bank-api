package com.cavalcante.hugo.smart_bank_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ContaRequestDTO(
        @NotBlank(message = "Agência não pode ser vazio")
        @Size(min = 4, max = 10, message = "A agência deve ter entre 4 a 10 caractêres")
        String agencia
) {
}
