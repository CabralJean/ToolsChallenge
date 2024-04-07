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

    // Construtor alternativo que aplica a máscara no número do cartão
    public PaymentResponse {
        this.id = id;
        this.description = description;
        this.paymentMethod = paymentMethod;
        if (cartNumber != null && cartNumber.length() >= 12) {
            String firstPart = cartNumber.substring(0, 4);
            String maskedPart = "******";
            String lastPart = cartNumber.substring(cartNumber.length() - 4);
            this.cartNumber = firstPart + maskedPart + lastPart;
        } else {
            this.cartNumber = cartNumber;
        }
    }
    
}
