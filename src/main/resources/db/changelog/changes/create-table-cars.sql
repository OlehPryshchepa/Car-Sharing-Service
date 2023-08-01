--liquibase formatted sql
--changeset CS5-create-cars:create-table

CREATE TABLE `cars` (
                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                      model VARCHAR(255) NOT NULL,
                      brand VARCHAR(255) NOT NULL,
                      type ENUM('SEDAN', 'SUV', 'HATCHBACK', 'UNIVERSAL') NOT NULL,
                      inventory INT NOT NULL,
                      daily_fee DECIMAL(10, 2) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
