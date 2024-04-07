package com.jeancabral.ToolsChalenge.repository;

import com.jeancabral.ToolsChalenge.model.TransacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<TransacaoEntity, Long> {
    
    @Query("""
        SELECT t FROM TransacaoEntity t
            WHERE t.descricao.status = 'CANCELADO'
            AND t.transacaoId = :transacaoId
       """)
    Optional<TransacaoEntity> findCanceladosById(@Param("transacaoId") final Long transacaoId);
    
    boolean existsByTransacaoId(final Long transacaoId);
}
