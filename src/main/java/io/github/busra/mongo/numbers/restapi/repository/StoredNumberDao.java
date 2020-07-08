package io.github.busra.mongo.numbers.restapi.repository;

import io.github.busra.mongo.numbers.restapi.domain.StoredNumber;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class StoredNumberDao {

    private final StoredNumberRepository storedNumberRepository;

    private final MongoTemplate mongoTemplate;

    StoredNumberDao(StoredNumberRepository storedNumberRepository, MongoTemplate mongoTemplate) {
        this.storedNumberRepository = storedNumberRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public void save(StoredNumber storedNumber) {
        storedNumberRepository.save(storedNumber);
    }

    public boolean existsByValue(int value) {
        return storedNumberRepository.existsByValue(value);
    }

    public void deleteByValue(int value) {
        storedNumberRepository.deleteByValue(value);
    }

    public Optional<StoredNumber> findFirstBy(Sort sort) {
        return storedNumberRepository.findFirstBy(sort);
    }

    public List<StoredNumber> findAll(Integer value, boolean isOrderAscending) {
        Query query = new Query();

        if (Objects.nonNull(value)) {
            query.addCriteria(Criteria.where("value").is(value));
        }

        query.with(Sort.by(isOrderAscending ? Sort.Direction.ASC : Sort.Direction.DESC, "value"));

        return mongoTemplate.find(query, StoredNumber.class);
    }

}