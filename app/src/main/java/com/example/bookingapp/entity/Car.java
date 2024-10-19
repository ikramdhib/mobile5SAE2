package com.example.bookingapp.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Car")
public class Car extends Transport {
    @PrimaryKey(autoGenerate = true)
    private int id ;
    @ColumnInfo
    private String carType ;
    @ColumnInfo
    private int nbSeats;

    public Car() {
    }

    public Car(int id, String carType, int nbSeats) {
        this.id = id;
        this.carType = carType;
        this.nbSeats = nbSeats;
    }

    public Car(int nbSeats, String carType) {
        this.nbSeats = nbSeats;
        this.carType = carType;
    }

    public int getId() {
        return id;
    }

    public String getCarType() {
        return carType;
    }

    public int getNbSeats() {
        return nbSeats;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public void setNbSeats(int nbSeats) {
        this.nbSeats = nbSeats;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", carType='" + carType + '\'' +
                ", nbSeats=" + nbSeats +
                '}';
    }
}
