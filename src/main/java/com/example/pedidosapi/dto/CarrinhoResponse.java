package com.example.pedidosapi.dto;

import com.example.pedidosapi.entity.Carrinho;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
