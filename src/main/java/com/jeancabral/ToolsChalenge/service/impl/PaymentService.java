package com.jeancabral.ToolsChalenge.service.impl;

import com.jeancabral.ToolsChalenge.dto.PaymentRequest;
import com.jeancabral.ToolsChalenge.dto.TransactionDTO;
import com.jeancabral.ToolsChalenge.exception.BusinessException;
import com.jeancabral.ToolsChalenge.exception.NotFoundException;
import com.jeancabral.ToolsChalenge.exception.PaymentException;
import com.jeancabral.ToolsChalenge.model.Description;
import com.jeancabral.ToolsChalenge.model.TransactionEntity;
import com.jeancabral.ToolsChalenge.repository.TransactionRepository;
import com.jeancabral.ToolsChalenge.service.PaymentServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.jeancabral.ToolsChalenge.util.CollectionUtil.mapTo;

@Service
public class PaymentService implements PaymentServiceInterface {

    private final TransactionRepository repository;

    public PaymentService(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TransactionDTO> findTransactions() {
        
        return mapTo(repository.findAll(), TransactionEntity::toDto);
    }

    @Override
    public TransactionDTO findTransactionById(final Long transactionId) {
        
        return repository.findById(transactionId)
                .map(TransactionEntity::toDto)
                .orElseThrow(() -> new NotFoundException("Transação não encontrada."));
    }

    @Override
    public TransactionDTO createPayment(PaymentRequest paymentRequest) {
        
        
        if(repository.existsByTransacaoId(paymentRequest.transactionId())) {
            throw new PaymentException("Já existe um pagamento para a transação com o ID fornecido.");
        }
        
        final var paymentDescription = Description.from(
                paymentRequest.paymentDescription()
        );

        TransactionDTO newPagamento = TransactionDTO.with(
                paymentRequest.transactionId(),
                paymentRequest.cartNumber(),
                paymentDescription,
                paymentRequest.payment()
        );
        
        return repository.save(
                TransactionEntity.from(newPagamento)
        ).toDto();
    }
 }
 
