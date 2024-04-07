package com.jeancabral.ToolsChalenge.service;

import com.jeancabral.ToolsChalenge.dto.PaymentRequest;
import com.jeancabral.ToolsChalenge.dto.TransactionDTO;

import java.util.List;

public interface PagamentoServiceInterface {

    List<TransactionDTO> buscarPagamentos();

    TransactionDTO buscarTransacaoId(Long transacaoId);

    TransactionDTO efetuarPagamento(PaymentRequest pagamentoDto);
}
