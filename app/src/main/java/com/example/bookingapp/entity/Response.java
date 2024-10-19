package com.example.bookingapp.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;
@Entity(
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "id",
                        childColumns = "userId",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Discusion.class,
                        parentColumns = "id",
                        childColumns = "discussionId",
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class Response {
    @PrimaryKey(autoGenerate = true)
    private int id ;
    @ColumnInfo
    private String createdAt ;
    @ColumnInfo
    private String content ;
    private int discussionId;
    private int userId;
    public Response() {
    }

    public Response(int id, String createdAt, String content, int discussionId, int userId) {
        this.id = id;
        this.createdAt = createdAt;
        this.content = content;
        this.discussionId = discussionId;
        this.userId = userId;
    }

    public Response(String createdAt, String content, int discussionId, int userId) {
        this.createdAt = createdAt;
        this.content = content;
        this.discussionId = discussionId;
        this.userId = userId;
    }

    public int getId() {
        return id;
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

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDiscussionId() {
        return discussionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setDiscussionId(int discussionId) {
        this.discussionId = discussionId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Response{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", content='" + content + '\'' +
                ", discussionId=" + discussionId +
                ", userId=" + userId +
                '}';
    }
}
