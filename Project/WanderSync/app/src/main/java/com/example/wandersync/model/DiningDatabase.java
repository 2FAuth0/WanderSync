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

public class DiningDatabase {
    private static DiningDatabase instance;
    private DatabaseReference databaseReference;
    private MutableLiveData<List<DiningReservation>> diningReservationsLiveData =
            new MutableLiveData<>();

    private DiningDatabase() {
        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("dining_reservations");
    }

    public static synchronized DiningDatabase getInstance() {
        if (instance == null) {
            instance = new DiningDatabase();
        }
        return instance;
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    // Method adding Dining Reservation
    public String addDiningReservation(DiningReservation reservation) {
        // Generate a unique key for each dining reservation
        String id = databaseReference.push().getKey();
        reservation.setId(id);
        Log.d("DiningDatabase", "addDiningReservation: " + id);
        assert id != null;

        // Save the dining reservation under users/{UID}/dining_reservations/{ReservationID}
        databaseReference.child(id).setValue(reservation);
        return id;
    }

    // Method to retrieve all dining reservations (returns MutableLiveData)
    public MutableLiveData<List<DiningReservation>> getDiningReservationsLiveData() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<DiningReservation> diningReservations = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DiningReservation reservation = snapshot.getValue(DiningReservation.class);
                    diningReservations.add(reservation);
                }
                diningReservationsLiveData.setValue(diningReservations);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DiningDatabase", "Error reading data: "
                        + databaseError.getMessage());
            }
        });

        return diningReservationsLiveData;
    }


    // Method to update an existing dining reservation
    public void updateDiningReservation(DiningReservation
        updatedReservation) {

        databaseReference.child(updatedReservation.getId()).setValue(updatedReservation)
                .addOnSuccessListener(aVoid -> Log.d("DiningDatabase",
                        "Dining reservation updated successfully"))
                .addOnFailureListener(e -> Log.e("DiningDatabase",
                        "Failed to update dining reservation: " + e.getMessage()));
    }
}
