package com.reservationmanagement.service;

import com.reservationmanagement.dto.GuestDTO;
import com.reservationmanagement.dto.RoomDTO;
import com.reservationmanagement.entity.Reservation;
import com.reservationmanagement.exception.*;
import com.reservationmanagement.feign.GuestFeign;
import com.reservationmanagement.feign.RoomsFeign;
import com.reservationmanagement.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class) 
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private GuestFeign guestFeign;

    @Mock
    private RoomsFeign roomsFeign;

    @InjectMocks
    private ReservationService reservationService;

    private Reservation testReservation;
    private GuestDTO testGuest;
    private RoomDTO testRoom;

    @BeforeEach
    public void setUp() {
        
        testReservation = new Reservation();
        testReservation.setGuestId(1L);
        testReservation.setRoomno(1);
        testReservation.setEmail("nagaraju@gmail.com");
        testReservation.setCheckinDate(LocalDate.of(2025, 5, 1));
        testReservation.setCheckoutDate(LocalDate.of(2025, 5, 5));
        testReservation.setRoom_type("Basic");
        testReservation.setCapacity(5);
        
        testGuest = new GuestDTO();
        testGuest.setGuestId(1L);
        testGuest.setEmail("nagaraju@gmail.com");
        
        testRoom = new RoomDTO();
        testRoom.setRoomno(1);
        testRoom.setType("Basic");
        testRoom.setCapacity(5);
    }

    @Test
    public void testAddReservation_success() {
     
        when(guestFeign.getGuestById(testReservation.getGuestId())).thenReturn(testGuest);
        when(roomsFeign.getRoomByRoomNo(testReservation.getRoomno())).thenReturn(testRoom);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(testReservation);
        
      
        Reservation savedReservation = reservationService.addReservation(testReservation);
        
   
        assertNotNull(savedReservation);
        assertEquals(testReservation.getGuestId(), savedReservation.getGuestId());
        assertEquals(testReservation.getRoomno(), savedReservation.getRoomno());
    }

    @Test
    public void testAddReservation_guestNotFound() {
       
        when(guestFeign.getGuestById(testReservation.getGuestId())).thenReturn(null);

        
        GuestNotFoundException thrown = assertThrows(GuestNotFoundException.class, () -> {
            reservationService.addReservation(testReservation);
        });
        
        assertEquals("Guest not found with GuestId:1", thrown.getMessage());
    }

    @Test
    public void testAddReservation_invalidDates() {
        // Setup reservation with invalid dates
        testReservation.setCheckinDate(LocalDate.of(2025, 5, 6));
        testReservation.setCheckoutDate(LocalDate.of(2025, 5, 5));
        
        when(guestFeign.getGuestById(testReservation.getGuestId())).thenReturn(testGuest);
        
        
        InvalidDatesException thrown = assertThrows(InvalidDatesException.class, () -> {
            reservationService.addReservation(testReservation);
        });

        assertEquals("Check out Date cannot be after the check In Date", thrown.getMessage());
    }

    @Test
    public void testUpdateReservation_success() {
        
        GuestDTO mockGuest = new GuestDTO();
        mockGuest.setGuestId(1L);
        mockGuest.setEmail("nagaraju@gmail.com");  

      
        Reservation updatedReservation = new Reservation();
        updatedReservation.setReservation_code(testReservation.getReservation_code());
        updatedReservation.setGuestId(1L); 
        updatedReservation.setRoomno(1); 
        updatedReservation.setEmail("nagaraju@gmail.com"); 
        updatedReservation.setCheckinDate(LocalDate.of(2025, 5, 2)); 
        updatedReservation.setCheckoutDate(LocalDate.of(2025, 5, 6)); 
        updatedReservation.setRoom_type("Basic");
        updatedReservation.setCapacity(5);

        
        when(guestFeign.getGuestById(updatedReservation.getGuestId())).thenReturn(mockGuest);
        RoomDTO mockRoom = new RoomDTO();
        mockRoom.setRoomno(1);
        mockRoom.setType("Basic");
        mockRoom.setCapacity(5);
        when(roomsFeign.getRoomByRoomNo(updatedReservation.getRoomno())).thenReturn(mockRoom);

        when(reservationRepository.findById(updatedReservation.getReservation_code()))
            .thenReturn(Optional.of(testReservation));
        
        when(reservationRepository.save(any(Reservation.class))).thenReturn(updatedReservation);
        
        Reservation result = reservationService.updateReservation(updatedReservation.getReservation_code(), updatedReservation);
        
        assertNotNull(result);
        assertEquals(updatedReservation.getCheckinDate(), result.getCheckinDate());
        assertEquals(updatedReservation.getCheckoutDate(), result.getCheckoutDate());
        assertEquals(updatedReservation.getRoomno(), result.getRoomno());
    }

    @Test
   public void testCancelReservation_success() {
        // Setup mocks
        when(reservationRepository.findById(testReservation.getReservation_code())).thenReturn(Optional.of(testReservation));
        doNothing().when(roomsFeign).updateRoomStatus(testReservation.getRoomno(), "Available");
        
        // Test the cancelReservation method
        String result = reservationService.cancelReservation(testReservation.getReservation_code());
        
        assertEquals("Reservation with ID " + testReservation.getReservation_code() + " has been successfully cancelled.", result);
        verify(reservationRepository, times(1)).deleteById(testReservation.getReservation_code());
    } 

    @Test
    public void testCancelReservation_notFound() {
        ReservationNotFoundException thrown = assertThrows(ReservationNotFoundException.class, () -> {
            reservationService.cancelReservation(9999); // Non-existing reservation ID
        });

        assertEquals("Reservation not found with ID: 9999", thrown.getMessage());
    }
}
