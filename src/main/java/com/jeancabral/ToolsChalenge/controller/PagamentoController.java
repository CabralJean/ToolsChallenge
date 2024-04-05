package com.jeancabral.ToolsChalenge.controller;

import com.jeancabral.ToolsChalenge.dto.PagamentoDto;
import com.jeancabral.ToolsChalenge.model.Transacao;
import com.jeancabral.ToolsChalenge.service.impl.PagamentoService;
import com.jeancabral.ToolsChalenge.wrapper.TransacaoWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transacoes")
public class PagamentoController {


    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService transacaoService) {
        this.pagamentoService = transacaoService;
    }


    @GetMapping
    public ResponseEntity<List<Transacao>> buscaTransacoes(){
        List<Transacao> transacoes = pagamentoService.buscarPagamentos();
        return ResponseEntity.ok().body(transacoes);
    }

    @GetMapping("/{transacaoId}")
    public ResponseEntity<Transacao> buscaTransacaoId(@PathVariable Long transacaoId){
        Optional<Transacao> buscaTransacaoId = pagamentoService.buscarTransacaoId(transacaoId);
        return buscaTransacaoId.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/pagamento")
    public ResponseEntity<TransacaoWrapper> pagamento(@RequestBody PagamentoDto pagamentoDto){
        Transacao transacao = pagamentoService.efetuarPagamento(pagamentoDto);
        TransacaoWrapper wrapper = new TransacaoWrapper(transacao);
        return ResponseEntity.ok().body(wrapper);
    }
}

