package com.reservationmanagement.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.reservationmanagement.dto.RoomDTO;



@FeignClient(name="ROOMMANAGEMENT",url="http://localhost:8005")
public interface RoomsFeign {
	
	@GetMapping("/room/availablerooms")
	public List<RoomDTO> AvailableRooms();
	
	@GetMapping("/room/rooms/{capacity}")
	public List<RoomDTO> getRoomsByCapacity(@PathVariable Integer capacity);
	
	@GetMapping("/room/available/{type}/{capacity}")
	public List<RoomDTO> getAvailableRooms(@PathVariable String type, @PathVariable Integer capacity);
	
	

	@GetMapping("/room/Available/{type}")
	public List<RoomDTO> getRoomsByType(String status,@PathVariable String type );
	
	@GetMapping("room/{roomno}")
	public RoomDTO getRoomByRoomNo(@PathVariable Integer roomno);
	
	@GetMapping("Available/Roomcount")
	public Integer getNoOfAvailableRooms();
	
	
	
	@GetMapping("room/occupied")
	public List<RoomDTO> OccupiedRooms();
	
	
	
	@PutMapping("/room/updateroomstatus/{roomno}/{status}")
    void updateRoomStatus(@PathVariable("roomno") Integer roomno, @PathVariable("status") String status);
	
	@GetMapping("room/AllRooms")
	public List<RoomDTO> getAllRooms();
	

}
