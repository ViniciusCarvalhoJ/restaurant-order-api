package com.example.pedidosapi.service;

import com.example.pedidosapi.exception.PedidoNaoEncontradoException;
import com.example.pedidosapi.entity.Pedido;
import com.example.pedidosapi.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido criarPedido(Pedido pedido){
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listarPedidos(){return pedidoRepository.findAll();}

    public List<Pedido> listarPorStatus(String status){return pedidoRepository.findByStatus(status);}

    public Pedido getPedidoById(Long id){
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));
    }

    public Pedido atualizarPedido(Long id, Pedido pedidoDetails){
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));
        pedido.setDescricao(pedidoDetails.getDescricao());
        return pedidoRepository.save(pedido);
    }

    public void deletarPedido(Long id){pedidoRepository.deleteById(id);}

}
