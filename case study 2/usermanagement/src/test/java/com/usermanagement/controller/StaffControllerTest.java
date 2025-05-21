package com.usermanagement.controller;

import com.usermanagement.entity.Staff;
import com.usermanagement.service.StaffService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

class StaffControllerTest {

    @Mock
    private StaffService staffService;

    @InjectMocks
    private StaffController staffController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(staffController).build();
    }

    @Test
    void testGetAllStaff() throws Exception {
        Staff staff1 = new Staff("John Doe", "NYC", 12345L, 5000, 30, "Manager", "12345", "johndoe@example.com");
        Staff staff2 = new Staff("Jane Smith", "LA", 67890L, 4500, 28, "Developer", "67890", "janesmith@example.com");
        List<Staff> staffList = Arrays.asList(staff1, staff2);

        when(staffService.getAllStaff()).thenReturn(staffList);

        mockMvc.perform(get("/staff/AllStaff"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetStaffByOccupation() throws Exception {
        Staff staff1 = new Staff("John Doe", "NYC", 12345L, 5000, 30, "Manager", "12345", "johndoe@example.com");
        List<Staff> staffList = Arrays.asList(staff1);

        when(staffService.getStaffByOccupation("Manager")).thenReturn(staffList);

        mockMvc.perform(get("/staff/occupation/Manager"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testAddStaff() throws Exception {
        Staff staff = new Staff("John Doe", "NYC", 12345L, 5000, 30, "Manager", "12345", "johndoe@example.com");
        
        when(staffService.addStaff(any(Staff.class))).thenReturn("Staff added successfully");

        mockMvc.perform(post("/staff/addStaff")
                        .contentType("application/json")
                        .content("{\"name\":\"John Doe\",\"address\":\"NYC\",\"nic\":12345,\"salary\":5000,\"age\":30,\"occupation\":\"Manager\",\"phoneno\":\"12345\",\"email\":\"johndoe@example.com\"}")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Staff added successfully"));
    }

    @Test
    void testUpdateStaff() throws Exception {
        Staff existingStaff = new Staff("John Doe", "NYC", 12345L, 5000, 30, "Manager", "12345", "johndoe@example.com");
        Staff updatedStaff = new Staff("John Doe", "NYC", 12345L, 5500, 30, "Manager", "12345", "johnupdated@example.com");

        when(staffService.updateStaff(eq(1L), any(Staff.class))).thenReturn("Staff updated in Database successfully");

        mockMvc.perform(put("/staff/update/1")
                        .contentType("application/json")
                        .content("{\"name\":\"John Doe\",\"address\":\"NYC\",\"nic\":12345,\"salary\":5500,\"age\":30,\"occupation\":\"Manager\",\"phoneno\":\"12345\",\"email\":\"johnupdated@example.com\"}")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Staff updated in Database successfully"));
    }

    @Test
    void testDeleteStaff() throws Exception {
        doNothing().when(staffService).deleteStaffByCode(1L);

        mockMvc.perform(delete("/staff/delete/1"))
                .andExpect(status().isOk());

        verify(staffService, times(1)).deleteStaffByCode(1L);
    }
}
