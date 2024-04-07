package com.jeancabral.ToolsChalenge.dto;

import com.jeancabral.ToolsChalenge.model.Description;
import com.jeancabral.ToolsChalenge.model.PaymentMethod;

public record TransactionDTO(
        
        Long transacaoId,
        String numCartao,
        Description descricao,
        PaymentMethod formaPagamento
) {
   
    
    public static TransactionDTO with(
            final Long transacaoId,
            final String numCartao,
            final Description descricao,
            final PaymentMethod formaPagamento
    ) {
        
        return new TransactionDTO(
                transacaoId,
                numCartao,
                descricao,
                formaPagamento
        );
    }
    
    public void estornar() {
        this.descricao.estornar();
    }
    
}
