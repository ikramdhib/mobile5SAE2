package com.example.bookingapp.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class HotelWithChambres{
    @Embedded
    public Hotel hotel;

    @Relation(
            parentColumn = "id",
            entityColumn = "hotelId"
    )
    public List<Chambre> chambres;
}
