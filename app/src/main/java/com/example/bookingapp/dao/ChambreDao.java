package com.example.bookingapp.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.bookingapp.entity.Chambre;

import java.util.List;

@Dao
public interface ChambreDao {
    @Insert
    void insertChambre(Chambre chambre);

    @Query("SELECT * FROM Chambre WHERE hotelId = :hotelId")
    List<Chambre> getChambresByHotelId(int hotelId);
}
