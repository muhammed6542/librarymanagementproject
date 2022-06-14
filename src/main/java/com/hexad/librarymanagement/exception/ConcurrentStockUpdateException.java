package com.hexad.librarymanagement.exception;

public class ConcurrentStockUpdateException extends IllegalStateException {
    public ConcurrentStockUpdateException() {
        super("Somebody has already updated your book stock, please retry");
    }
}
