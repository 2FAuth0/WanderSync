package com.example.wandersync.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wandersync.R;
import com.example.wandersync.model.TravelLog;

import java.util.List;

public class TravelLogAdapter extends RecyclerView.Adapter<TravelLogAdapter.TravelLogViewHolder> {

    private List<TravelLog> travelLogs;

    public TravelLogAdapter(List<TravelLog> travelLogs) {
        this.travelLogs = travelLogs;
    }

    public void setTravelLogs(List<TravelLog> travelLogs) {
        this.travelLogs = travelLogs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TravelLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_travel_log, parent, false);
        return new TravelLogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TravelLogViewHolder holder, int position) {
        TravelLog travelLog = travelLogs.get(position);
        holder.textViewLocation.setText(travelLog.getLocation());
        holder.textViewDuration.setText(travelLog.getDuration() + " days");
    }

    @Override
    public int getItemCount() {
        return travelLogs == null ? 0 : travelLogs.size();
    }

    static class TravelLogViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewLocation;
        private TextView textViewDuration;

        TravelLogViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewLocation = itemView.findViewById(R.id.textViewLocation);
            textViewDuration = itemView.findViewById(R.id.textViewDuration);
        }

        public TextView getTextViewLocation() {
            return textViewLocation;
        }

        public TextView getTextViewDuration() {
            return textViewDuration;
        }
    }
}