package com.jeancabral.ToolsChalenge.service.impl;

import com.jeancabral.ToolsChalenge.exception.ReversalException;
import com.jeancabral.ToolsChalenge.exception.ReversalNotFoundException;
import com.jeancabral.ToolsChalenge.exception.TransactionNotFoundException;
import org.modelmapper.ModelMapper;
import com.jeancabral.ToolsChalenge.enums.StatusEnum;
import com.jeancabral.ToolsChalenge.exception.UninformedIdException;
import com.jeancabral.ToolsChalenge.model.Descricao;
import com.jeancabral.ToolsChalenge.model.FormaPagamento;
import com.jeancabral.ToolsChalenge.model.Transacao;
import com.jeancabral.ToolsChalenge.repository.EstornoRepository;
import com.jeancabral.ToolsChalenge.service.EstornoServiceInterface;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class EstornoService implements EstornoServiceInterface {

    private final EstornoRepository repository;

    public EstornoService(EstornoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Transacao> buscarEstornoId(Long transacaoId) {
        if (transacaoId == null) {
            throw new UninformedIdException();
        }
        Optional<Transacao> transacaoOptional = repository.findById(transacaoId);

        if (transacaoOptional.isEmpty()) {
            throw new ReversalNotFoundException("Transação não encontrada com o ID fornecido: " + transacaoId);
        }

        Transacao transacao = transacaoOptional.get();
        if (!transacao.getDescricao().getStatus().equals(StatusEnum.CANCELADO.name())) {
            return Optional.empty();
        }

        return transacaoOptional;
    }

    /*public Optional<Transacao> buscarEstornoId(Long transacaoId) {
        Optional<Transacao> transacaoEstornada = repository.findById(transacaoId);
        if(transacaoEstornada.isPresent()){
            Transacao transacao = transacaoEstornada.get();
            if (transacao.getDescricao().getStatus().equals(StatusEnum.CANCELADO.name())){
                return transacaoEstornada;
            }else{
                return Optional.empty();
            }
        }else{
            return Optional.empty();
        }
    }*/

    @Override
    public Transacao estornarPagamento(Long transacaoId){
        /*Optional<Transacao> transacaoPagto = repository.findById(transacaoId);

        if (transacaoPagto == null) {
            throw new UninformedIdException();
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
        return estorno;*/
        ModelMapper modelMapper = new ModelMapper();
        Optional<Transacao> transacaoPagtoOptional = repository.findById(transacaoId);
        Transacao transacaoPagto = transacaoPagtoOptional.orElseThrow(TransactionNotFoundException::new);
        if(transacaoPagtoOptional.get().getDescricao().getStatus().equals(StatusEnum.CANCELADO.name())){
            throw new ReversalException();
        }

        Transacao estorno = modelMapper.map(transacaoPagto, Transacao.class);
        estorno.setTransacaoId(transacaoId);

        Descricao descricaoEstorno = modelMapper.map(transacaoPagto.getDescricao(), Descricao.class);
        descricaoEstorno.setDataHora(new Date());
        descricaoEstorno.setStatus(StatusEnum.CANCELADO.name());
        estorno.setDescricao(descricaoEstorno);

        FormaPagamento formaPagamentoEstorno = modelMapper.map(transacaoPagto.getFormaPagamento(), FormaPagamento.class);
        estorno.setFormaPagamento(formaPagamentoEstorno);

        estorno = repository.save(estorno);
        return estorno;
    }
}
