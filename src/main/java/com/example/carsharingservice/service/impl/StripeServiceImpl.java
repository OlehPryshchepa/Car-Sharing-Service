package com.example.carsharingservice.service.impl;

import static com.stripe.param.checkout.SessionCreateParams.LineItem.PriceData.ProductData.builder;

import com.example.carsharingservice.config.StripeConfig;
import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.service.StripeService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StripeServiceImpl implements StripeService {
    private final StripeConfig stripeConfig;

    @Override
    public Session createSession(Payment payment, Car car) throws StripeException {
        Stripe.apiKey = stripeConfig.getSecretKey();
        SessionCreateParams.Builder builder = new SessionCreateParams.Builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")
                                .setUnitAmount(payment.getPaymentAmount().longValue())
                                .setProductData(builder()
                                        .setName("Payment")
                                        .setDescription(car.getBrand() + " "
                                                + car.getModel() + " rental")
                                        .build())
                                .build()
                        ).setQuantity(1L)
                        .build()
                ).setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("https://localhost:8080/payments/success" + "?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl("https://localhost:8080/payments/cancel");
        SessionCreateParams createParams = builder.build();
        return Session.create(createParams);
    }

    @Override
    public String checkStatus(String sessionId) {
        Stripe.apiKey = stripeConfig.getSecretKey();
        try {
            Session session = Session.retrieve(sessionId);
            return session.getStatus();
        } catch (StripeException e) {
            throw new RuntimeException("Can't retrieve session",e);
        }
    }
}


