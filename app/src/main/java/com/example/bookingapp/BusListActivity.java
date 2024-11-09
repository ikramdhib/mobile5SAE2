package com.example.bookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bookingapp.adapters.BusAdapter;
import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.Bus;
import java.util.List;

public class BusListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BusAdapter busAdapter;
    private AppDatabase db;
    private Button btnAddBus, btnBackToTransportManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_list_activity);

        db = AppDatabase.getAppDatabase(this);
        recyclerView = findViewById(R.id.recyclerViewBusList);
        btnAddBus = findViewById(R.id.btnAddBus);
        btnBackToTransportManagement = findViewById(R.id.btnBackToTransportManagement);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadBuses();

        btnAddBus.setOnClickListener(v -> {
            Intent intent = new Intent(BusListActivity.this, AddBusActivity.class);
            startActivity(intent);
        });

        btnBackToTransportManagement.setOnClickListener(v -> {
            Intent intent = new Intent(BusListActivity.this, TransportManagementActivity.class);
            startActivity(intent);
        });
    }

    private void loadBuses() {
        List<Bus> busList = db.busDao().getAllBuses();
        busAdapter = new BusAdapter(busList, new BusAdapter.OnItemClickListener() {
            @Override
            public void onEdit(Bus bus) {
                Intent intent = new Intent(BusListActivity.this, EditBusActivity.class);
                intent.putExtra("bus_id", bus.getId());
                startActivity(intent);
            }

            @Override
            public void onDelete(Bus bus) {
                db.busDao().delete(bus);
                Toast.makeText(BusListActivity.this, "Bus deleted", Toast.LENGTH_SHORT).show();
                loadBuses(); // Refresh the list
            }
        });

        recyclerView.setAdapter(busAdapter);
    }
}
