package com.example.pedidosapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CriarProdutoRequest {
    @Schema(description = "Nome do produto", example = "Pizza Margherita")
    private String nome;

    @Schema(description = "Preço do produto", example = "29.90")
    private BigDecimal preco;

    @Schema(description = "Quantidade disponível em estoque", example = "100")
    private Integer estoqueDisponivel;
}
