package com.example.bookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bookingapp.R;
import com.example.bookingapp.adapters.BookingListCarsAdapter;
import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.Car;
import java.util.List;

public class BookingListCarsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookingListCarsAdapter carAdapter;
    private AppDatabase db;
    private Button btnBackToTransportManagement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_car_to_book);

        btnBackToTransportManagement = findViewById(R.id.btnBackToTransportManagement);


        db = AppDatabase.getAppDatabase(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Car> carList = db.carDao().getAllCars();
        carAdapter = new BookingListCarsAdapter(this, carList);
        recyclerView.setAdapter(carAdapter);

        btnBackToTransportManagement.setOnClickListener(v -> {
            Intent intent = new Intent(BookingListCarsActivity.this, TransportManagementActivity.class); // Replace with your actual Transport Management Activity class
            startActivity(intent);
        });
    }
}
