package com.example.carsharingservice.dto.request;

import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.model.Rental;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class PaymentRequestDto {
    private Payment.PaymentStatus paymentStatus;
    private Payment.PaymentType paymentType;
    private Long rentalId;
    private String paymentUrl;
    private String paymentSessionId;
    private BigDecimal paymentAmount;
}
