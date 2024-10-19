package com.example.bookingapp.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Categories")
public class Categorie {
    @PrimaryKey(autoGenerate = true)
    private int id ;
    @ColumnInfo
    private String name ;
    @ColumnInfo
    private String description ;

    public Categorie() {
    }

    public Categorie(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Categorie(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
