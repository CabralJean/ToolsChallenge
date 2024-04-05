package com.jeancabral.ToolsChalenge.service.impl;

import com.jeancabral.ToolsChalenge.dto.PagamentoDto;
import com.jeancabral.ToolsChalenge.enums.StatusEnum;
import com.jeancabral.ToolsChalenge.enums.TipoPagEnum;
import com.jeancabral.ToolsChalenge.exception.PaymentException;
import com.jeancabral.ToolsChalenge.exception.TransactionNotFoundException;
import com.jeancabral.ToolsChalenge.exception.UninformedIdException;
import com.jeancabral.ToolsChalenge.model.Descricao;
import com.jeancabral.ToolsChalenge.model.FormaPagamento;
import com.jeancabral.ToolsChalenge.model.Transacao;
import com.jeancabral.ToolsChalenge.repository.PagamentoRepository;
import com.jeancabral.ToolsChalenge.service.PagamentoServiceInterface;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.Random;


@Service
public class PagamentoService implements PagamentoServiceInterface {

    private final PagamentoRepository repository;


    public PagamentoService(PagamentoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Transacao> buscarPagamentos() {
        return repository.findAll();
    }

    @Override
    public Optional<Transacao> buscarTransacaoId(Long transacaoId) {
        Optional<Transacao> transacaoOptional = repository.findById(transacaoId);

        if (transacaoId == null) {
            throw new UninformedIdException();
        }
        if(transacaoOptional.isEmpty()){
            throw new TransactionNotFoundException();
        }
        return repository.findById(transacaoId);
    }

    @Override
    public Transacao efetuarPagamento(PagamentoDto pagamentoDto) {
        validarPagamentoExistente(pagamentoDto.getTransacaoId());

        Transacao newPagamento = criarNovaTransacao(pagamentoDto);

        gerarNsuECodigoAutorizacao(newPagamento.getDescricao());

        definirTipoPagamento(newPagamento, pagamentoDto);

        newPagamento.getDescricao().setStatus(StatusEnum.AUTORIZADO.name());

        return repository.save(newPagamento);
    }

    private void validarPagamentoExistente(Long transacaoId) {
        Optional<Transacao> transacaoOptional = repository.findById(transacaoId);
        if (transacaoOptional.isPresent()) {
            throw new PaymentException();
        }
        if(transacaoId == null){
            throw new UninformedIdException();
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

        if (pagamentoDto.getFormaPagamento().getParcelas() <= 1) {
            formaPagamento.setTipo(TipoPagEnum.AVISTA.name());

        } else {
            formaPagamento.setTipo(TipoPagEnum.PARCELADO_EMISSOR.name());

        }
    }
}
