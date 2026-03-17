package com.example.pedidosapi.dto;


import com.example.pedidosapi.entity.Carrinho;
import com.example.pedidosapi.entity.Pedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDto {

        private Long id;
        private Carrinho userId;
        private String enderecoEntrega;
        private String formaPagamento;
        private Double valorTotal;

        public static PedidoDto converterPedido(Pedido pedido) {
                PedidoDto dto = new PedidoDto();

                dto.setId(pedido.getId());
                dto.getUserId();
                dto.setEnderecoEntrega(pedido.getEnderecoEntrega());
                dto.setFormaPagamento(pedido.getFormaPagamento().name());
                dto.setValorTotal(pedido.getTotal().doubleValue());

                return dto;
        }
}
