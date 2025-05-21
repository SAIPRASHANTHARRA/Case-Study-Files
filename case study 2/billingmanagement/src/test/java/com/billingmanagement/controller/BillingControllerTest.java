package com.billingmanagement.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.billingmanagement.dto.ReservationDTO;
import com.billingmanagement.entity.Billing;
import com.billingmanagement.feign.ReservationFeign;
import com.billingmanagement.services.BillingService;

@WebMvcTest(BillingController.class)
public class BillingControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private BillingService billingService;

    @MockBean
    private ReservationFeign reservationFeign;

    @InjectMocks
    private BillingController billingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(billingController).build();
    }

    @Test
    void testGetReservationDetails() throws Exception {
        ReservationDTO reservationDTO = new ReservationDTO(1, "Deluxe", 2, 3, "test@example.com",1);
        when(reservationFeign.getReservationDetails(1)).thenReturn(reservationDTO);

        mockMvc.perform(get("/billing/reservation/reservationdetails/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGenerateBill() throws Exception {
        Billing billing = new Billing(1, 3000, 2, "test@example.com");
        when(billingService.generateBill(1, 2)).thenReturn(billing);

        mockMvc.perform(post("/billing/bill/1/2"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetBillByBillId() throws Exception {
        Billing billing = new Billing(1, 3000, 2, "test@example.com");
        when(billingService.getBillByBillId(1L)).thenReturn(billing);

        mockMvc.perform(get("/billing/bill/1"))
                .andExpect(status().isOk());
    }
}