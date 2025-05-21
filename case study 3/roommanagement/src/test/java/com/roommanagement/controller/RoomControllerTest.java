package com.roommanagement.controller;

import com.roommanagement.entity.Room;
import com.roommanagement.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RoomControllerTest {

    @Mock
    private RoomService roomService;

    @InjectMocks
    private RoomController roomController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(roomController).build();
    }

    @Test
    void testGetAllRooms() throws Exception {
        Room room1 = new Room(1L, 101, "Deluxe", 2, "Available");
        Room room2 = new Room(2L, 102, "Standard", 1, "Occupied");
        List<Room> rooms = Arrays.asList(room1, room2);

        when(roomService.getAllRooms()).thenReturn(rooms);

        mockMvc.perform(get("/room/AllRooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetRoomByCapacity() throws Exception {
        Room room = new Room(1L, 101, "Deluxe", 2, "Available");
        List<Room> rooms = Arrays.asList(room);

        when(roomService.getRoomByCapacity(2)).thenReturn(rooms);

        mockMvc.perform(get("/room/rooms/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testAddRoom() throws Exception {
        Room room = new Room(1L, 101, "Deluxe", 2, "Available");

        when(roomService.addRoom(any(Room.class))).thenReturn(room);

        mockMvc.perform(post("/room/addRoom")
                        .contentType("application/json")
                        .content("{\"roomID\":1,\"roomno\":101,\"type\":\"Deluxe\",\"capacity\":2,\"status\":\"Available\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateRoom() throws Exception {
        when(roomService.updateRoom(eq(101), any(Room.class)))
                .thenReturn("Room updated successfully");

        mockMvc.perform(put("/room/updateroom/101")  // Correct path
                        .contentType("application/json")
                        .content("{\"roomID\":1,\"roomno\":101,\"type\":\"Deluxe\",\"capacity\":2,\"status\":\"Occupied\"}"))
                .andExpect(status().isOk())  // Should return 200 now
                .andExpect(content().string("Room updated successfully"));
    }


    @Test
    void testDeleteRoom() throws Exception {
     //   doNothing().when(roomService).deleteRoom(101);

        mockMvc.perform(delete("/room/deleteRoom/101"))
                .andExpect(status().isOk());

        verify(roomService, times(1)).deleteRoom(101);
    }


    @Test
    void testGetAvailableRooms() throws Exception {
        Room room1 = new Room(1L, 101, "Deluxe", 2, "Available");
        List<Room> rooms = Arrays.asList(room1);

        when(roomService.AvailableRooms()).thenReturn(rooms);

        mockMvc.perform(get("/room/availablerooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }
}
