package com.jeancabral.ToolsChalenge.service;

import com.jeancabral.ToolsChalenge.dto.PaymentRequest;
import com.jeancabral.ToolsChalenge.dto.TransactionDTO;

import java.util.List;

public interface PaymentServiceInterface {

    List<TransactionDTO> findTransactions();

    TransactionDTO findTransactionById(Long transacaoId);

    TransactionDTO createPayment(PaymentRequest pagamentoDto);
}
