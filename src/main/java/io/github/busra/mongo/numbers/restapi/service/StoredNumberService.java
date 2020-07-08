package io.github.busra.mongo.numbers.restapi.service;

import io.github.busra.mongo.numbers.restapi.controller.model.StoredNumberRequest;
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

    public void saveStoredNumber(StoredNumberRequest numberRequest) {
        if (storedNumberDao.existsByValue(numberRequest.getValue())) {
            throw new DuplicateValueFoundException("Value must be unique: " + numberRequest.getValue());
        }

        storedNumberDao.save(mapStoredNumberEntity(numberRequest));
    }

    private StoredNumber mapStoredNumberEntity(StoredNumberRequest numberRequest) {
        StoredNumber storedNumber = new StoredNumber();

        storedNumber.setValue(numberRequest.getValue());
        storedNumber.setInsertDate(new Date());

        return storedNumber;
    }

    public List<StoredNumber> findAll(Integer value, boolean isOrderAscending) {
        return storedNumberDao.findAll(value, isOrderAscending);
    }

    public void deleteStoredNumber(int value) {
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