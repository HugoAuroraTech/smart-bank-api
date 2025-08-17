package com.cavalcante.hugo.smart_bank_api.dto;

import com.cavalcante.hugo.smart_bank_api.model.Cliente;

public record ClienteResponseDTO(
        Long id,
        String nome,
        String cpf,
        String email
) {
    public static ClienteResponseDTO fromEntity(Cliente cliente){
        return new ClienteResponseDTO(cliente.getId(), cliente.getNome(), cliente.getCpf(), cliente.getEmail());
    }
}
