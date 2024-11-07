package com.example.bookingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapp.entity.Hotel;

import java.util.List;

public class HotelAdminAdapter extends RecyclerView.Adapter<HotelAdminAdapter.HotelViewHolder> {

    private final List<Hotel> hotelList;
    private final Context context;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Hotel hotel);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public HotelAdminAdapter(List<Hotel> hotelList, Context context) {
        this.hotelList = hotelList;
        this.context = context;
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hotel_admin, parent, false);
        return new HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        Hotel hotel = hotelList.get(position);
        holder.hotelName.setText(hotel.getName());
        holder.hotelLocation.setText(hotel.getLocation());
        holder.hotelPrice.setText("TND " + hotel.getPricePerNight());

        if (hotel.getImageResource() != 0) {
            holder.hotelImage.setImageResource(hotel.getImageResource());
        } else {
            holder.hotelImage.setImageResource(R.drawable.ic_placeholder);
        }

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(hotel);
            }
        });

    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    public static class HotelViewHolder extends RecyclerView.ViewHolder {
        TextView hotelName, hotelLocation, hotelPrice;
        ImageView hotelImage;

        public HotelViewHolder(@NonNull View itemView) {
            super(itemView);
            hotelName = itemView.findViewById(R.id.hotelName);
            hotelLocation = itemView.findViewById(R.id.hotelLocation);
            hotelPrice = itemView.findViewById(R.id.hotelPrice);
            hotelImage = itemView.findViewById(R.id.ivHotelImage);
        }
    }

    public void updateData(List<Hotel> newHotels) {
        hotelList.clear();
        hotelList.addAll(newHotels);
        notifyDataSetChanged();
    }
}
