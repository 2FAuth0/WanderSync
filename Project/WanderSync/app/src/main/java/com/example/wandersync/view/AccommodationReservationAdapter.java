package com.example.wandersync.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wandersync.R;
import com.example.wandersync.model.AccommodationReservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            if (sdf.parse(reservation.getCheckIn()).getTime() > (new Date()).getTime()) {
                holder.pastAccommodationTextView.setText("");
            } else {
                holder.pastAccommodationTextView.setText("PAST");
            }
        } catch (ParseException e) { }
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
        private TextView pastAccommodationTextView;

        public AccommodationViewHolder(@NonNull View itemView) {
            super(itemView);
            locationView = itemView.findViewById(R.id.text_location);
            checkInTextView = itemView.findViewById(R.id.text_check_in);
            checkOutTextView = itemView.findViewById(R.id.text_check_out);
            numRoomsTextView = itemView.findViewById(R.id.text_num_rooms);
            roomTypeTextView = itemView.findViewById(R.id.text_room_type);
            pastAccommodationTextView = itemView.findViewById(R.id.past_label);
        }
    }
}