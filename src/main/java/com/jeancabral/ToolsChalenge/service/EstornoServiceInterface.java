package com.jeancabral.ToolsChalenge.service;

import com.jeancabral.ToolsChalenge.model.Transacao;

import java.util.Optional;

public interface EstornoServiceInterface {

    Optional<Transacao> buscarEstornoId(Long transacaoId);

    Transacao estornarPagamento(Long transacaoId);
}
