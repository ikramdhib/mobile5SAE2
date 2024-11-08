package com.example.bookingapp.FlightManagement;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapp.R;
import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.ReservationFlight;

import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder> {

    private List<ReservationFlight> reservations;
    private Context context;

    // Constructor to pass context
    public ReservationAdapter(List<ReservationFlight> reservations, Context context) {
        this.reservations = reservations;
        this.context = context;
    }

    @Override
    public ReservationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the view for each item in the list
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_reservation, parent, false);
        return new ReservationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ReservationViewHolder holder, int position) {
        ReservationFlight reservation = reservations.get(position);

        // Set the reservation data in the view
        holder.flightDate.setText(reservation.getCreatedAt());
        holder.flightPrice.setText(String.format("$%.2f", reservation.getPrice()));
        holder.status.setText(reservation.getStatus());

        // Set the delete button click listener
        holder.btnDelete.setOnClickListener(v -> {
            // Call the method to delete the reservation
            deleteReservation(reservation, position);
        });
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    // Method to delete the reservation
    private void deleteReservation(ReservationFlight reservation, int position) {
        // Remove from the list
        reservations.remove(position);
        notifyItemRemoved(position);

        // Remove from the database in a background thread
        new Thread(() -> {
            AppDatabase db = AppDatabase.getAppDatabase(context);
            db.reservationFlightDao().delete(reservation);  // Delete from database

            // Show a Toast message on the main thread
            ((Activity) context).runOnUiThread(() -> {
                Toast.makeText(context, "Reservation deleted", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }

    // ViewHolder class to bind views for each item in the list
    public static class ReservationViewHolder extends RecyclerView.ViewHolder {

        TextView flightDate, flightPrice, status;
        Button btnDelete;

        public ReservationViewHolder(View itemView) {
            super(itemView);
            flightDate = itemView.findViewById(R.id.flightDate);
            flightPrice = itemView.findViewById(R.id.flightPrice);
            status = itemView.findViewById(R.id.status);
            btnDelete = itemView.findViewById(R.id.btnDelete);  // Reference to the delete button
        }
    }
}
