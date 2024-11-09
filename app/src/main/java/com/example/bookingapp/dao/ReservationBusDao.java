package com.example.bookingapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bookingapp.entity.ReservationBus;

import java.util.List;

@Dao
public interface ReservationBusDao {

    // Insert a new reservation
    @Insert
    void insert(ReservationBus reservationBus);

    // Update an existing reservation
    @Update
    void update(ReservationBus reservationBus);

    // Delete a reservation
    @Delete
    void delete(ReservationBus reservationBus);

    // Get all reservations
    @Query("SELECT * FROM ReservationBus")
    List<ReservationBus> getAllReservations();

    // Get reservations by busId
    @Query("SELECT * FROM ReservationBus WHERE busId = :busId")
    List<ReservationBus> getReservationsByBusId(int busId);

    // Get a reservation by its ID
    @Query("SELECT * FROM ReservationBus WHERE id = :id")
    ReservationBus getReservationById(int id);

    // Delete all reservations
    @Query("DELETE FROM ReservationBus")
    void deleteAllReservations();
}
