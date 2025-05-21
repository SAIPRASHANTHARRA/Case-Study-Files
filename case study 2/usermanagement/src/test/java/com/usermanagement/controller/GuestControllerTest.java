package com.usermanagement.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.usermanagement.controller.GuestController;
import com.usermanagement.entity.Guest;
import com.usermanagement.service.GuestService;

@ExtendWith(MockitoExtension.class)
public class GuestControllerTest {

    @Mock
    private GuestService guestService;

    @InjectMocks
    private GuestController guestController;

    private Guest guest;

    @BeforeEach
    void setUp() {
        guest = new Guest(1L, "0987654321", "XYZ Corp", "John Smith", "johnsmith@example.com", "Male", "456 Elm St");
    }

    @Test
    void testGetAllGuests() {
        List<Guest> guests = Arrays.asList(guest);
        when(guestService.getAllGuests()).thenReturn(guests);
        List<Guest> result = guestController.getAllGuests();
        assertEquals(1, result.size());
        assertEquals("John Smith", result.get(0).getName());
    }

    @Test
    void testGetGuestById() {
        when(guestService.getGuestById(1L)).thenReturn(guest);
        Guest result = guestController.getGuestById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getGuestId());
    }

    @Test
    void testUpdateGuest() {
        when(guestService.updateGuest(eq(1L), any(Guest.class))).thenReturn("Database updated successfully");
        String response = guestController.updateGuest(1L, guest);
        assertEquals("Database updated successfully", response);
    }

    @Test
    void testAddGuest() {
        when(guestService.addGuest(any(Guest.class))).thenReturn("Guest added successfully");
        String response = guestController.addGuest(guest);
        assertEquals("Guest added successfully", response);
    }
}
