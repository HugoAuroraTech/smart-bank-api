package com.cavalcante.hugo.smart_bank_api.dto;

import com.cavalcante.hugo.smart_bank_api.model.Transacao;
import com.cavalcante.hugo.smart_bank_api.model.enums.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransacaoResponseDTO(
        Long id,
        BigDecimal valor,
        TipoTransacao tipo,
        LocalDateTime dataHora
) {
    public static TransacaoResponseDTO fromEntity(Transacao transacao) {
        return new TransacaoResponseDTO(
                transacao.getId(),
                transacao.getValor(),
                transacao.getTipo(),
                transacao.getDataHora()
        );
    }
}