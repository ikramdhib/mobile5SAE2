package com.example.bookingapp.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.bookingapp.entity.User;

import java.util.List;

@Dao
public interface UserDao {

    // Insérer un utilisateur dans la base de données
    @Insert
    long insertUser(User user);

    // Exemple de méthode pour récupérer un utilisateur par email (vous pouvez l'utiliser pour vérifier l'utilisateur statique)
    @Query("SELECT * FROM Users WHERE email = :email")
    User findUserByEmail(String email);
}
