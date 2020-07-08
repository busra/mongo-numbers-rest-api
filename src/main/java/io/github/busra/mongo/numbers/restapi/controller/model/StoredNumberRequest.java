package io.github.busra.mongo.numbers.restapi.controller.model;

import javax.validation.constraints.NotNull;

public class StoredNumberRequest {

    @NotNull
    private Integer value;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

}