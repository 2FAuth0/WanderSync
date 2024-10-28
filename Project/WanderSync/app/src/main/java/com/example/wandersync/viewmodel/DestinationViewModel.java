package com.example.wandersync.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.wandersync.model.DestinationDatabase;
import com.example.wandersync.model.TravelLog;
import com.example.wandersync.model.User;
import com.example.wandersync.model.UserDatabase;
import com.example.wandersync.model.VacationTime;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class DestinationViewModel extends ViewModel {
    private LiveData<List<TravelLog>> travelLogsLiveData;
    private DestinationDatabase destinationDatabase;
    private UserDatabase userDatabase;
    private LiveData<Integer> allottedDays = new MutableLiveData<>(21); // Example default value
    private LiveData<Integer> plannedDays = new MutableLiveData<>();
    private MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private MutableLiveData<List<User>> allUserLiveData = new MutableLiveData<>();

    public DestinationViewModel() {
        destinationDatabase = DestinationDatabase.getInstance();
        userDatabase = UserDatabase.getInstance();
        travelLogsLiveData = destinationDatabase.getTravelLogsLiveData();
        userLiveData = userDatabase.getUserData(FirebaseAuth.getInstance().getCurrentUser().getUid());
        allUserLiveData = userDatabase.getAllUserData();

        // Calculated allotted days whenever live data changes
        plannedDays = Transformations.map(travelLogsLiveData, travelLogs -> {
            int totalPlannedDays = 0;
            if (userLiveData.getValue().getTravelLogIDs() != null) {
                for (TravelLog log : travelLogs) {
                    if (userLiveData.getValue().getTravelLogIDs().contains(log.getId())) {
                        totalPlannedDays += Integer.parseInt(log.getDuration());
                    }
                }
            }
            return totalPlannedDays;
        });

        allottedDays = Transformations.map(userLiveData, user ->{
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
        return travelLogsLiveData;
    }

    public void addTravelLog(String location, String startDate, String endDate, String duration) {
        TravelLog travelLog = new TravelLog(null, location, startDate, endDate, duration);
        String id = destinationDatabase.addTravelLog(travelLog);
        User user = userLiveData.getValue();
        user.addTravelLog(id);
        // Update the live data list if needed (you might need to fetch from Firebase)
    }

    public void addVacationTime(String startDate, String endDate, int duration) {
        VacationTime vacationTime = new VacationTime(null, startDate, endDate, duration);
        User user = userLiveData.getValue();
        user.addVacationTime(vacationTime);

        // Update the live data list if needed (you might need to fetch from Firebase)
        userDatabase.updateUser(user);
    }

    public boolean addUserToTravelLog(String email) {
        User foundUser = null;
        for (User u:allUserLiveData.getValue()) {
            if(u.getEmail()== email) {
                foundUser = u;
                break;
            }
        }

        if (foundUser != null) {
            for (String id:userLiveData.getValue().getTravelLogIDs()) {
                foundUser.addTravelLog(id);
            }
            userDatabase.updateUser(foundUser);
            return true;
        } else {
            return false;
        }


    }


    // Add methods to retrieve travel logs from the database

    public LiveData<Integer> getAllottedDays() {

        return allottedDays;
    }

    public LiveData<Integer> getPlannedDays() {
        return plannedDays;
    }


}