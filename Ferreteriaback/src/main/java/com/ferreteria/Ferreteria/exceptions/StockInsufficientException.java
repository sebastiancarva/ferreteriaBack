package com.ferreteria.Ferreteria.exceptions;

public class StockInsufficientException extends RuntimeException{
    public StockInsufficientException(String message) {
        super(message);
    }
}
