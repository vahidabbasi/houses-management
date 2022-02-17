package com.oao.service;

import com.oao.repository.HousesManagementDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.oao.utils.TestUtils.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HousesManagementServiceTest {

    @Mock
    private HousesManagementDAO housesManagementDAO;

    @InjectMocks
    private HousesManagementService housesManagementService;

    @Test
    public void shouldSaveHouse() {
        when(housesManagementDAO.saveHouse(anyString(), anyString(), anyInt(), anyString())).thenReturn(HOUSE_ID);

        String actualResponse = housesManagementService.saveHouse(HOUSE_NUMBER, STREET_NAME, POSTAL_CODE, OWNER);

        verify(housesManagementDAO).saveHouse(HOUSE_NUMBER, STREET_NAME, POSTAL_CODE, OWNER);
        assertEquals(HOUSE_ID, actualResponse);
    }

    @Test
    public void shouldRemoveHouse() {
        housesManagementService.removeHouse(HOUSE_ID);

        verify(housesManagementDAO).removeHouse(HOUSE_ID);
    }
}