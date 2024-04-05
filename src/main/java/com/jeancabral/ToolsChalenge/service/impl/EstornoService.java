package com.jeancabral.ToolsChalenge.service.impl;

import com.jeancabral.ToolsChalenge.dto.TransacaoDto;
import com.jeancabral.ToolsChalenge.exception.ReversalException;
import com.jeancabral.ToolsChalenge.exception.ReversalNotFoundException;
import com.jeancabral.ToolsChalenge.exception.TransactionNotFoundException;
import org.modelmapper.ModelMapper;
import com.jeancabral.ToolsChalenge.enums.StatusEnum;
import com.jeancabral.ToolsChalenge.exception.UninformedIdException;
import com.jeancabral.ToolsChalenge.model.Descricao;
import com.jeancabral.ToolsChalenge.model.FormaPagamento;
import com.jeancabral.ToolsChalenge.model.Transacao;
import com.jeancabral.ToolsChalenge.repository.EstornoRepository;
import com.jeancabral.ToolsChalenge.service.EstornoServiceInterface;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class EstornoService implements EstornoServiceInterface {

    private final ModelMapper modelMapper;
    private final EstornoRepository repository;

    public EstornoService(EstornoRepository repository,ModelMapper modelMapper) {

        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<TransacaoDto> buscarEstornoId(Long transacaoId) {
        ModelMapper modelMapper = new ModelMapper();
        if (transacaoId == null) {
            throw new UninformedIdException();
        }
        Optional<Transacao> transacaoOptional = repository.findById(transacaoId);

        if (transacaoOptional.isEmpty()) {
            throw new ReversalNotFoundException("Transação não encontrada com o ID fornecido: " + transacaoId);
        }

        Transacao transacao = transacaoOptional.get();
        if (!transacao.getDescricao().getStatus().equals(StatusEnum.CANCELADO.name())) {
            return Optional.empty();
        }

        TransacaoDto transacaoDto = modelMapper.map(transacao, TransacaoDto.class);

        return Optional.of(transacaoDto);
    }

    /*public Optional<Transacao> buscarEstornoId(Long transacaoId) {
        Optional<Transacao> transacaoEstornada = repository.findById(transacaoId);
        if(transacaoEstornada.isPresent()){
            Transacao transacao = transacaoEstornada.get();
            if (transacao.getDescricao().getStatus().equals(StatusEnum.CANCELADO.name())){
                return transacaoEstornada;
            }else{
                return Optional.empty();
            }
        }else{
            return Optional.empty();
        }
    }*/

    @Override
    public TransacaoDto estornarPagamento(Long transacaoId){
        Transacao transacaoPagtoOptional = repository.findById(transacaoId)
                .orElseThrow(TransactionNotFoundException::new);

        if(StatusEnum.CANCELADO.name().equals(transacaoPagtoOptional.getDescricao().getStatus())){
            throw new ReversalException();
        }

        // Mapear de Transacao para TransacaoDto
        TransacaoDto estornoDto = modelMapper.map(transacaoPagtoOptional, TransacaoDto.class);
        estornoDto.setTransacaoId(transacaoId);

        Descricao descricaoEstorno = transacaoPagtoOptional.getDescricao();
        descricaoEstorno.setDataHora(new Date());
        descricaoEstorno.setStatus(StatusEnum.CANCELADO.name());
        estornoDto.setDescricao(descricaoEstorno);

        FormaPagamento formaPagamentoEstorno = transacaoPagtoOptional.getFormaPagamento();
        estornoDto.setFormaPagamento(formaPagamentoEstorno);

        // Mapear de TransacaoDto para Transacao
        Transacao estorno = modelMapper.map(estornoDto, Transacao.class);

        // Salvar o estorno no banco de dados
        estorno = repository.save(estorno);

        // Retornar o estorno
        return modelMapper.map(estorno, TransacaoDto.class);
    }

}
