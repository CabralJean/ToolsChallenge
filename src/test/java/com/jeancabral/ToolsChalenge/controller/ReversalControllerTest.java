package com.jeancabral.ToolsChalenge.controller;

import com.jeancabral.ToolsChalenge.ControllerTest;
import com.jeancabral.ToolsChalenge.dto.TransactionDTO;
import com.jeancabral.ToolsChalenge.enums.StatusEnum;
import com.jeancabral.ToolsChalenge.enums.TipoPagEnum;
import com.jeancabral.ToolsChalenge.model.Descricao;
import com.jeancabral.ToolsChalenge.model.FormaPagamento;
import com.jeancabral.ToolsChalenge.service.impl.EstornoService;
import com.jeancabral.ToolsChalenge.util.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
public class ReversalControllerTest {

    private final static String BASE_URL = "/api/estorno";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EstornoService service;


    @Test
    void testFindReversalById() throws Exception {

        final var expectedTransactionId = 1234L;
        final var expectedCartNumber = "12039494950506060";
        final var expectedDate = new Date();
        final var expectedDescriptionValue = 20.00;
        final var expectedDescriptionEstablishment = "LOJA 01";
        final var expectedDescriptionNsu = "1234567890";
        final var expectedDescriptionAuthorization = "123456789";
        final var expectedDescriptionStatus = StatusEnum.CANCELADO.name();

        final var expectedPaymentInstallments = 1;
        final var expectedPaymentType = TipoPagEnum.AVISTA;

        final var expectedReversalResponse = TransactionDTO.with(
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

        );

        when(service.buscarEstornoId(any())).thenReturn(expectedReversalResponse);

        final var request = get(BASE_URL + "/{transacaoId}", 1234L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        final var response = this.mvc.perform(request)
                .andDo(print());

        final var formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        final var expectedDateHour = formatter.format(expectedDate);

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.transacao.id", equalTo(String.valueOf(expectedTransactionId))))
                .andExpect(jsonPath("$.transacao.cartao", equalTo(expectedCartNumber)))
                .andExpect(jsonPath("$.transacao.descricao.valor", equalTo(expectedDescriptionValue)))
                .andExpect(jsonPath("$.transacao.descricao.dataHora", equalTo(expectedDateHour)))
                .andExpect(jsonPath(
                        "$.transacao.descricao.estabelecimento",
                        equalTo(expectedDescriptionEstablishment)
                ))
                .andExpect(jsonPath("$.transacao.descricao.nsu", equalTo(expectedDescriptionNsu)))
                .andExpect(jsonPath(
                        "$.transacao.descricao.codigoAutorizacao",
                        equalTo(expectedDescriptionAuthorization)
                ))
                .andExpect(jsonPath("$.transacao.descricao.status", equalTo(expectedDescriptionStatus)))
                .andExpect(jsonPath("$.transacao.formaPagamento.tipo", equalTo(expectedPaymentType.name())))
                .andExpect(jsonPath("$.transacao.formaPagamento.parcelas", equalTo(expectedPaymentInstallments)));

    }

    @Test
    void testReversal() throws Exception {

        final var expectedTransactionId = 1234L;
        final var expectedCartNumber = "12039494950506060";
        final var expectedDate = new Date();
        final var expectedDescriptionValue = 20.00;
        final var expectedDescriptionEstablishment = "LOJA 01";
        final var expectedDescriptionNsu = "1234567890";
        final var expectedDescriptionAuthorization = "123456789";
        final var expectedDescriptionStatus = StatusEnum.CANCELADO.name();

        final var expectedPaymentInstallments = 1;
        final var expectedPaymentType = TipoPagEnum.AVISTA;

        final var expectedReversalResponse = TransactionDTO.with(
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
        );

        when(service.estornarPagamento(any())).thenReturn(expectedReversalResponse);

        final var request = post(BASE_URL+"/{transacaoId}", 1234L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        final var response = this.mvc.perform(request)
                .andDo(print());

        final var formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        final var expectedDateHour = formatter.format(expectedDate);

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.transacao.id", equalTo(String.valueOf(expectedTransactionId))))
                .andExpect(jsonPath("$.transacao.cartao", equalTo(expectedCartNumber)))
                .andExpect(jsonPath("$.transacao.descricao.valor", equalTo(expectedDescriptionValue)))
                .andExpect(jsonPath("$.transacao.descricao.dataHora", equalTo(expectedDateHour)))
                .andExpect(jsonPath(
                        "$.transacao.descricao.estabelecimento",
                        equalTo(expectedDescriptionEstablishment)
                ))
                .andExpect(jsonPath("$.transacao.descricao.nsu", equalTo(expectedDescriptionNsu)))
                .andExpect(jsonPath(
                        "$.transacao.descricao.codigoAutorizacao",
                        equalTo(expectedDescriptionAuthorization)
                ))
                .andExpect(jsonPath("$.transacao.descricao.status", equalTo(expectedDescriptionStatus)))
                .andExpect(jsonPath("$.transacao.formaPagamento.tipo", equalTo(expectedPaymentType.name())))
                .andExpect(jsonPath("$.transacao.formaPagamento.parcelas", equalTo(expectedPaymentInstallments)));

    }
}