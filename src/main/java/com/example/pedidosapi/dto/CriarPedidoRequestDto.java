package com.example.pedidosapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CriarPedidoRequestDto {

    private String enderecoEntrega;
    private String formaPagamento;
    private String nome;
    private Integer quantidade;
    private BigDecimal preco;
    private String descricao;

}
