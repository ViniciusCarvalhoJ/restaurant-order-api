package com.example.pedidosapi.service;

import com.example.pedidosapi.dto.ProdutoResponse;
import com.example.pedidosapi.entity.Produto;
import com.example.pedidosapi.exception.ProdutoNaoEncontradoException;
import com.example.pedidosapi.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto criarProduto(String nome, BigDecimal preco, Integer estoqueDisponivel) {
        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setPreco(preco);
        produto.setEstoqueDisponivel(estoqueDisponivel);

        return produtoRepository.save(produto);
    }

    public Produto buscarProduto(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));
    }

    public ProdutoResponse buscarPorId(Long id) {
        Produto produto = buscarProduto(id);

        return new ProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getPreco(),
                produto.getEstoqueDisponivel()
        );
    }

    public List<ProdutoResponse> listarPorNome(String nome) {
        return produtoRepository.findAll().stream()
                .filter(p -> p.getNome().toLowerCase().contains(nome.toLowerCase()))
                .map(p -> new ProdutoResponse(p.getId(), p.getNome(), p.getPreco(), p.getEstoqueDisponivel()))
                .toList();
    }

    public List<ProdutoResponse> listaTodos() {
        return produtoRepository.findAll().stream()
                .map(p -> new ProdutoResponse(p.getId(), p.getNome(), p.getPreco(), p.getEstoqueDisponivel()))
                .toList();
    }

    public void deletarProduto(Long id) {
        Produto produto = buscarProduto(id);
        produtoRepository.delete(produto);
    }
}
