package com.roommanagement.service;

import org.slf4j.Logger;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roommanagement.entity.Room;
import com.roommanagement.exception.DuplicateRoomNoException;
import com.roommanagement.exception.InvalidInputException;
import com.roommanagement.exception.RoomsNotFoundException;
import com.roommanagement.repository.RoomRepository;

@Service
public class RoomService  {
	
	@Autowired
	RoomRepository roomRepository;
	
	private static final Logger logger =  LoggerFactory.getLogger(RoomService.class);

	public Room addRoom(Room room) {
		
		Room existingRoom = roomRepository.findRoomByRoomno(room.getRoomno());
			
		if (existingRoom!=null) {
			logger.warn("Room with room no {} for update ",room.getRoomno());
			throw new DuplicateRoomNoException("Room with "+existingRoom+" already exists");
		}
		
		if (room.getRoomno()==null || room.getRoomno()<0  ) {
			logger.warn("Invalid Room number format for room  {} ",room.getRoomno());
			throw new InvalidInputException("Invalid input "+room.getRoomno());
		}

		if (room.getCapacity()==null || room.getCapacity()<0) {
			logger.warn("Invalid Capacity for room  {} ",room.getRoomno());
			throw new InvalidInputException("Invalid input "+room.getCapacity()+ " for room capacity");
		}
		if (!(room.getStatus().equals("Available")||room.getStatus().equals("Occupied")||room.getStatus().equals("Under Maintainance"))) {
			logger.warn("Invalid Status for room  {} ",room.getRoomno());
			throw new InvalidInputException("Invalid input "+room.getStatus()+" for room status");
		}
		if(!(room.getType().equals("Standard")|| room.getType().equals("Deluxe")|| room.getType().equals("Basic"))) {
			logger.warn("Invalid Room type for room  {} ",room.getRoomno());
			throw new InvalidInputException("Invalid input"+room.getType()+" for room type");
		}
		
		
		return roomRepository.save(room);
	
	}
	
	public List<Room> getAllRooms(){
		logger.info("Fetching all rooms");
		return roomRepository.findAll();
	}
	
	public List<Room> getRoomByCapacity(Integer capacity){
		logger.info("Fetching rooms by capacity");
		List<Room> roombycapacity = roomRepository.findRoomByCapacity(capacity);
		
		if (roombycapacity.isEmpty()) {
			logger.warn("Rooms with capacity {} not found ",capacity);
			throw new RoomsNotFoundException("Rooms with capacity "+capacity+" not found");
		}
		return roombycapacity;
	}
	
	public String deleteRoom(Integer roomno) {
		
		 Room deleteroom= roomRepository.findRoomByRoomno(roomno);
		 
		if (deleteroom==null) {
			logger.warn("Room number {} not found to delete ",roomno);
			throw new RoomsNotFoundException("Room with "+roomno+" not found");
		}
	
		roomRepository.delete(deleteroom);
		return "Room deleted Successfully";
	}
	
	public String updateRoom(Integer roomno,Room updatedRoom) {
		Room existingroom = roomRepository.findRoomByRoomno(roomno);
		
		if(existingroom == null) {
			logger.warn("Room number {} not found to update ",roomno);
			throw new RoomsNotFoundException("Room with "+ roomno+ " not found");
		} 
		
		Room duproom = roomRepository.findRoomByRoomno(updatedRoom.getRoomno());
			
		if (duproom!= null && !duproom.getRoomno().equals(existingroom.getRoomno())) {
			logger.warn("Duplicate room number {} found while updating room {}", duproom.getRoomno(), existingroom.getRoomno());
			throw new DuplicateRoomNoException("Room with "+updatedRoom.getRoomno()+" already exists");
		} 
		
		
		
		if (updatedRoom.getCapacity()==null || updatedRoom.getCapacity()<0) {
			 logger.warn("Invalid input {} for room capacity", updatedRoom.getCapacity());
			throw new InvalidInputException("Invalid input "+updatedRoom.getCapacity()+ " for room capacity");
		}
		if (!(updatedRoom.getStatus().equals("Available")||updatedRoom.getStatus().equals("Occupied")||updatedRoom.getStatus().equals("Under Maintainance"))) {
			 logger.warn("Invalid input {} for room status", updatedRoom.getStatus());
			throw new InvalidInputException("Invalid input "+updatedRoom.getStatus()+" for room status");
		}
		if(!(updatedRoom.getType().equals("Standard")|| updatedRoom.getType().equals("Deluxe")|| updatedRoom.getType().equals("Basic"))) {
			 logger.warn("Invalid input {} for room type", updatedRoom.getType());
			throw new InvalidInputException("Invalid input "+updatedRoom.getType()+" for room type");
		}
		
		
		existingroom.setRoomno(updatedRoom.getRoomno());
		existingroom.setCapacity(updatedRoom.getCapacity());
		existingroom.setStatus(updatedRoom.getStatus());
		existingroom.setType(updatedRoom.getType());
		
		
		roomRepository.save(existingroom);
		return "Room updated successfully";
	}
	
	public List<Room> AvailableRooms(){
		logger.info("Fetching available rooms");
		return roomRepository.findRoomByStatus("Available");
	}
	public List<Room> OccupiedRooms(){
		logger.info("Fetching occupied rooms");
		return roomRepository.findRoomByStatus("Occupied");
	}
	
	public List<Room> getAvailableRoomsByType(String status,String type){
		/*List<Room> room = */ 
		List<Room> rooms= roomRepository.findByStatusAndType(status,type);
		
		if (!(type.equals("Deluxe") || type.equals("Basic") || type.equals("Standard"))) {
			logger.warn("Rooms not found with type {} ",type);
			throw new InvalidInputException("Rooms not found with type "+type);
		}
		
		if (rooms.isEmpty()) {
			logger.warn("Rooms not found with type {} ",type);
			throw new RoomsNotFoundException("Rooms not available with type: "+type);
		} 
		
		return rooms;
		//return roomRepository.findAvailableRoomByType(type);
	}
	
	public List<Room> getAvailableRoomsByTypeAndCapacity(String status,String type,Integer capacity){
		
		logger.info("Fetching available rooms based on {} and {} ",type,capacity);
		List<Room> rooms1= roomRepository.findRoomByStatusAndTypeAndCapacity(status,type,capacity);
		
		if (!(type.equals("Deluxe") || type.equals("Basic") || type.equals("Standard"))) {
			logger.warn("Rooms not found with type {} ",type);
			throw new InvalidInputException("Rooms not found with type "+type);
		}

		if (rooms1.isEmpty()) {
			logger.warn("Rooms not found with type {} ",type,capacity);
			throw new RoomsNotFoundException("Rooms not available with type: "+ type+" and capacity: "+capacity);
		}
		return rooms1;
	}
	
	public Integer getNoOfAvailableRooms() {
		logger.info("Fetching number of rooms available");
		return roomRepository.findRoomByStatus("Available").size();
	}
	
	
	public String updateRoomStatus(Integer roomno, String status) {
	    Room room = roomRepository.findRoomByRoomno(roomno);
	    
	    room.setStatus(status); 
	    roomRepository.save(room);  

	    return "Room No " + roomno + " updated to status: " + status;
	}
	
	public Room getRoomTypeAndCapacityByRoomNo(Integer roomno) {
		logger.info("Fetching room type and capacity by {}",roomno);
		return roomRepository.findTypeAndCapacityByRoomno(roomno);
	}


	
	
	public Room getRoomByRoomNo(Integer roomno) {
		logger.info("Fetching room details by roomno");
		return roomRepository.findRoomByRoomno(roomno);
	}
}
