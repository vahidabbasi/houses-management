package com.oao.validators;

import com.oao.exceptions.HousesManagementException;
import com.oao.repository.HousesManagementDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RequestValidator {

    private final HousesManagementDAO housesManagementDAO;

    @Autowired
    public RequestValidator(HousesManagementDAO housesManagementDAO) {
        Objects.requireNonNull(housesManagementDAO, "housesManagementDAO was null when injected");
        this.housesManagementDAO = housesManagementDAO;
    }
    public void validateRemoveHouseRequest(String houseId) {

        if (housesManagementDAO.hasHouse(houseId) != 1) {
            throw new HousesManagementException("House with id " + houseId + " not found", HttpStatus.NOT_FOUND);
        }
    }
}
