package com.oao.model.request;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class HouseCreationRequestTest extends AbstractValidatorTest {

    private HouseCreationRequest houseCreationRequest;
    private static List<Integer> INVALID_POSTAL_CODE = Arrays.asList(0, -100, null);

    @Before
    public void before() {
        houseCreationRequest = HouseCreationRequest.builder()
                .owner("Vahid Abbasi")
                .streetName("Kurmainzer Str")
                .houseNumber("123b")
                .postalCode(61440)
                .build();
    }

    @Test
    public void shouldBeValidRequest() {
        assertTrue(isRequestValid(houseCreationRequest));
    }

    @Test
    public void shouldValidateInvalidOwner() {
        houseCreationRequest.setOwner(null);
        assertFalse(isRequestValid(houseCreationRequest));
    }

    @Test
    public void shouldValidateInvalidStreetName() {
        houseCreationRequest.setStreetName(null);
        assertFalse(isRequestValid(houseCreationRequest));
    }

    @Test
    public void shouldValidateInvalidPostalCode() {
        INVALID_POSTAL_CODE.forEach(value -> assertFalse(isPostalCodeValid(value)));
    }

    private boolean isPostalCodeValid(Integer value) {
        houseCreationRequest.setPostalCode(value);
        return isRequestValid(houseCreationRequest);
    }
}