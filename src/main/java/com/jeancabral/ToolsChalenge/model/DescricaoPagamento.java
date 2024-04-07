package com.jeancabral.ToolsChalenge.model;

import java.util.Date;

public record DescricaoPagamento(
        
        double valor,
        Date dataHora,
        String estabelecimento
) {
    
    public static DescricaoPagamento with(
            final double valor,
            final Date date,
            final String establishment
    ) {
        
        return new DescricaoPagamento(
                valor,
                date,
                establishment
        );
        
    }
    
}

