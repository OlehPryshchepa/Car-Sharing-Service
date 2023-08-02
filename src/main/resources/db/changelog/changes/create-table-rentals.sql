--liquibase formatted sql
--changeset CS5-create-rentals:create-table

CREATE TABLE `rentals` (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         rental_date DATE NOT NULL,
                         return_date DATE NOT NULL,
                         actual_return_date DATE,
                         car_id BIGINT NOT NULL,
                         user_id BIGINT NOT NULL,
                         FOREIGN KEY (car_id) REFERENCES cars (id),
                         FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;