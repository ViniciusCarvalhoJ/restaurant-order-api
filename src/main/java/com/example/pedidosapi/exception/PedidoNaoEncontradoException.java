package com.example.pedidosapi.exception;

public class PedidoNaoEncontradoException extends RuntimeException{

    public PedidoNaoEncontradoException(Long id){
        super("Pedido com ID " + id + " não encontrado.");
    }


}
