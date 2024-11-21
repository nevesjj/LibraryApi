package com.LibraryApi.Biblioteca.exception;

public class LimiteEmprestimosExcedidoException extends RuntimeException {
    public LimiteEmprestimosExcedidoException(String message) {
        super(message);
    }
}
