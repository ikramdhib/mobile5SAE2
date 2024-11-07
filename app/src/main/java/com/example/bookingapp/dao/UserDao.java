package com.example.bookingapp.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.bookingapp.entity.User;

import java.util.List;

@Dao
public interface UserDao {
    // Insérer un utilisateur unique
    @Insert(onConflict = OnConflictStrategy.REPLACE)  // Cette option remplace les utilisateurs ayant le même ID en cas de conflit
    long insertUser(User user);
    // Insérer une liste d'utilisateurs
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertUsers(List<User> users);
    @Query("SELECT * FROM Users")
    List<User> getAllUsers();

}
