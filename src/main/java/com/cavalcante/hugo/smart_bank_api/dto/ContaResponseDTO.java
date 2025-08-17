package com.cavalcante.hugo.smart_bank_api.dto;

import com.cavalcante.hugo.smart_bank_api.model.Conta;

import java.math.BigDecimal;

public record ContaResponseDTO(
        Long id,
        String numeroConta,
        String agencia,
        BigDecimal saldo,
        Long clienteId
) {
    public static ContaResponseDTO fromEntity(Conta conta){
        return new ContaResponseDTO(conta.getId(), conta.getNumeroConta(), conta.getAgencia(), conta.getSaldo(), conta.getCliente().getId());
    }
}
