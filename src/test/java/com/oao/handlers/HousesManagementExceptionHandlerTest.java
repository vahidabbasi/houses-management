package com.oao.handlers;

import com.oao.exceptions.HousesManagementException;
import com.oao.model.response.ErrorResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HousesManagementExceptionHandlerTest {
    private static final String MESSAGE = "Exception message";

    private HousesManagementExceptionHandler exceptionHandler = new HousesManagementExceptionHandler();

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private FieldError fieldError;

    @Test
    public void shouldHandleGeneralException() {

        ResponseEntity responseEntity = exceptionHandler.handleException(new Exception());

        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, responseEntity.getStatusCode());
        assertEquals(ErrorResponse.builder().build(), responseEntity.getBody());
    }

    @Test
    public void shouldHandleHousesManagementException() {
        HousesManagementException housesManagementException = new HousesManagementException(MESSAGE);

        ResponseEntity responseEntity = exceptionHandler.handleHousesManagementException(housesManagementException);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        assertEquals(ErrorResponse.builder().message(MESSAGE).build(), responseEntity.getBody());
    }

    @Test
    public void shouldHandleHttpMessageNotReadableException() {
        HttpMessageNotReadableException housesManagementException = new HttpMessageNotReadableException(MESSAGE);

        ResponseEntity responseEntity = exceptionHandler.handleHttpMessageNotReadableException(housesManagementException);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(ErrorResponse.builder().message("Format not supported").build(), responseEntity.getBody());
    }

    @Test
    public void shouldHandleMethodArgumentNotValidException() {
        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldError()).thenReturn(fieldError);
        when(fieldError.getField()).thenReturn("field");
        when(fieldError.getDefaultMessage()).thenReturn("ArgumentNotValid");

        ResponseEntity responseEntity = exceptionHandler.handleMethodArgumentNotValidException(methodArgumentNotValidException);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(ErrorResponse.builder().message("field: ArgumentNotValid").build(), responseEntity.getBody());
    }
}