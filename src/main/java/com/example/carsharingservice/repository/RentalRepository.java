package com.example.carsharingservice.repository;

import com.example.carsharingservice.model.Rental;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByUserIdAndActualReturnDateIsNull(Long userId, PageRequest request);

    List<Rental> findByUserIdAndActualReturnDateIsNotNull(Long userId, PageRequest request);

    @Query("FROM Rental r " +
            "INNER JOIN FETCH r.car " +
            "INNER JOIN FETCH r.user " +
            "WHERE r.actualReturnDate=NULL AND r.returnDate < :localDateTime")
    List<Rental> findAllByActualReturnDateNullAndReturnDateLessThan(LocalDateTime localDateTime);
}
