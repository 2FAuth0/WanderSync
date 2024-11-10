package com.example.wandersync.model;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class AccommodationDatabase {
    private static AccommodationDatabase instance;
    private DatabaseReference databaseReference;
    private MutableLiveData<List<AccommodationReservation>> accommodationReservationsLiveData =
            new MutableLiveData<>();

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
        Log.d("AccommodationDatabase", "addAccommodationReservation: "
                + reservation.getCheckIn() + " " + reservation.getCheckOut() + " "
                + reservation.getNumRooms() + " " + reservation.getRoomType());
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (uid != null) {
            // Generate a unique key for each accommodation reservation
            String id = databaseReference.child(uid).child("accommodation_reservations")
                    .push().getKey();
            reservation.setId(id);
            Log.d("AccommodationDatabase", "addAccommodationReservation: " + id);
            assert id != null;

            // Save the accommodation under users/{UID}/accommodation_reservations/{ReservationID}
            databaseReference.child(uid).child("accommodation_reservations")
                    .child(id).setValue(reservation);
        }
    }

    // Method to retrieve all accommodation reservations
    public MutableLiveData<List<AccommodationReservation>> getAccommodationReservationsLiveData() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (uid != null) {
            DatabaseReference userAccommodationReservationsRef =
                    databaseReference.child(uid).child("accommodation_reservations");
            userAccommodationReservationsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<AccommodationReservation> reservations = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        AccommodationReservation reservation =
                                snapshot.getValue(AccommodationReservation.class);
                        if (reservation != null) {
                            reservation.setPast(isPastReservation(reservation.getCheckIn()));
                            reservations.add(reservation);
                        }
                    }

                    sortReservations(reservations);

                    accommodationReservationsLiveData.setValue(reservations);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("AccommodationDatabase", "Error reading data: "
                            + databaseError.getMessage());
                }
            });
        }
        return accommodationReservationsLiveData;
    }

    // Method to update an existing accommodation reservation
    public void updateAccommodationReservation(String reservationId,
                                               AccommodationReservation updatedReservation) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (uid != null && reservationId != null) {
            DatabaseReference reservationRef = databaseReference.child(uid)
                    .child("accommodation_reservations").child(reservationId);

            reservationRef.setValue(updatedReservation)
                    .addOnSuccessListener(aVoid -> Log.d("AccommodationDatabase",
                            "Reservation updated"))
                    .addOnFailureListener(e -> Log.e("AccommodationDatabase",
                            "Failed to update reservation", e));
        }
    }

    public boolean isPastReservation(String checkInDate) {
        if (checkInDate == null || checkInDate.isEmpty()) {
            Log.w("AccommodationDatabase", "Check-in date is null or empty");
            return false;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);  // This will cause the parse to be strict
            Date date = sdf.parse(checkInDate);
            return date.before(new Date());
        } catch (ParseException e) {
            Log.e("AccommodationDatabase", "Error parsing date: " + checkInDate, e);
            return false;
        }
    }
    public void sortReservations(List<AccommodationReservation> reservations) {
        Collections.sort(reservations, (r1, r2) -> {
            String checkInDate1 = r1.getCheckIn();
            String checkInDate2 = r2.getCheckIn();

            if (checkInDate1 == null && checkInDate2 == null) {
                return 0; // Both are null, considered equal
            }
            if (checkInDate1 == null) {
                return 1; // Push nulls to the end
            }
            if (checkInDate2 == null) {
                return -1; // Push nulls to the end
            }

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date1 = sdf.parse(checkInDate1);
                Date date2 = sdf.parse(checkInDate2);
                return date1.compareTo(date2);
            } catch (ParseException e) {
                Log.e("AccommodationDatabase", "Error parsing date", e);
                return 0; // In case of parse exception, consider them equal
            }
        });
    }
}
