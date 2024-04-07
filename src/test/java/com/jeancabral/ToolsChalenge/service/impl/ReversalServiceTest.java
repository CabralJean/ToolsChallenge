package com.jeancabral.ToolsChalenge.service.impl;

import com.jeancabral.ToolsChalenge.UnitTest;
import com.jeancabral.ToolsChalenge.dto.TransactionDTO;
import com.jeancabral.ToolsChalenge.enums.StatusEnum;
import com.jeancabral.ToolsChalenge.enums.TypePagEnum;
import com.jeancabral.ToolsChalenge.exception.BusinessException;
import com.jeancabral.ToolsChalenge.exception.NotFoundException;
import com.jeancabral.ToolsChalenge.model.Description;
import com.jeancabral.ToolsChalenge.model.PaymentMethod;
import com.jeancabral.ToolsChalenge.model.TransactionEntity;
import com.jeancabral.ToolsChalenge.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ReversalServiceTest extends UnitTest {

    @Mock
    private TransactionRepository repository;

    @InjectMocks
    private ReversalService estornoService;

    @BeforeEach
    void setup(){}

    @Test
    @DisplayName("Deve listar o estorno por ID")
    void findReversalById(){
        final var expectedTransactionId = 1234L;

        final var expectedDate = new Date();
        final var expectedDescriptionValue = 20.00;
        final var expectedDescriptionEstablishment = "LOJA 01";
        final var expectedDescriptionNsu = "1234567890";
        final var expectedDescriptionAuthorization = "123456789";
        final var expectedDescriptionStatus = StatusEnum.CANCELADO.name();

        final var expectedPaymentInstallments = 1;
        final var expectedPaymentType = TypePagEnum.AVISTA.name();

        final var expectedDto = TransactionDTO.with(
                1234L,
                "NUM_CARTAO",
                Description.with(
                        expectedDescriptionValue,
                        expectedDate,
                        expectedDescriptionEstablishment,
                        expectedDescriptionNsu,
                        expectedDescriptionAuthorization,
                        expectedDescriptionStatus
                ),
                PaymentMethod.with(
                        expectedPaymentType,
                        expectedPaymentInstallments
                )
        );

        final var expectedTransactionEntity = TransactionEntity.from(expectedDto);

        when(repository.findCanceladosById(any()))
                .thenReturn(Optional.of(expectedTransactionEntity));

        final var actual = estornoService.buscarEstornoId(expectedTransactionId);

        verify(repository).findCanceladosById(expectedTransactionId);
        assertEquals(expectedTransactionEntity.getTransacaoId(), actual.transacaoId());

    }

    @Test
    @DisplayName("Deve retornar NotFoundException quando buscado id inexistente")
    void transactionNotfound() {

        final var expectedNotFoundTransactionId = 1343466565L;

        when(repository.findCanceladosById(any()))
                .thenReturn(Optional.empty());

        final var actual = assertThrows(
                NotFoundException.class,
                () -> estornoService.buscarEstornoId(expectedNotFoundTransactionId)
        );

        final var expectedErrorMessage = "Transação não encontrada com o ID fornecido: " + expectedNotFoundTransactionId;

        assertEquals(expectedErrorMessage,actual.getMessage());
    }

    @Test
    @DisplayName("Deve efetuar o estorno e retornar os dados do mesmo")
    void estornarPagamento() {

        final var expectedDescriptionValue = 20.00;
        final var expectedDescriptionEstablishment = "LOJA 01";
        final var expectedDescriptionNsu = "1234567890";
        final var expectedDescriptionAuthorization = "123456789";

        final var expectedPaymentInstallments = 1;
        final var expectedPaymentType = TypePagEnum.AVISTA.name();

        final var expectedTransactionId = 1234L;
        final var expectedDate = new Date();
        final var expectedDescriptionStatus = StatusEnum.AUTORIZADO.name();

        final var expectedDto = TransactionDTO.with(
                1234L,
                "NUM_CARTAO",
                Description.with(
                        expectedDescriptionValue,
                        expectedDate,
                        expectedDescriptionEstablishment,
                        expectedDescriptionNsu,
                        expectedDescriptionAuthorization,
                        expectedDescriptionStatus
                ),
                PaymentMethod.with(
                        expectedPaymentType,
                        expectedPaymentInstallments
                )
        );

        final var expectedTransactionEntity = TransactionEntity.from(expectedDto);

        when(repository.findById(any()))
                .thenReturn(Optional.of(expectedTransactionEntity));

        when(repository.save(any()))
                .thenReturn(expectedTransactionEntity);

        final var actual = estornoService.reversalPayment(expectedTransactionId);

        assertNotNull(actual);
        assertEquals(StatusEnum.CANCELADO.name(), actual.descricao().getStatus());

    }

    @Test
    @DisplayName("Deve retornar BusinessException para estorno duplicado")
    void estornoJaExecutado() {

        final var expectedDescriptionValue = 20.00;
        final var expectedDescriptionEstablishment = "LOJA 01";
        final var expectedDescriptionNsu = "1234567890";
        final var expectedDescriptionAuthorization = "123456789";

        final var expectedPaymentInstallments = 1;
        final var expectedPaymentType = TypePagEnum.AVISTA.name();

        final var expectedTransactionId = 1234L;
        final var expectedDate = new Date();
        final var expectedDescriptionStatus = StatusEnum.CANCELADO.name();

        final var expectedDto = TransactionDTO.with(
                1234L,
                "NUM_CARTAO",
                Description.with(
                        expectedDescriptionValue,
                        expectedDate,
                        expectedDescriptionEstablishment,
                        expectedDescriptionNsu,
                        expectedDescriptionAuthorization,
                        expectedDescriptionStatus
                ),
                PaymentMethod.with(
                        expectedPaymentType,
                        expectedPaymentInstallments
                )
        );

        final var expectedTransactionEntity = TransactionEntity.from(expectedDto);

        when(repository.findById(any()))
                .thenReturn(Optional.of(expectedTransactionEntity));

        final var actual = assertThrows(
                BusinessException.class,
                () -> estornoService.reversalPayment(expectedTransactionId)
        );

        final var expectedErrorMessage = "Já existe um estorno para a transação com o ID fornecido: "+expectedTransactionId;

        assertEquals(expectedErrorMessage, actual.getMessage());

    }

    @Test
    @DisplayName("Deve retornar NotFoundException quando buscado id inexistente")
    void reversalTransactionNotfound() {

        final var expectedNotFoundTransactionId = 1343466565L;

        when(repository.findById(any()))
                .thenReturn(Optional.empty());

        final var actual = assertThrows(
                NotFoundException.class,
                () -> estornoService.reversalPayment(expectedNotFoundTransactionId)
        );

        final var expectedErrorMessage = "Transação não encontrada com o ID fornecido: " + expectedNotFoundTransactionId;

        assertEquals(expectedErrorMessage,actual.getMessage());
    }

}
