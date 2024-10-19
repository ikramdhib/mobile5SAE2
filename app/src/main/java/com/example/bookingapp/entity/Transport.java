package com.example.bookingapp.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Transports")
public class Transport {
    @PrimaryKey(autoGenerate = true)
    private int id ;
    @ColumnInfo
    private String type ;
    @ColumnInfo
    private String brand ;
    @ColumnInfo
    private double pricePerHour;
    @ColumnInfo
    private String availabilityStatus ;
    @ColumnInfo
    private String registrationNumber ;

    public Transport() {
    }

    public Transport(int id, String type, String brand, double pricePerHour, String availabilityStatus, String registrationNumber) {
        this.id = id;
        this.type = type;
        this.brand = brand;
        this.pricePerHour = pricePerHour;
        this.availabilityStatus = availabilityStatus;
        this.registrationNumber = registrationNumber;
    }

    public Transport(String type, String brand, double pricePerHour, String availabilityStatus, String registrationNumber) {
        this.type = type;
        this.brand = brand;
        this.pricePerHour = pricePerHour;
        this.availabilityStatus = availabilityStatus;
        this.registrationNumber = registrationNumber;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getBrand() {
        return brand;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    @Override
    public String toString() {
        return "Transport{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", brand='" + brand + '\'' +
                ", pricePerHour=" + pricePerHour +
                ", availabilityStatus='" + availabilityStatus + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                '}';
    }
}
