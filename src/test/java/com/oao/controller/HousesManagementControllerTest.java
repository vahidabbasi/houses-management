package com.oao.controller;

import com.oao.model.response.CreateHouseResponse;
import com.oao.service.HousesManagementService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.oao.utils.TestUtils.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HousesManagementControllerTest {

    @Mock
    private HousesManagementService housesManagementService;

    @InjectMocks
    private HousesManagementController housesManagementController;

    @Test
    public void shouldSaveHouse() {
        when(housesManagementService.saveHouse(anyString(), anyString(), anyInt(), anyString())).thenReturn(HOUSE_ID);

        CreateHouseResponse actualResponse = housesManagementController.saveHouse(HOUSE_CREATION_REQUEST);

        verify(housesManagementService).saveHouse(HOUSE_CREATION_REQUEST.getHouseNumber(), HOUSE_CREATION_REQUEST.getStreetName(),
                HOUSE_CREATION_REQUEST.getPostalCode(), HOUSE_CREATION_REQUEST.getOwner());
        assertEquals(HOUSE_ID, actualResponse.getHouseId());
    }

    @Test
    public void shouldRemoveHouse() {
        housesManagementController.removeHouse(HOUSE_ID);

        verify(housesManagementService).removeHouse(HOUSE_ID);
    }
}