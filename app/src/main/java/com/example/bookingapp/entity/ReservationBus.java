package com.example.bookingapp.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "ReservationBus",
        foreignKeys = {
                @ForeignKey(
                        entity = Bus.class,
                        parentColumns = "id",
                        childColumns = "busId",
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class ReservationBus {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String reservationDate;

    private String paymentMethod;
    private String status;

    private int nbseats;

    private int prix;

    private int busId;

    public ReservationBus(int id, String reservationDate, String paymentMethod, String status, int nbseats, int prix, int busId) {
        this.id = id;
        this.reservationDate = reservationDate;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.nbseats = nbseats;
        this.prix = prix;
        this.busId = busId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNbseats() {
        return nbseats;
    }

    public void setNbseats(int nbseats) {
        this.nbseats = nbseats;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }
// Constructor
}
