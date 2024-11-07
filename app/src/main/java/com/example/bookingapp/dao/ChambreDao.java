package com.example.bookingapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bookingapp.entity.Chambre;

import java.util.List;

@Dao
public interface ChambreDao {
    @Insert
    void insertChambre(Chambre chambre);

    @Query("SELECT * FROM Chambre")
    List<Chambre> getAllChambres();

    @Query("SELECT * FROM Chambre WHERE hotelId = :hotelId")
    List<Chambre> getChambresByHotelId(int hotelId);

    @Update
    int updateChambre(Chambre chambre);
    @Query("SELECT * FROM Chambre WHERE id = :chambreId")
    Chambre getChambreById(int chambreId);

    @Delete
    int deleteChambre(Chambre chambre);

    @Query("DELETE FROM Chambre WHERE hotelId = :hotelId")
    void deleteChambresByHotelId(int hotelId);
}
