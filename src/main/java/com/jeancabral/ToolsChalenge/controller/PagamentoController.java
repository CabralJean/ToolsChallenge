package com.jeancabral.ToolsChalenge.controller;

import com.jeancabral.ToolsChalenge.dto.PagamentoDto;
import com.jeancabral.ToolsChalenge.dto.TransacaoDto;
import com.jeancabral.ToolsChalenge.service.impl.PagamentoService;
import com.jeancabral.ToolsChalenge.wrapper.TransacaoWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transacoes")
public class PagamentoController {


    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService transacaoService) {
        this.pagamentoService = transacaoService;
    }

    @GetMapping
    public ResponseEntity<List<TransacaoWrapper>> buscaTransacoes() {
        List<TransacaoDto> transacoes = pagamentoService.buscarPagamentos();
        List<TransacaoWrapper> transacoesWrapper = transacoes.stream()
                .map(TransacaoWrapper::new) // Supondo que TransacaoWrapper tenha um construtor que aceite um TransacaoDto
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(transacoesWrapper);
    }

    @GetMapping("/{transacaoId}")
    public ResponseEntity<TransacaoWrapper> buscaTransacaoId(@PathVariable Long transacaoId) {
        Optional<TransacaoWrapper> buscaTransacaoId = pagamentoService.buscarTransacaoId(transacaoId)
                .map(TransacaoWrapper::new); // Supondo que TransacaoWrapper tenha um construtor que aceite um TransacaoDto
        return buscaTransacaoId.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping("/pagamento")
    public ResponseEntity<TransacaoWrapper> pagamento(@RequestBody PagamentoDto pagamentoDto){
        TransacaoDto transacao = pagamentoService.efetuarPagamento(pagamentoDto);
        TransacaoWrapper wrapper = new TransacaoWrapper(transacao);
        return ResponseEntity.ok().body(wrapper);
    }
}

