package com.example.bookingapp.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.bookingapp.entity.Categorie;

@Dao
public interface CategorieDao {

    @Insert
    long insertCategorie(Categorie categorie);
}
