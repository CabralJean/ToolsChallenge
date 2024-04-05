package com.jeancabral.ToolsChalenge.repository;

import com.jeancabral.ToolsChalenge.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstornoRepository extends JpaRepository<Transacao, Long> {

    //Transacao findByTransacaoId(Long transacaoId);

}
