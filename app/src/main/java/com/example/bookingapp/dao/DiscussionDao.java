package com.example.bookingapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bookingapp.entity.Discusion;

import java.util.List;

@Dao
public interface DiscussionDao {
    @Insert
    long insertDiscussion(Discusion discussion);


    @Update
    void updateDiscussion(Discusion discussion);

    @Delete
    void deleteDiscussion(Discusion discussion);

    @Query("SELECT * FROM Discusion WHERE id = :id")
    Discusion getDiscussionById(int id);

    @Query("SELECT * FROM Discusion")
    List<Discusion> getAllDiscussions();
}
