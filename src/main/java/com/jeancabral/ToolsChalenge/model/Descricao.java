package com.jeancabral.ToolsChalenge.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class Descricao {

    private double valor;
    private Date dataHora;
    private String estabelecimento;
    private Integer nsu;
    private Integer codigoAutorizacao;
    private String status;

    // Construtor que aceita um DescricaoPagamento e converte para Descricao
    public Descricao(DescricaoPagamento descricaoPagamento) {
        this.valor = descricaoPagamento.getValor();
        this.dataHora = descricaoPagamento.getDataHora();
        this.estabelecimento = descricaoPagamento.getEstabelecimento();

    }
}
