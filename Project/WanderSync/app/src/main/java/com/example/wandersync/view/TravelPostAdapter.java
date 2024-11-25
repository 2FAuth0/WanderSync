package com.example.wandersync.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wandersync.R;
import com.example.wandersync.model.AccommodationReservation;
import com.example.wandersync.model.TravelLog;
import com.example.wandersync.model.TravelPost;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TravelPostAdapter extends RecyclerView.Adapter<TravelPostAdapter.TravelPostViewHolder> {

    private List<TravelPost> travelPosts;

    public TravelPostAdapter(List<TravelPost> travelPosts) {
        this.travelPosts = travelPosts;
    }

    public void setTravelLogs(List<TravelPost> travelLogs) {
        this.travelPosts = travelLogs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TravelPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_travel_post, parent, false);
        return new TravelPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TravelPostViewHolder holder, int position) {
        TravelPost travelPost = travelPosts.get(position);
        holder.username.setText(travelPost.getId());
        holder.checkInDate.setText("Check-in: ");  // TODO: database mismatch
        holder.checkOutDate.setText("Check-out:"); // TODO: database mismatch
        holder.accommodations.setText("Accommodations: "); // TODO: database mismatch
        holder.diningReservations.setText("Dining: "); // TODO: database mismatch
        holder.tripNotes.setText(travelPost.getNotes());
    }

    @Override
    public int getItemCount() {
        return travelPosts == null ? 0 : travelPosts.size();
    }

    static class TravelPostViewHolder extends RecyclerView.ViewHolder {
        private TextView username;
        private TextView destination;
        private TextView checkInDate;
        private TextView checkOutDate;
        private TextView accommodations;
        private TextView diningReservations;
        private TextView tripNotes;

        TravelPostViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.edit_text_username);
            destination = itemView.findViewById(R.id.edit_text_destination);
            checkInDate = itemView.findViewById(R.id.edit_text_check_in_date);
            checkOutDate = itemView.findViewById(R.id.edit_text_check_out_date);
            accommodations = itemView.findViewById(R.id.edit_text_accommodations);
            diningReservations = itemView.findViewById(R.id.edit_text_dining_reservations);
            tripNotes = itemView.findViewById(R.id.edit_text_trip_notes);
        }

//        public TextView getTextViewLocation() {
//            return textViewLocation;
//        }
//
//        public TextView getTextViewDuration() {
//            return textViewDuration;
//        }
//    }
    }
}