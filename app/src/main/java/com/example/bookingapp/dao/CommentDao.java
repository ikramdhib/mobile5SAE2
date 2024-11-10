package com.example.bookingapp.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;
import com.example.bookingapp.entity.Comment;

import java.util.List;

@Dao
public interface CommentDao {

    @Insert
    long insertComment(Comment comment);

    @Update
    void updateComment(Comment comment);

    @Delete
    void deleteComment(Comment comment);

    @Query("SELECT * FROM Comment WHERE discussionId = :discussionId")
    List<Comment> getCommentsByDiscussionId(int discussionId);
}
