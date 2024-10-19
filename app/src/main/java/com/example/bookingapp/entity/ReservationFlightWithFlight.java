package com.example.bookingapp.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

public class ReservationFlightWithFlight {
    @Embedded
    public ReservationFlight reservationFlight;

    @Relation(
            parentColumn = "id",
            entityColumn = "flightId"
    )
    public Flight flight;
}
