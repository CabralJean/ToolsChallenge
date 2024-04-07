package com.jeancabral.ToolsChalenge.service.impl;

import com.jeancabral.ToolsChalenge.UnitTest;
import com.jeancabral.ToolsChalenge.dto.PaymentRequest;
import com.jeancabral.ToolsChalenge.dto.TransactionDTO;
import com.jeancabral.ToolsChalenge.enums.StatusEnum;
import com.jeancabral.ToolsChalenge.enums.TipoPagEnum;
import com.jeancabral.ToolsChalenge.exception.BusinessException;
import com.jeancabral.ToolsChalenge.exception.NotFoundException;
import com.jeancabral.ToolsChalenge.model.Descricao;
import com.jeancabral.ToolsChalenge.model.DescricaoPagamento;
import com.jeancabral.ToolsChalenge.model.FormaPagamento;
import com.jeancabral.ToolsChalenge.model.TransacaoEntity;
import com.jeancabral.ToolsChalenge.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.jeancabral.ToolsChalenge.util.CollectionUtil.mapTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PagamentoServiceTest  extends UnitTest {

    @Mock
    private TransactionRepository repository;
    
    @InjectMocks
    private PagamentoService pagamentoService;

    @BeforeEach
    void setup(){}

    @Test
    @DisplayName("Deve listar todos os pagamentos")
    void buscarPagamentos() {
        
        final var expectedTransactionId = 1234L;
        final var expectedCartNumber = "12039494950506060";
        final var expectedDate = new Date();
        final var expectedDescriptionValue = 20.00;
        final var expectedDescriptionEstablishment = "LOJA 01";
        final var expectedDescriptionNsu = "1234567890";
        final var expectedDescriptionAuthorization = "123456789";
        final var expectedDescriptionStatus = StatusEnum.AUTORIZADO.name();
        
        final var expectedPaymentInstallments = 1;
        final var expectedPaymentType = TipoPagEnum.AVISTA;
        
        final var expectedPaymentsResponse = List.of(
                TransactionDTO.with(
                        expectedTransactionId,
                        expectedCartNumber,
                        Descricao.with(
                                expectedDescriptionValue,
                                expectedDate,
                                expectedDescriptionEstablishment,
                                expectedDescriptionNsu,
                                expectedDescriptionAuthorization,
                                expectedDescriptionStatus
                        ),
                        FormaPagamento.with(
                        expectedPaymentType.name(),
                        expectedPaymentInstallments
                        )
                )
        );
        
        final var expectedPayments = mapTo(
                expectedPaymentsResponse,
                TransacaoEntity::from
        );
        
        when(repository.findAll())
                .thenReturn(expectedPayments);
        
        final var actual = pagamentoService.buscarPagamentos();
        
        verify(repository).findAll();
        assertEquals(expectedPayments.size(), actual.size());
        assertEquals(expectedPaymentsResponse, actual);
    }

    @Test
    @DisplayName("Deve listar todos os pagamentos a partir de um id")
    void buscarTransacaoId() {
        
        final var expectedTransactionId = 13345L;
        
        final var expectedDate = new Date();
        final var expectedDescriptionValue = 20.00;
        final var expectedDescriptionEstablishment = "LOJA 01";
        final var expectedDescriptionNsu = "1234567890";
        final var expectedDescriptionAuthorization = "123456789";
        final var expectedDescriptionStatus = StatusEnum.AUTORIZADO.name();
        
        final var expectedPaymentInstallments = 1;
        final var expectedPaymentType = TipoPagEnum.AVISTA;
        
        final var expectedDTO = TransactionDTO.with(
                1234L,
                "NUM_CARTAO",
                Descricao.with(
                        expectedDescriptionValue,
                        expectedDate,
                        expectedDescriptionEstablishment,
                        expectedDescriptionNsu,
                        expectedDescriptionAuthorization,
                        expectedDescriptionStatus
                ),
                FormaPagamento.with(
                        expectedPaymentType.name(),
                        expectedPaymentInstallments
                )
        );
        
        final var expectedTransactionEntity = TransacaoEntity.from(expectedDTO);
        
        when(repository.findById(any()))
                .thenReturn(Optional.of(expectedTransactionEntity));
        
        final var actual = pagamentoService.buscarTransacaoId(expectedTransactionId);
        
        verify(repository).findById(expectedTransactionId);
        assertEquals(expectedTransactionEntity.getTransacaoId(), actual.transacaoId());
    }
    
    @Test
    void transactionNotfound() {
    
        final var expectedNotFoundTransactionId = 1343466565L;
        
        when(repository.findById(any()))
                .thenReturn(Optional.empty());
    
        final var actual = assertThrows(
                NotFoundException.class,
                () -> pagamentoService.buscarTransacaoId(expectedNotFoundTransactionId)
        );
        
        final var expectedErrorMessage = "Transação não encontrada.";
        
        assertEquals(expectedErrorMessage, actual.getMessage());
    }

    @Test
    @DisplayName("Deve efetuar o pagamento e retornar os dados do mesmo")
    void efetuarPagamento() {
        
        final var expectedDate = new Date();
        final var expectedDescriptionValue = 20.00;
        final var expectedDescriptionEstablishment = "LOJA 01";
        
        final var expectedTransactionId = 1234L;
        final var expectedCartNumber = "1223435344454545";
        final var expectedDescriptionPayment = DescricaoPagamento.with(
                expectedDescriptionValue,
                expectedDate,
                expectedDescriptionEstablishment
        );
        
        final var expectedPaymentInstallments = 1;
        final var expectedPaymentType = TipoPagEnum.AVISTA;
        final var expectedPayment = FormaPagamento.with(
                expectedPaymentType.name(),
                expectedPaymentInstallments
        );
        
        
        final var expectedRequest = PaymentRequest.with(
                expectedTransactionId,
                expectedCartNumber,
                expectedDescriptionPayment,
                expectedPayment
        );
        
        final var expectedTransaction = TransactionDTO.with(
                expectedRequest.transactionId(),
                expectedRequest.cartNumber(),
                Descricao.from(expectedDescriptionPayment),
                expectedPayment
        );
        
        final var expectedEntity = TransacaoEntity.from(expectedTransaction);
        
        when(repository.existsByTransacaoId(any()))
                .thenReturn(Boolean.FALSE);
        
        when(repository.save(any()))
                .thenReturn(expectedEntity);
        
        final var actual = pagamentoService.efetuarPagamento(expectedRequest);
        
        assertNotNull(actual);
        assertNotNull(actual.descricao().getNsu());
        assertNotNull(actual.descricao().getCodigoAutorizacao());
        assertEquals(StatusEnum.AUTORIZADO.name(), actual.descricao().getStatus());
    }
    
    @Test
    @DisplayName("Deve lançar BusinessException quando pagamento ja foi efetuado")
    void testAlreadyTake() {
        
        final var expectedDate = new Date();
        final var expectedDescriptionValue = 20.00;
        final var expectedDescriptionEstablishment = "LOJA 01";
        
        final var expectedTransactionId = 1234L;
        final var expectedCartNumber = "1223435344454545";
        final var expectedDescriptionPayment = DescricaoPagamento.with(
                expectedDescriptionValue,
                expectedDate,
                expectedDescriptionEstablishment
        );
        
        final var expectedPaymentInstallments = 1;
        final var expectedPaymentType = TipoPagEnum.AVISTA;
        final var expectedPayment = FormaPagamento.with(
                expectedPaymentType.name(),
                expectedPaymentInstallments
        );
        
        final var expectedRequest = PaymentRequest.with(
                expectedTransactionId,
                expectedCartNumber,
                expectedDescriptionPayment,
                expectedPayment
        );
        
        
        when(repository.existsByTransacaoId(any()))
                .thenReturn(Boolean.TRUE);
        
        final var actual = assertThrows(
                BusinessException.class,
                () -> pagamentoService.efetuarPagamento(expectedRequest)
        );
        
        final var expectedErrorMessage = "Já existe um pagamento para a transação com o ID fornecido.";
        
        assertEquals(expectedErrorMessage, actual.getMessage());
        
    }
    
}