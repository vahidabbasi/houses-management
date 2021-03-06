package com.oao.service;

import com.oao.model.response.House;
import com.oao.repository.HousesManagementDAO;
import com.oao.validators.RequestValidator;
import javafx.print.Collation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

@Slf4j
@Service
public class HousesManagementService {

    private HousesManagementDAO housesManagementDAO;
    private RequestValidator requestValidator;


    @Autowired
    public HousesManagementService(HousesManagementDAO housesManagementDAO, RequestValidator requestValidator) {
        Objects.requireNonNull(housesManagementDAO, "housesManagementDAO was null when injected");
        Objects.requireNonNull(requestValidator, "requestValidator was null when injected");
        this.housesManagementDAO = housesManagementDAO;
        this.requestValidator = requestValidator;
    }

    public String saveHouse(String houseNumber, String streetName, int postalCode, String owner) {
        log.info("Save house in database");
        requestValidator.validateHouseRequest(houseNumber, streetName, postalCode, owner);
        return housesManagementDAO.saveHouse(houseNumber, streetName, postalCode, owner);
    }

    public void removeHouse(String houseId) {
        log.info("Remove house with id {} in database", houseId);
        requestValidator.validateRemoveHouseRequest(houseId);
        housesManagementDAO.removeHouse(houseId);
    }

    public ArrayList<House> orderedHouses() {
        return housesManagementDAO.getHouses();
    }
}
