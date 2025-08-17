package com.cavalcante.hugo.smart_bank_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record ClienteRequestDTO(
        @NotBlank(message = "Nome não pode ser vazio")
        String nome,
        @NotBlank(message = "CPF não pode ser vazio")
        @CPF(message = "CPF inválido")
        String cpf,
        @NotBlank(message = "Email não pode ser vazio")
        @Email(message = "Email inválido")
        String email
) {
}
