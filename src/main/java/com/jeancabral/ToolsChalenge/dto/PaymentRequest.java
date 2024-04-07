package com.jeancabral.ToolsChalenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.jeancabral.ToolsChalenge.model.DescricaoPagamento;
import com.jeancabral.ToolsChalenge.model.FormaPagamento;

@JsonPropertyOrder({"id", "num_cartao", "description", "paymentMethod"})
public record PaymentRequest(
        @JsonProperty("id")
        Long transactionId,
        
        @JsonProperty("num_cartao")
        String cartNumber,
        @JsonProperty("description")
        DescricaoPagamento paymentDescription,
        @JsonProperty("paymentMethod")
        FormaPagamento payment
) {
    
    public static PaymentRequest with(
            
            final Long id,
            final String cartNumber,
            final DescricaoPagamento paymentDescription,
            final FormaPagamento payment
    ) {
        
        return new PaymentRequest(
                id,
                cartNumber,
                paymentDescription,
                payment
        );
        
    }
    
}