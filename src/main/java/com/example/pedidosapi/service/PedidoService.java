package com.example.pedidosapi.service;

import com.example.pedidosapi.dto.CriarPedidoRequestDto;
import com.example.pedidosapi.dto.PedidoResponseDto;
import com.example.pedidosapi.exception.ErroDeValidacaoException;
import com.example.pedidosapi.exception.PedidoNaoEncontradoException;
import com.example.pedidosapi.entity.Pedido;
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

//    public PedidoResponseDto criarPedido(CriarPedidoRequestDto dto) throws ErroDeValidacaoException {
//        //1º passo: validar o dto
//        if(dto.getDescricao() == null || dto.getDescricao().isEmpty()){
//            throw new ErroDeValidacaoException();
//        }
//        //2º passo: converter o dto para entidade
//        Pedido novoPedido = new Pedido();
//        novoPedido.setDescricao(dto.getDescricao());
//        novoPedido.setStatus("CRIADO");
//
//        //3º passo: salvar a entidade
//        Pedido salvo = pedidoRepository.save(novoPedido);
//        //4º passo: converter a entidade salva para o dto de resposta
//        return new PedidoResponseDto(salvo.getId(), salvo.getDescricao(), salvo.getStatus());
//    }

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

    @Transactional
    public void pagarPedido(Long id){
        Pedido pedido = pedidoRepository.findById(id).orElseThrow();
        pedido.pagar();
        //pedidoRepository.save(pedido); // Não é necessário devido à anotação @Transactional
    }

    @Transactional
    public void iniciarPreparacaoPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow();
        pedido.iniciarPreparacao();
        //pedidoRepository.save(pedido); // Não é necessário devido à anotação @Transactional
    }

    @Transactional
    public void enviarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow();
        pedido.enviar();
        //pedidoRepository.save(pedido); // Não é necessário devido à anotação @Transactional
    }

    @Transactional
    public void entregarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow();
        pedido.entregar();
        //pedidoRepository.save(pedido); // Não é necessário devido à anotação @Transactional
    }
}
