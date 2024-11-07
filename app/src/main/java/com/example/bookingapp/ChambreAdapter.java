package com.example.bookingapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapp.entity.Chambre;

import java.util.List;

public class ChambreAdapter extends RecyclerView.Adapter<ChambreAdapter.ChambreViewHolder> {

    private List<Chambre> chambreList;
    private OnItemClickListener listener;

    public ChambreAdapter(List<Chambre> chambreList) {
        this.chambreList = chambreList;
    }

    // Définir l'interface pour le clic sur un item
    public interface OnItemClickListener {
        void onItemClick(Chambre chambre);
    }

    // Setter pour le listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChambreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chambre_item, parent, false);
        return new ChambreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChambreViewHolder holder, int position) {
        Chambre chambre = chambreList.get(position);
        holder.bind(chambre, listener);
    }

    @Override
    public int getItemCount() {
        return chambreList != null ? chambreList.size() : 0;
    }

    public void updateData(List<Chambre> newChambreList) {
        this.chambreList = newChambreList;
        notifyDataSetChanged();
    }

    public static class ChambreViewHolder extends RecyclerView.ViewHolder {
        private final TextView chambreType;
        private final TextView chambrePrice;
        private final TextView chambreCapacity;

        public ChambreViewHolder(@NonNull View itemView) {
            super(itemView);
            chambreType = itemView.findViewById(R.id.chambreType);
            chambrePrice = itemView.findViewById(R.id.chambrePrice);
            chambreCapacity = itemView.findViewById(R.id.chambreCapacity);
        }

        public void bind(Chambre chambre, OnItemClickListener listener) {
            chambreType.setText("Type: " + chambre.getType());
            chambrePrice.setText("Prix: TND " + chambre.getPricePerNight());
            chambreCapacity.setText("Capacité: " + chambre.getNbAdultes() + " Adultes, " + chambre.getNbEnfants() + " Enfants");

            // Gérer le clic sur l'élément
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(chambre);
                }
            });
        }
    }
}
