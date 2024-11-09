package com.example.bookingapp.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Car")
public class Car extends Transport {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "carType")
    private String carType;

    @ColumnInfo(name = "nbSeats")
    private int nbSeats;

    @ColumnInfo(name = "brand")
    private String brand;

    @ColumnInfo(name = "model")
    private String model;

    @ColumnInfo(name = "price")
    private double price;

    @ColumnInfo(name = "registration")
    private String registration;

    @ColumnInfo(name = "availabilityStatus")
    private String availabilityStatus;

    public Car() {
    }

    public Car(String brand, String model, double price, String registration, String availabilityStatus, int nbSeats) {
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.registration = registration;
        this.availabilityStatus = availabilityStatus;
        this.nbSeats = nbSeats;
    }

    public Car(String brand, String model, double price, String registration, String carType, String availabilityStatus, int numberOfSeats) {
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.registration = registration;
        this.carType = carType;
        this.availabilityStatus = availabilityStatus;
        this.nbSeats = numberOfSeats;
    }

    public Car(int carId, String carType, String brand, String model, int seats, double price, String registration, String availabilityStatus) {
        this.id = carId;
        this.carType = carType;
        this.brand = brand;
        this.model = model;
        this.nbSeats = seats;
        this.price = price;
        this.registration = registration;
        this.availabilityStatus = availabilityStatus;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public int getNbSeats() {
        return nbSeats;
    }

    public void setNbSeats(int nbSeats) {
        this.nbSeats = nbSeats;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", carType='" + carType + '\'' +
                ", nbSeats=" + nbSeats +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", registration='" + registration + '\'' +
                ", availabilityStatus='" + availabilityStatus + '\'' +
                '}';
    }
}
