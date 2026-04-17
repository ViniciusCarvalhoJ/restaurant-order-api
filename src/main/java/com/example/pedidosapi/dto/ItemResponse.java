package com.example.pedidosapi.dto;

import com.example.pedidosapi.entity.CarrinhoItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
