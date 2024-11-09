package com.example.bookingapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bookingapp.entity.User;

@Dao
public interface UserDao {
    @Insert
    void insertUser(User user);  // Method to insert a user
    // Login query using fullName and password
    @Query("SELECT * FROM Users WHERE fullName = :fullName AND password = :password LIMIT 1")
    User login(String fullName, String password);


    @Query("SELECT * FROM Users WHERE fullName = :fullName AND password = :password LIMIT 1")
    User loginByFullName(String fullName, String password);


    @Query("SELECT * FROM Users WHERE email = :email LIMIT 1")
    User getUserByEmail(String email);

    @Update
    void updateUser(User user);

}
