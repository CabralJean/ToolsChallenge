package com.jeancabral.ToolsChalenge.controller;

import com.jeancabral.ToolsChalenge.model.Transacao;
import com.jeancabral.ToolsChalenge.repository.EstornoRepository;

import com.jeancabral.ToolsChalenge.service.EstornoService;
import com.jeancabral.ToolsChalenge.wrapper.TransacaoWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/estorno")
public class EstornoController {

    private final EstornoService service;

    public EstornoController(EstornoService service) {
        this.service = service;
    }

    @PostMapping("/{transacaoId}")
    public ResponseEntity<TransacaoWrapper> estornaPagamento(@PathVariable Long transacaoId) {
        Transacao buscaId = service.estornarPagamento(transacaoId);
        TransacaoWrapper wrapper = new TransacaoWrapper(buscaId);
        return ResponseEntity.ok().body(wrapper);
    }

}
