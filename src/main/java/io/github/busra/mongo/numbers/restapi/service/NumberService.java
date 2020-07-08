package io.github.busra.mongo.numbers.restapi.service;

import io.github.busra.mongo.numbers.restapi.controller.model.NumberRequest;
import io.github.busra.mongo.numbers.restapi.domain.Number;
import io.github.busra.mongo.numbers.restapi.repository.NumberDao;
import io.github.busra.mongo.numbers.restapi.service.error.DuplicateValueFoundException;
import io.github.busra.mongo.numbers.restapi.service.error.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NumberService {

    private final NumberDao numberDao;

    NumberService(NumberDao numberDao) {
        this.numberDao = numberDao;
    }

    public void saveNumber(NumberRequest numberRequest) {
        if (numberDao.findByValue(numberRequest.getValue()).isPresent()) {
            throw new DuplicateValueFoundException("Numbers must be unique: " + numberRequest.getValue());
        }

        numberDao.save(mapNumberModel(numberRequest.getValue()));
    }

    private Number mapNumberModel(int value) {
        Number number = new Number();

        number.setValue(value);
        number.setInsertDate(new Date());

        return number;
    }

    public Number findByValue(int value) {
        return numberDao.findByValue(value).orElseThrow(() -> new NotFoundException("Number not found: " + String.valueOf(value)));
    }

    public List<Number> findAll(boolean isOrderAscending) {
        return numberDao.findAll(Sort.by(isOrderAscending ? Sort.Direction.ASC : Sort.Direction.DESC, "value"));
    }

    public void deleteNumber(int value) {
        numberDao.deleteByValue(value);
    }

    public Number findMinimumNumberStored() {
        return numberDao.findAll(Sort.by(Sort.Direction.ASC, "value")).get(0);
    }

    public Number findMaximumNumberStored() {
        return numberDao.findAll(Sort.by(Sort.Direction.DESC, "value")).get(0);
    }

}