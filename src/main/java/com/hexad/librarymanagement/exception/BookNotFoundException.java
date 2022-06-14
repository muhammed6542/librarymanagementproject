package com.hexad.librarymanagement.exception;

import javax.persistence.PersistenceException;

public class BookNotFoundException   extends PersistenceException {
    public BookNotFoundException() {
        super("Sorry, book not found");
    }

}

