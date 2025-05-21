package com.roommanagement.service;

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

import com.roommanagement.entity.Room;
import com.roommanagement.exception.DuplicateRoomNoException;
import com.roommanagement.exception.InvalidInputException;
import com.roommanagement.exception.RoomsNotFoundException;
import com.roommanagement.repository.RoomRepository;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @InjectMocks
    private RoomService roomService;

    @Mock
    private RoomRepository roomRepository;

    private Room room;

    @BeforeEach
    void setUp() {
        room = new Room(1L, 101, "Deluxe", 2, "Available");
    }

    @Test
    void testAddRoom_Success() {
        when(roomRepository.findRoomByRoomno(101)).thenReturn(null);
        when(roomRepository.save(room)).thenReturn(room);

        Room savedRoom = roomService.addRoom(room);

        assertNotNull(savedRoom);
        assertEquals(101, savedRoom.getRoomno());
    }

    @Test
    void testAddRoom_DuplicateRoomNo() {
        when(roomRepository.findRoomByRoomno(101)).thenReturn(room);
        
        assertThrows(DuplicateRoomNoException.class, () -> roomService.addRoom(room));
    }

    @Test
    void testGetAllRooms() {
        List<Room> rooms = Arrays.asList(room);
        when(roomRepository.findAll()).thenReturn(rooms);

        List<Room> result = roomService.getAllRooms();

        assertEquals(1, result.size());
    }

    @Test
    void testGetRoomByCapacity_Success() {
        when(roomRepository.findRoomByCapacity(2)).thenReturn(Arrays.asList(room));

        List<Room> result = roomService.getRoomByCapacity(2);

        assertEquals(1, result.size());
    }

    @Test
    void testGetRoomByCapacity_NotFound() {
        when(roomRepository.findRoomByCapacity(5)).thenReturn(Arrays.asList());

        assertThrows(RoomsNotFoundException.class, () -> roomService.getRoomByCapacity(5));
    }

    @Test
    void testDeleteRoom_Success() {
        when(roomRepository.findRoomByRoomno(101)).thenReturn(room);
        doNothing().when(roomRepository).delete(room);

        String result = roomService.deleteRoom(101);
        assertEquals("Room deleted Successfully", result);
    }

    @Test
    void testDeleteRoom_NotFound() {
        when(roomRepository.findRoomByRoomno(102)).thenReturn(null);

        assertThrows(RoomsNotFoundException.class, () -> roomService.deleteRoom(102));
    }

    @Test
    void testUpdateRoom_Success() {
        Room updatedRoom = new Room(1L, 101, "Standard", 3, "Occupied");
        when(roomRepository.findRoomByRoomno(101)).thenReturn(room);
        when(roomRepository.save(any(Room.class))).thenReturn(updatedRoom);

        String result = roomService.updateRoom(101, updatedRoom);

        assertEquals("Room updated successfully", result);
    }

    @Test
    void testUpdateRoom_NotFound() {
        when(roomRepository.findRoomByRoomno(102)).thenReturn(null);

        assertThrows(RoomsNotFoundException.class, () -> roomService.updateRoom(102, room));
    }

    @Test
    void testGetAvailableRooms() {
        when(roomRepository.findRoomByStatus("Available")).thenReturn(Arrays.asList(room));

        List<Room> result = roomService.AvailableRooms();

        assertEquals(1, result.size());
    }

    @Test
    void testGetOccupiedRooms() {
        when(roomRepository.findRoomByStatus("Occupied")).thenReturn(Arrays.asList(room));

        List<Room> result = roomService.OccupiedRooms();

        assertEquals(1, result.size());
    }
}
