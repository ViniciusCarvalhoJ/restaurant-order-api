package com.example.pedidosapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarrinhoItem {

    @Id
    @GeneratedValue
    private Long id;
    private Long produtoId;
    private Integer quantidade;
    private BigDecimal preco;
    private String nome;
}

