package com.example.wandersync.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wandersync.model.DestinationDatabase;
import com.example.wandersync.model.TravelLog;

import java.util.ArrayList;
import java.util.List;

public class DestinationViewModel extends ViewModel {
    private LiveData<List<TravelLog>> travelLogsLiveData;
    private DestinationDatabase database;

    public DestinationViewModel() {
        database = DestinationDatabase.getInstance();
        travelLogsLiveData = database.getTravelLogsLiveData();
    }

    public LiveData<List<TravelLog>> getTravelLogs() {
        return travelLogsLiveData;
    }

    public void addTravelLog(String location, String startDate, String endDate, String duration) {
        TravelLog travelLog = new TravelLog(null, location, startDate, endDate, duration);
        database.addTravelLog(travelLog);
        // Update the live data list if needed (you might need to fetch from Firebase)
    }

    // Add methods to retrieve travel logs from the database
}