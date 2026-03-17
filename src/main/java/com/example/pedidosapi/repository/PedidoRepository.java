package com.example.pedidosapi.repository;

import com.example.pedidosapi.dto.PedidoResponse;
import com.example.pedidosapi.entity.Pedido;
import com.example.pedidosapi.entity.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<PedidoResponse> findByStatus(StatusPedido status);
}
