package com.example.bookingapp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapp.R;
import com.example.bookingapp.ReservationBusActivity;
import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.Bus;
import com.example.bookingapp.entity.ReservationBus;

import java.util.List;

public class BookingListBusesAdapter extends RecyclerView.Adapter<BookingListBusesAdapter.BusViewHolder> {

    private Context context;
    private List<Bus> busList;
    private AppDatabase db;

    public BookingListBusesAdapter(Context context, List<Bus> busList) {
        this.context = context;
        this.busList = busList;
        this.db = AppDatabase.getAppDatabase(context);
    }

    @NonNull
    @Override
    public BusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_to_book_bus_items, parent, false);
        return new BusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusViewHolder holder, int position) {
        Bus bus = busList.get(position);
        holder.busTitle.setText(bus.getBrand() + " " + bus.getTime());
        holder.busDetails.setText(bus.getType() + " | " + bus.getNbSeats() + " seats");

        holder.btnBookLater.setOnClickListener(v -> {
            // Handle Book Later Action
        });

        // Update the onClickListener for btnRideNow
        holder.btnRideNow.setOnClickListener(v -> {
            // Start ReservationBusActivity
            Intent intent = new Intent(context, ReservationBusActivity.class);
            intent.putExtra("busDetails", bus.getBrand() + " " + bus.getDestination()); // Example bus details
            intent.putExtra("busId", bus.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return busList.size();
    }

    public static class BusViewHolder extends RecyclerView.ViewHolder {
        TextView busTitle, busDetails;
        Button btnBookLater, btnRideNow;

        public BusViewHolder(@NonNull View itemView) {
            super(itemView);
            busTitle = itemView.findViewById(R.id.busTitle);
            busDetails = itemView.findViewById(R.id.busDetails);
            btnBookLater = itemView.findViewById(R.id.btnBookLater);
            btnRideNow = itemView.findViewById(R.id.btnRideNow);
        }
    }
}
