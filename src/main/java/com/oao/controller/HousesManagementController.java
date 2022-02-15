package com.oao.controller;

import com.oao.service.HousesManagementService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Objects;
import javax.validation.Valid;
import java.net.HttpURLConnection;

@RestController
@RequestMapping(value = "/v1")
@Validated
@Api("A REST-controller to handle various request to service")
public class HousesManagementController {

    private final HousesManagementService housesManagementService;

    @Autowired
    public HousesManagementController(HousesManagementService housesManagementService) {
        Objects.requireNonNull(housesManagementService, "housesManagementService was null when injected");
        this.housesManagementService = housesManagementService;
    }

    @PostMapping("/house")
    @ApiOperation(value = "Transfer the money between the accounts")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_NO_CONTENT, message = "Transfer has been done.", response = ErrorResponse.class),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "The request is missing or have badly formatted"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAVAILABLE, message = "Server error", response = ErrorResponse.class)
    })
    public void saveHouse(@RequestBody @Valid TransferMoneyRequest transferMoneyRequest) {

        housesManagementService.saveHouse(transferMoneyRequest);
    }
}
