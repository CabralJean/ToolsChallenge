package com.jeancabral.ToolsChalenge.service.impl;

import com.jeancabral.ToolsChalenge.dto.TransactionDTO;
import com.jeancabral.ToolsChalenge.enums.StatusEnum;
import com.jeancabral.ToolsChalenge.exception.BusinessException;
import com.jeancabral.ToolsChalenge.exception.NotFoundException;
import com.jeancabral.ToolsChalenge.model.TransactionEntity;
import com.jeancabral.ToolsChalenge.repository.TransactionRepository;
import com.jeancabral.ToolsChalenge.service.ReversalServiceInterface;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class ReversalService implements ReversalServiceInterface {

    private final TransactionRepository repository;

    public ReversalService(
            final TransactionRepository repository
    ) {

        this.repository = repository;
    }

    @Override
    public TransactionDTO buscarEstornoId(final Long transactionId) {
        
        return repository.findCanceladosById(transactionId)
                .map(TransactionEntity::toDto)
                .orElseThrow(notFound("Transação não encontrada com o ID fornecido: " , transactionId));
        
    }

    @Override
    public TransactionDTO reversalPayment(final Long transactionId){
        
        final var transaction = repository.findById(transactionId)
                .map(TransactionEntity::toDto)
                .orElseThrow(notFound("Transação não encontrada com o ID fornecido: ", transactionId));
        
        if(StatusEnum.CANCELADO.name().equals(transaction.descricao().getStatus())){
            throw new BusinessException("Já existe um estorno para a transação com o ID fornecido: "+ transactionId);
        }

        transaction.estornar();

        return repository.save(
                TransactionEntity.from(transaction)
        ).toDto();
        
    }
    
    private Supplier<NotFoundException> notFound(final String message, final Long id) {
        return ()-> new NotFoundException(message + id);
    }

}
