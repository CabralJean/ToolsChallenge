package com.jeancabral.ToolsChalenge.service;

import com.jeancabral.ToolsChalenge.dto.PagamentoDto;
import com.jeancabral.ToolsChalenge.model.Transacao;

import java.util.List;
import java.util.Optional;

public interface PagamentoServiceInterface {

    List<Transacao> buscarPagamentos();

    Optional<Transacao> buscarTransacaoId(Long transacaoId);

    Transacao efetuarPagamento(PagamentoDto pagamentoDto);
}
