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
    private MutableLiveData<List<DiningReservation>> diningReservationsLiveData = new MutableLiveData<>();

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
    public void addDiningReservation(DiningReservation reservation) {
        String id = databaseReference.push().getKey(); // Generate a unique key
        reservation.setId(id);
        assert id != null;
        databaseReference.child(id).setValue(reservation);
        Log.d("DiningDatabase", "addDiningReservation: " + id);
    }

    // Method to retrieve all dining reservations
    public MutableLiveData<List<DiningReservation>> getDiningReservationsLiveData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<DiningReservation> reservations = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DiningReservation reservation = snapshot.getValue(DiningReservation.class);
                    reservations.add(reservation);
                }
                diningReservationsLiveData.setValue(reservations);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DiningDatabase", "Error reading data: " + databaseError.getMessage());
            }
        });
        return diningReservationsLiveData;
    }

    // Method to update an existing dining reservation
    public void updateDiningReservation(String id, DiningReservation updatedReservation) {
        databaseReference.child(id).setValue(updatedReservation)
                .addOnSuccessListener(aVoid -> Log.d("DiningDatabase", "Reservation updated"))
                .addOnFailureListener(e -> Log.e("DiningDatabase", "Failed to update reservation", e));
        }
}
