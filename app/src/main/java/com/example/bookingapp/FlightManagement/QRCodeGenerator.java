package com.example.bookingapp.FlightManagement;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRCodeGenerator {
    public static Bitmap generateQRCode(String content) {
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            return barcodeEncoder.encodeBitmap(content, BarcodeFormat.QR_CODE, 400, 400);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }
}
