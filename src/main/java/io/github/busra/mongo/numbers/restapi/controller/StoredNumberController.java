package io.github.busra.mongo.numbers.restapi.controller;

import io.github.busra.mongo.numbers.restapi.controller.model.StoreNumberRequest;
import io.github.busra.mongo.numbers.restapi.domain.StoredNumber;
import io.github.busra.mongo.numbers.restapi.service.StoredNumberService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/stored-number")
public class StoredNumberController {

    private final StoredNumberService storedNumberService;

    StoredNumberController(StoredNumberService storedNumberService) {
        this.storedNumberService = storedNumberService;
    }

    @PostMapping
    public void storeNumber(@Valid @RequestBody StoreNumberRequest storeNumberRequest) {
        storedNumberService.storeNumber(storeNumberRequest);
    }

    @GetMapping
    public List<StoredNumber> getStoredNumbers(@RequestParam(required = false) Integer value,
                                               @RequestParam(defaultValue = "true", required = false) boolean isOrderAscending) {
        return storedNumberService.findAll(value, isOrderAscending);
    }

    @DeleteMapping("/{value}")
    public void deleteStoredNumber(@PathVariable int value) {
        storedNumberService.deleteStoredNumber(value);
    }

    @GetMapping("/minimum")
    public StoredNumber getMinimumStoredNumber() {
        return storedNumberService.findMinimumStoredNumber();
    }

    @GetMapping("/maximum")
    public StoredNumber getMaximumStoredNumber() {
        return storedNumberService.findMaximumStoredNumber();
    }

}