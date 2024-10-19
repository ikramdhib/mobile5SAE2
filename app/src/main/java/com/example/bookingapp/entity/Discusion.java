package com.example.bookingapp.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        foreignKeys = {@ForeignKey(
                entity = Categorie.class,
                parentColumns = "id",
                childColumns = "categorieId",
                onDelete = ForeignKey.CASCADE
        ),
         @ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "creatorId",
                onDelete = ForeignKey.CASCADE
        )}
)
public class Discusion {
    @PrimaryKey(autoGenerate = true)
    private int id ;
    @ColumnInfo
    private String title ;
    @ColumnInfo
    private String content;
    @ColumnInfo
    private String createdAt ;

    private int categorieId;
    private int creatorId;

    public Discusion() {
    }

    public Discusion(String title, String content, String createdAt, int categorieId, int creatorId) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.categorieId = categorieId;
        this.creatorId = creatorId;
    }

    public Discusion(int id, String title, String content, String createdAt, int categorieId, int creatorId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.categorieId = categorieId;
        this.creatorId = creatorId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getCategorieId() {
        return categorieId;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCategorieId(int categorieId) {
        this.categorieId = categorieId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    @Override
    public String toString() {
        return "Discusion{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", categorieId=" + categorieId +
                ", creatorId=" + creatorId +
                '}';
    }
}
