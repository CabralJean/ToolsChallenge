package com.jeancabral.ToolsChalenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({"id", "cartao", "descricao", "formaPagamento"})
public record PaymentResponse(
        
        String id,
        @JsonProperty("cartao")
        String cartNumber,
        @JsonProperty("descricao")
        DescriptionResponse description,
        @JsonProperty("formaPagamento")
        PaymentMethodResponse paymentMethod
) {
    
    public static PaymentResponse from(final TransactionDTO transaction) {
        
        return new PaymentResponse(
                transaction.transacaoId().toString(),
                transaction.numCartao(),
                DescriptionResponse.from(transaction.descricao()),
                PaymentMethodResponse.from(transaction.formaPagamento())
        );
    }
    
    
}
