package com.roommanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roommanagement.entity.Room;
import com.roommanagement.service.RoomService;

@RestController
@RequestMapping("room")
public class RoomController {
	
	@Autowired
	RoomService roomService;
	
	@PostMapping("addRoom")
	public Room addRoom(@RequestBody Room room) {
		return roomService.addRoom(room);
	}
	
	@GetMapping("AllRooms")
	public List<Room> getAllRooms(){
		return roomService.getAllRooms();
	}
	
	@GetMapping("rooms/{capacity}")
	public List<Room> getRoomsByCapacity(@PathVariable Integer capacity){
		return roomService.getRoomByCapacity(capacity);
	}
	
	@PutMapping("updateroom/{roomno}")
	public String updateRoom(@PathVariable Integer roomno, @RequestBody Room room) {
		return roomService.updateRoom(roomno, room);
	}

	@DeleteMapping("deleteRoom/{roomno}")
	public void deleteRoom(@PathVariable Integer roomno ) {
		 roomService.deleteRoom(roomno);
	}
	
	@GetMapping("/availablerooms")
	public List<Room> AvailableRooms(){
		return roomService.AvailableRooms();
	}
	
	
	@GetMapping("/Available/{type}")
	public List<Room> getRoomsByType(String status,@PathVariable String type ){
		return roomService.getAvailableRoomsByType("Available",type);
	}
	
	@GetMapping("/available/{type}/{capacity}")
	public List<Room> getAvailableRooms(String status,@PathVariable String type, @PathVariable Integer capacity){
		return roomService.getAvailableRoomsByTypeAndCapacity("Available",type, capacity);
	}
	
	@GetMapping("Available/Roomcount")
	public Integer getNoOfAvailableRooms(){
		return roomService.getNoOfAvailableRooms();
	}
	
	@GetMapping("/occupied")
	public List<Room> OccupiedRooms(){
		return roomService.OccupiedRooms();
	}
	
	@GetMapping("/{roomno}")
	public Room getRoomByRoomNo(@PathVariable Integer roomno){
		return roomService.getRoomByRoomNo(roomno);
	}
	
	
	@PutMapping("/updateroomstatus/{roomno}/{status}")
	public void updateRoomStatus(@PathVariable Integer roomno, @PathVariable String status) {
	    roomService.updateRoomStatus(roomno, status);
	}

	@GetMapping("/roomtypeandcapacity/{roomno}")
	public Room getRoomTypeAndCapacityByRoomNo(@PathVariable Integer roomno) {
		return roomService.getRoomTypeAndCapacityByRoomNo(roomno);
	}
	

}
