--liquibase formatted sql
--changeset CS5-create-payments:create-table

CREATE TABLE `payments` (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          status ENUM('PENDING', 'PAID') NOT NULL,
                          type ENUM('PAYMENT', 'FINE') NOT NULL,
                          rentals_id BIGINT NOT NULL,
                          session_url VARCHAR(255) NOT NULL,
                          session_id VARCHAR(255) NOT NULL,
                          amount_to_pay DECIMAL(10, 2) NOT NULL,
                          FOREIGN KEY (rentals_id) REFERENCES rentals (id)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;