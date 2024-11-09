package com.example.bookingapp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapp.R;
import com.example.bookingapp.ReservationCarActivity;
import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.Car;
import com.example.bookingapp.entity.ReservationCar;

import java.util.List;

public class BookingListCarsAdapter extends RecyclerView.Adapter<BookingListCarsAdapter.CarViewHolder> {

    private Context context;
    private List<Car> carList;
    private AppDatabase db;

    public BookingListCarsAdapter(Context context, List<Car> carList) {
        this.context = context;
        this.carList = carList;
        this.db = AppDatabase.getAppDatabase(context);
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_to_book_car_items, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = carList.get(position);
        holder.carTitle.setText(car.getBrand() + " " + car.getModel());
        holder.carDetails.setText(car.getCarType() + " | " + car.getNbSeats() + " seats");
        holder.carDistance.setText("800m (5mins away)");

        holder.btnBookLater.setOnClickListener(v -> {
            // Handle Book Later Action
        });

        // Update the onClickListener for btnRideNow
        holder.btnRideNow.setOnClickListener(v -> {
            // Start ReservationFormActivity
            Intent intent = new Intent(context, ReservationCarActivity.class);
            intent.putExtra("carDetails", car.getBrand() + " " + car.getModel()); // Example car details
            intent.putExtra("carId", car.getId());
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return carList.size();
    }




    public static class CarViewHolder extends RecyclerView.ViewHolder {
        TextView carTitle, carDetails, carDistance;
        Button btnBookLater, btnRideNow;
        ImageView carImage;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            carTitle = itemView.findViewById(R.id.carTitle);
            carDetails = itemView.findViewById(R.id.carDetails);
            carDistance = itemView.findViewById(R.id.carDistance);
            btnBookLater = itemView.findViewById(R.id.btnBookLater);
            btnRideNow = itemView.findViewById(R.id.btnRideNow);
            carImage = itemView.findViewById(R.id.carImage);
        }
    }
}
