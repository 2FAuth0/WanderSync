package com.example.wandersync.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wandersync.R;
import com.example.wandersync.model.AccommodationReservation;

import org.w3c.dom.Text;

import java.util.List;

public class AccommodationReservationAdapter extends RecyclerView.Adapter<AccommodationReservationAdapter.AccommodationViewHolder> {

    private List<AccommodationReservation> accommodationList;

    public AccommodationReservationAdapter(List<AccommodationReservation> accommodationList) {
        this.accommodationList = accommodationList;
    }

    @NonNull
    @Override
    public AccommodationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_accommodation_reservation, parent, false);
        return new AccommodationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccommodationViewHolder holder, int position) {
        AccommodationReservation reservation = accommodationList.get(position);
        holder.locationView.setText("Location: " + reservation.getLocation());
        holder.checkInTextView.setText("Check-in: " + reservation.getCheck_in());
        holder.checkOutTextView.setText("Check-out: " + reservation.getCheck_out());
        holder.numRoomsTextView.setText("Rooms: " + reservation.getNum_Rooms());
        holder.roomTypeTextView.setText("Room Type: " + reservation.getRoom_Type());
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
        TextView locationView;
        TextView checkInTextView;
        TextView checkOutTextView;
        TextView numRoomsTextView;
        TextView roomTypeTextView;

        public AccommodationViewHolder(@NonNull View itemView) {
            super(itemView);
            locationView = itemView.findViewById(R.id.text_location);
            checkInTextView = itemView.findViewById(R.id.text_check_in);
            checkOutTextView = itemView.findViewById(R.id.text_check_out);
            numRoomsTextView = itemView.findViewById(R.id.text_num_rooms);
            roomTypeTextView = itemView.findViewById(R.id.text_room_type);
        }
    }
}