package com.example.pedidosapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
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
    private Long userId = 1L; //!Simulação de usuário fixo
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "carrinho_id")
    private List<CarrinhoItem> itens = new ArrayList<>();
    private BigDecimal total = BigDecimal.ZERO;

    //TODO: Metodo que recalcula estado interno NÃO recebe parâmetros.
    public void somarTotal() {

        BigDecimal novoTotal = BigDecimal.ZERO;
        for (CarrinhoItem item : this.itens) {
            BigDecimal itemTotal =
                    item.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade()));

            novoTotal = novoTotal.add(itemTotal);
        }
        this.total = novoTotal;
    }

    public void addItem(Produto produto, int quantidade) {

        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }

        // 1️⃣ verifica se o item já existe
        for (CarrinhoItem item : this.itens) {
            if (item.getProdutoId().equals(produto.getId())) {
                item.setQuantidade(item.getQuantidade() + quantidade);
                somarTotal();
                return;
            }

            if (this.itens.size() >= 100) {
                throw new IllegalStateException("Carrinho não pode ter mais de 100 itens");
            }

            //Cria novos itens
            CarrinhoItem cartItem = new CarrinhoItem();
            cartItem.setProdutoId(produto.getId());
            cartItem.setNome(produto.getNome());
            cartItem.setPreco(produto.getPreco());
            cartItem.setQuantidade(quantidade);

            this.itens.add(cartItem);
            somarTotal();
        }
    }
}
