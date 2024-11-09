package com.example.bookingapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bookingapp.entity.Bus;
import com.example.bookingapp.entity.Car;
import com.example.bookingapp.entity.Transport;

import java.util.List;

@Dao
public interface TransportDao {
    @Insert
    void insert(Transport transport);

    @Update
    void update(Transport transport);

    @Delete
    void delete(Transport transport);

    @Query("SELECT * FROM Transports")
    List<Transport> getAllTransports();

    @Insert
    void insertCar(Car car);

    @Insert
    void insertBus(Bus bus);
    @Insert
    void insertTransport(Transport transport);


    @Query("SELECT * FROM Transports WHERE id = :transportId")
    Transport getTransportById(int transportId);  // Add the @Query annotation here
    // Delete all entries in the table
    @Query("DELETE FROM transports")
    void deleteAll();


}
