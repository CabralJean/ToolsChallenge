package com.jeancabral.ToolsChalenge.model;

import jakarta.persistence.Embeddable;

@Embeddable
public record FormaPagamento (
        String tipo,
        Integer parcelas
) {
    
    public static FormaPagamento with(
            final String tipo,
            final Integer parcelas
    ) {
        
        return new FormaPagamento(tipo, parcelas);
    }
}
