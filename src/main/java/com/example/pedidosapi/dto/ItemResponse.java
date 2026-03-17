package com.example.pedidosapi.dto;

import com.example.pedidosapi.entity.CarrinhoItem;

import java.math.BigDecimal;

public class ItemResponse {

    private String nome;
    private int quantidade;
    private BigDecimal precoUnitario;

    public ItemResponse (CarrinhoItem item) {
        this.nome = item.getNome();
        this.quantidade = item.getQuantidade();
        this.precoUnitario = item.getPrecoUnitario();
    }
}
