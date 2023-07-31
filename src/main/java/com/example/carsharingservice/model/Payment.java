package com.example.carsharingservice.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType paymentType;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Rental rental;
    @Column(nullable = false)
    private String paymentUrl;
    @Column(nullable = false)
    private String paymentSessionId;
    @Column(nullable = false)
    private BigDecimal paymentAmount;

    public enum PaymentStatus {
        PENDING,
        PAID
    }

    public enum PaymentType {
        PAYMENT,
        FINE
    }
}
