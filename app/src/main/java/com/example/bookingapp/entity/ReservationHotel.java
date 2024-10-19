package com.example.bookingapp.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;
@Entity(foreignKeys = {
        @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId", onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Chambre.class, parentColumns = "id", childColumns = "chambreId", onDelete = ForeignKey.CASCADE)
})
public class ReservationHotel {
    @PrimaryKey(autoGenerate = true)
    private int id ;
    @ColumnInfo
    private String startDate ;
    @ColumnInfo
    private String endDate;
    @ColumnInfo
    private int nbPerson;
    @ColumnInfo
    private String status;
    private int userId;
    private int chambreId;

    public ReservationHotel() {
    }

    public ReservationHotel(int id, String startDate, String endDate, int nbPerson, String status, int userId, int chambreId) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.nbPerson = nbPerson;
        this.status = status;
        this.userId = userId;
        this.chambreId = chambreId;
    }

    public ReservationHotel(int chambreId, int userId, String status, int nbPerson, String endDate, String startDate) {
        this.chambreId = chambreId;
        this.userId = userId;
        this.status = status;
        this.nbPerson = nbPerson;
        this.endDate = endDate;
        this.startDate = startDate;
    }

    public int getUserId() {
        return userId;
    }

    public int getChambreId() {
        return chambreId;
    }

    public int getId() {
        return id;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getNbPerson() {
        return nbPerson;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setNbPerson(int nbPerson) {
        this.nbPerson = nbPerson;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setChambreId(int chambreId) {
        this.chambreId = chambreId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ReservationHotel{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", nbPerson=" + nbPerson +
                ", status='" + status + '\'' +
                ", userId=" + userId +
                ", chambreId=" + chambreId +
                '}';
    }
}
