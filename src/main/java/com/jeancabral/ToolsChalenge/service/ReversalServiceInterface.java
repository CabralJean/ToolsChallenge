package com.jeancabral.ToolsChalenge.service;

import com.jeancabral.ToolsChalenge.dto.TransactionDTO;

public interface ReversalServiceInterface {

    TransactionDTO buscarEstornoId(Long transacaoId);

    TransactionDTO reversalPayment(Long transacaoId);
}
