package com.jeancabral.ToolsChalenge.controller;

import com.jeancabral.ToolsChalenge.dto.PaymentResponse;
import com.jeancabral.ToolsChalenge.service.impl.ReversalService;
import com.jeancabral.ToolsChalenge.wrapper.TransactionWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reversal")
public class ReversalController {

    private final ReversalService service;

    public ReversalController(ReversalService service) {
        this.service = service;
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionWrapper> buscaEstornoId(@PathVariable Long transactionId) {
        
        final var result = service.buscarEstornoId(transactionId);
        
        return ResponseEntity.ok()
                .body(new TransactionWrapper(PaymentResponse.from(result)));
    }

    @PostMapping("/{transactionId}")
    public ResponseEntity<TransactionWrapper> estornaPagamento(@PathVariable Long transactionId) {
        
        final var result = service.reversalPayment(transactionId);
        
        return ResponseEntity.ok()
                .body(new TransactionWrapper(PaymentResponse.from(result)));
    }

}
