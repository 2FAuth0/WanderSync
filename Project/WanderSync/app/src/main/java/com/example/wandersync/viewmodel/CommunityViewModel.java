package com.example.wandersync.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.wandersync.model.TravelCommunityDatabase;
import com.example.wandersync.model.TravelPost;
import java.util.List;

public class CommunityViewModel extends ViewModel {
    private final TravelCommunityDatabase travelCommunityDatabase;
    private final LiveData<List<TravelPost>> travelPostsLiveData;

    public CommunityViewModel() {
        travelCommunityDatabase = TravelCommunityDatabase.getInstance();
        travelPostsLiveData = travelCommunityDatabase.getTravelPostsLiveData();
    }

    public LiveData<List<TravelPost>> getTravelPosts() {
        return travelPostsLiveData;
    }

    public void addTravelPost(TravelPost travelPost) {
        travelCommunityDatabase.addTravelPost(travelPost);
    }

    public void updateTravelPost(TravelPost updatedPost) {
        travelCommunityDatabase.updateTravelPost(updatedPost);
    }
}