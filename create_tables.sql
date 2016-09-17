drop database flying_dog_airline;
create database flying_dog_airline;
​
drop table if exists plane;
CREATE TABLE plane (
    id_plane INT PRIMARY KEY AUTO_INCREMENT,
    brand ENUM('boeing', 'airbus'),
    model VARCHAR(45),
    first_class INT,
    business_class INT,
    economy_class INT,
    max_capacity INT
);
​
drop table if exists passenger;
CREATE TABLE passenger (
    id_passenger INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(45),
    last_name VARCHAR(45),
    username VARCHAR(45),
    password VARCHAR(45)
);
​
drop table if exists flight_schedule;
CREATE TABLE flight_schedule (
    id_flight_schedule INT PRIMARY KEY AUTO_INCREMENT,
    id_plane INT,
    departure_date DATETIME,
    departure_city VARCHAR(45),
    arrival_date DATETIME,
    arrival_city VARCHAR(45),
    CONSTRAINT id_plane FOREIGN KEY (id_plane)
    REFERENCES flying_dog_airline.plane (id_plane)
);
​
drop table if exists seat;
CREATE TABLE seat (
    id_seat INT PRIMARY KEY AUTO_INCREMENT,
    id_flight_schedule INT,
    id_passenger INT,
    status ENUM('available', 'confirmed'),
    price DOUBLE,
    cabin ENUM('first', 'business', 'economy'),
    CONSTRAINT id_flight_schedule FOREIGN KEY (id_flight_schedule)
    REFERENCES flying_dog_airline.flight_schedule (id_flight_schedule),
    CONSTRAINT id_passenger FOREIGN KEY (id_passenger)
    REFERENCES flying_dog_airline.passenger (id_passenger)
);
​
​
drop table if exists employee;
CREATE TABLE employee (
    id_employee INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(45),
    last_name VARCHAR(45),
    username VARCHAR(45),
    password VARCHAR(45)
);