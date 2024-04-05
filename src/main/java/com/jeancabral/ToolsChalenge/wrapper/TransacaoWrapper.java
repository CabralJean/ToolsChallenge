package com.jeancabral.ToolsChalenge.wrapper;

import com.jeancabral.ToolsChalenge.dto.TransacaoDto;
import com.jeancabral.ToolsChalenge.model.Transacao;
import lombok.*;


@Data
public class TransacaoWrapper {
    private TransacaoDto transacao;

    public TransacaoWrapper(TransacaoDto transacaoObj) {
        this.transacao = transacaoObj;
    }
}
