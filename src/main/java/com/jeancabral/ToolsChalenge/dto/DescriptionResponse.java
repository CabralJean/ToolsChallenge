package com.jeancabral.ToolsChalenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jeancabral.ToolsChalenge.model.Descricao;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public record DescriptionResponse(
        @JsonProperty("valor")
        double value,
        @JsonProperty("dataHora")
        String date,
        @JsonProperty("estabelecimento")
        String establishment,
        String nsu,
        @JsonProperty("codigoAutorizacao")
        String authorizationCode,
        String status
) {
    
    public static DescriptionResponse from (final Descricao description) {
        
        final var formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        final var dateString = formatter.format(description.getDataHora());
        
        return new DescriptionResponse(
                description.getValor(),
                dateString,
                description.getEstabelecimento(),
                description.getNsu(),
                description.getCodigoAutorizacao(),
                description.getStatus()
        );
        
    }
}
