package com.jeancabral.ToolsChalenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jeancabral.ToolsChalenge.model.FormaPagamento;

public record PaymentMethodResponse(
        @JsonProperty("tipo")
        String type,
        @JsonProperty("parcelas")
        Integer installments
) {
    
    public static PaymentMethodResponse from(final FormaPagamento paymentMethod) {
        
        return new PaymentMethodResponse(paymentMethod.tipo(), paymentMethod.parcelas());
    }
    
}
