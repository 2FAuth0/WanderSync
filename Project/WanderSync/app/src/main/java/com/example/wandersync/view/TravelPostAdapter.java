package com.example.wandersync.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wandersync.R;
import com.example.wandersync.model.TravelPost;
import java.util.List;
import androidx.recyclerview.widget.LinearLayoutManager;

public class TravelPostAdapter
        extends RecyclerView.Adapter<TravelPostAdapter.TravelPostViewHolder> {

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
        holder.destination.setText(travelPost.getDestination());
        holder.tripNotes.setText(travelPost.getNotes());

        AccommodationReservationAdapter accommodationAdapter
                = new AccommodationReservationAdapter(travelPost.getAccommodations());
        holder.accommodationsRecyclerView.setAdapter(accommodationAdapter);
        holder.accommodationsRecyclerView.setLayoutManager(
                new LinearLayoutManager(holder.itemView.getContext()));

        DiningReservationAdapter diningAdapter
                = new DiningReservationAdapter(travelPost.getDiningReservations());
        holder.diningRecyclerView.setAdapter(diningAdapter);
        holder.diningRecyclerView.setLayoutManager(
                new LinearLayoutManager(holder.itemView.getContext()));
    }

    @Override
    public int getItemCount() {
        return travelPosts == null ? 0 : travelPosts.size();
    }

    static class TravelPostViewHolder extends RecyclerView.ViewHolder {
        private TextView username;
        private TextView destination;
        private TextView dates;
        private RecyclerView accommodationsRecyclerView;
        private RecyclerView diningRecyclerView;
        private TextView tripNotes;

        TravelPostViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.text_view_username);
            destination = itemView.findViewById(R.id.text_view_destination);
            dates = itemView.findViewById(R.id.text_view_dates);
            accommodationsRecyclerView = itemView.findViewById(R.id.recycler_view_accommodations);
            diningRecyclerView = itemView.findViewById(R.id.recycler_view_dining);
            tripNotes = itemView.findViewById(R.id.text_view_trip_notes);
        }
    }
}