package com.reservationmanagement.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reservationmanagement.dto.GuestDTO;
import com.reservationmanagement.dto.RoomDTO;
import com.reservationmanagement.entity.Reservation;
import com.reservationmanagement.feign.GuestFeign;
import com.reservationmanagement.feign.RoomsFeign;
import com.reservationmanagement.repository.ReservationRepository;
import com.reservationmanagement.service.ReservationService;

@RestController
@RequestMapping("reservation")
public class ReservationController {


	
	@Autowired
	ReservationService reservationService;
	
	@Autowired
	RoomsFeign roomFeign;
	
	@Autowired 
	GuestFeign guestFeign;

 
	
	@PostMapping("/add")
	public Reservation addReservation(@RequestBody Reservation reservation) {
		return reservationService.addReservation(reservation);
	}
	
	@GetMapping("/allReservations")
	public List<Reservation> getReservation(){
		return reservationService.getReservation();
	}

	@PutMapping("/updateReservation/{reservationId}")
	public Reservation updateReservation(@PathVariable Integer reservationId, @RequestBody Reservation reservation) {
		return reservationService.updateReservation(reservationId,reservation );
	}
	
	@GetMapping("room/availablerooms")
	public List<RoomDTO> fetchAvailableRooms(){
		return roomFeign.AvailableRooms();
	}
	
	@GetMapping("room/occupiedrooms")
	public List<RoomDTO> OccupiedRooms(){
		return roomFeign.OccupiedRooms();
	}
	
	@GetMapping("/room/rooms/{capacity}")
	public List<RoomDTO> getRoomsByCapacity(@PathVariable Integer capacity){
		return roomFeign.getRoomsByCapacity(capacity);
	}

	@GetMapping("/room/available/{type}/{capacity}")
	public List<RoomDTO> getAvailableRoomsByTypeAndCapacity(String status,@PathVariable String type, @PathVariable Integer capacity){
		return roomFeign.getAvailableRooms( type, capacity);
	}
	
	@GetMapping("guest/guestId/{guestId}")
	public GuestDTO fetchGuestById(@PathVariable Long guestId) {
		GuestDTO guestDTO= guestFeign.getGuestById(guestId);
		
		if(guestDTO==null) {
			throw new RuntimeException("Guest Not Found dor ID: "+ guestId);
		}
		return guestDTO;
			
	}
	
	@GetMapping("/guest/AllGuests")
	public List<GuestDTO> fetchAllGuests(){
		return guestFeign.getAllGuests();
	}
	
	@GetMapping("room/Available/Roomcount")
	public Integer getNoOfAvailableRooms(){
		return roomFeign.getNoOfAvailableRooms();
	}
	@GetMapping("room/{roomno}")
	public RoomDTO getRoomByRoomNo(@PathVariable Integer roomno) {
		return roomFeign.getRoomByRoomNo(roomno);
	}
	
	
	/*@PutMapping("room/updateroomstatus/{roomno}/{status}")
	public void updateRoomStatus(@PathVariable Integer roomno, @PathVariable String status) {
	    roomFeign.updateRoomStatus(roomno, status);
	} */
	
	@DeleteMapping("/cancelreservation/{reservationId}")
	public String cancelReservation(@PathVariable Integer reservationId) {
		return reservationService.cancelReservation(reservationId);
		}
	@GetMapping("/reservationdetails/{reservationId}")
	public Reservation getReservationDetails(@PathVariable Integer reservationId) {
		return reservationService.getReservationDetails(reservationId);
	}
	
	@GetMapping("/reservationdetails/{checkinDate}/{checkoutDate}")
	public  List<Reservation> getReservationByCheckinDateAndCheckoutDate( @PathVariable LocalDate checkinDate,@PathVariable LocalDate checkoutDate){
		return reservationService.getReservationByCheckinDateAndCheckoutDate( checkinDate,checkoutDate);
	}
	
	@GetMapping("/reservationdetails/availablerooms/{checkinDate}/{checkoutDate}")
		public List<RoomDTO> getAvailableRooms(@PathVariable LocalDate checkinDate,@PathVariable LocalDate checkoutDate){
		return reservationService.getAvailableRooms(checkinDate, checkoutDate);
	}
	
	@GetMapping("/reservationdetails/occupiedrooms/{checkinDate}/{checkoutDate}")
	public List<RoomDTO> getOccupiedRooms(@PathVariable LocalDate checkinDate,@PathVariable LocalDate checkoutDate){
		return reservationService.getOccupiedRooms(checkinDate, checkoutDate);
	}
	
	
}
