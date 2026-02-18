package com.example.pedidosapi.controller;

import com.example.pedidosapi.dto.AddItemRequest;
import com.example.pedidosapi.dto.CriarPedidoRequestDto;
import com.example.pedidosapi.dto.PedidoDto;
import com.example.pedidosapi.entity.Carrinho;
import com.example.pedidosapi.service.CarrinhoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    public CarrinhoController(CarrinhoService carrinhoService) {this.carrinhoService = carrinhoService;}

    /**
     * Retorna o carrinho do usuário autenticado.
     *
     * <p>Endpoint GET que recupera o carrinho existente ou cria um novo caso não exista.
     * A implementação atual usa um ID de usuário fixo como simulação; a autenticação real
     * deve fornecer o ID do usuário.</p>
     *
     * @return o {@code Carrinho} do usuário (existente ou recém-criado)
     */
    @GetMapping
    public Carrinho getCarrinho() {
        Long userId = 1L; // Simulação de obtenção do ID do usuário autenticado
        return carrinhoService.getOrCreateCart(userId);
    }

    @PostMapping("/add-item")
    public Carrinho adicionarItemAoCarrinho(@RequestBody AddItemRequest request) {
        Long userId = 1L; // Simulação de obtenção do ID do usuário autenticado
        return carrinhoService.adicionarItem(request.getProdutoId(), userId, request.getQuantidade());
    }

//    @GetMapping("/itens")
//    public Carrinho getCarrinho() {
//        Long userId = getCurrentUserId(); // Simulação de obtenção do ID do usuário autenticado
//        return carrinhoService.obterCarrinhoComItens(userId);
//    }

    @GetMapping("/criarPedido")
    public PedidoDto criarPedido(@RequestBody CriarPedidoRequestDto request) {
        Long userId = 1L; // Simulação de obtenção do ID do usuário autenticado
        return carrinhoService.criarPedido(userId, request.getEnderecoEntrega(), request.getFormaPagamento());
    }
}

