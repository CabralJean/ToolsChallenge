package com.jeancabral.ToolsChalenge.model;

import com.jeancabral.ToolsChalenge.enums.StatusEnum;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Random;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Description {

    private double valor;
    private Date dataHora;
    private String estabelecimento;
    private String nsu;
    private String codigoAutorizacao;
    private String status;
    
    public static Description with(
            final double valor,
            final Date dataHora,
            final String estabelecimento,
            final String nsu,
            final String codigoAutorizacao,
            final String status
    ) {
        
        return new Description(
                valor,
                dataHora,
                estabelecimento,
                nsu,
                codigoAutorizacao,
                status
        );
    }
    
    
    public static Description from(
            final PaymentDescription descricaoPagamento
    ) {
        
        Random random = new Random();
        
        final var nsu = String.valueOf(random.nextInt(999999999));
        final var codigoAutorizacao = String.valueOf(random.nextInt(999999999));
        final var status = StatusEnum.AUTORIZADO.name();
        
        return with(
                descricaoPagamento.valor(),
                descricaoPagamento.dataHora(),
                descricaoPagamento.estabelecimento(),
                nsu,
                codigoAutorizacao,
                status
        );
        
    }
    
    public Description estornar() {
        this.status = StatusEnum.CANCELADO.name();
        this.dataHora = new Date();
        return this;
    }
    
}
