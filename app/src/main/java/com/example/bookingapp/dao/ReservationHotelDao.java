package com.example.bookingapp.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.bookingapp.entity.ReservationHotel;

import java.util.List;

@Dao
public interface ReservationHotelDao {
    @Insert
    void insertReservation(ReservationHotel reservation);

    @Query("SELECT * FROM ReservationHotel")
    List<ReservationHotel> getAllReservations();

    @Query("SELECT * FROM ReservationHotel WHERE id = :reservationId")
    ReservationHotel getReservationById(int reservationId);
}
