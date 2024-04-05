package com.jeancabral.ToolsChalenge.controller;


import com.jeancabral.ToolsChalenge.dto.PagamentoDto;
import com.jeancabral.ToolsChalenge.dto.TransacaoDto;
import com.jeancabral.ToolsChalenge.model.Descricao;
import com.jeancabral.ToolsChalenge.model.FormaPagamento;
import com.jeancabral.ToolsChalenge.service.impl.PagamentoService;
import com.jeancabral.ToolsChalenge.wrapper.TransacaoWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PagamentoControllerTest {

    @Mock
    private PagamentoService pagamentoService;

    @InjectMocks
    private PagamentoController pagamentoController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void buscaTransacoes_DeveRetornarListaDeTransacoesWrapper() {
        // Arrange
        TransacaoDto transacaoDto = criarTransacaoDto();
        TransacaoWrapper transacaoWrapper = new TransacaoWrapper(transacaoDto);
        when(pagamentoService.buscarPagamentos()).thenReturn(Arrays.asList(transacaoDto));

        // Act
        ResponseEntity<List<TransacaoWrapper>> responseEntity = pagamentoController.buscaTransacoes();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().contains(transacaoWrapper));
    }

    @Test
    public void buscaTransacaoId_DeveRetornarTransacaoWrapper() {
        // Arrange
        Long transacaoId = 1234L;
        TransacaoDto transacaoDto = criarTransacaoDto();
        TransacaoWrapper transacaoWrapper = new TransacaoWrapper(transacaoDto);
        when(pagamentoService.buscarTransacaoId(transacaoId)).thenReturn(Optional.of(transacaoDto));

        // Act
        ResponseEntity<TransacaoWrapper> responseEntity = pagamentoController.buscaTransacaoId(transacaoId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(transacaoWrapper, responseEntity.getBody());
    }

    @Test
    public void pagamento_DeveRetornarTransacaoWrapper() {
        // Arrange
        PagamentoDto pagamentoDto = new PagamentoDto();
        TransacaoDto transacaoDto = criarTransacaoDto();
        TransacaoWrapper transacaoWrapper = new TransacaoWrapper(transacaoDto);
        when(pagamentoService.efetuarPagamento(any(PagamentoDto.class))).thenReturn(transacaoDto);

        // Act
        ResponseEntity<TransacaoWrapper> responseEntity = pagamentoController.pagamento(pagamentoDto);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(transacaoWrapper, responseEntity.getBody());
    }

    private TransacaoDto criarTransacaoDto() {
        TransacaoDto transacaoDto = new TransacaoDto();
        transacaoDto.setTransacaoId(1234L);
        transacaoDto.setNum_cartao("55555555555");
        transacaoDto.setDescricao(criarDescricaoDto());
        transacaoDto.setFormaPagamento(criarFormaPagamentoDto());
        return transacaoDto;
    }

    private Descricao criarDescricaoDto() {
        Descricao descricaoDto = new Descricao();
        LocalDateTime dataHoraLocal = LocalDateTime.parse("2024-04-05T19:22:48.140");
        Date dataHoraDate = Date.from(dataHoraLocal.atZone(ZoneId.systemDefault()).toInstant());

        descricaoDto.setValor(0);
        descricaoDto.setDataHora(dataHoraDate);
        descricaoDto.setEstabelecimento("Teste");
        descricaoDto.setNsu(550754514);
        descricaoDto.setCodigoAutorizacao(48169603);
        descricaoDto.setStatus("AUTORIZADO");
        return descricaoDto;
    }

    private FormaPagamento criarFormaPagamentoDto() {
        FormaPagamento formaPagamentoDto = new FormaPagamento();
        formaPagamentoDto.setTipo("AVISTA");
        formaPagamentoDto.setParcelas(0);
        return formaPagamentoDto;
    }
}
