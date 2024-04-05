package com.jeancabral.ToolsChalenge.repository;

import com.jeancabral.ToolsChalenge.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<Transacao, Long> {

    boolean existsByTransacaoId(Long transacaoId);
}
