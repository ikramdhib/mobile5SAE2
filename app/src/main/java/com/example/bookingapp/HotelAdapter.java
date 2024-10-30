package com.example.bookingapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapp.entity.Hotel;
import com.example.bookingapp.entity.HotelWithChambres;

import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {

    private final List<HotelWithChambres> hotelList;
    private final Context context;

    public HotelAdapter(List<HotelWithChambres> hotelList, Context context) {
        this.hotelList = hotelList;
        this.context = context;
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hotel_item, parent, false);
        return new HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        // Récupérer l'hôtel et les chambres associés à la position actuelle
        HotelWithChambres hotelWithChambres = hotelList.get(position);
        Hotel hotel = hotelWithChambres.hotel;
        holder.hotelName.setText(hotelWithChambres.hotel.getName());
        holder.hotelLocation.setText(hotelWithChambres.hotel.getLocation());
        holder.hotelPrice.setText("TND " + hotelWithChambres.hotel.getPricePerNight());
        holder.reservationStatus.setText(hotelWithChambres.hotel.isAvailable() ? "Disponible" : "Non disponible");

        // Charger l'image de l'hôtel à partir des ressources drawable
        holder.hotelImage.setImageResource(hotelWithChambres.hotel.getImageResource());
        holder.bookingButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, HotelDetailActivity.class);
            // Passer les informations de l'hôtel via l'intent
            intent.putExtra("hotelName", hotel.getName());
            intent.putExtra("hotelLocation", hotel.getLocation());
            intent.putExtra("hotelPrice", hotel.getPricePerNight());
            intent.putExtra("hotelDescription", hotel.getDescription());
            intent.putExtra("hotelAvailable", hotel.isAvailable());
            intent.putExtra("hotelImage", hotel.getImageResource());

            context.startActivity(intent); // Lancer l'activité de détail
        });
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    public static class HotelViewHolder extends RecyclerView.ViewHolder {

        TextView hotelName, hotelLocation, hotelPrice, numberOfGuests, reservationStatus;
        ImageView hotelImage;
        Button bookingButton;

        public HotelViewHolder(@NonNull View itemView) {
            super(itemView);
            hotelName = itemView.findViewById(R.id.hotelName);
            hotelLocation = itemView.findViewById(R.id.hotelLocation);
            hotelPrice = itemView.findViewById(R.id.hotelPrice);
            numberOfGuests = itemView.findViewById(R.id.numberOfGuests);
            reservationStatus = itemView.findViewById(R.id.reservationStatus);
            hotelImage = itemView.findViewById(R.id.hotelImage);
            bookingButton = itemView.findViewById(R.id.bookingButton);
        }
    }
}
