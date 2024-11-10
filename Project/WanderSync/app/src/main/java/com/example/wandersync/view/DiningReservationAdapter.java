package com.example.wandersync.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wandersync.R;
import com.example.wandersync.model.DiningReservation;

import java.util.List;

public class DiningReservationAdapter
        extends RecyclerView.Adapter<DiningReservationAdapter.DiningViewHolder> {

    private List<DiningReservation> diningList;

    public DiningReservationAdapter(List<DiningReservation> diningList) {
        this.diningList = diningList;
    }

    @NonNull
    @Override
    public DiningViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dining_reservation, parent, false);
        return new DiningViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiningViewHolder holder, int position) {
        DiningReservation reservation = diningList.get(position);
        holder.locationView.setText("Location: " + reservation.getLocation());
        holder.timingTextView.setText("Time: " + reservation.getTiming());
        holder.websiteTextView.setText("Website: " + reservation.getWebsite());
    }

    @Override
    public int getItemCount() {
        return diningList != null ? diningList.size() : 0;
    }

    public void setDiningList(List<DiningReservation> diningList) {
        this.diningList = diningList;
        notifyDataSetChanged();
    }

    static class DiningViewHolder extends RecyclerView.ViewHolder {
        private TextView locationView;
        private TextView timingTextView;
        private TextView websiteTextView;

        public DiningViewHolder(@NonNull View itemView) {
            super(itemView);
            locationView = itemView.findViewById(R.id.text_location);
            timingTextView = itemView.findViewById(R.id.text_timing);
            websiteTextView = itemView.findViewById(R.id.text_website);
        }
    }
}