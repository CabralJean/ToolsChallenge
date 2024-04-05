package com.jeancabral.ToolsChalenge.exception;

public class ReversalException extends RuntimeException {

    public ReversalException(){
        super("Já existe um estorno para a transação com o ID fornecido.");
    }

}
