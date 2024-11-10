package com.example.wandersync.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.wandersync.R;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ContributorViewHolder> {
    private List<String> contributors = new ArrayList<>();

    @Override
    public ContributorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new ContributorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContributorViewHolder holder, int position) {
        String contributorId = contributors.get(position);
        holder.bind(contributorId);
    }

    @Override
    public int getItemCount() {
        return contributors.size();
    }

    public void setContributors(List<String> newContributors) {
        this.contributors = newContributors;
        notifyDataSetChanged();
    }

    static class ContributorViewHolder extends RecyclerView.ViewHolder {
        private final TextView contributorNameText;

        public ContributorViewHolder(View itemView) {
            super(itemView);
            contributorNameText = itemView.findViewById(R.id.contributor_name);
        }

        public void bind(String contributorId) {
            contributorNameText.setText(contributorId);
        }
    }
}
