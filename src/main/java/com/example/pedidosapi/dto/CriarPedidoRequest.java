package com.example.pedidosapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CriarPedidoRequest {

    @Schema(description = "Descrição do pedido", example = "Pedido de 2 pizzas e 1 refrigerante")
    private String descricao;

    @Schema(description = "Endereço de entrega", example = "Rua das Flores, 123, São Paulo")
    private String enderecoEntrega;

    @Schema(description = "Forma de pagamento", example = "CARTAO")
    private String formaPagamento;

}
