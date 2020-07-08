package io.github.busra.mongo.numbers.restapi.repository;

import io.github.busra.mongo.numbers.restapi.domain.Number;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

interface NumberRepository extends MongoRepository<Number, String> {

    Optional<Number> findByValue(int value);

    void deleteByValue(int value);

    List<Number> findAll(Sort sort);

}