package io.github.busra.mongo.numbers.restapi.service.error;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

}