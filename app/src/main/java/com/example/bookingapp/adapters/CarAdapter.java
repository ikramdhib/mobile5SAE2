package com.example.bookingapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bookingapp.R;
import com.example.bookingapp.entity.Car;
import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private List<Car> carList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEdit(Car car);
        void onDelete(Car car);
    }

    public CarAdapter(List<Car> carList, OnItemClickListener listener) {
        this.carList = carList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car, parent, false);
        return new CarViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = carList.get(position);

        // Bind data to the views
        holder.carTypeTextView.setText("Car Type: " + car.getCarType());
        holder.brandTextView.setText("Brand: " + car.getBrand());
        holder.modelTextView.setText("Model: " + car.getModel());
        holder.seatsTextView.setText("Seats: " + car.getNbSeats());
        holder.priceTextView.setText("Price: $" + car.getPrice());
        holder.registrationTextView.setText("Registration: " + car.getRegistration());
        holder.availabilityStatusTextView.setText("Status: " + car.getAvailabilityStatus());

        // Set the click listeners for delete and edit buttons
        holder.deleteButton.setOnClickListener(v -> {
            listener.onDelete(car);
        });

        holder.editButton.setOnClickListener(v -> {
            listener.onEdit(car);
        });
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {

        public TextView carTypeTextView, brandTextView, modelTextView, seatsTextView, priceTextView, registrationTextView, availabilityStatusTextView;
        public Button deleteButton, editButton;

        public CarViewHolder(View itemView) {
            super(itemView);

            // Initialize views
            carTypeTextView = itemView.findViewById(R.id.textCarType);
            brandTextView = itemView.findViewById(R.id.textBrand);
            modelTextView = itemView.findViewById(R.id.textModel);
            seatsTextView = itemView.findViewById(R.id.textSeats);
            priceTextView = itemView.findViewById(R.id.textPrice);
            registrationTextView = itemView.findViewById(R.id.textRegistration);
            availabilityStatusTextView = itemView.findViewById(R.id.textAvailabilityStatus);
            deleteButton = itemView.findViewById(R.id.buttonDelete);
            editButton = itemView.findViewById(R.id.buttonEdit);
        }
    }
}
