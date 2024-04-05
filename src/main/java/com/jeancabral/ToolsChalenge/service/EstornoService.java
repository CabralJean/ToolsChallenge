package com.jeancabral.ToolsChalenge.service;

import com.jeancabral.ToolsChalenge.enums.StatusEnum;
import com.jeancabral.ToolsChalenge.model.Descricao;
import com.jeancabral.ToolsChalenge.model.FormaPagamento;
import com.jeancabral.ToolsChalenge.model.Transacao;
import com.jeancabral.ToolsChalenge.repository.EstornoRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EstornoService {

    private final EstornoRepository repository;

    public EstornoService(EstornoRepository repository) {
        this.repository = repository;
    }

    public Transacao estornarPagamento(Long transacaoId){
        Transacao transacaoPagto = repository.findByTransacaoId(transacaoId);

        if (transacaoPagto == null) {
            throw new RuntimeException("Transação não encontrada para o ID fornecido: " + transacaoId);
        }

        Transacao estorno = new Transacao();
        estorno.setTransacaoId(transacaoId);
        estorno.setNum_cartao(transacaoPagto.getNum_cartao());

        // Define os detalhes do estorno
        Descricao descricaoEstorno = new Descricao();
        descricaoEstorno.setValor(transacaoPagto.getDescricao().getValor()); // Estorna o mesmo valor
        descricaoEstorno.setDataHora(new Date()); // Define a data e hora atual
        descricaoEstorno.setEstabelecimento(transacaoPagto.getDescricao().getEstabelecimento());
        descricaoEstorno.setNsu(transacaoPagto.getDescricao().getNsu()); // Busca o NSU para o estorno
        descricaoEstorno.setCodigoAutorizacao(transacaoPagto.getDescricao().getCodigoAutorizacao()); // Busca o código de autorização
        descricaoEstorno.setStatus(StatusEnum.CANCELADO.name()); // Define o status como "CANCELADO"
        estorno.setDescricao(descricaoEstorno);

        FormaPagamento formaPagamento = new FormaPagamento();
        formaPagamento.setTipo(transacaoPagto.getFormaPagamento().getTipo());
        formaPagamento.setParcelas(transacaoPagto.getFormaPagamento().getParcelas());

        estorno.setFormaPagamento(formaPagamento);

        // Salva a transação de estorno no banco de dados
        estorno = repository.save(estorno);

        // Retorna a transação de estorno
        return estorno;

    }
}
