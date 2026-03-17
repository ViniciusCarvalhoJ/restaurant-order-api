package com.example.pedidosapi.dto;

import com.example.pedidosapi.entity.Carrinho;

import java.math.BigDecimal;
import java.util.List;

public class CarrinhoResponse {

    private Long id;
    private BigDecimal total;
    private List<ItemResponse> itens;

    public CarrinhoResponse(Carrinho carrinho) {
        this.id = carrinho.getId();
        this.total = carrinho.getTotal();
        this.itens = carrinho.getItens()
                .stream()
                .map(ItemResponse::new)
                .toList();
    }
}
