package com.example.bookingapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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

    // Mettre à jour un hôtel existant
    @Update
    void updateHotel(Hotel hotel);

    // Supprimer un hôtel
    @Delete
    void deleteHotel(Hotel hotel);

    // Récupérer un hôtel par son ID
    @Query("SELECT * FROM Hotels WHERE id = :hotelId")
    Hotel getHotelById(int hotelId);

    // Récupérer les hôtels disponibles
    @Query("SELECT * FROM Hotels WHERE available = 1")
    List<Hotel> getAvailableHotels();



}
