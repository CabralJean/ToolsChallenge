package com.jeancabral.ToolsChalenge.dto;

import com.jeancabral.ToolsChalenge.model.Descricao;
import com.jeancabral.ToolsChalenge.model.FormaPagamento;

public record TransactionDTO(
        
        Long transacaoId,
        String numCartao,
        Descricao descricao,
        FormaPagamento formaPagamento
) {
   
    
    public static TransactionDTO with(
            final Long transacaoId,
            final String numCartao,
            final Descricao descricao,
            final FormaPagamento formaPagamento
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
