package com.jeancabral.ToolsChalenge.controller;


import com.jeancabral.ToolsChalenge.ControllerTest;
import com.jeancabral.ToolsChalenge.dto.PaymentRequest;
import com.jeancabral.ToolsChalenge.dto.TransactionDTO;
import com.jeancabral.ToolsChalenge.enums.StatusEnum;
import com.jeancabral.ToolsChalenge.enums.TypePagEnum;
import com.jeancabral.ToolsChalenge.model.Description;
import com.jeancabral.ToolsChalenge.model.PaymentDescription;
import com.jeancabral.ToolsChalenge.model.PaymentMethod;
import com.jeancabral.ToolsChalenge.service.impl.PaymentService;
import com.jeancabral.ToolsChalenge.util.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
public class PaymentControllerTest {
    
    private final static String BASE_URL = "/api/v1/transaction";
    
    @Autowired
    private MockMvc mvc;
    
    @MockBean
    private PaymentService service;

    
    @Test
    void testListTransactions() throws Exception {
        
        final var expectedTransactionId = 1234L;
        final var expectedCartNumber = "1203*****6060";
        final var expectedDate = new Date();
        final var expectedDescriptionValue = 20.00;
        final var expectedDescriptionEstablishment = "LOJA 01";
        final var expectedDescriptionNsu = "1234567890";
        final var expectedDescriptionAuthorization = "123456789";
        final var expectedDescriptionStatus = StatusEnum.AUTORIZADO.name();
        
        final var expectedPaymentInstallments = 1;
        final var expectedPaymentType = TypePagEnum.AVISTA;
        
        final var expectedPaymentsResponse = List.of(
                TransactionDTO.with(
                        expectedTransactionId,
                        expectedCartNumber,
                        Description.with(
                                expectedDescriptionValue,
                                expectedDate,
                                expectedDescriptionEstablishment,
                                expectedDescriptionNsu,
                                expectedDescriptionAuthorization,
                                expectedDescriptionStatus
                        ),
                        PaymentMethod.with(
                                expectedPaymentType.name(),
                                expectedPaymentInstallments
                        )
                )
        );
        
        when(service.findTransactions())
                .thenReturn(expectedPaymentsResponse);
        
        final var request = get(BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        
        final var response = this.mvc.perform(request)
                .andDo(print());
        
        final var formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        
        final var expectedDateHour = formatter.format(expectedDate);
        
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.transacoes", hasSize(1)))
                .andExpect(jsonPath("$.transacoes[0].id", equalTo(String.valueOf(expectedTransactionId))))
                .andExpect(jsonPath("$.transacoes[0].cartao", equalTo(expectedCartNumber)))
                .andExpect(jsonPath("$.transacoes[0].descricao.valor", equalTo(expectedDescriptionValue)))
                .andExpect(jsonPath("$.transacoes[0].descricao.dataHora", equalTo(expectedDateHour)))
                .andExpect(jsonPath(
                        "$.transacoes[0].descricao.estabelecimento",
                        equalTo(expectedDescriptionEstablishment)
                ))
                .andExpect(jsonPath("$.transacoes[0].descricao.nsu", equalTo(expectedDescriptionNsu)))
                .andExpect(jsonPath(
                        "$.transacoes[0].descricao.codigoAutorizacao",
                        equalTo(expectedDescriptionAuthorization)
                ))
                .andExpect(jsonPath("$.transacoes[0].descricao.status", equalTo(expectedDescriptionStatus)))
                .andExpect(jsonPath("$.transacoes[0].formaPagamento.tipo", equalTo(expectedPaymentType.name())))
                .andExpect(jsonPath("$.transacoes[0].formaPagamento.parcelas", equalTo(expectedPaymentInstallments)));
    }
    
    @Test
    void testPayment() throws Exception {
        
        final var expectedTransactionId = 1234L;
        final var expectedCartNumber = "1203*****6060";
        final var expectedDate = new Date();
        final var expectedDescriptionValue = 20.00;
        final var expectedDescriptionEstablishment = "LOJA 01";
        final var expectedDescriptionNsu = "1234567890";
        final var expectedDescriptionAuthorization = "123456789";
        final var expectedDescriptionStatus = StatusEnum.AUTORIZADO.name();
        
        final var expectedPaymentInstallments = 1;
        final var expectedPaymentType = TypePagEnum.AVISTA;
        
        final var expectedPaymentsResponse = TransactionDTO.with(
                        expectedTransactionId,
                        expectedCartNumber,
                        Description.with(
                                expectedDescriptionValue,
                                expectedDate,
                                expectedDescriptionEstablishment,
                                expectedDescriptionNsu,
                                expectedDescriptionAuthorization,
                                expectedDescriptionStatus
                        ),
                        PaymentMethod.with(
                                expectedPaymentType.name(),
                                expectedPaymentInstallments
                        )
        );
        
        final var expectedPayment = PaymentMethod.with(
                expectedPaymentType.name(),
                expectedPaymentInstallments
        );
        
        final var expectedDescriptionPayment = PaymentDescription.with(
                expectedDescriptionValue,
                expectedDate,
                expectedDescriptionEstablishment
        );
        
        final var expectedRequest = PaymentRequest.with(
                expectedTransactionId,
                expectedCartNumber,
                expectedDescriptionPayment,
                expectedPayment
        );
        
        when(service.createPayment(any()))
                .thenReturn(expectedPaymentsResponse);
        
        final var request = post(BASE_URL+"/payment")
                .content(JsonUtils.json(expectedRequest))
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
    void testFindTransactionById() throws Exception {

        final var expectedTransactionId = 1234L;
        final var expectedCartNumber = "1203*****6060";
        final var expectedDate = new Date();
        final var expectedDescriptionValue = 20.00;
        final var expectedDescriptionEstablishment = "LOJA 01";
        final var expectedDescriptionNsu = "1234567890";
        final var expectedDescriptionAuthorization = "123456789";
        final var expectedDescriptionStatus = StatusEnum.AUTORIZADO.name();

        final var expectedPaymentInstallments = 1;
        final var expectedPaymentType = TypePagEnum.AVISTA;

        final var expectedPaymentsResponse = TransactionDTO.with(
                expectedTransactionId,
                expectedCartNumber,
                Description.with(
                        expectedDescriptionValue,
                        expectedDate,
                        expectedDescriptionEstablishment,
                        expectedDescriptionNsu,
                        expectedDescriptionAuthorization,
                        expectedDescriptionStatus
                ),
                PaymentMethod.with(
                        expectedPaymentType.name(),
                        expectedPaymentInstallments
                )

        );

        when(service.findTransactionById(any())).thenReturn(expectedPaymentsResponse);

        final var request = get(BASE_URL + "/{transactionId}", 1234L)
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
