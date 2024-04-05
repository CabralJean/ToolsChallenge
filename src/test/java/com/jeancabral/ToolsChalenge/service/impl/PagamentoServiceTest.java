package com.jeancabral.ToolsChalenge.service.impl;

import com.jeancabral.ToolsChalenge.dto.PagamentoDto;
import com.jeancabral.ToolsChalenge.dto.TransacaoDto;
import com.jeancabral.ToolsChalenge.enums.StatusEnum;
import com.jeancabral.ToolsChalenge.model.Descricao;
import com.jeancabral.ToolsChalenge.model.DescricaoPagamento;
import com.jeancabral.ToolsChalenge.model.FormaPagamento;
import com.jeancabral.ToolsChalenge.model.Transacao;
import com.jeancabral.ToolsChalenge.repository.PagamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PagamentoServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PagamentoRepository repository;

    @Autowired
    @InjectMocks
    private PagamentoService pagamentoService;

    @BeforeEach
    void setup(){}

    @Test
    void buscarPagamentos() {
    }

    @Test
    void buscarTransacaoId() {
    }

    /*@Test
    void efetuarPagamento() {
        // Arrange
        Long transacaoId = 1234L;
        PagamentoDto pagamentoDto = new PagamentoDto();
        pagamentoDto.setTransacaoId(transacaoId);
        pagamentoDto.setNum_cartao("55555555555");

        DescricaoPagamento descricao = new DescricaoPagamento();
        descricao.setValor(0);
        descricao.setDataHora(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        descricao.setEstabelecimento("string");
        pagamentoDto.setDescricao(descricao);

        FormaPagamento formaPagamento = new FormaPagamento();
        formaPagamento.setTipo("string");
        formaPagamento.setParcelas(0);
        pagamentoDto.setFormaPagamento(formaPagamento);

// Arrange
        TransacaoDto transacao = new TransacaoDto();
        when(repository.findById(transacaoId)).thenReturn(Optional.empty());
        when(repository.save(any(TransacaoDto.class))).thenAnswer(invocation -> {
            Transacao argument = invocation.getArgument(0);
            argument.setTransacaoId(123L); // Mocking the saved transaction ID
            return argument;
        });

// Act
        TransacaoDto result = pagamentoService.efetuarPagamento(pagamentoDto);

// Assert
        assertNotNull(result);
        assertEquals(StatusEnum.AUTORIZADO.name(), result.getDescricao().getStatus());
        verify(repository).findById(transacaoId);
        verify(repository).save(any(TransacaoDto.class));
    }*/
}