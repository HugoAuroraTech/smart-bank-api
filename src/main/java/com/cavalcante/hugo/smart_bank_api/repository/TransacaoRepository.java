package com.cavalcante.hugo.smart_bank_api.repository;

import com.cavalcante.hugo.smart_bank_api.model.Transacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    Page<Transacao> findByContaId(Long contaId, Pageable pageable);
}
