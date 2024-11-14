package com.example.bookingapp.FlightManagement;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class QRCodeSaver {

    private Context context;

    public QRCodeSaver(Context context) {
        this.context = context;
    }

    public File saveQRCodeImage(Bitmap qrCodeBitmap, String fileName) throws IOException {
        // Créer un répertoire pour stocker les codes QR
        File directory = new File(context.getExternalFilesDir(null), "BookingApp/QR_Codes");
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IOException("Failed to create directory: " + directory.getAbsolutePath());
        }

        // Créer le fichier
        File qrCodeFile = new File(directory, fileName);
        try (FileOutputStream outputStream = new FileOutputStream(qrCodeFile)) {
            qrCodeBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        }

        return qrCodeFile;
    }
}
