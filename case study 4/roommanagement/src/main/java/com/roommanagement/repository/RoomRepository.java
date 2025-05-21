package com.roommanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roommanagement.entity.Room;
import java.util.List;
@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {

	public List<Room> findRoomByCapacity(Integer capacity);
	
	//public void deleteRoomByRoomNo(Integer roomno);
	
	public Room findRoomByRoomno(Integer roomno);

	public List<Room> findRoomByStatus(String status);
	
	public List<Room> findByStatusAndType(String status,String type);
	
	public List<Room> findRoomByStatusAndTypeAndCapacity(String status,String type,Integer capacity);

	public Room findTypeAndCapacityByRoomno(Integer roomno);
	
	
	
}
