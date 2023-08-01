--liquibase formatted sql
--changeset CS5:create-tables

CREATE TABLE Users (
                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                      email VARCHAR(255) NOT NULL,
                      first_name VARCHAR(100) NOT NULL,
                      last_name VARCHAR(100) NOT NULL,
                      password VARCHAR(255) NOT NULL,
                      role ENUM('MANAGER', 'CUSTOMER') NOT NULL
);

CREATE TABLE Cars (
                     id BIGINT PRIMARY KEY AUTO_INCREMENT,
                     model VARCHAR(255) NOT NULL,
                     brand VARCHAR(255) NOT NULL,
                     type ENUM('SEDAN', 'SUV', 'HATCHBACK', 'UNIVERSAL') NOT NULL,
                     inventory INT NOT NULL,
                     daily_fee DECIMAL(10, 2) NOT NULL
);

CREATE TABLE Rentals (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        rental_date DATE NOT NULL,
                        return_date DATE NOT NULL,
                        actual_return_date DATE,
                        car_id BIGINT NOT NULL,
                        user_id BIGINT NOT NULL,
                        FOREIGN KEY (car_id) REFERENCES Car (id),
                        FOREIGN KEY (user_id) REFERENCES User (id)
);

CREATE TABLE Payments (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         status ENUM('PENDING', 'PAID') NOT NULL,
                         type ENUM('PAYMENT', 'FINE') NOT NULL,
                         rental_id BIGINT NOT NULL,
                         session_url VARCHAR(255) NOT NULL,
                         session_id VARCHAR(255) NOT NULL,
                         amount_to_pay DECIMAL(10, 2) NOT NULL,
                         FOREIGN KEY (rental_id) REFERENCES Rental (id)
);