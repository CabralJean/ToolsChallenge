package com.jeancabral.ToolsChalenge.service;

import com.jeancabral.ToolsChalenge.dto.PagamentoDto;
import com.jeancabral.ToolsChalenge.dto.TransacaoDto;
import com.jeancabral.ToolsChalenge.model.Transacao;

import java.util.List;
import java.util.Optional;

public interface PagamentoServiceInterface {

    List<TransacaoDto> buscarPagamentos();

    Optional<TransacaoDto> buscarTransacaoId(Long transacaoId);

    TransacaoDto efetuarPagamento(PagamentoDto pagamentoDto);
}
