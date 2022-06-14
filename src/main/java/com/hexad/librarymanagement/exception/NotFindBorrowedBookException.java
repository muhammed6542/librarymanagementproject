package com.hexad.librarymanagement.exception;

import javax.persistence.PersistenceException;

public class NotFindBorrowedBookException extends PersistenceException {
    public NotFindBorrowedBookException() {
        super("Sorry, not found   borrowed book");
    }
}

