package com.example.pedidosapi.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PedidoNaoEncontradoException.class)
    public ResponseEntity<String> handlePedidoNaoEncontradoException(PedidoNaoEncontradoException ex) {
        return ResponseEntity
                .status(404)
                .body(ex.getMessage());
    }

    @ExceptionHandler(ErroDeValidacaoException.class)
    public ResponseEntity<String> handleErroDeValidacaoException(ErroDeValidacaoException ex){
        return ResponseEntity.status(400).body(ex.getMessage());
    }
}
