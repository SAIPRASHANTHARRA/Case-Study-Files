package com.usermanagement.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.usermanagement.entity.Staff;
import com.usermanagement.exception.*;
import com.usermanagement.repository.StaffRepository;

@ExtendWith(MockitoExtension.class)
class StaffServiceTest {

    @Mock
    private StaffRepository staffRepository;

    @InjectMocks
    private StaffService staffService;

    private Staff staff;

    @BeforeEach
    void setUp() {
        staff = new Staff();
        staff.setCode(1L);
        staff.setName("John Doe");
        staff.setEmail("john.doe@example.com");
        staff.setPhoneno("1234567890");
        staff.setNic(123456789L);
        staff.setAddress("123 Main Street");
        staff.setOccupation("Manager");
        staff.setAge(30);
        staff.setSalary(50000);
    }

    // Test for valid staff addition
    @Test
    void testAddStaff_ValidStaff() {
        when(staffRepository.existsByEmail(staff.getEmail())).thenReturn(false);
        when(staffRepository.existsByPhoneno(staff.getPhoneno())).thenReturn(false);
        when(staffRepository.existsByNic(staff.getNic())).thenReturn(false);
        when(staffRepository.save(any(Staff.class))).thenReturn(staff);

        String response = staffService.addStaff(staff);

        assertEquals("Staff added successfully", response);
    }

    // Test for duplicate email
    @Test
    void testAddStaff_DuplicateEmail() {
        when(staffRepository.existsByEmail(staff.getEmail())).thenReturn(true);

        DuplicateStaffException exception = assertThrows(DuplicateStaffException.class, () -> {
            staffService.addStaff(staff);
        });

        assertEquals("Staff with email " + staff.getEmail() + " already exists", exception.getMessage());
    }

    // Test for missing name
    @Test
    void testAddStaff_EmptyName() {
        staff.setName(""); // Set empty name

        InvalidStaffInputException exception = assertThrows(InvalidStaffInputException.class, () -> {
            staffService.addStaff(staff);
        });

        assertEquals("Staff name cannot be empty", exception.getMessage());
    }

    // Test for invalid phone number length
    @Test
    void testAddStaff_InvalidPhoneNumberLength() {
        staff.setPhoneno("12345"); // Invalid phone number

        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            staffService.addStaff(staff);
        });

        assertEquals("Length of Staff phonenumber cannot be lesser than or greater than 10", exception.getMessage());
    }

    // Test for invalid email format
    @Test
    void testAddStaff_InvalidEmailFormat() {
        staff.setEmail("invalidemail"); // Invalid email format

        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            staffService.addStaff(staff);
        });

        assertEquals("Invalid Email format", exception.getMessage());
    }

    // Test for updating a staff
    @Test
    void testUpdateStaff_ValidUpdate() {
        // Stub the necessary methods only
        when(staffRepository.findById(1L)).thenReturn(Optional.of(staff));
        when(staffRepository.save(any(Staff.class))).thenReturn(staff);

        String response = staffService.updateStaff(1L, staff);

        assertEquals("Staff updated in Database successfully", response);
    }


    // Test for staff not found when updating
    @Test
    void testUpdateStaff_StaffNotFound() {
        when(staffRepository.findById(1L)).thenReturn(Optional.empty());

        StaffNotFoundException exception = assertThrows(StaffNotFoundException.class, () -> {
            staffService.updateStaff(1L, staff);
        });

        assertEquals("Staff not found with code:1", exception.getMessage());
    }

    // Test for retrieving staff by occupation
    @Test
    void testGetStaffByOccupation() {
        when(staffRepository.findStaffByOccupation("Manager")).thenReturn(Arrays.asList(staff));

        List<Staff> staffList = staffService.getStaffByOccupation("Manager");

        assertEquals(1, staffList.size());
    }

    // Test for staff not found by occupation
    @Test
    void testGetStaffByOccupation_StaffNotFound() {
        when(staffRepository.findStaffByOccupation("NonExistingOccupation")).thenReturn(Arrays.asList());

        StaffNotFoundException exception = assertThrows(StaffNotFoundException.class, () -> {
            staffService.getStaffByOccupation("NonExistingOccupation");
        });

        assertEquals("No Staff with occupation: NonExistingOccupation", exception.getMessage());
    }

    // Test for deleting a staff
    @Test
    void testDeleteStaff_Valid() {
        when(staffRepository.existsById(1L)).thenReturn(true);
        doNothing().when(staffRepository).deleteById(1L);

        staffService.deleteStaffByCode(1L);

        verify(staffRepository, times(1)).deleteById(1L);
    }

    // Test for staff not found when deleting
    @Test
    void testDeleteStaff_NotFound() {
        when(staffRepository.existsById(1L)).thenReturn(false);

        StaffNotFoundException exception = assertThrows(StaffNotFoundException.class, () -> {
            staffService.deleteStaffByCode(1L);
        });

        assertEquals("Staff with id 1 Not Found", exception.getMessage());
    }
}
