package com.usermanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.usermanagement.entity.Guest;
import com.usermanagement.exception.DuplicateGuestException;
import com.usermanagement.exception.GuestNotFoundException;
import com.usermanagement.repository.GuestRepository;
import com.usermanagement.service.GuestService;

@ExtendWith(MockitoExtension.class) 
public class GuestServiceTest {
	
	@Mock
	private GuestRepository guestRepository;
	
	@InjectMocks
	private GuestService guestService;
	
	private Guest guest;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		guest = new Guest(1L,"9998876654","ADM","Adam","adam123@gov.in","male","Main Road, Uppal");	
	}

	//Long guestId,String phonenumber, String company, String name, String email, String gender, String address    
	  	@Test
	    void testAddGuest_Success() {
	        when(guestRepository.existsByEmail(guest.getEmail())).thenReturn(false);
	        when(guestRepository.existsByPhonenumber(guest.getPhonenumber())).thenReturn(false);
	        when(guestRepository.save(any(Guest.class))).thenReturn(guest);

	        String result = guestService.addGuest(guest);
	        assertEquals("guest added Successfully", result);
	    }

	   @Test
	    void testAddGuest_DuplicateEmail() {
	        when(guestRepository.existsByEmail(guest.getEmail())).thenReturn(true);

	        assertThrows(DuplicateGuestException.class, () -> guestService.addGuest(guest));
	    }

	    @Test
	    void testAddGuest_DuplicatePhoneNumber() {
	        when(guestRepository.existsByEmail(guest.getEmail())).thenReturn(false);
	        when(guestRepository.existsByPhonenumber(guest.getPhonenumber())).thenReturn(true);

	        assertThrows(DuplicateGuestException.class, () -> guestService.addGuest(guest));
	    }

	    @Test
	    void testGetGuestById_Success() {
	        when(guestRepository.findById(1L)).thenReturn(Optional.of(guest));

	        Guest result = guestService.getGuestById(1L);
	        assertEquals(guest, result);
	    }

	    @Test
	    void testGetGuestById_NotFound() {
	        when(guestRepository.findById(1L)).thenReturn(Optional.empty());

	        assertThrows(GuestNotFoundException.class, () -> guestService.getGuestById(1L));
	    }

	    @Test
	    void testUpdateGuest_Success() {
	        Guest guest = new Guest(1L,"1234567890", "ABC Corp","John Doe","johndoe@example.com","Male","123 Main St");
	        Guest updatedGuest = new Guest(1L,"0987654321","XYZ Corp","John Smith","johnsmith@example.com","Male","456 Elm St");

	        when(guestRepository.findById(1L)).thenReturn(Optional.of(guest));
	        when(guestRepository.save(any(Guest.class))).thenReturn(updatedGuest);
	        String result = guestService.updateGuest(1L, updatedGuest);
	        assertEquals("Database updated successfully", result);
	    }


	    @Test
	    void testUpdateGuest_DuplicateEmail() {
	        Guest updatedGuest = new Guest(1L, "0987654321","XYZ Corp", "John Smith","johnsmith@example.com",    "Male","456 Elm St");
	        when(guestRepository.findById(1L)).thenReturn(Optional.of(guest));
	        when(guestRepository.existsByEmail(updatedGuest.getEmail())).thenReturn(true);
	        assertThrows(DuplicateGuestException.class, () -> guestService.updateGuest(1L, updatedGuest));
	    }

	    @Test
	    void testUpdateGuest_DuplicatePhoneNumber() {
	        Guest guest = new Guest(1L, "1234567890", "ABC Corp", "John Doe", "johndoe@example.com", "Male", "123 Main St");
	        Guest updatedGuest = new Guest(1L, "0987654321", "XYZ Corp", "John Smith", "johnsmith@example.com", "Male", "456 Elm St");
	        when(guestRepository.findById(1L)).thenReturn(Optional.of(guest));
	        when(guestRepository.existsByPhonenumber(updatedGuest.getPhonenumber())).thenReturn(true);
	        assertThrows(DuplicateGuestException.class, () -> guestService.updateGuest(1L, updatedGuest));
	    }

	    @Test
	    void testGetAllGuests() {
	        List<Guest> guests = Arrays.asList(guest, new Guest(2L, "Jane Doe", "jane@example.com", "1112223333", "789 Oak St", "DEF Ltd", "Female"));
	        when(guestRepository.findAll()).thenReturn(guests);

	        List<Guest> result = guestService.getAllGuests();
	        assertEquals(2, result.size());
	    } 
	}



