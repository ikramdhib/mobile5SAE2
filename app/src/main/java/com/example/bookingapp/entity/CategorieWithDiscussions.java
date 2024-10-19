package com.example.bookingapp.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CategorieWithDiscussions {
    @Embedded
    public Categorie categorie;

    @Relation(
            parentColumn = "id",
            entityColumn = "categorieId"
    )
    public List<Discusion> discussions;
}
