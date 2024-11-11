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

public class AccommodationDatabase {
    private static AccommodationDatabase instance;
    private DatabaseReference databaseReference;
    private MutableLiveData<List<AccommodationReservation>>
            accommodationReservationsLiveData = new MutableLiveData<>();

    private AccommodationDatabase() {
        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().
                getReference("accommodation_reservations");
    }

    public static synchronized AccommodationDatabase getInstance() {
        if (instance == null) {
            instance = new AccommodationDatabase();
        }
        return instance;
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    // Method to add an accommodation reservation
    public String addAccommodationReservation(AccommodationReservation reservation) {
        Log.d("AccommodationDatabase", "addAccommodationReservation: " + reservation.getCheckIn()
                + " " + reservation.getCheckOut() + " "
                + reservation.getNumRooms() + " "
                + reservation.getRoomType());
        String id = databaseReference.push().getKey();
        reservation.setId(id);
        Log.d("AccommodationDatabase", "addAccommodationReservation: " + id);
        assert id != null;
        databaseReference.child(id).setValue(reservation);
        return id;

    }

    // Method to retrieve all accommodation reservations
    public MutableLiveData<List<AccommodationReservation>> getAccommodationReservationsLiveData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<AccommodationReservation> reservations = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    AccommodationReservation reservation =
                            snapshot.getValue(AccommodationReservation.class);
                    reservations.add(reservation);
                }
                accommodationReservationsLiveData.setValue(reservations);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("AccommodationDatabase", "Error reading data: "
                        + databaseError.getMessage());
            }
        });

        return accommodationReservationsLiveData;
    }

    // Method to update an existing accommodation reservation
    public void updateAccommodationReservation(AccommodationReservation updatedReservation) {
        databaseReference.child(updatedReservation.getId()).setValue(updatedReservation)
                .addOnSuccessListener(aVoid -> Log.d("AccommodationDatabase",
                        "Reservation updated"))
                .addOnFailureListener(e -> Log.e("AccommodationDatabase",
                        "Failed to update reservation", e));
    }
}
