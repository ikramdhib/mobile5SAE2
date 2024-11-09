package com.example.bookingapp.dao;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bookingapp.entity.Bus;

import java.util.List;

@Dao
public interface BusDao {
    @Insert
    void insert(Bus bus);

    @Update
    void update(Bus bus);

    @Delete
    void delete(Bus bus);

    @Query("SELECT * FROM Bus")
    List<Bus> getAllBuses();

    @Query("SELECT * FROM Bus WHERE id = :busId")
    Bus getBusById(int busId);
}

