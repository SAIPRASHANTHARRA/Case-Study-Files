package com.reservationmanagement.repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reservationmanagement.entity.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Integer> {

	boolean existsByRoomnoAndBookingStatus(Integer roomno, String bookingStatus);

	Reservation getReservationByRoomno(Integer roomno);

	List<Reservation> findByRoomnoAndCheckinDateLessThanAndCheckoutDateGreaterThan(
		    Integer roomNo, LocalDate checkOutDate, LocalDate checkInDate);
	
	 List<Reservation> findByRoomnoAndCheckinDateLessThanEqualAndCheckoutDateGreaterThanEqual(
		        Integer roomno, LocalDate checkoutDate, LocalDate checkinDate
		    );
	

	 
	 public List<Reservation> getReservationByCheckinDateAndCheckoutDate(LocalDate checkinDate,LocalDate checkoutDate);
}
