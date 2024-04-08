package com.jeancabral.ToolsChalenge.controller;

import com.jeancabral.ToolsChalenge.dto.ListPaymentResponse;
import com.jeancabral.ToolsChalenge.dto.PaymentRequest;
import com.jeancabral.ToolsChalenge.dto.PaymentResponse;
import com.jeancabral.ToolsChalenge.service.impl.PaymentService;
import com.jeancabral.ToolsChalenge.wrapper.TransactionWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transaction")
public class PaymentController {
    
    private final PaymentService pagamentoService;

    public PaymentController(PaymentService transacaoService) {
        this.pagamentoService = transacaoService;
    }

    @GetMapping
    public ResponseEntity<ListPaymentResponse> findTransactions() {
       
        final var result = pagamentoService.findTransactions();
       
        return ResponseEntity.ok().body(
                ListPaymentResponse.from(result)
        );
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionWrapper> findTransactionById(@PathVariable Long transactionId) {
        
        final var result = pagamentoService.findTransactionById(transactionId);
        
        return ResponseEntity.ok().body(
                new TransactionWrapper(PaymentResponse.from(result)));

    }


    @PostMapping("/payment")
    public ResponseEntity<TransactionWrapper> createPayment(@RequestBody PaymentRequest paymentDto){
       
        final var result = pagamentoService.createPayment(paymentDto);
       
        return ResponseEntity.ok()
                .body(new TransactionWrapper(PaymentResponse.from(result)));
    }
}

