package com.jeancabral.ToolsChalenge.service.impl;

import org.junit.jupiter.api.Test;

import com.jeancabral.ToolsChalenge.dto.TransacaoDto;
import com.jeancabral.ToolsChalenge.enums.StatusEnum;
import com.jeancabral.ToolsChalenge.exception.ReversalException;
import com.jeancabral.ToolsChalenge.exception.ReversalNotFoundException;
import com.jeancabral.ToolsChalenge.exception.TransactionNotFoundException;
import com.jeancabral.ToolsChalenge.model.Descricao;
import com.jeancabral.ToolsChalenge.model.FormaPagamento;
import com.jeancabral.ToolsChalenge.model.Transacao;
import com.jeancabral.ToolsChalenge.repository.EstornoRepository;
import com.jeancabral.ToolsChalenge.service.impl.EstornoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EstornoServiceTest {

    @Mock
    private EstornoRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EstornoService service;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void buscarEstornoId_TransacaoNaoEncontrada_DeveLancarExcecao() {
        // Arrange
        Long transacaoId = 1L;
        when(repository.findById(transacaoId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ReversalNotFoundException.class, () -> {
            service.buscarEstornoId(transacaoId);
        });
    }

    @Test
    void buscarEstornoId_TransacaoCancelada_RetornaTransacaoDto() {
        // Arrange
        Long transacaoId = 1L;
        Transacao transacao = new Transacao();
        transacao.setTransacaoId(transacaoId);
        Descricao descricao = new Descricao();
        descricao.setStatus(StatusEnum.CANCELADO.name());
        transacao.setDescricao(descricao);
        when(repository.findById(transacaoId)).thenReturn(Optional.of(transacao));

        // Act
        Optional<TransacaoDto> result = service.buscarEstornoId(transacaoId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(transacaoId, result.get().getTransacaoId());
    }

    @Test
    void estornarPagamento_TransacaoNaoEncontrada_DeveLancarExcecao() {
        // Arrange
        Long transacaoId = 1L;
        when(repository.findById(transacaoId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(TransactionNotFoundException.class, () -> {
            service.estornarPagamento(transacaoId);
        });
    }

    @Test
    void estornarPagamento_TransacaoCancelada_DeveLancarExcecao() {
        // Arrange
        Long transacaoId = 1L;
        Transacao transacao = new Transacao();
        transacao.setTransacaoId(transacaoId);
        Descricao descricao = new Descricao();
        descricao.setStatus(StatusEnum.CANCELADO.name());
        transacao.setDescricao(descricao);
        when(repository.findById(transacaoId)).thenReturn(Optional.of(transacao));

        // Act & Assert
        assertThrows(ReversalException.class, () -> {
            service.estornarPagamento(transacaoId);
        });
    }

    @Test
    void estornarPagamento_TransacaoValida_RetornaTransacaoDto() {
        // Arrange
        Long transacaoId = 1L;
        Transacao transacao = new Transacao();
        transacao.setTransacaoId(transacaoId);
        Descricao descricao = new Descricao();
        descricao.setStatus(StatusEnum.AUTORIZADO.name());
        descricao.setDataHora(new Date());
        transacao.setDescricao(descricao);

        // Mock do método save para retornar a transacao salva
        when(repository.save(any(Transacao.class))).thenAnswer(invocation -> {
            Transacao savedTransacao = invocation.getArgument(0);
            savedTransacao.setTransacaoId(123L); // Mocking the saved transaction ID
            return savedTransacao;
        });

        // Mock do findById para retornar a transacao criada
        when(repository.findById(transacaoId)).thenReturn(Optional.of(transacao));

        // Mock do mapeamento de Transacao para TransacaoDto
        TransacaoDto transacaoDtoMock = new TransacaoDto();
        TransacaoDto transacaoMock = new TransacaoDto();
        transacaoDtoMock.setTransacaoId(transacaoMock.getTransacaoId());
        when(modelMapper.map(transacao, TransacaoDto.class)).thenReturn(transacaoDtoMock);

        // Act
        TransacaoDto result = service.estornarPagamento(transacaoId);

        // Assert
        assertNotNull(result);
        assertEquals(123L, result.getTransacaoId()); // Verifique se o ID foi alterado para o ID mockado
        //assertEquals(StatusEnum.CANCELADO.name(), result.getTransacaoId().getDescricao().getStatus());
    }





}
