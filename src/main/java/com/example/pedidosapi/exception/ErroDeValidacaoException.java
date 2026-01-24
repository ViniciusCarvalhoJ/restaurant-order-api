package com.example.pedidosapi.exception;

public class ErroDeValidacaoException extends Throwable {

    public ErroDeValidacaoException(){
        super("Erro na validacao que foi efetuada.");
    }
}
