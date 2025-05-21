package com.reservationmanagement.controller;

import com.reservationmanagement.dto.GuestDTO;
import com.reservationmanagement.dto.RoomDTO;
import com.reservationmanagement.entity.Reservation;
import com.reservationmanagement.feign.GuestFeign;
import com.reservationmanagement.feign.RoomsFeign;
import com.reservationmanagement.service.ReservationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ReservationControllerTest {

    @InjectMocks
    private ReservationController reservationController;

    @Mock
    private ReservationService reservationService;

    @Mock
    private RoomsFeign roomFeign;

    @Mock
    private GuestFeign guestFeign;

    private Reservation mockReservation;
    private RoomDTO mockRoomDTO;
    private GuestDTO mockGuestDTO;

    @BeforeEach
    void setup() {
        mockReservation = new Reservation();
        mockReservation.setReservation_code(1);
        mockReservation.setGuestId(101L);

        mockRoomDTO = new RoomDTO();
        mockRoomDTO.setRoomno(202);
        mockRoomDTO.setType("Deluxe");

        mockGuestDTO = new GuestDTO();
        mockGuestDTO.setGuestId(101L);
        mockGuestDTO.setName("John");
    }

    @Test
    void testAddReservation() {
        when(reservationService.addReservation(any(Reservation.class))).thenReturn(mockReservation);

        Reservation result = reservationController.addReservation(mockReservation);
        assertEquals(mockReservation.getReservation_code(), result.getReservation_code());
    }

    @Test
    void testGetAllReservations() {
        when(reservationService.getReservation()).thenReturn(List.of(mockReservation));

        List<Reservation> reservations = reservationController.getReservation();
        assertFalse(reservations.isEmpty());
    }

    @Test
    void testFetchGuestById() {
        when(guestFeign.getGuestById(101L)).thenReturn(mockGuestDTO);

        GuestDTO result = reservationController.fetchGuestById(101L);
        assertEquals("John", result.getName());
    }

    @Test
    void testGetAvailableRooms() {
        when(roomFeign.AvailableRooms()).thenReturn(List.of(mockRoomDTO));

        List<RoomDTO> result = reservationController.fetchAvailableRooms();
        assertFalse(result.isEmpty());
    }

    @Test
    void testCancelReservation() {
        when(reservationService.cancelReservation(1)).thenReturn("Reservation Cancelled");

        String result = reservationController.cancelReservation(1);
        assertEquals("Reservation Cancelled", result);
    }

    @Test
    void testGetReservationByCheckinCheckoutDates() {
        LocalDate checkin = LocalDate.of(2025, 5, 1);
        LocalDate checkout = LocalDate.of(2025, 5, 5);
        when(reservationService.getReservationByCheckinDateAndCheckoutDate(checkin, checkout))
            .thenReturn(List.of(mockReservation));

        List<Reservation> result = reservationController.getReservationByCheckinDateAndCheckoutDate(checkin, checkout);
        assertEquals(1, result.size());
    }

    @Test
    void testGetAvailableRoomsByTypeAndCapacityWithDate() {
        LocalDate checkin = LocalDate.of(2025, 5, 1);
        LocalDate checkout = LocalDate.of(2025, 5, 5);
        when(reservationService.getAvailableRoomsByTypeAndCapacity("Deluxe", 2, checkin, checkout))
            .thenReturn(List.of(mockRoomDTO));

        List<RoomDTO> result = reservationController.getAvailableRoomsByTypeAndCapacity("Deluxe", 2, checkin, checkout);
        assertEquals(1, result.size());
    }

    @Test
    void testFetchGuestByIdNotFound() {
        when(guestFeign.getGuestById(102L)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reservationController.fetchGuestById(102L);
        });

        assertTrue(exception.getMessage().contains("Guest Not Found"));
    }
}
