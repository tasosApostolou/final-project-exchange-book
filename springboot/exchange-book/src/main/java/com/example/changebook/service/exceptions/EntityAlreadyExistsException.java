package com.example.changebook.service.exceptions;

public class EntityAlreadyExistsException extends Exception {
    private static final long serialVersionUID = 1L;

    public EntityAlreadyExistsException(Class<?> entityClass, String username) {
        super("Entity " + entityClass.getSimpleName() + " with username " + username + " already exist");
    }
}
