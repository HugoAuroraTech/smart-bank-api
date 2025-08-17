package com.cavalcante.hugo.smart_bank_api.repository;

import com.cavalcante.hugo.smart_bank_api.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
}
