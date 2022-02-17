package com.oao.utils;


import com.oao.model.request.HouseCreationRequest;
import com.oao.model.response.CreateHouseResponse;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Collections;

public final class TestUtils {

    public static final String HOUSE_ID = "house_id";
    public static final String HOUSE_NUMBER = "123b";
    public static final String STREET_NAME = "Kurmainzer str";
    public static final int POSTAL_CODE = 12345;
    public static final String OWNER = "vahid_abbasi";
    public static final CreateHouseResponse CREATE_HOUSE_RESPONSE = CreateHouseResponse.builder()
            .houseId(HOUSE_ID)
            .build();
    public static final HouseCreationRequest HOUSE_CREATION_REQUEST = HouseCreationRequest.builder()
            .houseNumber(HOUSE_NUMBER)
            .streetName(STREET_NAME)
            .postalCode(POSTAL_CODE)
            .owner(OWNER)
            .build();
}
