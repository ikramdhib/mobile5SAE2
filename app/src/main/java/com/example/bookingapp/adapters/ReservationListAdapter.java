package com.example.bookingapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bookingapp.R;
import com.example.bookingapp.entity.ReservationBus;
import com.example.bookingapp.entity.ReservationCar;
import java.util.List;

public class ReservationListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ReservationCar> reservationCars;
    private List<ReservationBus> reservationBuses;
    private OnDeleteClickListener onDeleteClickListener;

    public ReservationListAdapter(List<ReservationCar> reservationCars, List<ReservationBus> reservationBuses, OnDeleteClickListener onDeleteClickListener) {
        this.reservationCars = reservationCars;
        this.reservationBuses = reservationBuses;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reservation, parent, false);
        return new ReservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ReservationViewHolder viewHolder = (ReservationViewHolder) holder;

        if (reservationCars != null && position < reservationCars.size()) {
            ReservationCar car = reservationCars.get(position);
            viewHolder.titleTextView.setText("Car Reservation: " + car.getStatus());
            viewHolder.detailsTextView.setText("Payment: " + car.getPaymentMethod());
            viewHolder.deleteButton.setOnClickListener(v -> onDeleteClickListener.onDeleteReservationCar(position));
        } else if (reservationBuses != null) {
            int busPosition = reservationCars != null ? position - reservationCars.size() : position;
            ReservationBus bus = reservationBuses.get(busPosition);
            viewHolder.titleTextView.setText("Bus Reservation: " + bus.getStatus());
            viewHolder.detailsTextView.setText("Seats: " + bus.getNbseats());
            viewHolder.deleteButton.setOnClickListener(v -> onDeleteClickListener.onDeleteReservationBus(busPosition));
        }
    }

    @Override
    public int getItemCount() {
        if (reservationCars != null) {
            return reservationCars.size();
        } else if (reservationBuses != null) {
            return reservationBuses.size();
        } else {
            return 0;
        }
    }

    static class ReservationViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView detailsTextView;
        Button deleteButton;

        public ReservationViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            detailsTextView = itemView.findViewById(R.id.detailsTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }

    public interface OnDeleteClickListener {
        void onDeleteReservationCar(int position);
        void onDeleteReservationBus(int position);
    }
}
