package com.example.bookingapp.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;
@Entity(tableName = "Flights")
public class Flight  implements Serializable {
@PrimaryKey(autoGenerate = true)
    private int id ;
    @ColumnInfo
    private String flightMatricule;
    private String flightDate;
    @ColumnInfo

    private String arrivalTime ;
    @ColumnInfo

    private String departureTime;
    @ColumnInfo

    private boolean availability;
    @ColumnInfo

    private int nbSeats  ;
    @ColumnInfo

    private String from ;
    @ColumnInfo

    private String to ;
    @ColumnInfo

    private int duration ;
    @ColumnInfo

    private String type;

    public Flight() {
    }

    public Flight(int id, String flightDate,String flightMatricule, String arrivalTime, String departureTime, boolean availability, int nbSeats, String from, String to, int duration, String type) {
        this.id = id;
        this.flightDate = flightDate;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.availability = availability;
        this.nbSeats = nbSeats;
        this.from = from;
        this.to = to;
        this.duration = duration;
        this.type = type;
        this.flightMatricule = flightMatricule;
    }

    public Flight(String flightMatricule, String type, int duration, String to, String from, int nbSeats, boolean availability, String departureTime, String flightDate, String arrivalTime) {
        this.type = type;
        this.duration = duration;
        this.to = to;
        this.from = from;
        this.nbSeats = nbSeats;
        this.availability = availability;
        this.departureTime = departureTime;
        this.flightDate = flightDate;
        this.arrivalTime = arrivalTime;
        this.flightMatricule = flightMatricule;
    }

    public int getId() {
        return id;
    }

    public String getFlightDate() {
        return flightDate;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public boolean isAvailability() {
        return availability;
    }

    public int getNbSeats() {
        return nbSeats;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getDuration() {
        return duration;
    }

    public String getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public void setNbSeats(int nbSeats) {
        this.nbSeats = nbSeats;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFlightMatricule() {
        return flightMatricule;
    }

    public void setFlightMatricule(String flightMatricule) {
        this.flightMatricule = flightMatricule;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", flightMatricule='" + flightMatricule + '\'' +
                ", flightDate='" + flightDate + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", availability=" + availability +
                ", nbSeats=" + nbSeats +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", duration=" + duration +
                ", type='" + type + '\'' +
                '}';
    }
}
