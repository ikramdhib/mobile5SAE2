package com.example.bookingapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bookingapp.R;
import com.example.bookingapp.entity.Bus;
import java.util.List;

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.BusViewHolder> {

    private List<Bus> busList;
    private OnItemClickListener listener;

    public BusAdapter(List<Bus> busList, OnItemClickListener listener) {
        this.busList = busList;
        this.listener = listener;
    }

    @Override
    public BusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bus, parent, false);
        return new BusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BusViewHolder holder, int position) {
        Bus bus = busList.get(position);
        holder.textViewNbSeats.setText("Seats: " + bus.getNbSeats());
        holder.textViewDestination.setText("Destination: " + bus.getDestination());
        holder.textViewTicketPrice.setText("Price: " + bus.getTicketprice());

        holder.btnEdit.setOnClickListener(v -> listener.onEdit(bus));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(bus));
    }

    @Override
    public int getItemCount() {
        return busList.size();
    }

    public interface OnItemClickListener {
        void onEdit(Bus bus);
        void onDelete(Bus bus);
    }

    public static class BusViewHolder extends RecyclerView.ViewHolder {

        TextView textViewNbSeats, textViewDestination, textViewTicketPrice;
        Button btnEdit, btnDelete;

        public BusViewHolder(View itemView) {
            super(itemView);
            textViewNbSeats = itemView.findViewById(R.id.textViewNbSeats);
            textViewDestination = itemView.findViewById(R.id.textViewDestination);
            textViewTicketPrice = itemView.findViewById(R.id.textViewTicketPrice);
            btnEdit = itemView.findViewById(R.id.buttonEdit);
            btnDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
