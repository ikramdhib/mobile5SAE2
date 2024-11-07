package com.example.bookingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.Hotel;

public class AddEditHotelActivity extends AppCompatActivity {

    private EditText etHotelName, etHotelLocation, etHotelDescription, etPricePerNight;
    private CheckBox cbHotelAvailable;
    private Button btnSave;
    private ImageView ivHotelImage;
    private AppDatabase database;
    private boolean isEditMode = false;
    private int hotelId;
    private int selectedImageResource = R.drawable.ic_placeholder;

    private Spinner spinnerImageSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_hotel);

        // Initialisation des vues
        etHotelName = findViewById(R.id.etHotelName);
        etHotelLocation = findViewById(R.id.etHotelLocation);
        etHotelDescription = findViewById(R.id.etHotelDescription);
        etPricePerNight = findViewById(R.id.etPricePerNight);
        cbHotelAvailable = findViewById(R.id.cbHotelAvailable);
        ivHotelImage = findViewById(R.id.ivHotelImage);
        spinnerImageSelector = findViewById(R.id.spinnerImageSelector);
        btnSave = findViewById(R.id.btnSave);

        database = AppDatabase.getAppDatabase(this);

        // Configuration du Spinner pour les images
        setupImageSpinner();

        // Vérifier si on est en mode édition ou ajout
        if (getIntent().hasExtra("hotelId")) {
            isEditMode = true;
            hotelId = getIntent().getIntExtra("hotelId", -1);
            loadHotelData(hotelId);
        }

        // Bouton pour sauvegarder les données
        btnSave.setOnClickListener(v -> saveHotelData());
    }

    private void setupImageSpinner() {
        // Liste des images disponibles dans drawable
        Integer[] images = {
                R.drawable.img1,
                R.drawable.img2,
                R.drawable.img3,
                R.drawable.img4
        };

        // Adapter personnalisé pour afficher les images dans le Spinner
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, images) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(android.R.layout.simple_spinner_item, parent, false);
                }
                ImageView imageView = new ImageView(AddEditHotelActivity.this);
                imageView.setImageResource(getItem(position));
                imageView.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
                return imageView;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
                }
                ImageView imageView = new ImageView(AddEditHotelActivity.this);
                imageView.setImageResource(getItem(position));
                imageView.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
                return imageView;
            }
        };

        spinnerImageSelector.setAdapter(adapter);

        spinnerImageSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedImageResource = images[position];
                ivHotelImage.setImageResource(selectedImageResource); // Afficher l'image sélectionnée
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedImageResource = R.drawable.ic_placeholder;
            }
        });
    }

    private void loadHotelData(int hotelId) {
        new Thread(() -> {
            Hotel hotel = database.hotelDao().getHotelById(hotelId);
            runOnUiThread(() -> {
                if (hotel != null) {
                    etHotelName.setText(hotel.getName());
                    etHotelLocation.setText(hotel.getLocation());
                    etHotelDescription.setText(hotel.getDescription());
                    etPricePerNight.setText(String.valueOf(hotel.getPricePerNight()));
                    cbHotelAvailable.setChecked(hotel.isAvailable());

                    // Sélectionner l'image dans le Spinner
                    selectedImageResource = hotel.getImageResource();
                    ivHotelImage.setImageResource(selectedImageResource);

                    for (int i = 0; i < spinnerImageSelector.getCount(); i++) {
                        if ((int) spinnerImageSelector.getItemAtPosition(i) == selectedImageResource) {
                            spinnerImageSelector.setSelection(i);
                            break;
                        }
                    }
                }
            });
        }).start();
    }

    private void saveHotelData() {
        String name = etHotelName.getText().toString().trim();
        String location = etHotelLocation.getText().toString().trim();
        String description = etHotelDescription.getText().toString().trim();
        String pricePerNightStr = etPricePerNight.getText().toString().trim();
        boolean isAvailable = cbHotelAvailable.isChecked();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(location) || TextUtils.isEmpty(description) || TextUtils.isEmpty(pricePerNightStr)) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        double pricePerNight = Double.parseDouble(pricePerNightStr);

        Hotel hotel = new Hotel();
        hotel.setName(name);
        hotel.setLocation(location);
        hotel.setDescription(description);
        hotel.setPricePerNight(pricePerNight);
        hotel.setAvailable(isAvailable);
        hotel.setImageResource(selectedImageResource);

        if (isEditMode) {
            hotel.setId(hotelId);
            updateHotel(hotel);
        } else {
            addNewHotel(hotel);
        }
    }

    private void addNewHotel(Hotel hotel) {
        new Thread(() -> {
            database.hotelDao().insertHotel(hotel);
            runOnUiThread(() -> {
                Toast.makeText(this, "Hôtel ajouté avec succès", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            });
        }).start();
    }

    private void updateHotel(Hotel hotel) {
        new Thread(() -> {
            database.hotelDao().updateHotel(hotel);
            runOnUiThread(() -> {
                Toast.makeText(this, "Hôtel modifié avec succès", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            });
        }).start();
    }
}
