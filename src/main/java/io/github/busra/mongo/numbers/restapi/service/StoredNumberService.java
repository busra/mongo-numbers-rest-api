package io.github.busra.mongo.numbers.restapi.service;

import io.github.busra.mongo.numbers.restapi.controller.model.StoreNumberRequest;
import io.github.busra.mongo.numbers.restapi.domain.StoredNumber;
import io.github.busra.mongo.numbers.restapi.repository.StoredNumberDao;
import io.github.busra.mongo.numbers.restapi.service.error.DuplicateValueFoundException;
import io.github.busra.mongo.numbers.restapi.service.error.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StoredNumberService {

    private final StoredNumberDao storedNumberDao;

    StoredNumberService(StoredNumberDao storedNumberDao) {
        this.storedNumberDao = storedNumberDao;
    }

    public void storeNumber(StoreNumberRequest storeNumberRequest) {
        if (storedNumberDao.existsByValue(storeNumberRequest.getValue())) {
            throw new DuplicateValueFoundException("Value must be unique: " + storeNumberRequest.getValue());
        }

        storedNumberDao.save(mapStoredNumberEntity(storeNumberRequest));
    }

    private StoredNumber mapStoredNumberEntity(StoreNumberRequest storeNumberRequest) {
        StoredNumber storedNumber = new StoredNumber();

        storedNumber.setValue(storeNumberRequest.getValue());
        storedNumber.setInsertDate(new Date());

        return storedNumber;
    }

    public List<StoredNumber> findAll(Integer value, boolean isOrderAscending) {
        return storedNumberDao.findAll(value, isOrderAscending);
    }

    public void deleteStoredNumberByValue(int value) {
        storedNumberDao.deleteByValue(value);
    }

    public StoredNumber findMinimumStoredNumber() {
        return storedNumberDao.findFirstBy(Sort.by(Sort.Direction.ASC, "value"))
                .orElseThrow(() -> new NotFoundException("No stored number found"));
    }

    public StoredNumber findMaximumStoredNumber() {
        return storedNumberDao.findFirstBy(Sort.by(Sort.Direction.DESC, "value"))
                .orElseThrow(() -> new NotFoundException("No stored number found"));
    }

}