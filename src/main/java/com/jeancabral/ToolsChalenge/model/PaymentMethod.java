package com.jeancabral.ToolsChalenge.model;

import jakarta.persistence.Embeddable;

@Embeddable
public record PaymentMethod(
        String tipo,
        Integer parcelas
) {
    
    public static PaymentMethod with(
            final String tipo,
            final Integer parcelas
    ) {
        
        return new PaymentMethod(tipo, parcelas);
    }
}
