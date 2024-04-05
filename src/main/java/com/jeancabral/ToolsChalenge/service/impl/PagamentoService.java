package com.jeancabral.ToolsChalenge.service.impl;

import com.jeancabral.ToolsChalenge.dto.PagamentoDto;
import com.jeancabral.ToolsChalenge.dto.TransacaoDto;
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
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;


@Service
public class PagamentoService implements PagamentoServiceInterface {

    private final ModelMapper modelMapper;
    private final PagamentoRepository repository;

    public PagamentoService(PagamentoRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TransacaoDto> buscarPagamentos() {
        List<Transacao> transacoes = repository.findAll();
        return transacoes.stream()
                .map(transacao -> modelMapper.map(transacao, TransacaoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TransacaoDto> buscarTransacaoId(Long transacaoId) {
        if (transacaoId == null) {
            throw new UninformedIdException();
        }
        Optional<Transacao> transacaoOptional = repository.findById(transacaoId);
        if (transacaoOptional.isEmpty()) {
            throw new TransactionNotFoundException();
        }
        Transacao transacao = transacaoOptional.get();
        return Optional.of(modelMapper.map(transacao, TransacaoDto.class));
    }

    @Override
    public TransacaoDto efetuarPagamento(PagamentoDto pagamentoDto) {
        validarPagamentoExistente(pagamentoDto.getTransacaoId());

        TransacaoDto newPagamento = criarNovaTransacao(pagamentoDto);

        gerarNsuECodigoAutorizacao(newPagamento.getDescricao());

        definirTipoPagamento(newPagamento, pagamentoDto);

        newPagamento.getDescricao().setStatus(StatusEnum.AUTORIZADO.name());

        Transacao newPagamentoDto = modelMapper.map(newPagamento, Transacao.class);

        Transacao pagamentoSalvo = repository.save(newPagamentoDto);
        return modelMapper.map(pagamentoSalvo, TransacaoDto.class);
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

    private TransacaoDto criarNovaTransacao(PagamentoDto pagamentoDto) {
        TransacaoDto newPagamento = new TransacaoDto(pagamentoDto);
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

    private void definirTipoPagamento(TransacaoDto transacao, PagamentoDto pagamentoDto) {
        FormaPagamento formaPagamento = transacao.getFormaPagamento();

        if (pagamentoDto.getFormaPagamento().getParcelas() <= 1) {
            formaPagamento.setTipo(TipoPagEnum.AVISTA.name());

        } else {
            formaPagamento.setTipo(TipoPagEnum.PARCELADO_EMISSOR.name());

        }
    }
}
