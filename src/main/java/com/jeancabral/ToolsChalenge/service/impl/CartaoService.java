package com.jeancabral.ToolsChalenge.service.impl;

import com.jeancabral.ToolsChalenge.dto.CartaoDto;
import com.jeancabral.ToolsChalenge.model.Cartao;
import com.jeancabral.ToolsChalenge.repository.CartaoRepository;
import org.springframework.stereotype.Service;

@Service
public class CartaoService {

    private CartaoRepository repository;

    public CartaoService(CartaoRepository cartaoRepository) {
        this.repository = cartaoRepository;
    }

    public Cartao create(CartaoDto cartaoData){
        Cartao newCartao = new Cartao(cartaoData);
        this.repository.save(newCartao);
        return newCartao;
    }
}
