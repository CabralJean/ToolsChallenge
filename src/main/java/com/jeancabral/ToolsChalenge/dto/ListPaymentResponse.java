package com.jeancabral.ToolsChalenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import static com.jeancabral.ToolsChalenge.util.CollectionUtil.mapTo;

public record ListPaymentResponse (
        @JsonProperty("transacoes")
        List<PaymentResponse> payments
        
) {
    
    public static ListPaymentResponse from (final List<TransactionDTO> transactions) {
        
        return new ListPaymentResponse(
                mapTo(transactions, PaymentResponse::from)
        );
    }
}
