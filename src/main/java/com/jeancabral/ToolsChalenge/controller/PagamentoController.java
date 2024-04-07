package com.jeancabral.ToolsChalenge.controller;

import com.jeancabral.ToolsChalenge.dto.ListPaymentResponse;
import com.jeancabral.ToolsChalenge.dto.PaymentRequest;
import com.jeancabral.ToolsChalenge.dto.PaymentResponse;
import com.jeancabral.ToolsChalenge.service.impl.PagamentoService;
import com.jeancabral.ToolsChalenge.wrapper.TransactionWrapper;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transacoes")
public class PagamentoController {
    
    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService transacaoService) {
        this.pagamentoService = transacaoService;
    }

    @GetMapping
    public ResponseEntity<ListPaymentResponse> buscaTransacoes() {
       
        final var result = pagamentoService.buscarPagamentos();
       
        return ResponseEntity.ok().body(
                ListPaymentResponse.from(result)
        );
    }

    @GetMapping("/{transacaoId}")
    public ResponseEntity<TransactionWrapper> buscaTransacaoId(@PathVariable Long transacaoId) {
        
        final var result = pagamentoService.buscarTransacaoId(transacaoId);
        
        return ResponseEntity.ok().body(
                new TransactionWrapper(PaymentResponse.from(result)));

    }


    @PostMapping("/pagamento")
    public ResponseEntity<TransactionWrapper> pagamento(@RequestBody PaymentRequest pagamentoDto){
       
        final var result = pagamentoService.efetuarPagamento(pagamentoDto);
       
        return ResponseEntity.ok()
                .body(new TransactionWrapper(PaymentResponse.from(result)));
    }
}

