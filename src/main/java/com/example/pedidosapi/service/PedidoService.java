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

    /**
     * Busca um pedido pelo seu ID.
     * Caso o pedido não seja encontrado, lança uma exceção.
     *
     * @param id O ID do pedido a ser buscado.
     * @return O pedido correspondente ao ID fornecido.
     * @throws PedidoNaoEncontradoException Se o pedido não for encontrado.
     */
    public Pedido buscarPedido(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));
    }

    /**
     * Busca um pedido pelo seu ID e retorna uma resposta com os dados do pedido.
     *
     * @param id O ID do pedido a ser buscado.
     * @return Um objeto PedidoResponse contendo os dados do pedido.
     * @throws PedidoNaoEncontradoException Se o pedido não for encontrado.
     */
    public PedidoResponse buscarPorId(Long id) {
        Pedido pedido = buscarPedido(id);

        return new PedidoResponse(
                pedido.getId(),
                pedido.getDescricao(),
                pedido.getStatus()
        );
    }

    /**
     * Cria um novo pedido com a descrição fornecida.
     *
     * @param descricao A descrição do novo pedido.
     * @return O ID do pedido criado.
     */
    @Transactional

    public Long criarPedido(String descricao) {
        Pedido pedido = new Pedido();
    pedido.atualizarDescricao(descricao);

        return pedidoRepository.save(pedido).getId();
    }

    /**
     * Atualiza a descrição de um pedido existente.
     *
     * @param id        O ID do pedido a ser atualizado.
     * @param descricao A nova descrição do pedido.
     * @throws PedidoNaoEncontradoException Se o pedido não for encontrado.
     */
    @Transactional

    public void atualizarDescricao(Long id, String descricao) {
        Pedido pedido = buscarPedido(id);
        pedido.atualizarDescricao(descricao);
    }

    /**
     * Lista todos os pedidos cadastrados.
     *
     * @return Uma lista de objetos PedidoResponse contendo os dados dos pedidos.
     */
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

    /**
     * Lista todos os pedidos com um status específico.
     *
     * @param status O status dos pedidos a serem listados.
     * @return Uma lista de objetos PedidoResponse contendo os dados dos pedidos filtrados.
     */
    public List<PedidoResponse> listarPorStatus(StatusPedido status) {
        return pedidoRepository.findByStatus(status);
    }

    /**
     * Marca um pedido como pago.
     *
     * @param id O ID do pedido a ser pago.
     * @throws PedidoNaoEncontradoException Se o pedido não for encontrado.
     */
    @Transactional

    public void pagarPedido(Long id) {
        Pedido pedido = buscarPedido(id);
        pedido.pagar();
    }

    /**
     * Inicia a preparação de um pedido.
     *
     * @param id O ID do pedido a ser preparado.
     * @throws PedidoNaoEncontradoException Se o pedido não for encontrado.
     */
    @Transactional

    public void iniciarPreparacaoPedido(Long id) {
        Pedido pedido = buscarPedido(id);
        pedido.iniciarPreparacao();
    }

    /**
     * Marca um pedido como enviado.
     *
     * @param id O ID do pedido a ser enviado.
     * @throws PedidoNaoEncontradoException Se o pedido não for encontrado.
     */
    @Transactional

    public void enviarPedido(Long id) {
        Pedido pedido = buscarPedido(id);
        pedido.enviar();
    }


    /**
     * Marca um pedido como entregue.
     *
     * @param id O ID do pedido a ser entregue.
     * @throws PedidoNaoEncontradoException Se o pedido não for encontrado.
     */
    @Transactional

    public void entregarPedido(Long id) {
        Pedido pedido = buscarPedido(id);
        pedido.entregar();
    }


    /**
     * Cancela um pedido.
     *
     * @param id O ID do pedido a ser cancelado.
     * @throws PedidoNaoEncontradoException Se o pedido não for encontrado.
     */
    @Transactional

    public void cancelarPedido(Long id) {
        Pedido pedido = buscarPedido(id);
        pedido.cancelar();
    }


}
