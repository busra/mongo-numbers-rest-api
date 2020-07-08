package io.github.busra.mongo.numbers.restapi.service;

import io.github.busra.mongo.numbers.restapi.controller.model.StoreNumberRequest;
import io.github.busra.mongo.numbers.restapi.domain.StoredNumber;
import io.github.busra.mongo.numbers.restapi.repository.StoredNumberDao;
import io.github.busra.mongo.numbers.restapi.service.error.DuplicateValueFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StoredNumberServiceTests {

    @Mock
    StoredNumberDao storedNumberDao;

    @InjectMocks
    StoredNumberService storedNumberService;

    @Test
    void shouldStoreNumberWhenNoDuplicateValue() {
        StoreNumberRequest request = new StoreNumberRequest();
        request.setValue(29);

        when(storedNumberDao.existsByValue(29)).thenReturn(false);

        storedNumberService.storeNumber(request);

        ArgumentCaptor<StoredNumber> storedNumberArgumentCaptor = ArgumentCaptor.forClass(StoredNumber.class);
        verify(storedNumberDao).save(storedNumberArgumentCaptor.capture());

        StoredNumber storedNumber = storedNumberArgumentCaptor.getValue();
        assertThat(storedNumber.getValue()).isEqualTo(29);
        assertThat(storedNumber.getInsertDate()).isNotNull();

        verify(storedNumberDao).existsByValue(29);

        verifyNoMoreInteractions(storedNumberDao);
    }

    @Test
    void shouldNotStoreNumberWhenDuplicateValueExists() {
        StoreNumberRequest request = new StoreNumberRequest();
        request.setValue(34);

        when(storedNumberDao.existsByValue(34)).thenReturn(true);

        assertThrows(DuplicateValueFoundException.class, () -> {
            storedNumberService.storeNumber(request);
        });

        verify(storedNumberDao).existsByValue(34);

        verifyNoMoreInteractions(storedNumberDao);
    }

    @Test
    void shouldFindAll() {
        StoredNumber storedNumber = new StoredNumber();
        storedNumber.setId("1");
        storedNumber.setValue(35);

        when(storedNumberDao.findAll(35, true)).thenReturn(Arrays.asList(storedNumber));

        List<StoredNumber> resultList = storedNumberService.findAll(35, true);

        assertThat(resultList).hasSize(1);
        assertThat(resultList.get(0).getId()).isEqualTo("1");
        assertThat(resultList.get(0).getValue()).isEqualTo(35);

        verify(storedNumberDao).findAll(35, true);

        verifyNoMoreInteractions(storedNumberDao);
    }

    @Test
    void shouldDeleteStoredNumberByValue() {
        storedNumberService.deleteStoredNumberByValue(25);

        verify(storedNumberDao).deleteByValue(25);

        verifyNoMoreInteractions(storedNumberDao);
    }

    @Test
    void shouldFindMinimumStoredNumber() {
        StoredNumber storedNumber = new StoredNumber();
        storedNumber.setId("1");
        storedNumber.setValue(34);

        when(storedNumberDao.findFirstBy(Sort.by(Sort.Direction.ASC, "value"))).thenReturn(Optional.of(storedNumber));

        StoredNumber resultStoredNumber = storedNumberService.findMinimumStoredNumber();

        assertThat(resultStoredNumber.getId()).isEqualTo("1");
        assertThat(resultStoredNumber.getValue()).isEqualTo(34);

        verify(storedNumberDao).findFirstBy(Sort.by(Sort.Direction.ASC, "value"));

        verifyNoMoreInteractions(storedNumberDao);
    }

    @Test
    void shouldFindMaximumStoredNumber() {
        StoredNumber storedNumber = new StoredNumber();
        storedNumber.setId("1");
        storedNumber.setValue(81);

        when(storedNumberDao.findFirstBy(Sort.by(Sort.Direction.DESC, "value"))).thenReturn(Optional.of(storedNumber));

        StoredNumber resultStoredNumber = storedNumberService.findMaximumStoredNumber();

        assertThat(resultStoredNumber.getId()).isEqualTo("1");
        assertThat(resultStoredNumber.getValue()).isEqualTo(81);

        verify(storedNumberDao).findFirstBy(Sort.by(Sort.Direction.DESC, "value"));

        verifyNoMoreInteractions(storedNumberDao);
    }

}