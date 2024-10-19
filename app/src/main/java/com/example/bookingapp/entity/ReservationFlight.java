package com.example.bookingapp.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;
@Entity(
        foreignKeys = {@ForeignKey(
                entity = Flight.class,
                parentColumns = "id",
                childColumns = "flightId",
                onDelete = ForeignKey.CASCADE
        ), @ForeignKey(
        entity = User.class,
        parentColumns = "id",
        childColumns = "userId",
        onDelete = ForeignKey.CASCADE
)
        }

)
public class ReservationFlight {
    @PrimaryKey(autoGenerate = true)
    private int id ;
    @ColumnInfo
    private double price ;
    @ColumnInfo

    private String status ;
    @ColumnInfo

    private String createdAt ;
    private int flightId;
    private int userId;
    public ReservationFlight() {
    }

    public ReservationFlight(int id, double price, String status, String createdAt, int flightId, int userId) {
        this.id = id;
        this.price = price;
        this.status = status;
        this.createdAt = createdAt;
        this.flightId = flightId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getFlightId() {
        return flightId;
    }

    public int getUserId() {
        return userId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ReservationFlight{" +
                "id=" + id +
                ", price=" + price +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", flightId=" + flightId +
                ", userId=" + userId +
                '}';
    }
}
