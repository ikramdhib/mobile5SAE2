package com.example.bookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.Chambre;
import com.example.bookingapp.entity.Hotel;

import java.util.ArrayList;
import java.util.List;

public class GestionChambreActivity extends AppCompatActivity {

    private static final int REQUEST_ADD_CHAMBRE = 1;
    private static final int REQUEST_EDIT_CHAMBRE = 2;

    private RecyclerView recyclerViewChambres;
    private ChambreAdapter chambreAdapter;
    private Spinner spinnerHotels;
    private Button btnAddChambre, btnEditChambre, btnDeleteChambre, btnConfirmAssociation;
    private AppDatabase database;
    private List<Hotel> hotelsList = new ArrayList<>();
    private List<Chambre> chambresList = new ArrayList<>();
    private int selectedHotelId = -1;  // ID de l'hôtel sélectionné pour associer la chambre
    private Chambre selectedChambre;   // Chambre sélectionnée pour modification/suppression

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gestion_chambre);

        database = AppDatabase.getAppDatabase(this);

        // Initialiser les vues
        recyclerViewChambres = findViewById(R.id.recyclerViewChambres);
        spinnerHotels = findViewById(R.id.spinnerHotels);
        btnAddChambre = findViewById(R.id.btnAddChambre);
        btnEditChambre = findViewById(R.id.btnEditChambre);
        btnDeleteChambre = findViewById(R.id.btnDeleteChambre);
        btnConfirmAssociation = findViewById(R.id.btnConfirmAssociation);

        // Configurer le RecyclerView
        recyclerViewChambres.setLayoutManager(new LinearLayoutManager(this));
        chambreAdapter = new ChambreAdapter(chambresList);
        recyclerViewChambres.setAdapter(chambreAdapter);

        // Charger les données
        loadHotels();
        loadChambres();

        // Configurer le Spinner des hôtels
        spinnerHotels.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedHotelId = hotelsList.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedHotelId = -1;
            }
        });

        // Ajouter une nouvelle chambre
        btnAddChambre.setOnClickListener(v -> {
            Intent intent = new Intent(GestionChambreActivity.this, AddEditChambreActivity.class);
            startActivityForResult(intent, REQUEST_ADD_CHAMBRE);
        });


        // Modifier la chambre sélectionnée
        btnEditChambre.setOnClickListener(v -> {
            if (selectedChambre != null) {
                Intent intent = new Intent(GestionChambreActivity.this, AddEditChambreActivity.class);
                intent.putExtra("chambreId", selectedChambre.getId());
                startActivityForResult(intent, REQUEST_EDIT_CHAMBRE);
            } else {
                Toast.makeText(this, "Veuillez sélectionner une chambre", Toast.LENGTH_SHORT).show();
            }
        });

        // Supprimer la chambre sélectionnée
        btnDeleteChambre.setOnClickListener(v -> {
            if (selectedChambre != null) {
                deleteChambre(selectedChambre);
            } else {
                Toast.makeText(this, "Veuillez sélectionner une chambre", Toast.LENGTH_SHORT).show();
            }
        });

        // Associer une chambre à un hôtel
        btnConfirmAssociation.setOnClickListener(v -> {
            if (selectedChambre != null && selectedHotelId != -1) {
                selectedChambre.setHotelId(selectedHotelId);
                updateChambre(selectedChambre);
            } else {
                Toast.makeText(this, "Veuillez sélectionner une chambre et un hôtel", Toast.LENGTH_SHORT).show();
            }
        });

        // Gestion de la sélection de la chambre dans le RecyclerView
        chambreAdapter.setOnItemClickListener(chambre -> selectedChambre = chambre);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && (requestCode == REQUEST_ADD_CHAMBRE || requestCode == REQUEST_EDIT_CHAMBRE)) {
            loadChambres(); // Refresh room list after add or edit
        }
    }

    // Charger la liste des hôtels pour le Spinner
    private void loadHotels() {
        new Thread(() -> {
            hotelsList = database.hotelDao().getAllHotels();
            runOnUiThread(() -> {
                ArrayAdapter<Hotel> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, hotelsList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerHotels.setAdapter(adapter);
            });
        }).start();
    }

    // Charger la liste des chambres pour le RecyclerView
    private void loadChambres() {
        new Thread(() -> {
            chambresList = database.chambreDao().getAllChambres();
            runOnUiThread(() -> chambreAdapter.updateData(chambresList));
        }).start();
    }

    // Méthode pour supprimer une chambre
    private void deleteChambre(Chambre chambre) {
        new Thread(() -> {
            database.chambreDao().deleteChambre(chambre);
            runOnUiThread(() -> {
                loadChambres();
                Toast.makeText(this, "Chambre supprimée", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }

    // Méthode pour mettre à jour une chambre
    private void updateChambre(Chambre chambre) {
        new Thread(() -> {
            database.chambreDao().updateChambre(chambre);
            runOnUiThread(() -> {
                loadChambres();
                Toast.makeText(this, "Chambre associée à l'hôtel", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }
}
