package com.jeancabral.ToolsChalenge.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class FormaPagamento {

    private String tipo;
    private Integer parcelas;

    public FormaPagamento(FormaPagamento formaPagamento) {
        this.tipo = formaPagamento.tipo;
        this.parcelas = formaPagamento.parcelas;
    }
}
