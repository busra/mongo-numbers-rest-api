package io.github.busra.mongo.numbers.restapi.service.error;

public class DuplicateValueFoundException extends RuntimeException {

    public DuplicateValueFoundException(String message) {
        super(message);
    }

}