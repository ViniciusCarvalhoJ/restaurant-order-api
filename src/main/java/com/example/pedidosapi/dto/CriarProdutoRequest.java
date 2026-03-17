package com.example.pedidosapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CriarProdutoRequest {
    private String nome;
    private BigDecimal preco;
    private Integer estoqueDisponivel;
}
