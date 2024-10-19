package com.example.bookingapp.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Bus")
public class Bus extends Transport{
    @PrimaryKey(autoGenerate = true)
    private int id ;
    @ColumnInfo
    private int nbSeats ;
    @ColumnInfo
    private String destination ;

    public Bus() {
    }

    public Bus(int id, int nbSeats, String destination) {
        this.id = id;
        this.nbSeats = nbSeats;
        this.destination = destination;
    }

    public Bus(String destination, int nbSeats) {
        this.destination = destination;
        this.nbSeats = nbSeats;
    }

    public int getId() {
        return id;
    }

    public int getNbSeats() {
        return nbSeats;
    }

    public String getDestination() {
        return destination;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNbSeats(int nbSeats) {
        this.nbSeats = nbSeats;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "Bus{" +
                "id=" + id +
                ", nbSeats=" + nbSeats +
                ", destination='" + destination + '\'' +
                '}';
    }
}
