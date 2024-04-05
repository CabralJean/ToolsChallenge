package com.jeancabral.ToolsChalenge.controller;

import com.jeancabral.ToolsChalenge.dto.PagamentoDto;
import com.jeancabral.ToolsChalenge.model.Transacao;
import com.jeancabral.ToolsChalenge.service.PagamentoService;
import com.jeancabral.ToolsChalenge.wrapper.TransacaoWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transacoes")
public class PagamentoController {


    private final PagamentoService transacaoService;

    public PagamentoController(PagamentoService transacaoService) {
        this.transacaoService = transacaoService;
    }


    @GetMapping
    public ResponseEntity<List<Transacao>> buscaTransacoes(){
        List<Transacao> transacoes = this.transacaoService.buscarPagamentos();
        return ResponseEntity.ok().body(transacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transacao> buscaTransacaoId(@PathVariable Long id){
        Transacao buscaId = this.transacaoService.buscarPorId(id);
        if(buscaId != null){
            return ResponseEntity.ok().body(buscaId);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/pagamento")
    public ResponseEntity<TransacaoWrapper> pagamento(@RequestBody PagamentoDto pagamentoDto){
        Transacao transacao = this.transacaoService.efetuarPagamento(pagamentoDto);
        TransacaoWrapper wrapper = new TransacaoWrapper(transacao);
        return ResponseEntity.ok().body(wrapper);
    }
}

