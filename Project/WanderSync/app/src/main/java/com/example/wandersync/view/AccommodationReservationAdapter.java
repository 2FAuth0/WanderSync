package com.example.wandersync.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wandersync.R;
import com.example.wandersync.model.AccommodationReservation;

import java.util.List;

public class AccommodationReservationAdapter
        extends RecyclerView.Adapter<AccommodationReservationAdapter.AccommodationViewHolder> {

    private List<AccommodationReservation> accommodationList;

    public AccommodationReservationAdapter(List<AccommodationReservation> accommodationList) {
        this.accommodationList = accommodationList;
    }

    @NonNull
    @Override
    public AccommodationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_accommodation_reservation, parent, false);
        return new AccommodationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccommodationViewHolder holder, int position) {
        AccommodationReservation reservation = accommodationList.get(position);
        holder.locationView.setText("Location: " + reservation.getLocation());
        holder.checkInTextView.setText("Check-in: " + reservation.getCheckIn());
        holder.checkOutTextView.setText("Check-out: " + reservation.getCheckOut());
        holder.numRoomsTextView.setText("Rooms: " + reservation.getNumRooms());
        holder.roomTypeTextView.setText("Room Type: " + reservation.getRoomType());
        // Set visibility of past indicator
        if (reservation.isPast()) {
            holder.textPastIndicator.setVisibility(View.VISIBLE);
        } else {
            holder.textPastIndicator.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return accommodationList != null ? accommodationList.size() : 0;
    }

    public void setAccommodationList(List<AccommodationReservation> accommodationList) {
        this.accommodationList = accommodationList;
        notifyDataSetChanged();
    }

    static class AccommodationViewHolder extends RecyclerView.ViewHolder {
        private TextView locationView;
        private TextView checkInTextView;
        private TextView checkOutTextView;
        private TextView numRoomsTextView;
        private TextView roomTypeTextView;
        private TextView textPastIndicator;

        public AccommodationViewHolder(@NonNull View itemView) {
            super(itemView);
            locationView = itemView.findViewById(R.id.text_location);
            checkInTextView = itemView.findViewById(R.id.text_check_in);
            checkOutTextView = itemView.findViewById(R.id.text_check_out);
            numRoomsTextView = itemView.findViewById(R.id.text_num_rooms);
            roomTypeTextView = itemView.findViewById(R.id.text_room_type);
            textPastIndicator = itemView.findViewById(R.id.text_past_indicator);
        }
    }
}