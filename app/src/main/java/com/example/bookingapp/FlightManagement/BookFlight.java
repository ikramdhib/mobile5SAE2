package com.example.bookingapp.FlightManagement;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.bookingapp.R;
import com.example.bookingapp.database.AppDatabase;
import com.example.bookingapp.entity.Flight;
import com.example.bookingapp.entity.ReservationFlight;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

public class BookFlight extends AppCompatActivity {

    private TextView flightDate, flightDepartureTime, flightArrivalTime, passengerCount, totalPrice;
    private Button btnDecreasePassengers, btnIncreasePassengers, btnFinalBook, btnCancel;

    private int id, passengers = 1;
    private double pricePerPassenger; // Valeur par défaut, peut être mise à jour avec les données passées
    private double totalCost;

    private Flight flight;  // Objet Flight pour récupérer les informations sur le vol

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_flight);

        // Initialisation des vues
        flightDate = findViewById(R.id.flightDate);
        flightDepartureTime = findViewById(R.id.flightDepartureTime);
        flightArrivalTime = findViewById(R.id.flightArrivalTime);
        passengerCount = findViewById(R.id.passengerCount);
        totalPrice = findViewById(R.id.totalPrice);
        btnDecreasePassengers = findViewById(R.id.btnDecreasePassengers);
        btnIncreasePassengers = findViewById(R.id.btnIncreasePassengers);
        btnFinalBook = findViewById(R.id.btnFinalBook);
        btnCancel = findViewById(R.id.btnAnnuler); // Initialisation du bouton Cancel

        // Récupérer les informations depuis l'Intent
        Intent intent = getIntent();
        id = intent.getIntExtra("flightId", -1);
        String date = intent.getStringExtra("flightDate");
        String departureTime = intent.getStringExtra("departureTime");
        String arrivalTime = intent.getStringExtra("arrivalTime");
        pricePerPassenger = intent.getDoubleExtra("price", 100.00); // Prix par passager par défaut si non spécifié

        // Obtenez l'objet Flight pour vérifier le nombre de sièges disponibles
        AppDatabase db = AppDatabase.getAppDatabase(BookFlight.this);
        flight = db.flightDao().getFlightById(id);  // Obtenez le vol par son ID

        // Mettre à jour l'interface utilisateur
        flightDate.setText("Date: " + date);
        flightDepartureTime.setText("Departure Time: " + departureTime);
        flightArrivalTime.setText("Arrival Time: " + arrivalTime);
        updateTotalPrice();
// Vérification des permissions avant de générer le QR code
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Demande de permission si elle n'est pas déjà accordée
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        // Configuration des boutons
        btnDecreasePassengers.setOnClickListener(v -> {
            if (passengers > 1) {
                passengers--;
                passengerCount.setText(String.valueOf(passengers));
                updateTotalPrice();
            }
        });

        btnIncreasePassengers.setOnClickListener(v -> {
            if (passengers < flight.getNbSeats()) {
                passengers++;
                passengerCount.setText(String.valueOf(passengers));
                updateTotalPrice();
            } else {
                Toast.makeText(BookFlight.this, "Not enough available seats!", Toast.LENGTH_SHORT).show();
            }
        });

        btnFinalBook.setOnClickListener(v -> {
            if (passengers <= flight.getNbSeats()) {
                new Thread(() -> {
                    AppDatabase database = AppDatabase.getAppDatabase(BookFlight.this);
                    for (int i = 0; i < passengers; i++) {
                        ReservationFlight reservation = new ReservationFlight();
                        reservation.setCreatedAt(flight.getFlightDate());
                        reservation.setFlightId(id);
                        reservation.setUserId(1);
                        reservation.setStatus("CONFIRMED");
                        reservation.setPrice(pricePerPassenger);

                        database.reservationFlightDao().insert(reservation);
                    }

                    flight.setNbSeats(flight.getNbSeats() - passengers);
                    database.flightDao().updateFlight(flight);

                    runOnUiThread(() -> {
                        String flightDetails = "Flight Details:\n" +
                                "Date: " + flight.getFlightDate() + "\n" +
                                "Departure: " + flight.getDepartureTime() + "\n" +
                                "Arrival: " + flight.getArrivalTime() + "\n" +
                                "Passengers: " + passengers + "\n" +
                                "Total Price: DT" + totalCost;

                        Bitmap qrCodeBitmap = generateQRCode(flightDetails);
                        saveQRCodeToExternalStorage(qrCodeBitmap);

                        // Afficher un message de confirmation
                        Toast.makeText(BookFlight.this, "Reservation confirmed! QR code saved.", Toast.LENGTH_SHORT).show();

                        Intent confirmationIntent = new Intent(BookFlight.this, ConfirmationFlight.class);
                        confirmationIntent.putExtra("flightDetails", flightDetails);
                        startActivity(confirmationIntent);
                        finish();
                    });
                }).start();
            } else {
                Toast.makeText(BookFlight.this, "Not enough seats available for the number of passengers!", Toast.LENGTH_SHORT).show();
            }
        });


        // Configuration du bouton Cancel
        btnCancel.setOnClickListener(v -> {
            // Retourner à l'activité de liste des vols (SortedFilightList)
            Intent backToList = new Intent(BookFlight.this, FindFlightActivity.class);
            startActivity(backToList);
            finish();  // Facultatif : pour fermer l'activité BookFlight et ne pas la laisser dans la pile d'activités
        });
    }

    private void updateTotalPrice() {
        totalCost = passengers * pricePerPassenger;
        totalPrice.setText(String.format("DT%.2f", totalCost));
    }

    // Générer le QR code
    private Bitmap generateQRCode(String content) {
        try {
            Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
            hints.put(EncodeHintType.MARGIN, 1);

            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 500, 500, hints);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }

            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveQRCodeToExternalStorage(Bitmap qrCodeBitmap) {
        // Utiliser getExternalFilesDir() pour obtenir un répertoire spécifique à l'application
        File path = new File(getExternalFilesDir(null), "BookingApp/QR_Codes");

        // Vérifier si le répertoire existe, sinon le créer
        if (!path.exists()) {
            boolean created = path.mkdirs(); // Créer les répertoires nécessaires
            if (created) {
                Log.d("QR Code", "Directory created: " + path.getAbsolutePath());
            } else {
                Log.d("QR Code", "Directory already exists or failed to create");
            }
        }

        // Créer le fichier pour sauvegarder le QR code
        File file = new File(path, "reservation_qr_code.png");

        try (FileOutputStream fos = new FileOutputStream(file)) {
            qrCodeBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            Toast.makeText(this, "QR Code saved!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving QR Code", Toast.LENGTH_SHORT).show();
        }
    }

}
