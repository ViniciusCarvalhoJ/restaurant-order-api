package com.example.pedidosapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Carrinho {

    @Id
    @GeneratedValue
    private Long id;

    private Long usuarioId = 1L; //!Simulação de usuário fixo

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "carrinho_id")
    @JsonIgnore
    private List<CarrinhoItem> itens = new ArrayList<>();

    private BigDecimal total = BigDecimal.ZERO;

    private LocalDateTime createdAt;

    //TODO: Metodo que recalcula estado interno NÃO recebe parâmetros.
    public void recalcularTotal() {
        this.total = this.itens.stream()
                .map(item -> item.getPrecoUnitario()
                        .multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addItem(Produto produto, int quantidade) {

        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }

        // 1️⃣ verifica se já existe
        for (CarrinhoItem item : this.itens) {
            if (item.getProdutoId().equals(produto.getId())) {
                item.setQuantidade(item.getQuantidade() + quantidade);
                recalcularTotal();
                return;
            }

            if (this.itens.size() >= 100) {
                throw new IllegalStateException("Carrinho não pode ter mais de 100 itens");
            }

            //Cria novos itens
            CarrinhoItem novoItem = new CarrinhoItem(
                    null,
                    produto.getId(),
                    produto.getNome(),
                    produto.getPreco(),
                    quantidade
            );

            this.itens.add(novoItem);
            recalcularTotal();
        }
    }

    public void limpar() {
        this.itens.clear();
        this.total = BigDecimal.ZERO;
    }

    public Pedido gerarPedido(String enderecoEntrega, FormaPagamento formaPagamento) {
        if (this.itens.isEmpty()) {
            throw new IllegalArgumentException("Carrinho vazio.");
        }

        Pedido pedido = new Pedido();
        pedido.setUserId(this.usuarioId);
        pedido.setEnderecoEntrega(enderecoEntrega);
        pedido.setFormaPagamento(formaPagamento);
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setTotal(this.total);

        for (CarrinhoItem item : this.itens) {
            ItemPedido itemPedido = new ItemPedido(
                    item.getNome(),
                    item.getQuantidade(),
                    item.getPrecoUnitario()
            );
            pedido.getItens().add(itemPedido);
        }

        return pedido;
    }


}
