package com.allianz.erpsystem.exceptions;

public class StockException extends Exception{
    public StockException() {
        super("Out of stock!");
    }
}
