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
    private int nbAdultes;
    @ColumnInfo
    private int nbEnfants;
    /*@ColumnInfo
    private boolean available ;*/
    @ColumnInfo
    private String dateDebutDisponibilite;
    @ColumnInfo
    private String dateFinDisponibilite;
    @ColumnInfo(name = "hotelId")
    private int hotelId;

    public String getDateDebutDisponibilite() {
        return dateDebutDisponibilite;
    }

    public void setDateDebutDisponibilite(String dateDebutDisponibilite) {
        this.dateDebutDisponibilite = dateDebutDisponibilite;
    }

    public String getDateFinDisponibilite() {
        return dateFinDisponibilite;
    }

    public void setDateFinDisponibilite(String dateFinDisponibilite) {
        this.dateFinDisponibilite = dateFinDisponibilite;
    }

    public Chambre() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNbAdultes() {
        return nbAdultes;
    }

    public void setNbAdultes(int nbAdultes) {
        this.nbAdultes = nbAdultes;
    }

    public int getNbEnfants() {
        return nbEnfants;
    }

    public void setNbEnfants(int nbEnfants) {
        this.nbEnfants = nbEnfants;
    }


    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    @Override
    public String toString() {
        return "Chambre{" +
                "id=" + id +
                ", pricePerNight=" + pricePerNight +
                ", type='" + type + '\'' +
                ", nbAdultes=" + nbAdultes +
                ", nbEnfants=" + nbEnfants +
                ", dateDebutDisponibilite='" + dateDebutDisponibilite + '\'' +
                ", dateFinDisponibilite='" + dateFinDisponibilite + '\'' +
                ", hotelId=" + hotelId +
                '}';
    }


}
