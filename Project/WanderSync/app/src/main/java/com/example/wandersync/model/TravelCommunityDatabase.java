package com.example.wandersync.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TravelCommunityDatabase {
    private static TravelCommunityDatabase instance;
    private DatabaseReference databaseReference;

    private TravelCommunityDatabase() {
        databaseReference = FirebaseDatabase.getInstance().getReference("community_travel");
    }
    private static synchronized TravelCommunityDatabase getInstance() {
        if (instance == null) {
            instance = new TravelCommunityDatabase();
        }
        return instance;
    }
}