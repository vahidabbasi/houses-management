package com.oao.validators;

import com.oao.exceptions.HousesManagementException;
import com.oao.repository.HousesManagementDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import static com.oao.utils.TestUtils.HOUSE_ID;
import static com.oao.utils.TestUtils.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.fail;

@RunWith(MockitoJUnitRunner.class)
public class RequestValidatorTest {
    @Mock
    private HousesManagementDAO housesManagementDAO;

    @InjectMocks
    private RequestValidator requestValidator;

    @Test
    public void shouldBeValidHouseCreationRequest() {
        when(housesManagementDAO.hasHouse(anyString(), anyString(), anyInt(), anyString())).thenReturn(0);

        requestValidator.validateHouseRequest(HOUSE_NUMBER, STREET_NAME, POSTAL_CODE, OWNER);

        verify(housesManagementDAO).hasHouse(HOUSE_NUMBER, STREET_NAME, POSTAL_CODE, OWNER);
    }

    @Test
    public void shouldBeThrowBadRequestIfHouseHasAlreadyCreated() {
        when(housesManagementDAO.hasHouse(anyString(), anyString(), anyInt(), anyString())).thenReturn(1);

        try {
            requestValidator.validateHouseRequest(HOUSE_NUMBER, STREET_NAME, POSTAL_CODE, OWNER);
            fail("Expected HousesManagementException");
        } catch (HousesManagementException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getHttpStatus());
            verify(housesManagementDAO).hasHouse(HOUSE_NUMBER, STREET_NAME, POSTAL_CODE, OWNER);
        }

    }

    @Test
    public void shouldBeValidRequestForRemoving() {
        when(housesManagementDAO.hasHouse(anyString())).thenReturn(1);

        requestValidator.validateRemoveHouseRequest(HOUSE_ID);

        verify(housesManagementDAO).hasHouse(HOUSE_ID);
    }

    @Test
    public void shouldThrowNotFoundIfHouseDoesNotExistAndTryToRemoveIt() {
        when(housesManagementDAO.hasHouse(anyString())).thenReturn(0);

        try {
            requestValidator.validateRemoveHouseRequest(HOUSE_ID);
            fail("Expected HousesManagementException");
        } catch (HousesManagementException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getHttpStatus());
            verify(housesManagementDAO).hasHouse(HOUSE_ID);
        }
    }
}