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
                dto.id = pedido.getId();
                return dto;
        }
}
