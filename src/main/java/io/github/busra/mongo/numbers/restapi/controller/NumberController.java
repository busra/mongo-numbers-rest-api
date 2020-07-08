package io.github.busra.mongo.numbers.restapi.controller;

import io.github.busra.mongo.numbers.restapi.controller.model.NumberRequest;
import io.github.busra.mongo.numbers.restapi.domain.Number;
import io.github.busra.mongo.numbers.restapi.service.NumberService;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/number")
public class NumberController {

    private final NumberService numberService;

    NumberController(NumberService numberService) {
        this.numberService = numberService;
    }

    @PostMapping
    public void saveNumber(@RequestBody NumberRequest numberRequest) {
        numberService.saveNumber(numberRequest);
    }

    @GetMapping
    public List<Number> getNumber(@RequestParam(required = false) Integer value, @RequestParam(defaultValue = "true", required = false) boolean isOrderAscending) {
        if (Objects.nonNull(value)) {
            return Arrays.asList(numberService.findByValue(value));
        } else {
            return numberService.findAll(isOrderAscending);
        }
    }

    @DeleteMapping("/{value}")
    public void deleteNumber(@PathVariable int value) {
        numberService.deleteNumber(value);
    }

    @GetMapping("/minimum")
    public Number getMinimumNumber() {
        return numberService.findMinimumNumberStored();
    }

    @GetMapping("/maximum")
    public Number getMaximumNumber() {
        return numberService.findMaximumNumberStored();
    }

}