package com.oao.validators;

import com.oao.exceptions.HousesManagementException;
import com.oao.repository.HousesManagementDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RequestValidator {

    public static String SPACE = " ";
    private HousesManagementDAO housesManagementDAO;

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

    public void validateHouseRequest(String houseNumber, String streetName, int postalCode, String owner) {
        if (housesManagementDAO.hasHouse(houseNumber, streetName, postalCode, owner) == 1) {
            StringBuilder message = new StringBuilder();
            message.append("House with number: ");
            message.append(houseNumber);
            message.append(SPACE);
            message.append("in street: ");
            message.append(streetName);
            message.append(SPACE);
            message.append("with owner: ");
            message.append(owner);
            message.append(SPACE);
            message.append("and postal code: ");
            message.append(postalCode);
            message.append(SPACE);
            message.append("has been already created.");
            throw new HousesManagementException(message.toString(),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
