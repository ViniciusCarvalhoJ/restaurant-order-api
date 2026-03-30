package com.example.pedidosapi.controller;

import com.example.pedidosapi.dto.CriarPedidoRequest;
import com.example.pedidosapi.dto.PedidoResponse;
import com.example.pedidosapi.entity.StatusPedido;
import com.example.pedidosapi.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
@Tag(name = "Pedidos", description = "Gerenciamento de pedidos, incluindo criação, listagem, atualização de status e cancelamento.")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Operation(
            summary = "Criar um novo pedido",
            description = "Adiciona um novo pedido ao sistema com a descrição fornecida.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso."),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique os dados enviados.")
            }
    )
    @PostMapping
    public ResponseEntity<Void> criarPedido(@RequestBody CriarPedidoRequest request) {
        Long id = pedidoService.criarPedido(request.getDescricao());
        return ResponseEntity.created(URI.create("/pedidos/" + id)).build();
    }

    @Operation(
            summary = "Listar pedidos",
            description = "Lista todos os pedidos cadastrados, com opção de filtrar por status.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso."),
                    @ApiResponse(responseCode = "400", description = "Parâmetros de filtro inválidos.")
            }
    )
    @GetMapping
    public List<PedidoResponse> listarPedidos(@RequestParam(required = false) StatusPedido status) {
        if (status != null) {
            return pedidoService.listarPorStatus(status);
        }
        return pedidoService.listarPedidos();
    }

    @Operation(
            summary = "Buscar pedido por ID",
            description = "Retorna os detalhes de um pedido específico com base no ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pedido encontrado com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Pedido não encontrado.")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> buscarPedido(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.buscarPorId(id));
    }

    @Operation(
            summary = "Pagar um pedido",
            description = "Atualiza o status de um pedido para 'Pago'.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Pedido pago com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Pedido não encontrado.")
            }
    )
    @PostMapping("/{id}/pagar")
    public ResponseEntity<Void> pagarPedido(@PathVariable Long id) {
        pedidoService.pagarPedido(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Iniciar preparação de um pedido",
            description = "Atualiza o status de um pedido para 'Em preparação'.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Preparação iniciada com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Pedido não encontrado.")
            }
    )
    @PostMapping("/{id}/preparar")
    public ResponseEntity<Void> prepararPedido(@PathVariable Long id) {
        pedidoService.iniciarPreparacaoPedido(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Enviar pedido para entrega",
            description = "Atualiza o status de um pedido para 'Enviado'.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Pedido enviado com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Pedido não encontrado.")
            }
    )
    @PostMapping("/{id}/enviar")
    public ResponseEntity<Void> enviarPedido(@PathVariable Long id) {
        pedidoService.enviarPedido(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Confirmar entrega de um pedido",
            description = "Atualiza o status de um pedido para 'Entregue'.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Entrega confirmada com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Pedido não encontrado.")
            }
    )
    @PostMapping("/{id}/entregar")
    public ResponseEntity<Void> entregarPedido(@PathVariable Long id) {
        pedidoService.entregarPedido(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Cancelar um pedido",
            description = "Atualiza o status de um pedido para 'Cancelado'.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Pedido cancelado com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Pedido não encontrado.")
            }
    )
    @PostMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarPedido(@PathVariable Long id) {
        pedidoService.cancelarPedido(id);
        return ResponseEntity.noContent().build();
    }
}
