package com.jeancabral.ToolsChalenge.service;

import com.jeancabral.ToolsChalenge.dto.TransacaoDto;
import com.jeancabral.ToolsChalenge.model.Transacao;

import java.util.Optional;

public interface EstornoServiceInterface {

    Optional<TransacaoDto> buscarEstornoId(Long transacaoId);

    TransacaoDto estornarPagamento(Long transacaoId);
}
