package com.jeancabral.ToolsChalenge.service.impl;

import com.jeancabral.ToolsChalenge.dto.PaymentRequest;
import com.jeancabral.ToolsChalenge.dto.TransactionDTO;
import com.jeancabral.ToolsChalenge.exception.BusinessException;
import com.jeancabral.ToolsChalenge.exception.NotFoundException;
import com.jeancabral.ToolsChalenge.model.Descricao;
import com.jeancabral.ToolsChalenge.model.TransacaoEntity;
import com.jeancabral.ToolsChalenge.repository.TransactionRepository;
import com.jeancabral.ToolsChalenge.service.PagamentoServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.jeancabral.ToolsChalenge.util.CollectionUtil.mapTo;

@Service
public class PagamentoService implements PagamentoServiceInterface {

    private final TransactionRepository repository;

    public PagamentoService(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TransactionDTO> buscarPagamentos() {
        
        return mapTo(repository.findAll(), TransacaoEntity::toDto);
    }

    @Override
    public TransactionDTO buscarTransacaoId(Long transacaoId) {
        
        return repository.findById(transacaoId)
                .map(TransacaoEntity::toDto)
                .orElseThrow(() -> new NotFoundException("Transação não encontrada."));
    }

    @Override
    public TransactionDTO efetuarPagamento(PaymentRequest paymentRequest) {
        
        
        if(repository.existsByTransacaoId(paymentRequest.transactionId())) {
            throw new BusinessException("Já existe um pagamento para a transação com o ID fornecido.");
        }
        
        final var descricaoPagamento = Descricao.from(
                paymentRequest.paymentDescription()
        );

        TransactionDTO newPagamento = TransactionDTO.with(
                paymentRequest.transactionId(),
                paymentRequest.cartNumber(),
                descricaoPagamento,
                paymentRequest.payment()
        );
        
        return repository.save(
                TransacaoEntity.from(newPagamento)
        ).toDto();
    }
 }
 
