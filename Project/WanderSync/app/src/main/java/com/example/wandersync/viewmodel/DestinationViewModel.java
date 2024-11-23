package com.example.wandersync.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.wandersync.model.DestinationDatabase;
import com.example.wandersync.model.TravelLog;
import com.example.wandersync.model.Trip;
import com.example.wandersync.model.TripDatabase;
import com.example.wandersync.model.User;
import com.example.wandersync.model.UserDatabase;
import com.example.wandersync.model.VacationTime;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class DestinationViewModel extends ViewModel {
    private LiveData<List<TravelLog>> travelLogsLiveData;
    private LiveData<List<TravelLog>> tripTravelLogsLiveData;
    private DestinationDatabase destinationDatabase;
    private UserDatabase userDatabase;
    private TripDatabase tripDatabase;
    private LiveData<Integer> allottedDays = new MutableLiveData<>(21); // Example default value
    private LiveData<Integer> plannedDays = new MutableLiveData<>();
    private MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private MutableLiveData<List<User>> allUserLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Trip>> allTripLiveData = new MutableLiveData<>();
    private LiveData<Trip> tripLiveData = new MutableLiveData<>();
    private LiveData<List<String>> contributorsLiveData = new MutableLiveData<>();
    private LiveData<List<String>> notesLiveData = new MutableLiveData<>();

    public DestinationViewModel() {
        destinationDatabase = DestinationDatabase.getInstance();
        userDatabase = UserDatabase.getInstance();
        tripDatabase = TripDatabase.getInstance();

        travelLogsLiveData = destinationDatabase.getTravelLogsLiveData();
        userLiveData =
                userDatabase.getUserData(FirebaseAuth.getInstance().getCurrentUser().getUid());
        allUserLiveData = userDatabase.getAllUserData();
        allTripLiveData = tripDatabase.getTripData();

        tripLiveData = Transformations.switchMap(userLiveData, user -> {
            if (user != null && user.getTripID() != null) {
                return tripDatabase.getTripDataByID(user.getTripID());
            }
            return new MutableLiveData<>(null);
        });


        tripTravelLogsLiveData = Transformations.switchMap(tripLiveData, trip ->
            Transformations.map(travelLogsLiveData, travelLogs -> {
                List<TravelLog> filteredLogs = new ArrayList<>();
                if (trip != null && trip.getTravelLogs() != null) {
                    for (TravelLog log : travelLogs) {
                        if (trip.getTravelLogs().contains(log.getId())) {
                            filteredLogs.add(log);
                        }
                    }
                }
                return filteredLogs;
            }));

        contributorsLiveData = Transformations.map(tripLiveData, trip -> {
            if (trip != null && trip.getUsers() != null) {
                return trip.getUsers();
            }
            return new ArrayList<>();
        });

        notesLiveData = Transformations.map(tripLiveData, trip -> {
            if (trip != null && trip.getNotes() != null) {
                return trip.getNotes();
            }
            return new ArrayList<>();
        });


        // Calculated allotted days whenever live data changes
        plannedDays = Transformations.map(tripTravelLogsLiveData, travelLogs -> {
            int totalPlannedDays = 0;

            for (TravelLog log : travelLogs) {
                totalPlannedDays += Integer.parseInt(log.getDuration());
            }

            return totalPlannedDays;
        });

        allottedDays = Transformations.map(userLiveData, user -> {
            int days = 0;
            if (user.getAllottedVacation() != null) {
                for (VacationTime v: user.getAllottedVacation()) {
                    days += v.getDuration();
                }
            }
            return days;
        });
    }

    public LiveData<List<TravelLog>> getTravelLogs() {
        return tripTravelLogsLiveData;
    }

    public LiveData<List<String>> getContributors() {
        return contributorsLiveData;
    }
    public LiveData<List<String>> getNotes() {
        return notesLiveData;
    }

    public void changeActiveTrip(int tripNumber) {
        tripLiveData = Transformations.switchMap(userLiveData, user -> {
            if (user != null && user.getTripID() != null) {
                return tripDatabase.getTripDataByID(
                        user.getTrips().get(tripNumber % user.getTrips().size()));
            }
            return new MutableLiveData<>(null);
        });

        tripTravelLogsLiveData = Transformations.switchMap(tripLiveData, trip ->
                Transformations.map(travelLogsLiveData, travelLogs -> {
                    List<TravelLog> filteredLogs = new ArrayList<>();
                    if (trip != null && trip.getTravelLogs() != null) {
                        for (TravelLog log : travelLogs) {
                            if (trip.getTravelLogs().contains(log.getId())) {
                                filteredLogs.add(log);
                            }
                        }
                    }
                    return filteredLogs;
                }));

        contributorsLiveData = Transformations.map(tripLiveData, trip -> {
            if (trip != null && trip.getUsers() != null) {
                return trip.getUsers();
            }
            return new ArrayList<>();
        });

        notesLiveData = Transformations.map(tripLiveData, trip -> {
            if (trip != null && trip.getNotes() != null) {
                return trip.getNotes();
            }
            return new ArrayList<>();
        });

        plannedDays = Transformations.map(tripTravelLogsLiveData, travelLogs -> {
            int totalPlannedDays = 0;
            for (TravelLog log : travelLogs) {
                totalPlannedDays += Integer.parseInt(log.getDuration());
            }
            return totalPlannedDays;
        });
    }

    public void addTrip() {
        User currentUser = userLiveData.getValue();
        String tripID = tripDatabase.addTrip(currentUser.getEmail());
        currentUser.addTrip(tripID);
        userDatabase.updateUser(currentUser);
    }

    public void addTravelLog(int tripNumber, String location,
                             String startDate, String endDate, String duration) {
        TravelLog travelLog = new TravelLog(null, location, startDate, endDate, duration);
        String logId = destinationDatabase.addTravelLog(travelLog);
        if (userLiveData.getValue() != null
                && userLiveData.getValue().getTrips().get(tripNumber
                % userLiveData.getValue().getTrips().size()) != null) {
            String tripId = userLiveData.getValue().getTrips()
                    .get(tripNumber % userLiveData.getValue().getTrips().size());
            Observer<Trip> oneTimeObserver = new Observer<Trip>() {
                @Override
                public void onChanged(Trip trip) {
                    if (trip != null) {
                        trip.addTravelLog(logId);
                        tripDatabase.updateTrip(trip);
                    }
                    // Remove itself after the first observation
                    tripDatabase.getTripDataByID(tripId).removeObserver(this);
                }
            };
            tripDatabase.getTripDataByID(tripId).observeForever(oneTimeObserver);
        }
    }

    public void addVacationTime(String startDate, String endDate, int duration) {
        VacationTime vacationTime = new VacationTime(null, startDate, endDate, duration);
        User user = userLiveData.getValue();
        user.addVacationTime(vacationTime);

        // Update the live data list if needed (you might need to fetch from Firebase)
        userDatabase.updateUser(user);
    }

    public boolean addUserToTrip(String email, int tripNumber) {
        Log.d("DestinationViewModel", "addUserToTrip: Add user with email " + email);
        User foundUser = null;

        for (User u: allUserLiveData.getValue()) {
            if (u.getEmail().equals(email)) {
                foundUser = u;
                break;
            }
        }

        if (foundUser != null && foundUser.getTripID() != null) {
            Log.d("DestinationViewModel", "addUserToTrip: found user with trip id "
                    + foundUser.getTripID());
            String foundUserTripID = foundUser.getTripID();
            List<Trip> allTrips = allTripLiveData.getValue();
            Trip foundTrip = null;
            for (Trip t: allTrips) {
                if (foundUserTripID.equals(t.getId())) {
                    foundTrip = t;
                    break;
                }
            }
            if (foundTrip == null) {
                Log.d("DestinationViewModel", "addUserToTrip: trip not found");
            }


            for (Trip t: allTrips) {
                if (userLiveData.getValue().getTrips().get(tripNumber
                        % userLiveData.getValue().getTrips().size()).equals(t.getId())) {
                    foundTrip.merge(t);
                    break;
                }
            }

            User currentUser = userLiveData.getValue();
            currentUser.setTripID(foundTrip.getId());
            foundTrip.addUser(currentUser.getEmail());
            tripDatabase.updateTrip(foundTrip);
            userDatabase.updateUser(currentUser);

            return true;
        } else {
            Log.d("DestinationViewModel", "addUserToTrip: could not find user");
            return false;
        }

    }

    public void addNote(String note, int tripNumber) {
        List<Trip> allTrips = allTripLiveData.getValue();
        Trip foundTrip = null;
        for (Trip t: allTrips) {
            if (userLiveData.getValue().getTrips().get(
                    tripNumber % userLiveData.getValue().getTrips().size()).equals(t.getId())) {
                foundTrip = t;
                break;
            }
        }
        foundTrip.addNote(note);
        tripDatabase.updateTrip(foundTrip);
    }
    // Add methods to retrieve travel logs from the database

    public LiveData<Integer> getAllottedDays() {

        return allottedDays;
    }

    public LiveData<Integer> getPlannedDays() {
        return plannedDays;
    }



}