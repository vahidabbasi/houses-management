package com.oao.it;

import ch.qos.logback.core.status.ErrorStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.oao.it.config.IntegrationTestConfiguration;
import com.oao.model.request.HouseCreationRequest;
import com.oao.model.response.CreateHouseResponse;
import com.oao.model.response.ErrorResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;
import static com.oao.utils.TestUtils.HOUSE_CREATION_REQUEST;
import static com.oao.utils.TestUtils.HOUSE_NUMBER;
import static com.oao.utils.TestUtils.STREET_NAME;
import static com.oao.utils.TestUtils.POSTAL_CODE;
import static com.oao.utils.TestUtils.OWNER;
import static liquibase.util.Validate.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
@Import(IntegrationTestConfiguration.class)
public class HouseManagementControllerIt {

    @Autowired
    RestTemplate testRestTemplate;

    private static String BASE_URL = "http://localhost:8082/houses-management-service/v1";
    private static String SAVE_HOUSE_URL = "/house";
    private static String REMOVE_HOUSE_URL = "/houses/{houseId}";
    private HouseCreationRequest houseCreationRequest;

    @Before
    public void setup() {
        houseCreationRequest = HouseCreationRequest.builder()
                .houseNumber(HOUSE_NUMBER)
                .streetName(STREET_NAME)
                .postalCode(POSTAL_CODE)
                .owner(OWNER)
                .build();
    }

    @Test
    public void shouldSaveHouse() {
        ResponseEntity<CreateHouseResponse> actualResponse = saveHouse(houseCreationRequest);
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        //clean database
        removeHouse(actualResponse.getBody().getHouseId());
    }

    @Test
    public void shouldReturn400IfRequestIsInvalid() throws IOException {
        //Invalid postal code
        houseCreationRequest.setPostalCode(null);
        try {
            saveHouse(houseCreationRequest);
            fail("Expected 400 exception");
        } catch (final HttpClientErrorException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
            final ObjectMapper mapper = new ObjectMapper();
            final ErrorResponse response = mapper.readValue(e.getResponseBodyAsString(), ErrorResponse.class);
            assertTrue(response.getMessage().contains("postalCode: must not be null"));
        }
    }

    @Test
    public void shouldReturn400IfHouseHasAlreadyBeenCreated() throws IOException {
        //Create House
        ResponseEntity<CreateHouseResponse> actualResponse = saveHouse(houseCreationRequest);

        try {
            //Try to create same house
            saveHouse(houseCreationRequest);
            fail("Expected 400 exception");
        } catch (final HttpClientErrorException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
            final ObjectMapper mapper = new ObjectMapper();
            final ErrorResponse response = mapper.readValue(e.getResponseBodyAsString(), ErrorResponse.class);
            assertTrue(response.getMessage().contains("has been already created"));
        }
        //clean database
        removeHouse(actualResponse.getBody().getHouseId());
    }

    @Test
    public void shouldRemoveHouse() {
        ResponseEntity<CreateHouseResponse> response = saveHouse(houseCreationRequest);

        ResponseEntity<Void> actualResponse = removeHouse(response.getBody().getHouseId());
        assertEquals(HttpStatus.NO_CONTENT, actualResponse.getStatusCode());
    }

    @Test
    public void shouldReturn400IfHouseHasAlreadyBeenRemoved() throws IOException{
        ResponseEntity<CreateHouseResponse> response = saveHouse(houseCreationRequest);
        removeHouse(response.getBody().getHouseId());

        try {
            removeHouse(response.getBody().getHouseId());
            fail("Expected 404 exception");
        } catch (final HttpClientErrorException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
            final ObjectMapper mapper = new ObjectMapper();
            final ErrorResponse errorResponse = mapper.readValue(e.getResponseBodyAsString(), ErrorResponse.class);
            assertTrue(errorResponse.getMessage().contains("not found"));
        }
    }

    private ResponseEntity<Void> removeHouse(String houseId) {
        final Map<String, Object> params = ImmutableMap.of(
                "houseId", houseId);
        return testRestTemplate.exchange(BASE_URL + REMOVE_HOUSE_URL, HttpMethod.DELETE, null, Void.class, params);
    }

    private ResponseEntity<CreateHouseResponse> saveHouse(HouseCreationRequest houseCreationRequest) {
        return testRestTemplate.postForEntity(BASE_URL + SAVE_HOUSE_URL,
                houseCreationRequest,
                CreateHouseResponse.class);
    }
}
