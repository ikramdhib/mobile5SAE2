package com.example.bookingapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bookingapp.entity.ReservationFlight;

import java.util.List;

@Dao
public interface ReservationFlightDao {
    @Insert
    void insert(ReservationFlight reservationFlight);

    // Mettre à jour une réservation
    @Update
    void update(ReservationFlight reservationFlight);

    // Supprimer une réservation
    @Delete
    void delete(ReservationFlight reservationFlight);

    // Obtenir toutes les réservations
    @Query("SELECT * FROM ReservationFlight")
    List<ReservationFlight> getAllReservations();

    // Obtenir une réservation par son ID
    @Query("SELECT * FROM ReservationFlight WHERE id = :id")
    ReservationFlight getReservationById(int id);

    // Obtenir toutes les réservations pour un utilisateur spécifique
    @Query("SELECT * FROM ReservationFlight WHERE userId = :userId")
    List<ReservationFlight> getReservationsByUserId(int userId);

    @Query("DELETE  FROM ReservationFlight")
    void deleteAllReservations();

    // Obtenir toutes les réservations pour un vol spécifique
    @Query("SELECT * FROM ReservationFlight WHERE flightId = :flightId")
    List<ReservationFlight> getReservationsByFlightId(int flightId);
}
