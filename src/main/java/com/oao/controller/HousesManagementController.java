package com.oao.controller;

import com.oao.model.request.HouseCreationRequest;
import com.oao.model.response.CreateHouseResponse;
import com.oao.model.response.ErrorResponse;
import com.oao.model.response.GetHousesResponse;
import com.oao.model.response.House;
import com.oao.service.HousesManagementService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Objects;
import javax.validation.Valid;
import java.net.HttpURLConnection;

@RestController
@RequestMapping(value = "/v1")
@Validated
@Api("A REST-controller to handle various request to service")
public class HousesManagementController {

    private HousesManagementService housesManagementService;

    @Autowired
    public HousesManagementController(HousesManagementService housesManagementService) {
        Objects.requireNonNull(housesManagementService, "housesManagementService was null when injected");
        this.housesManagementService = housesManagementService;
    }

    @PostMapping(value ="/house", produces = "application/json")
    @ApiOperation(value = "Create the house")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_CREATED, message = "House has been created.", response = CreateHouseResponse.class),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "The request is missing or have badly formatted"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAVAILABLE, message = "Server error", response = ErrorResponse.class)
    })
    public CreateHouseResponse saveHouse(@RequestBody @Valid HouseCreationRequest houseCreationRequest) {
        String houseId = housesManagementService.saveHouse(houseCreationRequest.getHouseNumber(), houseCreationRequest.getStreetName(),
                houseCreationRequest.getPostalCode(), houseCreationRequest.getOwner());

        return CreateHouseResponse.builder()
                .houseId(houseId)
                .build();
    }

    @DeleteMapping(value = "/houses/{houseId}", produces = "application/json")
    @ApiOperation(value = "Delete related house form database")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_NO_CONTENT, message = "House has been deleted"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Server error", response = ErrorResponse.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "If the house id is not found.", response = ErrorResponse.class),

    })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeHouse(@PathVariable String houseId) {
        housesManagementService.removeHouse(houseId);
    }


    @GetMapping(value = "/houses", produces = "application/json")
    @ApiOperation(value = "Get ordered house form database")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_NO_CONTENT, message = "return all houses in order"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Server error", response = ErrorResponse.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "If the house id is not found.", response = ErrorResponse.class),

    })
    public GetHousesResponse orderedHouses() {
        ArrayList<House> houses = housesManagementService.orderedHouses();

        return GetHousesResponse.builder()
                .houses(houses)
                .build();
    }
}
