package com.example.pedidosapi.service;

import com.example.pedidosapi.dto.PedidoResponse;
import com.example.pedidosapi.entity.Pedido;
import com.example.pedidosapi.entity.StatusPedido;
import com.example.pedidosapi.exception.PedidoNaoEncontradoException;
import com.example.pedidosapi.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido buscarPedido(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));
    }

    public PedidoResponse buscarPorId(Long id) {
        Pedido pedido = buscarPedido(id);

        return new PedidoResponse(
                pedido.getId(),
                pedido.getDescricao(),
                pedido.getStatus()
        );
    }

    @Transactional
    public Long criarPedido(String descricao) {
        Pedido pedido = new Pedido();
        pedido.atualizarDescricao(descricao);

        return pedidoRepository.save(pedido).getId();
    }

    @Transactional
    public void atualizarDescricao(Long id, String descricao) {
        Pedido pedido = buscarPedido(id);
        pedido.atualizarDescricao(descricao);
    }

    public List<PedidoResponse> listarPedidos() {
        return pedidoRepository.findAll()
                .stream()
                .map(p -> new PedidoResponse(
                        p.getId(),
                        p.getDescricao(),
                        p.getStatus()
                ))
                .toList();
    }


    public List<PedidoResponse> listarPorStatus(StatusPedido status) {
        return pedidoRepository.findByStatus(status);
    }

    @Transactional
    public void pagarPedido(Long id) {
        Pedido pedido = buscarPedido(id);
        pedido.pagar();
    }

    @Transactional
    public void iniciarPreparacaoPedido(Long id) {
        Pedido pedido = buscarPedido(id);
        pedido.iniciarPreparacao();
    }

    @Transactional
    public void enviarPedido(Long id) {
        Pedido pedido = buscarPedido(id);
        pedido.enviar();
    }

    @Transactional
    public void entregarPedido(Long id) {
        Pedido pedido = buscarPedido(id);
        pedido.entregar();
    }

    @Transactional
    public void cancelarPedido(Long id) {
        Pedido pedido = buscarPedido(id);
        pedido.cancelar();
    }

}
