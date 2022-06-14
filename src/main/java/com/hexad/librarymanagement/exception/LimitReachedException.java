package com.hexad.librarymanagement.exception;


public class LimitReachedException extends IllegalArgumentException {
    public LimitReachedException() {
        super("Sorry, Received book limit reached");
    }

}

