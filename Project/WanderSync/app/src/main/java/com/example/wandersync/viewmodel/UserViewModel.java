package com.example.wandersync.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.wandersync.model.User;
import com.example.wandersync.model.UserDatabase;
import com.example.wandersync.model.VacationTime;
import com.google.firebase.auth.FirebaseUser;

public class UserViewModel extends ViewModel {
    private final UserDatabase userDatabase;
    private LiveData<User> userLiveData;
    public UserViewModel() {
        userDatabase = UserDatabase.getInstance();
    }
    public void setActiveUser(String userID) {
        userLiveData = userDatabase.getUserData(userID);
    }

    public void addUser(String email, FirebaseUser firebaseUser) {
        userDatabase.addUser(email,firebaseUser);
        userLiveData = userDatabase.getUserData(firebaseUser.getUid());
    }

    public User getActiveUser() {
        return userLiveData.getValue();
    }
    public void addVacationTime(String startDate, String endDate, int duration) {
        VacationTime vacationTime = new VacationTime(null, startDate, endDate, duration);
        User user = this.getActiveUser();
        user.addVacationTime(vacationTime);

        // Update the live data list if needed (you might need to fetch from Firebase)
        userDatabase.updateUser(user);
    }
}
