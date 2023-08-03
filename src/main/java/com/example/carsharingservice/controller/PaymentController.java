package com.example.carsharingservice.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import com.example.carsharingservice.dto.request.PaymentRequestDto;
import com.example.carsharingservice.dto.response.PaymentResponseDto;
import com.example.carsharingservice.mapper.DtoMapper;
import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.service.PaymentCalculationService;
import com.example.carsharingservice.service.PaymentService;
import com.example.carsharingservice.service.StripeService;
import com.stripe.model.checkout.Session;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/payments")
public class PaymentController {
    private final StripeService stripeService;
    private final PaymentService paymentService;
    private final DtoMapper<PaymentRequestDto, PaymentResponseDto, Payment> paymentDtoMapper;
    private final PaymentCalculationService paymentCalculationService;

    @PostMapping
    public PaymentResponseDto create(@Valid @RequestBody PaymentRequestDto paymentRequestDto) {
        Payment payment = paymentDtoMapper.mapToModel(paymentRequestDto);
        BigDecimal moneyToPay = paymentCalculationService.calculatePaymentAmount(payment);
        BigDecimal moneyToFine = paymentCalculationService.calculateFineAmount(payment);
        payment.setPaymentAmount(moneyToPay);
        Session session = stripeService.createPaymentSession(payment.getPaymentAmount(),
                moneyToFine, payment);
        payment.setPaymentSessionId(session.getId());
        payment.setPaymentUrl(session.getUrl());
        payment = paymentService.save(payment);
        return paymentDtoMapper.mapToDto(payment);
    }

    @GetMapping
    public List<PaymentResponseDto> getByUserId(@RequestParam Long userId) {
        return paymentService.getByUserId(userId).stream()
                .map(paymentDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/my-payments")
    public List<PaymentResponseDto> findAllMyPayments(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userEmail = userDetails.getUsername();
        return paymentService.getByUserEmail(userEmail).stream()
                .map(paymentDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/success/{id}")
        public PaymentResponseDto getSucceed(@PathVariable Long id) {
            Payment payment = paymentService.getById(id);
            payment.setPaymentStatus(Payment.PaymentStatus.PAID);
            return paymentDtoMapper.mapToDto(paymentService.save(payment));
        }

    @GetMapping("/cancel/{id}")
    public PaymentResponseDto getCanceled(@PathVariable Long id) {
        return paymentDtoMapper.mapToDto(paymentService.save(paymentService.getById(id)));
    }
}
