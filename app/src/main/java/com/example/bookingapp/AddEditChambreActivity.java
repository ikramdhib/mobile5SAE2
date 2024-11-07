package com.example.bookingapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.Chambre;
import com.example.bookingapp.entity.Hotel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddEditChambreActivity extends AppCompatActivity {

    private Spinner spinnerHotelId;
    private EditText etChambreType, etNbAdultes, etNbEnfants, etPricePerNight, etDateDebutDisponibilite, etDateFinDisponibilite;
    private Button btnSave;
    private AppDatabase database;
    private boolean isEditMode = false;
    private int chambreId;
    private int selectedHotelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_chambre);

        // Initialisation des vues
        spinnerHotelId = findViewById(R.id.spinnerHotelId);
        etChambreType = findViewById(R.id.etChambreType);
        etNbAdultes = findViewById(R.id.etNbAdultes);
        etNbEnfants = findViewById(R.id.etNbEnfants);
        etPricePerNight = findViewById(R.id.etPricePerNight);
        etDateDebutDisponibilite = findViewById(R.id.etDateDebutDisponibilite);
        etDateFinDisponibilite = findViewById(R.id.etDateFinDisponibilite);
        btnSave = findViewById(R.id.btnSave);

        database = AppDatabase.getAppDatabase(this);

        // Charger les IDs d'hôtels dans le Spinner
        loadHotelIds();

        // Vérifier si on est en mode édition ou ajout
        if (getIntent().hasExtra("chambreId")) {
            isEditMode = true;
            chambreId = getIntent().getIntExtra("chambreId", -1);
            loadChambreData(chambreId);
        }

        // DatePicker pour Date Debut
        etDateDebutDisponibilite.setOnClickListener(v -> showDatePickerDialog(etDateDebutDisponibilite));
        etDateFinDisponibilite.setOnClickListener(v -> showDatePickerDialog(etDateFinDisponibilite));

        // Bouton pour sauvegarder les données
        btnSave.setOnClickListener(v -> saveChambreData());
    }

    private void loadHotelIds() {
        new Thread(() -> {
            List<Hotel> hotels = database.hotelDao().getAllHotels();
            List<String> hotelIds = new ArrayList<>();

            for (Hotel hotel : hotels) {
                hotelIds.add("ID: " + hotel.getId() + " - " + hotel.getName());
            }

            runOnUiThread(() -> {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, hotelIds);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerHotelId.setAdapter(adapter);
            });

            // Définir l'action lors de la sélection d'un ID d'hôtel dans le Spinner
            spinnerHotelId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedHotelId = hotels.get(position).getId(); // Obtenez l'ID de l'hôtel sélectionné
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    selectedHotelId = -1;
                }
            });
        }).start();
    }

    private void loadChambreData(int chambreId) {
        // Charger les informations de la chambre depuis la base de données
        new Thread(() -> {
            Chambre chambre = database.chambreDao().getChambreById(chambreId);
            runOnUiThread(() -> {
                if (chambre != null) {
                    // Trouver l'index correspondant dans le Spinner
                    etChambreType.setText(chambre.getType());
                    etNbAdultes.setText(String.valueOf(chambre.getNbAdultes()));
                    etNbEnfants.setText(String.valueOf(chambre.getNbEnfants()));
                    etPricePerNight.setText(String.valueOf(chambre.getPricePerNight()));
                    etDateDebutDisponibilite.setText(chambre.getDateDebutDisponibilite());
                    etDateFinDisponibilite.setText(chambre.getDateFinDisponibilite());
                    // Sélectionner l'hôtel dans le Spinner
                    for (int i = 0; i < spinnerHotelId.getCount(); i++) {
                        if (chambre.getHotelId() == selectedHotelId) {
                            spinnerHotelId.setSelection(i);
                            break;
                        }
                    }
                }
            });
        }).start();
    }

    private void saveChambreData() {
        String type = etChambreType.getText().toString().trim();
        String nbAdultesStr = etNbAdultes.getText().toString().trim();
        String nbEnfantsStr = etNbEnfants.getText().toString().trim();
        String pricePerNightStr = etPricePerNight.getText().toString().trim();
        String dateDebut = etDateDebutDisponibilite.getText().toString().trim();
        String dateFin = etDateFinDisponibilite.getText().toString().trim();

        if (TextUtils.isEmpty(type) || TextUtils.isEmpty(nbAdultesStr) || TextUtils.isEmpty(nbEnfantsStr)
                || TextUtils.isEmpty(pricePerNightStr) || TextUtils.isEmpty(dateDebut) || TextUtils.isEmpty(dateFin)) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        int nbAdultes = Integer.parseInt(nbAdultesStr);
        int nbEnfants = Integer.parseInt(nbEnfantsStr);
        double pricePerNight = Double.parseDouble(pricePerNightStr);

        Chambre chambre = new Chambre();
        chambre.setHotelId(selectedHotelId); // Utiliser l'ID de l'hôtel sélectionné
        chambre.setType(type);
        chambre.setNbAdultes(nbAdultes);
        chambre.setNbEnfants(nbEnfants);
        chambre.setPricePerNight(pricePerNight);
        chambre.setDateDebutDisponibilite(dateDebut);
        chambre.setDateFinDisponibilite(dateFin);

        if (isEditMode) {
            chambre.setId(chambreId);
            updateChambre(chambre);
        } else {
            addNewChambre(chambre);
        }
    }

    private void addNewChambre(Chambre chambre) {
        new Thread(() -> {
            database.chambreDao().insertChambre(chambre);
            runOnUiThread(() -> {
                Toast.makeText(this, "Chambre ajoutée avec succès", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK); // Set result to indicate success
                finish();
            });
        }).start();
    }

    private void updateChambre(Chambre chambre) {
        new Thread(() -> {
            database.chambreDao().updateChambre(chambre);
            runOnUiThread(() -> {
                Toast.makeText(this, "Chambre modifiée avec succès", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK); // Set result to indicate success
                finish();
            });
        }).start();
    }

    private void showDatePickerDialog(EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
            editText.setText(date);
        }, year, month, day);
        datePickerDialog.show();
    }
}
