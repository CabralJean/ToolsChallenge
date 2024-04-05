package com.jeancabral.ToolsChalenge.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.jeancabral.ToolsChalenge.dto.PagamentoDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transacoes")
@JsonPropertyOrder({"id", "num_cartao", "descricao", "formaPagamento"})
public class Transacao {

    /*@GeneratedValue
    @JsonIgnore
    private Long Id;*/

    @Id
    @JsonProperty("id")
    private Long transacaoId;

    private String num_cartao;

    @Embedded
    private Descricao descricao;

    @Embedded
    private FormaPagamento formaPagamento;

    public Transacao(PagamentoDto pagamento) {
        this.num_cartao = pagamento.getNum_cartao();

    }


}
