package com.example.bookingapp.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Comment")
public class Comment {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private String content;

    @ColumnInfo
    private String createdAt;

    @ColumnInfo
    private String author;

    @ColumnInfo
    private int discussionId;

    // Constructor
    public Comment(String content, String createdAt, String author, int discussionId) {
        this.content = content;
        this.createdAt = createdAt;
        this.author = author;
        this.discussionId = discussionId;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getDiscussionId() {
        return discussionId;
    }

    public void setDiscussionId(int discussionId) {
        this.discussionId = discussionId;
    }
}
