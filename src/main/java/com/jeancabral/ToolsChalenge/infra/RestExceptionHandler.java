package com.jeancabral.ToolsChalenge.infra;

import com.jeancabral.ToolsChalenge.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<String> transactionNotFoundHandler(NotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transacao não encontrada.");
    }

    @ExceptionHandler(BusinessException.class)
    private ResponseEntity<String> reversalHandler(BusinessException exception){
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("Já existe um estorno para a transação com o ID fornecido.");
    }

    @ExceptionHandler(UninformedIdException.class)
    private ResponseEntity<String> uninformedHandler(UninformedIdException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O ID da transação não foi informado.");
    }

    @ExceptionHandler(PaymentException.class)
    private ResponseEntity<String> paymentdHandler(PaymentException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Já existe um pagamento para a transação com o ID fornecido.");
    }
}
