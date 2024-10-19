package com.example.bookingapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.bookingapp.dao.CategorieDao;
import com.example.bookingapp.dao.ChambreDao;
import com.example.bookingapp.dao.DiscussionDao;
import com.example.bookingapp.dao.FlightDao;
import com.example.bookingapp.dao.HotelDao;
import com.example.bookingapp.dao.ReservationFlightDao;
import com.example.bookingapp.dao.ReservationHotelDao;
import com.example.bookingapp.dao.ReservationTransportDao;
import com.example.bookingapp.dao.ResponseDao;
import com.example.bookingapp.dao.TransportDao;
import com.example.bookingapp.dao.UserDao;
import com.example.bookingapp.entity.Bus;
import com.example.bookingapp.entity.Car;
import com.example.bookingapp.entity.Categorie;
import com.example.bookingapp.entity.Chambre;
import com.example.bookingapp.entity.Discusion;
import com.example.bookingapp.entity.Flight;
import com.example.bookingapp.entity.Hotel;
import com.example.bookingapp.entity.ReservationFlight;
import com.example.bookingapp.entity.ReservationHotel;
import com.example.bookingapp.entity.ReservationTransport;
import com.example.bookingapp.entity.Response;
import com.example.bookingapp.entity.Transport;
import com.example.bookingapp.entity.User;

@Database(entities = {User.class , Hotel.class , Transport.class,
Flight.class , Discusion.class , Response.class, Chambre.class , Categorie.class,
ReservationHotel.class, ReservationTransport.class, ReservationFlight.class,
Bus.class, Car.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    //DAO dec
    public abstract UserDao userDao();
    public abstract TransportDao transportDao();
    public abstract HotelDao hotelDao();
    public abstract DiscussionDao discussionDao5();
    public abstract ResponseDao responseDao();
    public abstract FlightDao flightDao();
    public abstract CategorieDao categorieDao();
    public abstract ChambreDao chambreDao();
    public abstract ReservationFlightDao reservationFlightDao();
    public abstract ReservationTransportDao reservationTransportDao();
    public abstract ReservationHotelDao reservationHotelDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "booking_table")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }


}
