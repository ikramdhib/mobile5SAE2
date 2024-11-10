package com.example.bookingapp.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class DiscussionWithComments {
    @Embedded
    public Discusion discussion;

    @Relation(
            parentColumn = "id",
            entityColumn = "discussionId"
    )
    public List<Comment> comments;
}
