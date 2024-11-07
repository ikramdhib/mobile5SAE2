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
    @Query("SELECT * FROM Hotels h " +
            "WHERE h.location LIKE :location " +
            "AND h.id IN (" +
            "  SELECT c.hotelId " +
            "  FROM Chambre c " +
            "  WHERE c.nbAdultes = :minAdultes " +
            "  AND c.nbEnfants = :minEnfants " +
            "  AND c.dateDebutDisponibilite <= :checkInDate " +
            "  AND c.dateFinDisponibilite >= :checkOutDate" +
            ")")
    List<HotelWithChambres> searchHotels(String location, int minAdultes, int minEnfants, String checkInDate, String checkOutDate);
    @Query("SELECT * FROM Hotels WHERE userId = :userId")
    List<Hotel> getHotelsByUserId(int userId);



}
