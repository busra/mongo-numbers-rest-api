package io.github.busra.mongo.numbers.restapi.repository;

import io.github.busra.mongo.numbers.restapi.domain.Number;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class NumberDao {

    private final NumberRepository numberRepository;

    private final MongoTemplate mongoTemplate;

    NumberDao(NumberRepository numberRepository, MongoTemplate mongoTemplate) {
        this.numberRepository = numberRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public void save(Number number) {
        numberRepository.save(number);
    }

    public Optional<Number> findByValue(int value) {
        return numberRepository.findByValue(value);
    }

    public void deleteByValue(int value) {
        numberRepository.deleteByValue(value);
    }

    public Optional<Number> findById(String id) {
        return numberRepository.findById(id);
    }

    public List<Number> findAll(Sort sort) {
        return numberRepository.findAll(sort);
    }

}