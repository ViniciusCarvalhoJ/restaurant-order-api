package com.example.pedidosapi.dto;

import com.example.pedidosapi.entity.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponse {

    public Long id;
    public String descricao;
    public String status;

    public PedidoResponse(Long id, String descricao, StatusPedido status) {}
}
