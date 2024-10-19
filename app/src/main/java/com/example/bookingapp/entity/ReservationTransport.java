package com.example.bookingapp.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;
@Entity(
        foreignKeys = {
                @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId"),
                @ForeignKey(entity = Transport.class, parentColumns = "id", childColumns = "transportId")
        }
)
public class ReservationTransport {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo
    private String reservationDate;
    @ColumnInfo
    private String startsDate ;
    @ColumnInfo
    private String endDate ;
    @ColumnInfo
    private String paymentMethode ;
    @ColumnInfo
    private String status;
    private int userId;
    private int transportId;
    public ReservationTransport() {
    }

    public ReservationTransport(String reservationDate, int id, String startsDate, String endDate, String paymentMethode, String status, int userId, int transportId) {
        this.reservationDate = reservationDate;
        this.id = id;
        this.startsDate = startsDate;
        this.endDate = endDate;
        this.paymentMethode = paymentMethode;
        this.status = status;
        this.userId = userId;
        this.transportId = transportId;
    }

    public ReservationTransport(String reservationDate, String startsDate, String endDate, String paymentMethode, String status, int userId, int transportId) {
        this.reservationDate = reservationDate;
        this.startsDate = startsDate;
        this.endDate = endDate;
        this.paymentMethode = paymentMethode;
        this.status = status;
        this.userId = userId;
        this.transportId = transportId;
    }

    public int getId() {
        return id;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public String getStartsDate() {
        return startsDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getPaymentMethode() {
        return paymentMethode;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public void setStartsDate(String startsDate) {
        this.startsDate = startsDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setPaymentMethode(String paymentMethode) {
        this.paymentMethode = paymentMethode;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public int getTransportId() {
        return transportId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTransportId(int transportId) {
        this.transportId = transportId;
    }

    @Override
    public String toString() {
        return "ReservationTransport{" +
                "id=" + id +
                ", reservationDate=" + reservationDate +
                ", startsDate=" + startsDate +
                ", endDate=" + endDate +
                ", paymentMethode='" + paymentMethode + '\'' +
                ", status='" + status + '\'' +
                ", userId=" + userId +
                ", transportId=" + transportId +
                '}';
    }
}
