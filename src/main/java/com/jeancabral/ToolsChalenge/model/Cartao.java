package com.jeancabral.ToolsChalenge.model;

import com.jeancabral.ToolsChalenge.dto.CartaoDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cartoes")
@NoArgsConstructor
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer num_cartao;

    public Cartao(CartaoDto cartaoDto){
        this.num_cartao = cartaoDto.num_cartao();
    }
}
