package com.jeancabral.ToolsChalenge.service.impl;

import com.jeancabral.ToolsChalenge.dto.TransactionDTO;
import com.jeancabral.ToolsChalenge.enums.StatusEnum;
import com.jeancabral.ToolsChalenge.exception.BusinessException;
import com.jeancabral.ToolsChalenge.exception.NotFoundException;
import com.jeancabral.ToolsChalenge.model.TransacaoEntity;
import com.jeancabral.ToolsChalenge.repository.TransactionRepository;
import com.jeancabral.ToolsChalenge.service.EstornoServiceInterface;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class EstornoService implements EstornoServiceInterface {

    private final TransactionRepository repository;

    public EstornoService(
            final TransactionRepository repository
    ) {

        this.repository = repository;
    }

    @Override
    public TransactionDTO buscarEstornoId(final Long transacaoId) {
        
        return repository.findCanceladosById(transacaoId)
                .map(TransacaoEntity::toDto)
                .orElseThrow(notFound("Transação não encontrada com o ID fornecido: " , transacaoId));
        
    }

    @Override
    public TransactionDTO estornarPagamento(final Long transacaoId){
        
        final var transacao = repository.findById(transacaoId)
                .map(TransacaoEntity::toDto)
                .orElseThrow(notFound("Transação não encontrada com o ID fornecido: ", transacaoId));
        
        if(StatusEnum.CANCELADO.name().equals(transacao.descricao().getStatus())){
            throw new BusinessException("Já existe um estorno para a transação com o ID fornecido.");
        }
        
        transacao.estornar();

        return repository.save(
                TransacaoEntity.from(transacao)
        ).toDto();
        
    }
    
    private Supplier<NotFoundException> notFound(final String message, final Long id) {
        return ()-> new NotFoundException(message + id);
    }

}
