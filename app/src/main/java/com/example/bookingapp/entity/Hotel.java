package com.example.bookingapp.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Hotels")
public class Hotel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo
    private String name ;
    @ColumnInfo

    private String location ;
    @ColumnInfo

    private String description;
    @ColumnInfo
    private boolean available;
    @ColumnInfo
    private double pricePerNight ;
    @ColumnInfo
    private double evaluation ;

    public Hotel() {
    }

    public Hotel(int id, String name, String location, String description, boolean available, double pricePerNight, double evaluation) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.description = description;
        this.available = available;
        this.pricePerNight = pricePerNight;
        this.evaluation = evaluation;
    }

    public Hotel(String name, String location, String description, boolean available, double pricePerNight, double evaluation) {
        this.name = name;
        this.location = location;
        this.description = description;
        this.available = available;
        this.pricePerNight = pricePerNight;
        this.evaluation = evaluation;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public boolean isAvailable() {
        return available;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public double getEvaluation() {
        return evaluation;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public void setEvaluation(double evaluation) {
        this.evaluation = evaluation;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", available=" + available +
                ", pricePerNight=" + pricePerNight +
                ", evaluation=" + evaluation +
                '}';
    }
}
