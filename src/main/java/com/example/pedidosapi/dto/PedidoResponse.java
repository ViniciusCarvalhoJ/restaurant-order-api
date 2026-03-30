package com.example.pedidosapi.dto;

import com.example.pedidosapi.entity.StatusPedido;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponse {
    @Schema(description = "ID do pedido", example = "1")
    private Long id;

    @Schema(description = "Descrição do pedido", example = "Pedido de 2 pizzas e 1 refrigerante")
    private String descricao;

    @Schema(description = "Status do pedido", example = "PENDENTE")
    private String status;

    @Schema(description = "Data de criação do pedido", example = "2026-03-24T15:30:00")
    private String dataCriacao;

    public PedidoResponse(Long id, String descricao, StatusPedido status) {}
}
