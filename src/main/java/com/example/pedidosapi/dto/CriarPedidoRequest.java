package com.example.pedidosapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CriarPedidoRequest {

    private String enderecoEntrega;
    private String formaPagamento;
    private String descrição;

}
