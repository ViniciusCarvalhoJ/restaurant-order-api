package com.example.pedidosapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "pedidos")
@AllArgsConstructor
@Entity
@Setter
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    private BigDecimal total;

    private String enderecoEntrega;

    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

    private LocalDateTime dataCriacao = LocalDateTime.now();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pedido_id")
    private List<ItemPedido> itens = new ArrayList<>();

    private String descricao;

    public Pedido() {
        this.status = StatusPedido.CRIADO;
    }

    public void atualizarDescricao(String descricao){
        this.descricao = descricao;
    }

    public void pagar(){
        if(this.status != StatusPedido.CRIADO){
            throw new IllegalStateException("Pedido nao pode ser pago.");
        }
        this.status = StatusPedido.PAGO;
    }

    public void iniciarPreparacao(){
        if(this.status != StatusPedido.PAGO){
            throw new IllegalStateException("Pedido nao pode iniciser preparado.");
        }
        this.status = StatusPedido.EM_PREPARO;
    }

    public void enviar(){
        if(this.status != StatusPedido.EM_PREPARO){
            throw new IllegalStateException("Pedido nao pode ser enviado.");
        }
        this.status = StatusPedido.ENVIADO;
    }

    public void entregar(){
        if(this.status != StatusPedido.ENVIADO){
            throw new IllegalStateException("Pedido nao pode ser entregue.");
        }
        this.status = StatusPedido.ENTREGUE;
    }

    public void cancelar(){
        if(this.status == StatusPedido.ENTREGUE){
            throw new IllegalStateException("Pedido nao pode ser cancelado.");
        }
        this.status = StatusPedido.CANCELADO;
    }
}
