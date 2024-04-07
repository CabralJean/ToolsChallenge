package com.jeancabral.ToolsChalenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.jeancabral.ToolsChalenge.model.Description.with;

@JsonPropertyOrder({"id", "cartao", "descricao", "formaPagamento"})
public record PaymentResponse(
        
        String id,
        @JsonProperty("cartao")
        String cartNumber,
        @JsonProperty("descricao")
        DescriptionResponse description,
        @JsonProperty("formaPagamento")
        PaymentMethodResponse paymentMethod
){

        public static PaymentResponse from(
            final TransactionDTO transaction

     ){
            final var cartNumber = maskCartNumber(transaction.numCartao());

        return new PaymentResponse(
                transaction.transacaoId().toString(),
                cartNumber, //transaction.numCartao(),
                DescriptionResponse.from(transaction.descricao()),
                PaymentMethodResponse.from(transaction.formaPagamento())
        );
    }

    private static String maskCartNumber(String cartNumber) {
        if (cartNumber != null && cartNumber.length() >= 10) {
            String firstPart = cartNumber.substring(0, 4);
            String maskedPart = "*****";
            String lastPart = cartNumber.substring(cartNumber.length() - 4);
            return firstPart + maskedPart + lastPart;
        } else {
            return cartNumber;
        }
    }

}
