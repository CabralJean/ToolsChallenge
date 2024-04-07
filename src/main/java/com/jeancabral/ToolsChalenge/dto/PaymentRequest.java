package com.jeancabral.ToolsChalenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.jeancabral.ToolsChalenge.model.PaymentDescription;
import com.jeancabral.ToolsChalenge.model.PaymentMethod;

@JsonPropertyOrder({"id", "cartao", "descricao", "formaPagamento"})
public record PaymentRequest(
        @JsonProperty("id")
        Long transactionId,
        
        @JsonProperty("cartao")
        String cartNumber,
        @JsonProperty("descricao")
        PaymentDescription paymentDescription,
        @JsonProperty("formaPagamento")
        PaymentMethod payment
) {
    
    public static PaymentRequest with(
            
            final Long id,
            final String cartNumber,
            final PaymentDescription paymentDescription,
            final PaymentMethod payment
    ) {
        
        return new PaymentRequest(
                id,
                cartNumber,
                paymentDescription,
                payment
        );
        
    }
    
}