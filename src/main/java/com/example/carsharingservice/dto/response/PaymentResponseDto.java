package com.example.carsharingservice.dto.response;

import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.model.Rental;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class PaymentResponseDto {
    private Long id;
    private Payment.PaymentStatus paymentStatus;
    private Payment.PaymentType paymentType;
    private Long rentalId;
    private String paymentUrl;
    private String paymentSessionId;
    private BigDecimal paymentAmount;
}
