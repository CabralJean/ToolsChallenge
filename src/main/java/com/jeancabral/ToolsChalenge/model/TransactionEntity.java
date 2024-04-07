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
@JsonPropertyOrder({"id", "cartao", "descricao", "formaPagamento"})
public class TransactionEntity {
    
    @Id
    @NotNull
    @Column(name = "id")
    private Long transacaoId;
    
    @Column(name = "cartao")
    private String cartao;

    @Embedded
    private Description descricao;

    @Embedded
    private PaymentMethod formaPagamento;
    
    
    public TransactionDTO toDto() {
        return TransactionDTO.with(
                this.transacaoId,
                this.cartao,
                this.descricao,
                this.formaPagamento
        );
    }
    
    public static TransactionEntity from(TransactionDTO transaction) {
        
        return new TransactionEntity(
                transaction.transacaoId(),
                transaction.numCartao(),
                transaction.descricao(),
                transaction.formaPagamento()
        );
        
    }
}
