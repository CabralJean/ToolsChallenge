package com.jeancabral.ToolsChalenge.controller;

import com.jeancabral.ToolsChalenge.dto.CartaoDto;
import com.jeancabral.ToolsChalenge.model.Cartao;
import com.jeancabral.ToolsChalenge.service.impl.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cartoes")
public class CartaoController {

    @Autowired
    private CartaoService service;

    @PostMapping
    public ResponseEntity<Cartao> create(@RequestBody CartaoDto cartaoData){
        Cartao newCartao = this.service.create(cartaoData);
        return ResponseEntity.ok().body(newCartao);

    }
}
