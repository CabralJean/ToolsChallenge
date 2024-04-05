package com.jeancabral.ToolsChalenge.controller;


import com.jeancabral.ToolsChalenge.dto.TransacaoDto;
import com.jeancabral.ToolsChalenge.service.impl.EstornoService;
import com.jeancabral.ToolsChalenge.wrapper.TransacaoWrapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class EstornoControllerTest {

    @Mock
    private EstornoService estornoService;

    @InjectMocks
    private EstornoController estornoController;

    public EstornoControllerTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void buscaEstornoId_DeveRetornarTransacaoWrapper() {
        // Arrange
        Long transacaoId = 123L;
        TransacaoDto transacaoDto = criarTransacaoDto();
        TransacaoWrapper transacaoWrapper = new TransacaoWrapper(transacaoDto);
        when(estornoService.buscarEstornoId(transacaoId)).thenReturn(Optional.of(transacaoDto));

        // Act
        ResponseEntity<TransacaoWrapper> responseEntity = estornoController.buscaEstornoId(transacaoId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(transacaoWrapper, responseEntity.getBody());
    }

    @Test
    void estornaPagamento_DeveRetornarTransacaoWrapper() {
        // Arrange
        Long transacaoId = 123L;
        TransacaoDto transacaoDto = criarTransacaoDto();
        TransacaoWrapper transacaoWrapper = new TransacaoWrapper(transacaoDto);
        when(estornoService.estornarPagamento(transacaoId)).thenReturn(transacaoDto);

        // Act
        ResponseEntity<TransacaoWrapper> responseEntity = estornoController.estornaPagamento(transacaoId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(transacaoWrapper, responseEntity.getBody());
    }

    private TransacaoDto criarTransacaoDto() {
        TransacaoDto transacaoDto = new TransacaoDto();
        transacaoDto.setTransacaoId(123L);

        return transacaoDto;
    }
}
