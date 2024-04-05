package com.jeancabral.ToolsChalenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.jeancabral.ToolsChalenge.model.DescricaoPagamento;
import com.jeancabral.ToolsChalenge.model.FormaPagamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"id", "num_cartao", "descricao", "formaPagamento"})
public class PagamentoDto {

    @JsonProperty("id")
    private Long transacaoId;

    private Integer num_cartao;

    private DescricaoPagamento descricao;

    private FormaPagamento formaPagamento;

    public Integer num_cartao() {
        return 0;
    }

}
