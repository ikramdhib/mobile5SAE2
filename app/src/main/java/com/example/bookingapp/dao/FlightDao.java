package com.example.bookingapp.dao;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bookingapp.entity.Flight;

import java.util.List;

@Dao
public interface FlightDao {

    // Insertion d'un vol
    @Insert
    void insertFlight(Flight flight);

    // Insertion de plusieurs vols
    @Insert
    void insertAllFlights(List<Flight> flights);

    // Mise à jour d'un vol
    @Update
    void updateFlight(Flight flight);

    // Suppression d'un vol
    @Delete
    void deleteFlight(Flight flight);

    // Suppression de plusieurs vols
    @Delete
    void deleteAllFlights(List<Flight> flights);

    // Récupération de tous les vols
    @Query("SELECT * FROM flights")
    List<Flight> getAllFlights();

    // Récupération d'un vol spécifique par ID
    @Query("SELECT * FROM flights WHERE id = :flightId")
    Flight getFlightById(int flightId);

    // Récupération de vols par la destination "from" et "to"
    @Query("SELECT * FROM flights WHERE `from` = :fromLocation AND `to` = :toLocation")
    List<Flight> getFlightsByRoute(String fromLocation, String toLocation);
}
