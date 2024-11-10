package com.example.bookingapp.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.bookingapp.dao.*;
import com.example.bookingapp.entity.*;

import java.util.concurrent.Executors;

@Database(entities = {User.class, Hotel.class, Transport.class, Flight.class, Discusion.class, Response.class,
        Chambre.class, ReservationHotel.class, ReservationTransport.class, ReservationFlight.class, Bus.class, Car.class, Comment.class},
        version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    // DAO declarations
    public abstract UserDao userDao();
    public abstract TransportDao transportDao();
    public abstract HotelDao hotelDao();
    public abstract DiscussionDao discussionDao();
    public abstract ResponseDao responseDao();
    public abstract FlightDao flightDao();
    public abstract ChambreDao chambreDao();
    public abstract ReservationFlightDao reservationFlightDao();
    public abstract ReservationTransportDao reservationTransportDao();
    public abstract ReservationHotelDao reservationHotelDao();
    public abstract CommentDao commentDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "booking_table")
                    .allowMainThreadQueries() // À éviter en production
                    .fallbackToDestructiveMigration() // Recréation de la base de données si changement de schéma
                    .addCallback(new RoomDatabase.Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            android.util.Log.d("AppDatabase", "Database created");
                            Executors.newSingleThreadExecutor().execute(() -> {
                                try {
                                    User staticUser = new User(
                                            "Jane Doe",
                                            "jane.doe@example.com",
                                            "password123",
                                            "987654321",
                                            "user"
                                    );
                                    long userId = getAppDatabase(context).userDao().insertUser(staticUser);
                                    android.util.Log.d("AppDatabase", "Static user inserted successfully with ID: " + userId);
                                } catch (Exception e) {
                                    android.util.Log.e("AppDatabase", "Error inserting static data", e);
                                }
                            });
                        }
                    })
                    .build();
        }
        return instance;
    }
}
