package com.hexad.librarymanagement.exception;

public class StockNotFoundException   extends IllegalArgumentException {
    public StockNotFoundException() {
        super("Sorry, stock is not enough");
    }
}

