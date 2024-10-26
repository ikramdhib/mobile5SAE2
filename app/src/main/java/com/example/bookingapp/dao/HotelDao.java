package com.example.bookingapp.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.bookingapp.entity.Hotel;
import com.example.bookingapp.entity.HotelWithChambres;

import java.util.List;

@Dao
public interface HotelDao {
    // Récupérer la liste de tous les hôtels
    @Query("SELECT * FROM Hotels")
    List<Hotel> getAllHotels();
    @Insert
    long insertHotel(Hotel hotel);

    // Recherche d'hôtels selon les critères spécifiés
    @Query("SELECT * FROM Hotels h " +
            "INNER JOIN Chambre c ON h.id = c.hotelId " +
            "WHERE h.location LIKE :location " +
            "AND c.nbAdultes >= :minAdultes " +
            "AND c.nbEnfants >= :minEnfants " +
            "AND c.available = 1 " +
            "GROUP BY h.id ")
    List<HotelWithChambres> searchHotels(String location, int minAdultes, int minEnfants);
}
