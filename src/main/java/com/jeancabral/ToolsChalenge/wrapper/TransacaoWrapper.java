package com.jeancabral.ToolsChalenge.wrapper;

import com.jeancabral.ToolsChalenge.model.Transacao;
import lombok.*;


@Getter
@Setter
public class TransacaoWrapper {
    private Transacao transacao;

    public TransacaoWrapper(Transacao transacaoObj) {
        this.transacao = transacaoObj;
    }
}
