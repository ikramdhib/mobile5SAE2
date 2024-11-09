package com.example.bookingapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.bookingapp.entity.ReservationCar;
import java.util.List;

@Dao
public interface ReservationCarDao {

    @Insert
    long insertReservation(ReservationCar reservationCar);



    @Query("SELECT * FROM ReservationCar WHERE carId = :carId")
    List<ReservationCar> getReservationsByCar(int carId);
    @Query("SELECT * FROM ReservationCar")
    List<ReservationCar> getAllReservations();

    @Delete
    void delete(ReservationCar reservationCar);

    @Query("DELETE FROM ReservationCar")
    void deleteAll();
}
