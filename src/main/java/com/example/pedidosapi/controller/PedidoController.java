package com.example.pedidosapi.controller;

import com.example.pedidosapi.dto.CriarPedidoRequest;
import com.example.pedidosapi.dto.PedidoResponse;
import com.example.pedidosapi.entity.StatusPedido;
import com.example.pedidosapi.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Void> criarPedido(@RequestBody CriarPedidoRequest request) {
        Long id = pedidoService.criarPedido(request.getDescrição());
        return ResponseEntity.
                created(URI.create("/pedidos/" + id))
                .build();
    }

    @GetMapping
    public List<PedidoResponse> listarPedidos(
            @RequestParam(required = false) StatusPedido status) {

        if (status != null) {
            return pedidoService.listarPorStatus(status);
        }
        return pedidoService.listarPedidos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> buscarPedido(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.buscarPorId(id));
    }

    @PostMapping("/{id}/pagar")
    public ResponseEntity<Void> pagarPedido(@PathVariable Long id) {
        pedidoService.pagarPedido(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/preparar")
    public ResponseEntity<Void> prepararPedido(@PathVariable Long id) {
        pedidoService.iniciarPreparacaoPedido(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/enviar")
    public ResponseEntity<Void> enviarPedido(@PathVariable Long id) {
        pedidoService.enviarPedido(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/entregar")
    public ResponseEntity<Void> entregarPedido(@PathVariable Long id) {
        pedidoService.entregarPedido(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarPedido(@PathVariable Long id) {
        pedidoService.cancelarPedido(id);
        return ResponseEntity.noContent().build();
    }
}
