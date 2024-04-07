package com.jeancabral.ToolsChalenge.service;

import com.jeancabral.ToolsChalenge.dto.TransactionDTO;

public interface EstornoServiceInterface {

    TransactionDTO buscarEstornoId(Long transacaoId);

    TransactionDTO estornarPagamento(Long transacaoId);
}
