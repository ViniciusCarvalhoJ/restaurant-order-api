package com.example.pedidosapi.controller;

import com.example.pedidosapi.dto.AddItemRequest;
import com.example.pedidosapi.dto.CarrinhoResponse;
import com.example.pedidosapi.dto.CriarPedidoRequest;
import com.example.pedidosapi.dto.PedidoDto;
import com.example.pedidosapi.entity.Carrinho;
import com.example.pedidosapi.service.CarrinhoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    public CarrinhoController(CarrinhoService carrinhoService) {this.carrinhoService = carrinhoService;}


    private Long getCurrentUserId() {
        // Simulação de obtenção do ID do usuário autenticado
        return 1L;
    }

    @GetMapping
    public ResponseEntity<CarrinhoResponse> getCarrinho() {
        Carrinho carrinho = carrinhoService.getOrCreateCart(getCurrentUserId());
        return ResponseEntity.ok(new CarrinhoResponse(carrinho));
    }

    @PostMapping("/adicionar-item")
    public ResponseEntity<CarrinhoResponse> adicionarItem(
            @RequestBody AddItemRequest request) {
        Carrinho carrinho = carrinhoService.adicionarItem(
                getCurrentUserId(),
                request.getProdutoId(),
                request.getQuantidade());
        return ResponseEntity.ok(new CarrinhoResponse(carrinho));
    }

    @PostMapping("/finalizar")
    public ResponseEntity<PedidoDto> finalizarCarrinho(
            @RequestBody CriarPedidoRequest request) {
        PedidoDto pedido = carrinhoService.criarPedido(
                getCurrentUserId(),
                request.getEnderecoEntrega(),
                request.getFormaPagamento());
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }



}

