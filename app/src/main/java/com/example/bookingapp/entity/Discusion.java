package com.example.bookingapp.entity;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(
        foreignKeys = {@ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "creatorId",
                onDelete = ForeignKey.CASCADE
        )}
)
public class Discusion {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    @Nullable
    private String title;

    @ColumnInfo
    private String content;

    @ColumnInfo
    @Nullable
    private String createdAt;

    @Nullable
    private int creatorId;

    // Constructor utilisé par Room
    public Discusion(String title, String content, String createdAt, int creatorId) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.creatorId = creatorId;
    }

    // Constructeur par défaut ignoré par Room
    @Ignore
    public Discusion() {
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Nullable
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@Nullable String createdAt) {
        this.createdAt = createdAt;
    }

    @Nullable
    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(@Nullable int creatorId) {
        this.creatorId = creatorId;
    }
}
