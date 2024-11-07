package com.example.wandersync.model;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.auth.FirebaseAuth;
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
    private MutableLiveData<List<AccommodationReservation>> accommodationReservationsLiveData = new MutableLiveData<>();

    private AccommodationDatabase() {
        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
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
    public void addAccommodationReservation(AccommodationReservation reservation) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (uid != null) {
            // Generate a unique key for each accommodation reservation
            String id = databaseReference.child(uid).child("accommodation_reservations").push().getKey();
            reservation.setId(id);
            Log.d("AccommodationDatabase", "addAccommodationReservation: " + id);
            assert id != null;

            // Save the accommodation reservation under users/{UID}/accommodation_reservations/{ReservationID}
            databaseReference.child(uid).child("accommodation_reservations").child(id).setValue(reservation);
        }
    }

    // Method to retrieve all accommodation reservations
    public MutableLiveData<List<AccommodationReservation>> getAccommodationReservationsLiveData() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (uid != null) {
            DatabaseReference userAccommodationReservationsRef = databaseReference.child(uid).child("accommodation_reservations");
            userAccommodationReservationsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<AccommodationReservation> reservations = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        AccommodationReservation reservation = snapshot.getValue(AccommodationReservation.class);
                        reservations.add(reservation);
                    }
                    accommodationReservationsLiveData.setValue(reservations);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("AccommodationDatabase", "Error reading data: " + databaseError.getMessage());
                }
            });
        }
        return accommodationReservationsLiveData;
    }

    // Method to update an existing accommodation reservation
    public void updateAccommodationReservation(String reservationId, AccommodationReservation updatedReservation) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (uid != null && reservationId != null) {
            DatabaseReference reservationRef = databaseReference.child(uid)
                    .child("accommodation_reservations").child(reservationId);

            reservationRef.setValue(updatedReservation)
                    .addOnSuccessListener(aVoid -> Log.d("AccommodationDatabase", "Reservation updated"))
                    .addOnFailureListener(e -> Log.e("AccommodationDatabase", "Failed to update reservation", e));
        }
    }
}
