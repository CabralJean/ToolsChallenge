package com.jeancabral.ToolsChalenge.dto;

import com.jeancabral.ToolsChalenge.model.Cartao;
import com.jeancabral.ToolsChalenge.model.Descricao;
import com.jeancabral.ToolsChalenge.model.FormaPagamento;

public record TransacaoPagtoEstornoDto(Cartao num_cartao, Integer id, Descricao descricao, FormaPagamento formaPagamento) {
}
