package com.jeancabral.ToolsChalenge.model;

import java.util.Date;

public record PaymentDescription(
        
        double valor,
        Date dataHora,
        String estabelecimento
) {
    
    public static PaymentDescription with(
            final double valor,
            final Date date,
            final String establishment
    ) {
        
        return new PaymentDescription(
                valor,
                date,
                establishment
        );
        
    }
    
}

