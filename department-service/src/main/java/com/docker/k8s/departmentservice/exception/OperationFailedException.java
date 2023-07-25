package com.docker.k8s.departmentservice.exception;


public class OperationFailedException extends RuntimeException{
    public OperationFailedException() {
    }

    public OperationFailedException(String message) {
        super(message);
    }
}
