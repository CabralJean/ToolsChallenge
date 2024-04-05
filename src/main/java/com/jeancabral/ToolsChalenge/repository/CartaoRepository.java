package com.jeancabral.ToolsChalenge.repository;

import com.jeancabral.ToolsChalenge.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {
}
