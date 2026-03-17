package com.example.pedidosapi.service;

import com.example.pedidosapi.dto.PedidoDto;
import com.example.pedidosapi.entity.*;
import com.example.pedidosapi.repository.CarrinhoRepository;
import com.example.pedidosapi.repository.PedidoRepository;
import com.example.pedidosapi.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoRepository pedidoRepository;

    public CarrinhoService(CarrinhoRepository carrinhoRepository, ProdutoRepository produtoRepository, PedidoRepository pedidoRepository) {
        this.carrinhoRepository = carrinhoRepository;
        this.produtoRepository = produtoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    //!“Me dá o carrinho desse usuário… se não existir, cria um novo e já salva no banco.”
    public Carrinho getOrCreateCart(Long usuarioId) {
        return carrinhoRepository.findByUsuarioId(usuarioId).orElseGet(() -> {
            Carrinho carrinho = new Carrinho();
            carrinho.setUsuarioId(usuarioId);
            carrinho.setTotal(BigDecimal.ZERO);
            return carrinhoRepository.save(carrinho);
        });
    }

    @Transactional
    public Carrinho adicionarItem(Long usuarioId, Long produtoId, int quantidade) {

        Carrinho carrinho = getOrCreateCart(usuarioId);

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (produto.getEstoqueDisponivel() < quantidade) {
            throw new IllegalArgumentException("Produto indisponível no estoque");
        }

        carrinho.addItem(produto, quantidade);

        return carrinho;  // dirty checking resolve
    }

    public Carrinho obterCarrinhoComItens(Long usuarioId) {
        return getOrCreateCart(usuarioId);
    }

    @Transactional
    public PedidoDto criarPedido(Long usuarioId, String enderecoEntrega, String formaPagamento) {

        Carrinho carrinho = getOrCreateCart(usuarioId);

        Pedido pedido = carrinho.gerarPedido(enderecoEntrega, FormaPagamento.valueOf(formaPagamento));

        //debitar estoque e salvar itens do pedido
        for (   CarrinhoItem item : carrinho.getItens()) {
            Produto produto = produtoRepository.findById(item.getProdutoId()).orElseThrow();

            produto.debitarEstoque(item.getQuantidade());
        }

        carrinho.limpar();

        return PedidoDto.converterPedido(pedidoRepository.save(pedido));
    }
}

