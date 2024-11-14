package com.example.bookingapp.FlightManagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapp.R;
import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.ReservationFlight;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReservationHistory extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReservationAdapter adapter;
    private List<ReservationFlight> reservations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_history);

        recyclerView = findViewById(R.id.recyclerViewReservations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Replace with actual user ID from your authentication system
        int userId = 1;

        new Thread(() -> {
            try {
                AppDatabase db = AppDatabase.getAppDatabase(ReservationHistory.this);
                reservations = db.reservationFlightDao().getReservationsByUserId(userId);

                runOnUiThread(() -> {
                    if (reservations == null || reservations.isEmpty()) {
                        Toast.makeText(ReservationHistory.this, "No reservations found", Toast.LENGTH_SHORT).show();
                    } else {
                        adapter = new ReservationAdapter(reservations, ReservationHistory.this);
                        recyclerView.setAdapter(adapter);
                    }
                });
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(ReservationHistory.this, "Error loading reservations", Toast.LENGTH_SHORT).show());
                e.printStackTrace();
            }
        }).start();
    }

    public boolean isModifyButtonEnabled(String reservationDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date flightDate = dateFormat.parse(reservationDate);
            Date currentDate = new Date();
            long difference = flightDate.getTime() - currentDate.getTime();
            return difference > 24 * 60 * 60 * 1000; // 24 hours in milliseconds
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder> {
        private List<ReservationFlight> reservations;
        private ReservationHistory activity;

        public ReservationAdapter(List<ReservationFlight> reservations, ReservationHistory activity) {
            this.reservations = reservations;
            this.activity = activity;
        }

        @Override
        public ReservationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reservation, parent, false);
            return new ReservationViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ReservationViewHolder holder, int position) {
            ReservationFlight reservation = reservations.get(position);

            holder.flightDate.setText("Flight Date: " + reservation.getCreatedAt());
            holder.flightPrice.setText("Price: $" + reservation.getPrice());
            holder.status.setText("Status: " + reservation.getStatus());

            boolean isEnabled = activity.isModifyButtonEnabled(reservation.getCreatedAt());
            holder.cancelButton.setEnabled(isEnabled);
            holder.cancelButton.setEnabled(!reservation.getStatus().equals("Cancelled"));


            holder.cancelButton.setOnClickListener(v -> {
                new Thread(() -> {
                    try {
                        AppDatabase db = AppDatabase.getAppDatabase(activity);
                        reservation.setStatus("Cancelled");
                        db.reservationFlightDao().update(reservation);

                        activity.runOnUiThread(() -> {
                            holder.status.setText("Status: Cancelled");
                            holder.modifyButton.setEnabled(false);
                            holder.cancelButton.setEnabled(false);
                            Toast.makeText(activity, "Reservation cancelled", Toast.LENGTH_SHORT).show();
                        });
                    } catch (Exception e) {
                        activity.runOnUiThread(() -> Toast.makeText(activity, "Error cancelling reservation", Toast.LENGTH_SHORT).show());
                    }
                }).start();
            });

            holder.deleteButton.setOnClickListener(v -> {
                new Thread(() -> {
                    try {
                        AppDatabase db = AppDatabase.getAppDatabase(activity);
                        db.reservationFlightDao().delete(reservation);

                        reservations.remove(position);
                        activity.runOnUiThread(() -> notifyItemRemoved(position));
                        activity.runOnUiThread(() -> Toast.makeText(activity, "Reservation deleted", Toast.LENGTH_SHORT).show());
                    } catch (Exception e) {
                        activity.runOnUiThread(() -> Toast.makeText(activity, "Error deleting reservation", Toast.LENGTH_SHORT).show());
                    }
                }).start();
            });
        }

        @Override
        public int getItemCount() {
            return reservations.size();
        }

        public static class ReservationViewHolder extends RecyclerView.ViewHolder {
            TextView flightDate, flightPrice, status;
            Button deleteButton, modifyButton, cancelButton;

            public ReservationViewHolder(View itemView) {
                super(itemView);
                flightDate = itemView.findViewById(R.id.flightDate);
                flightPrice = itemView.findViewById(R.id.flightPrice);
                status = itemView.findViewById(R.id.status);
                deleteButton = itemView.findViewById(R.id.btnDelete);
                cancelButton = itemView.findViewById(R.id.btnModifyReservation); // New cancel button
            }
        }
    }
}
