package io.github.busra.mongo.numbers.restapi.repository;

import io.github.busra.mongo.numbers.restapi.domain.StoredNumber;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

interface StoredNumberRepository extends MongoRepository<StoredNumber, String> {

    boolean existsByValue(int value);

    void deleteByValue(int value);

    List<StoredNumber> findAll(Sort sort);

    Optional<StoredNumber> findFirstBy(Sort sort);

}