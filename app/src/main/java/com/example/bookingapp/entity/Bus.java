package com.example.bookingapp.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.format.DateTimeFormatter;

@Entity(tableName = "Bus")
public class Bus extends Transport {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private int nbSeats;

    @ColumnInfo
    private String destination;

    @ColumnInfo(name = "ticketprice")
    private double ticketprice;

    @ColumnInfo(name = "time")
    private String time;  // Store time as String

    public Bus(int id, int nbSeats, String destination, double ticketPrice, String time) {
        this.id = id;
        this.nbSeats = nbSeats;
        this.destination = destination;
        this.ticketprice = ticketPrice;
        this.time = time;
    }


    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbSeats() {
        return nbSeats;
    }

    public void setNbSeats(int nbSeats) {
        this.nbSeats = nbSeats;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getTicketprice() {
        return ticketprice;
    }

    public void setTicketprice(double ticketprice) {
        this.ticketprice = ticketprice;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    // Constructors
    public Bus(int nbSeats, String destination, double ticketprice, String time) {
        this.nbSeats = nbSeats;
        this.destination = destination;
        this.ticketprice = ticketprice;
        this.time = time;
    }

    // ToString method
    @Override
    public String toString() {
        return "Bus{" +
                "id=" + id +
                ", nbSeats=" + nbSeats +
                ", destination='" + destination + '\'' +
                ", ticketprice=" + ticketprice +
                ", time='" + time + '\'' +
                '}';
    }
}
