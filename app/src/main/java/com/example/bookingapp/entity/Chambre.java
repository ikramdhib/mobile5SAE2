package com.example.bookingapp.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(
        entity = Hotel.class,
        parentColumns = "id",
        childColumns = "hotelId",
        onDelete = ForeignKey.CASCADE))
public class Chambre {
    @PrimaryKey(autoGenerate = true)
    private int id ;
    @ColumnInfo
    private double pricePerNight ;
    @ColumnInfo

    private String type ;
    @ColumnInfo

    private boolean available ;

    private int hotelId;

    public Chambre() {
    }

    public Chambre(double pricePerNight, String type, boolean available, int hotelId) {
        this.pricePerNight = pricePerNight;
        this.type = type;
        this.available = available;
        this.hotelId = hotelId;
    }

    public Chambre(int id, double pricePerNight, String type, boolean available, int hotelId) {
        this.id = id;
        this.pricePerNight = pricePerNight;
        this.type = type;
        this.available = available;
        this.hotelId = hotelId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getId() {
        return id;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public String getType() {
        return type;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Chambre{" +
                "id=" + id +
                ", pricePerNight=" + pricePerNight +
                ", type='" + type + '\'' +
                ", available=" + available +
                ", hotelId=" + hotelId +
                '}';
    }
}
