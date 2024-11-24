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

public class TravelCommunityDatabase {
    private static TravelCommunityDatabase instance;
    private DatabaseReference databaseReference;
    private MutableLiveData<List<TravelPost>> travelPostsLiveData = new MutableLiveData<>();

    private TravelCommunityDatabase() {
        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("travel_community_posts");
    }

    public static synchronized TravelCommunityDatabase getInstance() {
        if (instance == null) {
            instance = new TravelCommunityDatabase();
        }
        return instance;
    }

    // Add a new travel post
    public void addTravelPost(TravelPost travelPost) {
        String postId = databaseReference.push().getKey();
        travelPost.setId(postId);
        Log.d("TravelCommunityDatabase", "addTravelPost: " + postId);
        assert postId != null;
        databaseReference.child(postId).setValue(travelPost);
    }

    // Retrieve all travel posts
    public MutableLiveData<List<TravelPost>> getTravelPostsLiveData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<TravelPost> travelPosts = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        TravelPost travelPost = snapshot.getValue(TravelPost.class);
                        travelPosts.add(travelPost);
                    }
                    travelPostsLiveData.setValue(travelPosts);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("TravelCommunityDatabase",
                            "Error reading data: " + databaseError.getMessage());
                }
            });

        return travelPostsLiveData;
    }

    // Update an existing travel post
    public void updateTravelPost(TravelPost updatedPost) {
        if (updatedPost != null) {
            databaseReference.child(updatedPost.getId()).setValue(updatedPost)
                    .addOnSuccessListener(aVoid -> Log.d("TravelCommunityDatabase",
                            "Travel post updated successfully"))
                    .addOnFailureListener(e -> Log.e("TravelCommunityDatabase",
                            "Failed to update travel post: " + e.getMessage()));
        }
    }
}
