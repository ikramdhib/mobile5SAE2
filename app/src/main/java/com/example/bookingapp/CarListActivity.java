package com.example.bookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bookingapp.adapters.CarAdapter;
import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.Car;
import java.util.List;

public class CarListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CarAdapter adapter;
    private AppDatabase db;
    private Button btnAddCar;
    private Button btnBackToTransportManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);

        db = AppDatabase.getAppDatabase(this);
        recyclerView = findViewById(R.id.recyclerViewCars);
        btnAddCar = findViewById(R.id.btnAddCar);
        btnBackToTransportManagement = findViewById(R.id.btnBackToTransportManagement);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Car> carList = db.carDao().getAllCars();
        adapter = new CarAdapter(carList, new CarAdapter.OnItemClickListener() {
            @Override
            public void onEdit(Car car) {
                Log.d("CarListActivity", "Editing car with ID: " + car.getId());
                Intent intent = new Intent(CarListActivity.this, EditCarActivity.class);
                intent.putExtra("car_id", car.getId());
                startActivity(intent);
            }


            @Override
            public void onDelete(Car car) {
                // Delete the car from the database
                db.carDao().delete(car);
                Toast.makeText(CarListActivity.this, "Car deleted", Toast.LENGTH_SHORT).show();
                // Refresh the car list
                List<Car> updatedList = db.carDao().getAllCars();
                adapter = new CarAdapter(updatedList, this);
                recyclerView.setAdapter(adapter);
            }
        });

        recyclerView.setAdapter(adapter);

        btnAddCar.setOnClickListener(v -> {
            // Navigate to AddCarActivity
            Intent intent = new Intent(CarListActivity.this, AddCarActivity.class);
            startActivity(intent);
        });

        btnBackToTransportManagement.setOnClickListener(v -> {
            Intent intent = new Intent(CarListActivity.this, TransportManagementActivity.class);
            startActivity(intent);
        });
    }
}
