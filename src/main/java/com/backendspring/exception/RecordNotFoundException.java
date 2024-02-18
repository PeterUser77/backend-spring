package com.backendspring.exception;

public class RecordNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RecordNotFoundException(Long id) {
        super("Record with id " + id + " not be found.");
    }
    
}
