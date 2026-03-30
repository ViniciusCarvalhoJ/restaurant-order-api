package com.example.pedidosapi.controller;

import com.example.pedidosapi.dto.CriarProdutoRequest;
import com.example.pedidosapi.dto.ProdutoResponse;
import com.example.pedidosapi.entity.Produto;
import com.example.pedidosapi.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/produtos")
@Tag(name = "Produtos", description = "Gerenciamento de produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {this.produtoService = produtoService;}

    @Operation(summary = "Criar um novo produto")
    @PostMapping
    public ResponseEntity<ProdutoResponse> CriarProduto(@RequestBody CriarProdutoRequest request) {

        //Chama a service para criar o produto e obter o produto criado
        Produto produto =  produtoService.criarProduto(
                request.getNome(),
                request.getPreco(),
                request.getEstoqueDisponivel());

        ProdutoResponse response = ProdutoResponse.from(produto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Listar produtos, opcionalmente filtrados por nome, preço ou estoque disponível")
    @GetMapping
    public List<ProdutoResponse> listarProdutos(
            @RequestParam (required = false)
            String nome,
            BigDecimal preco,
            Integer estoqueDisponivel) {

        if (nome != null) {
            return produtoService.listarPorNome(nome);
        }
        return produtoService.listaTodos();
    }

    @Operation(summary = "Buscar detalhes de um produto por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> buscarProduto(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.buscarPorId(id));
    }

    @Operation(summary = "Deletar um produto por ID")
    @DeleteMapping("/{id}/deletar")
    public ResponseEntity<String> deletarProduto(@PathVariable Long id) {
        try {
            produtoService.deletarProduto(id);
            return ResponseEntity.ok("Produto deletado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
    }
}
