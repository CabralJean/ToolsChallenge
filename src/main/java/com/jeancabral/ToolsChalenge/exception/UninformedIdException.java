package com.jeancabral.ToolsChalenge.exception;

public class UninformedIdException extends RuntimeException{

    public UninformedIdException(){
        super("O ID da transação não foi informado.");
    }
}
