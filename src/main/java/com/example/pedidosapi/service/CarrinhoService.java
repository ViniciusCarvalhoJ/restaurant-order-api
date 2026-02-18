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
    public Carrinho getOrCreateCart(Long userId) {
        return carrinhoRepository.findByUsuarioId(userId).orElseGet(() -> {
            Carrinho carrinho = new Carrinho();
            carrinho.setUserId(userId);
            carrinho.setTotal(BigDecimal.ZERO);
            return carrinhoRepository.save(carrinho);
        });
    }

    @Transactional
    public Carrinho adicionarItem(Long userId, Long produtoId, int quantidade) {

        Carrinho carrinho = getOrCreateCart(userId);

        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (produto.getEstoqueDisponivel() < quantidade) {
            throw new IllegalArgumentException("Produto indisponível no estoque");
        }

        carrinho.addItem(produto, quantidade);

        return carrinhoRepository.save(carrinho);
    }

    public Carrinho obterCarrinhoComItens(Long userId) {
        return getOrCreateCart(userId);
    }

    @Transactional
    public PedidoDto criarPedido(Long userId, String enderecoEntrega, String formaPagamento) {

        Carrinho carrinho = getOrCreateCart(userId);

        if (carrinho.getItens().isEmpty()) {
            throw new IllegalStateException("Carrinho vazio. Adicione itens antes de criar um pedido.");
        }

        Pedido pedido = new Pedido();
        pedido.setUserId(userId);
        pedido.setEnderecoEntrega(enderecoEntrega);
        pedido.setFormaPagamento(FormaPagamento.valueOf(formaPagamento));
        //pedido.setStatus("PENDENTE");
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setTotal(carrinho.getTotal());

        // Copiar itens do carrinho para o pedido
        for (CarrinhoItem itemCarrinho : carrinho.getItens()) {

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setNomeProduto(itemCarrinho.getNome());
            itemPedido.setQuantidade(itemCarrinho.getQuantidade());
            itemPedido.setPrecoUnitario(itemCarrinho.getPreco());

            BigDecimal subtotal =
                    itemCarrinho.getPreco()
                            .multiply(BigDecimal.valueOf(itemCarrinho.getQuantidade()));

            itemPedido.setSubTotal(subtotal);

            pedido.getItens().add(itemPedido);

            Produto produto = produtoRepository.findById(itemCarrinho.getProdutoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            produto.debitarEstoque(itemCarrinho.getQuantidade());
            produtoRepository.save(produto);
        }
        Pedido salvo = pedidoRepository.save(pedido);

        //LimparCarrinho
        carrinho.getItens().clear();
        carrinho.setTotal(BigDecimal.ZERO);
        carrinhoRepository.save(carrinho);

        return PedidoDto.converterPedido(salvo);
    }
}

