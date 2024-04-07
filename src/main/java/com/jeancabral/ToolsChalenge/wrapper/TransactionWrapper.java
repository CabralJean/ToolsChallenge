package com.jeancabral.ToolsChalenge.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jeancabral.ToolsChalenge.dto.PaymentResponse;


public record TransactionWrapper (
    @JsonProperty("transacao")
    PaymentResponse transaction
) { }
