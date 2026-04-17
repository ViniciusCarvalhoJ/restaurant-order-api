package com.example.pedidosapi.controller;

import com.example.pedidosapi.dto.AddItemRequest;
import com.example.pedidosapi.dto.CarrinhoResponse;
import com.example.pedidosapi.dto.CriarPedidoRequest;
import com.example.pedidosapi.dto.PedidoDto;
import com.example.pedidosapi.entity.Carrinho;
import com.example.pedidosapi.service.CarrinhoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrinho")
@Tag(name = "Carrinho", description = "Gerenciamento do carrinho de compras, incluindo adição de itens e finalização.")
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    public CarrinhoController(CarrinhoService carrinhoService) {
        this.carrinhoService = carrinhoService;
    }

    private Long getCurrentUserId() {
        // Simulação de obtenção do ID do usuário autenticado
        return 1L;
    }

    @Operation(
            summary = "Obter o carrinho atual",
            description = "Retorna o carrinho de compras atual do usuário autenticado.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Carrinho retornado com sucesso."),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Carrinho não encontrado.")
            }
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CarrinhoResponse> getCarrinho() {
        Carrinho carrinho = carrinhoService.getOrCreateCart(getCurrentUserId());
        return ResponseEntity.ok(new CarrinhoResponse(carrinho));
    }

    @Operation(
            summary = "Adicionar item ao carrinho",
            description = "Adiciona um item ao carrinho de compras do usuário autenticado.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Item adicionado com sucesso."),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique os dados enviados.")
            }
    )
    @PostMapping(value = "/adicionar-item", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CarrinhoResponse> adicionarItem(@RequestBody AddItemRequest request) {
        Carrinho carrinho = carrinhoService.adicionarItem(
                getCurrentUserId(),
                request.getProdutoId(),
                request.getQuantidade()
        );
        return ResponseEntity.ok(new CarrinhoResponse(carrinho));
    }

    @Operation(
            summary = "Finalizar carrinho",
            description = "Finaliza o carrinho de compras e cria um pedido com os itens do carrinho.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Carrinho finalizado e pedido criado com sucesso."),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique os dados enviados.")
            }
    )
    @PostMapping(value = "/finalizar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PedidoDto> finalizarCarrinho(@RequestBody CriarPedidoRequest request) {
        PedidoDto pedido = carrinhoService.criarPedido(
                getCurrentUserId(),
                request.getEnderecoEntrega(),
                request.getFormaPagamento()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }
}
