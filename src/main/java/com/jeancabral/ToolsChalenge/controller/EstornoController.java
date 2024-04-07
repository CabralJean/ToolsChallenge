package com.jeancabral.ToolsChalenge.controller;

import com.jeancabral.ToolsChalenge.dto.PaymentResponse;
import com.jeancabral.ToolsChalenge.service.impl.EstornoService;
import com.jeancabral.ToolsChalenge.wrapper.TransactionWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/estorno")
public class EstornoController {

    private final EstornoService service;

    public EstornoController(EstornoService service) {
        this.service = service;
    }

    @GetMapping("/{transacaoId}")
    public ResponseEntity<TransactionWrapper> buscaEstornoId(@PathVariable Long transacaoId) {
        
        final var result = service.buscarEstornoId(transacaoId);
        
        return ResponseEntity.ok()
                .body(new TransactionWrapper(PaymentResponse.from(result)));
    }

    @PostMapping("/{transacaoId}")
    public ResponseEntity<TransactionWrapper> estornaPagamento(@PathVariable Long transacaoId) {
        
        final var result = service.estornarPagamento(transacaoId);
        
        return ResponseEntity.ok()
                .body(new TransactionWrapper(PaymentResponse.from(result)));
    }

}
