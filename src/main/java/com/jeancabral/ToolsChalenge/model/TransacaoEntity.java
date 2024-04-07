package com.jeancabral.ToolsChalenge.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.jeancabral.ToolsChalenge.dto.TransactionDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transacoes")
@JsonPropertyOrder({"id", "num_cartao", "descricao", "formaPagamento"})
public class TransacaoEntity {
    
    @Id
    @NotNull
    @Column(name = "id")
    private Long transacaoId;
    
    @Column(name = "num_cartao")
    private String numCartao;

    @Embedded
    private Descricao descricao;

    @Embedded
    private FormaPagamento formaPagamento;
    
    
    public TransactionDTO toDto() {
        return TransactionDTO.with(
                this.transacaoId,
                this.numCartao,
                this.descricao,
                this.formaPagamento
        );
    }
    
    public static TransacaoEntity from(TransactionDTO transacao) {
        
        return new TransacaoEntity(
                transacao.transacaoId(),
                transacao.numCartao(),
                transacao.descricao(),
                transacao.formaPagamento()
        );
        
    }
}
