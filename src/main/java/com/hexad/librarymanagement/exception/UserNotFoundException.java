package com.hexad.librarymanagement.exception;

import javax.persistence.PersistenceException;

public class UserNotFoundException   extends PersistenceException {
    public UserNotFoundException() {
        super("Sorry, user not found");
    }
}

