package com.jeancabral.ToolsChalenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jeancabral.ToolsChalenge.model.PaymentMethod;

public record PaymentMethodResponse(
        @JsonProperty("tipo")
        String type,
        @JsonProperty("parcelas")
        Integer installments
) {
    
    public static PaymentMethodResponse from(final PaymentMethod paymentMethod) {
        
        return new PaymentMethodResponse(paymentMethod.tipo(), paymentMethod.parcelas());
    }
    
}
