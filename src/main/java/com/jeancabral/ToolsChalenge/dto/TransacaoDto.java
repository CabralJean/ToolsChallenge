package com.jeancabral.ToolsChalenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.jeancabral.ToolsChalenge.model.Descricao;
import com.jeancabral.ToolsChalenge.model.FormaPagamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"id", "num_cartao", "descricao", "formaPagamento"})
public class TransacaoDto {

    @JsonProperty("id")
    private Long transacaoId;

    private String num_cartao;

    private Descricao descricao;

    private FormaPagamento formaPagamento;

    public TransacaoDto(PagamentoDto pagamentoDto) {
    }
}
