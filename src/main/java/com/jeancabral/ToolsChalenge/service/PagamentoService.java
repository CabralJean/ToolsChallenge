package com.jeancabral.ToolsChalenge.service;

import com.jeancabral.ToolsChalenge.dto.PagamentoDto;
import com.jeancabral.ToolsChalenge.enums.StatusEnum;
import com.jeancabral.ToolsChalenge.enums.TipoPagEnum;
import com.jeancabral.ToolsChalenge.model.Descricao;
import com.jeancabral.ToolsChalenge.model.FormaPagamento;
import com.jeancabral.ToolsChalenge.model.Transacao;
import com.jeancabral.ToolsChalenge.repository.PagamentoRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Random;


@Service
public class PagamentoService {

    private final PagamentoRepository repository;


    public PagamentoService(PagamentoRepository repository) {
        this.repository = repository;
    }

    public List<Transacao> buscarPagamentos() {
        return repository.findAll();
    }

    public Transacao buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Transacao efetuarPagamento(PagamentoDto pagamentoDto) {
        validarPagamentoExistente(pagamentoDto.getTransacaoId());

        Transacao newPagamento = criarNovaTransacao(pagamentoDto);

        gerarNsuECodigoAutorizacao(newPagamento.getDescricao());

        definirTipoPagamento(newPagamento, pagamentoDto);

        newPagamento.getDescricao().setStatus(StatusEnum.AUTORIZADO.name());

        return repository.save(newPagamento);
    }

    private void validarPagamentoExistente(Long transacaoId) {
        if (repository.existsByTransacaoId(transacaoId)) {
            throw new RuntimeException("Já existe um pagamento para a transação com o ID fornecido.");
        }
    }

    private Transacao criarNovaTransacao(PagamentoDto pagamentoDto) {
        Transacao newPagamento = new Transacao(pagamentoDto);
        newPagamento.setDescricao(new Descricao(pagamentoDto.getDescricao()));
        newPagamento.setFormaPagamento(new FormaPagamento(pagamentoDto.getFormaPagamento()));
        newPagamento.setTransacaoId(pagamentoDto.getTransacaoId());
        newPagamento.setNum_cartao(pagamentoDto.getNum_cartao());

        return newPagamento;
    }

    private void gerarNsuECodigoAutorizacao(Descricao descricao) {
        Random random = new Random();
        descricao.setNsu(random.nextInt(999999999));
        descricao.setCodigoAutorizacao(random.nextInt(999999999));
    }

    private void definirTipoPagamento(Transacao transacao, PagamentoDto pagamentoDto) {
        FormaPagamento formaPagamento = transacao.getFormaPagamento();

        if (pagamentoDto.getFormaPagamento().getParcelas() == 1) {
            formaPagamento.setTipo(TipoPagEnum.AVISTA.name());

        } else {
            formaPagamento.setTipo(TipoPagEnum.PARCELADO_EMISSOR.name());

        }
    }
}
