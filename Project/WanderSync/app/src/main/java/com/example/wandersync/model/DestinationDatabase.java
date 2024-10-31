package com.example.wandersync.model;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DestinationDatabase {
    private static DestinationDatabase instance;
    private DatabaseReference databaseReference;
    private MutableLiveData<List<TravelLog>> travelLogsLiveData = new MutableLiveData<>();

    private DestinationDatabase() {
        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("travel_logs");
    }

    public static synchronized DestinationDatabase getInstance() {
        if (instance == null) {
            instance = new DestinationDatabase();
        }
        return instance;
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    // Method to retrieve a travel log
    public MutableLiveData<List<TravelLog>> getTravelLogsLiveData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<TravelLog> travelLogs = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    TravelLog travelLog = snapshot.getValue(TravelLog.class);
                    travelLogs.add(travelLog);
                }
                travelLogsLiveData.setValue(travelLogs);
            }
            
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DestinationDatabase", "Error reading data: " + databaseError.getMessage());
            }
        });
        return travelLogsLiveData;
    }

    // Method to add a travel log
    public String addTravelLog(TravelLog travelLog) {
        String id = databaseReference.child("travel_logs").push().getKey();
        travelLog.setId(id);
        Log.d("DestinationDatabase", "addTravelLog:" + id);
        assert id != null;
        databaseReference.child(id).setValue(travelLog);
        return id;
    }




    // Add more methods as needed for retrieving and deleting travel logs
}