package com.example.bookingapp.dao;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bookingapp.entity.Car;

import java.util.List;

@Dao
public interface CarDao {

    @Insert
    void insert(Car car);

    @Update
    void update(Car car);

    @Delete
    void delete(Car car);

    @Query("SELECT * FROM Car")
    List<Car> getAllCars();
    @Query("SELECT * FROM car WHERE id = :carId LIMIT 1")
    Car getById(long carId);  // Fetch a car by its ID
}
