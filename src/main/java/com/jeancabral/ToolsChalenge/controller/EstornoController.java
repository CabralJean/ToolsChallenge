package com.jeancabral.ToolsChalenge.controller;

import com.jeancabral.ToolsChalenge.model.Transacao;

import com.jeancabral.ToolsChalenge.service.impl.EstornoService;
import com.jeancabral.ToolsChalenge.wrapper.TransacaoWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/estorno")
public class EstornoController {

    private final EstornoService service;

    public EstornoController(EstornoService service) {
        this.service = service;
    }

    @GetMapping("/{transacaoId}")
    public ResponseEntity<Transacao> buscaEstornoId(@PathVariable Long transacaoId){
        Optional<Transacao> buscaTransacaoId = service.buscarEstornoId(transacaoId);
        return buscaTransacaoId.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{transacaoId}")
    public ResponseEntity<TransacaoWrapper> estornaPagamento(@PathVariable Long transacaoId) {
        Transacao buscaId = service.estornarPagamento(transacaoId);
        TransacaoWrapper wrapper = new TransacaoWrapper(buscaId);
        return ResponseEntity.ok().body(wrapper);
    }

}
