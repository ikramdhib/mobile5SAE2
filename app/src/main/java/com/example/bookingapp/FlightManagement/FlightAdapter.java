package com.example.bookingapp.FlightManagement;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.bookingapp.R;
import com.example.bookingapp.entity.Flight;

import java.util.List;

public class FlightAdapter extends ArrayAdapter<Flight> {
    private final Context context;
    private final List<Flight> flights;

    public FlightAdapter(Context context, List<Flight> flights) {
        super(context, R.layout.flight_item, flights);
        this.context = context;
        this.flights = flights;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Flight flight = flights.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.flight_item, parent, false);
        }

        TextView matriculeTextView = convertView.findViewById(R.id.matriculeFlightTextView);
        TextView fromTextView = convertView.findViewById(R.id.fromTextView);
        TextView toTextView = convertView.findViewById(R.id.toTextView);
        TextView dateTextView = convertView.findViewById(R.id.dateTextView);
        TextView seatsTextView = convertView.findViewById(R.id.seatsTextView);

        matriculeTextView.setText(flight.getFlightMatricule());
        fromTextView.setText(flight.getFrom());
        toTextView.setText(flight.getTo());
        dateTextView.setText(flight.getFlightDate());
        seatsTextView.setText(String.valueOf(flight.getNbSeats()));

        // Ajout d'un OnClickListener pour chaque élément
        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, FlightDetail.class);
            intent.putExtra("flight", flight); // Passer l'objet Flight à la nouvelle activité
            context.startActivity(intent);
        });

        return convertView;
    }
}
