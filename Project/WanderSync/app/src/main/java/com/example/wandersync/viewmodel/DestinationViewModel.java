package com.example.wandersync.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.wandersync.model.DestinationDatabase;
import com.example.wandersync.model.TravelLog;
import com.example.wandersync.model.UserDatabase;
import com.example.wandersync.model.VacationTime;

import java.util.List;

public class DestinationViewModel extends ViewModel {
    private LiveData<List<TravelLog>> travelLogsLiveData;
    private DestinationDatabase destinationDatabase;
    private MutableLiveData<Integer> allottedDays = new MutableLiveData<>(21); // Example default value
    private LiveData<Integer> plannedDays = new MutableLiveData<>();


    public DestinationViewModel() {
        destinationDatabase = DestinationDatabase.getInstance();
        travelLogsLiveData = destinationDatabase.getTravelLogsLiveData();

        // Calculated allotted days whenever live data changes
        plannedDays = Transformations.map(travelLogsLiveData, travelLogs -> {
            int totalPlannedDays = 0;
            for (TravelLog log : travelLogs) {
                totalPlannedDays += Integer.parseInt(log.getDuration());
            }
            return totalPlannedDays;
        });

    }

    public LiveData<List<TravelLog>> getTravelLogs() {
        return travelLogsLiveData;
    }

    public void addTravelLog(String location, String startDate, String endDate, String duration) {
        TravelLog travelLog = new TravelLog(null, location, startDate, endDate, duration);
        destinationDatabase.addTravelLog(travelLog);
        // Update the live data list if needed (you might need to fetch from Firebase)
    }



    // Add methods to retrieve travel logs from the database

    public LiveData<Integer> getAllottedDays() {
        return allottedDays;
    }

    public LiveData<Integer> getPlannedDays() {
        return plannedDays;
    }

    public void setAllottedDays(int days) {
        allottedDays.setValue(days);
    }
}