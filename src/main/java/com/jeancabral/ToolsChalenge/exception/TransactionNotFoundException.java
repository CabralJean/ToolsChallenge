package com.jeancabral.ToolsChalenge.exception;

public class TransactionNotFoundException extends RuntimeException{

    public TransactionNotFoundException(){
        super("Transacao não encontrada!");
    }

}
