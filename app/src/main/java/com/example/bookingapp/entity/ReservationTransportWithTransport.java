package com.example.bookingapp.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

public class ReservationTransportWithTransport {
    @Embedded
    public ReservationTransport reservationTransport;

    @Relation(
            parentColumn = "id",
            entityColumn = "transportId"
    )
    public Transport transport;
}
