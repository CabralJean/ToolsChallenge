package com.jeancabral.ToolsChalenge.exception;

public class PaymentException extends RuntimeException {

    public PaymentException(){
        super("Já existe um pagamento para a transação com o ID fornecido.");
    }
}
