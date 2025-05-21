package com.billingmanagement.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.billingmanagement.dto.ReservationDTO;
import com.billingmanagement.entity.Billing;
import com.billingmanagement.exception.ReservationNotFoundException;
import com.billingmanagement.feign.ReservationFeign;
import com.billingmanagement.repository.BillingRepository;
import com.billingmanagement.services.BillingService;

@ExtendWith(MockitoExtension.class)
class BillingServiceTest {

    @Mock
    private BillingRepository billingRepository;

    @Mock
    private ReservationFeign reservationFeign;

    @InjectMocks
    private BillingService billingService;

    private ReservationDTO reservation;

    @BeforeEach
    void setUp() {
        reservation = new ReservationDTO();
        reservation.setCapacity(2);
        reservation.setRoom_type("Deluxe");
        reservation.setNo_of_nights(3);
        reservation.setEmail("test@example.com");
    }

    @Test
    void testGenerateBill_Success() {
        Integer reservationId = 1;
        Integer foodQuantity = 2;
        Integer expectedPrice = (1000 * 2 * 3) + (200 * 2); // 6000 + 400 = 6400

        when(reservationFeign.getReservationDetails(reservationId)).thenReturn(reservation);
        when(billingRepository.save(any(Billing.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Billing bill = billingService.generateBill(reservationId, foodQuantity);

        assertNotNull(bill);
        assertEquals(reservationId, bill.getReservation_code());
        assertEquals(expectedPrice, bill.getPrice());
        assertEquals("test@example.com", bill.getEmail());
    }

    @Test
    void testGenerateBill_ReservationNotFound() {
        Integer reservationId = 2;
        when(reservationFeign.getReservationDetails(reservationId)).thenReturn(null);

        assertThrows(ReservationNotFoundException.class, () -> billingService.generateBill(reservationId, 2));
    }

    @Test
    void testGetBillByBillId_Success() {
        Long billId = 1L;
        Billing billing = new Billing(1, 6400, 2, "test@example.com");
        when(billingRepository.findById(billId)).thenReturn(Optional.of(billing));

        Billing foundBill = billingService.getBillByBillId(billId);

        assertNotNull(foundBill);
        assertEquals(6400, foundBill.getPrice());
    }

    @Test
    void testGetBillByBillId_NotFound() {
        Long billId = 2L;
        when(billingRepository.findById(billId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> billingService.getBillByBillId(billId));
    }
}
