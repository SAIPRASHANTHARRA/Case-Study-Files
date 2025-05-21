package com.payment.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.payment.dto.BillingDTO;
import com.payment.entity.Payment;
import com.payment.feign.BillingFeign;
import com.payment.service.PaymentService;
import com.razorpay.RazorpayException;

@ExtendWith(MockitoExtension.class)
class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @Mock
    private BillingFeign billingFeign;

    @InjectMocks
    private PaymentController paymentController;

    private BillingDTO mockBilling;
    private Payment mockPayment;

    @BeforeEach
    void setUp() {
        mockBilling = new BillingDTO();
        mockBilling.setBillId(1L);
        mockBilling.setPrice(1500L);
        mockBilling.setEmail("test@example.com");

        mockPayment = new Payment();
        mockPayment.setBillId(1L);
        mockPayment.setStatus("Pending");
    }

    @Test
    void testGetBillByBillId() {
        when(billingFeign.getBillByBillId(1L)).thenReturn(mockBilling);

        BillingDTO result = paymentController.getBillByBillId(1L);

        assertNotNull(result);
        assertEquals(1L, result.getBillId());
        assertEquals(1500L, result.getPrice());
        assertEquals("test@example.com", result.getEmail());

        verify(billingFeign, times(1)).getBillByBillId(1L);
    }

    @Test
    void testCreatePayment() throws RazorpayException {
        when(paymentService.createPayment(1L)).thenReturn("http://payment-link.com");

        String paymentLink = paymentController.createPayment(1L);

        assertNotNull(paymentLink);
        assertEquals("http://payment-link.com", paymentLink);

        verify(paymentService, times(1)).createPayment(1L);
    }

    @Test
    void testGetPaymentByPaymentId() {
        when(paymentService.getPaymentByPaymentId(1)).thenReturn(mockPayment);

        Payment result = paymentController.getPaymentByPaymentId(1);

        assertNotNull(result);
        assertEquals("Pending", result.getStatus());

        verify(paymentService, times(1)).getPaymentByPaymentId(1);
    }
}
