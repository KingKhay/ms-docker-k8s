package com.docker.k8s.employeeservice.exception;

public class OperationFailedException extends RuntimeException {
    public OperationFailedException() {
    }

    public OperationFailedException(String message) {
        super(message);
    }
}
