package com.example.pedidosapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CarrinhoItem {

    @Id
    @GeneratedValue
    private Long id;
    private Long produtoId;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private String nome;

    public CarrinhoItem(Long id, Long produtoId, String nome, BigDecimal preco, int quantidade) {
        this.id = id;
        this.produtoId = produtoId;
        this.nome = nome;
        this.precoUnitario = preco;
        this.quantidade = quantidade;
    }
}

