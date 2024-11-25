package com.example.wandersync.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.wandersync.R;
import com.example.wandersync.model.AccommodationReservation;
import com.example.wandersync.model.DiningReservation;
import com.example.wandersync.model.TravelLog;
import com.example.wandersync.model.TravelPost;
import com.example.wandersync.viewmodel.AccommodationViewModel;
import com.example.wandersync.viewmodel.CommunityViewModel;
import com.example.wandersync.view.TravelPostAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommunityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommunityFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private int sortOrder = -1;
    private List<TravelPost> currentPosts = new ArrayList<>();
    private EditText inputDestination;
    private EditText inputStart;
    private EditText inputEnd;
    private EditText inputAccommodations;
    private EditText inputDiningReservations;
    private EditText inputNotes;
    private EditText inputTransportation;
    private FloatingActionButton openTravelPostForm;
    private Button postTravelButton;
    private LinearLayout travelPostForm;
    private CommunityViewModel communityViewModel; // TODO: change once Justin commits
    private RecyclerView recyclerTravelPosts;
    private SimpleDateFormat sdf;

    public CommunityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CommunityFragment.
     */
    public static CommunityFragment newInstance(String param1, String param2) {
        CommunityFragment fragment = new CommunityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
        communityViewModel = new ViewModelProvider(this).get(CommunityViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_community, container, false);
        // Connect EditText fields
        inputDestination = view.findViewById(R.id.input_destination);
        inputStart = view.findViewById(R.id.input_start);
        inputEnd = view.findViewById(R.id.input_end);
        inputAccommodations = view.findViewById(R.id.input_accommodations);
        inputDiningReservations = view.findViewById(R.id.input_dining_reservations);
        inputTransportation = view.findViewById(R.id.input_transportation);
        inputNotes = view.findViewById(R.id.input_notes);
        travelPostForm = view.findViewById(R.id.travel_post_form);
        postTravelButton = view.findViewById(R.id.button_add_accommodation);

        // Connect FloatingActionButton
        openTravelPostForm = view.findViewById(R.id.button_open_accomodation_form);

        recyclerTravelPosts = view.findViewById(R.id.recyler_travel_posts);
        recyclerTravelPosts.setLayoutManager(new LinearLayoutManager(getContext()));

        TravelPostAdapter adapter =
                new TravelPostAdapter(new ArrayList<>());
        recyclerTravelPosts.setAdapter(adapter);

        communityViewModel.getTravelPosts().observe(getViewLifecycleOwner(),
                travelPosts -> {
                    Log.d("CommunityFragment", "Number of posts found: "
                            + String.valueOf(travelPosts.size()));
                    currentPosts = travelPosts;
                    adapter.setTravelLogs(travelPosts);
                });

        ImageButton sortButton = view.findViewById(R.id.button_sort_accommodations);

//        sortButton.setOnClickListener(v -> {
//            sortOrder *= -1;
//            adapter.notifyDataSetChanged();
//            currentReservations.sort((reservation1, reservation2) -> {
//                try {
//                    if (sdf.parse(reservation1.getCheckIn()).getTime()
//                            > sdf.parse(reservation2.getCheckIn()).getTime()) {
//                        return sortOrder;
//                    } else {
//                        return -1 * sortOrder;
//                    }
//                } catch (ParseException e) {
//                    return 0;
//                }
//            });
//            adapter.setAccommodationList(currentReservations);
//        });


        // Set up FloatingActionButton click listener
        openTravelPostForm.setOnClickListener(v -> {
            if (travelPostForm.getVisibility() == View.GONE) {
                travelPostForm.setVisibility(View.VISIBLE);
            } else {
                travelPostForm.setVisibility(View.GONE);
            }
        });

        postTravelButton.setOnClickListener(v -> {
            String destination = inputDestination.getText().toString();
            String start = inputStart.getText().toString();
            String end = inputEnd.getText().toString();
            String accommodations = inputAccommodations.getText().toString();
            String diningReservations = inputDiningReservations.getText().toString();
            String transportation = inputTransportation.getText().toString();
            String notes = inputNotes.getText().toString();

            if (areDatesValid(start, end)) {
                // TODO: fix this
                int duration = calculateDuration(start, end);
                String id = "1@1.com";
                List<TravelLog> travelLogs = new ArrayList<>(); // TODO: database mismatch
                List<AccommodationReservation> accommodationsLists = new ArrayList<>(); // TODO: database mismatch
                List<DiningReservation> diningReservationsList = new ArrayList<>(); // TODO: database mismatch
                List<String> transportations = new ArrayList<>(); // TODO: database mismatch
                TravelPost travelPost = new TravelPost(id, travelLogs, accommodationsLists, diningReservationsList, transportations, notes);
                communityViewModel.addTravelPost(travelPost);
                travelPostForm.setVisibility(View.GONE);
                inputDestination.setText("");
                inputStart.setText("");
                inputEnd.setText("");
                inputAccommodations.setText("");
                inputDiningReservations.setText("");
                inputTransportation.setText("");
            }
        });

        return view;


    }
    public boolean areDatesValid(String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        sdf.setLenient(false);

        try {
            Date start = sdf.parse(startDate);
            Date end = sdf.parse(endDate);

            if (start != null && end != null && start.before(end)) {
                return true; // Dates are valid
            } else {
                Toast.makeText(getContext(),
                        "End date must be after start date", Toast.LENGTH_SHORT).show();
            }
        } catch (ParseException e) {
            Toast.makeText(getContext(),
                    "Invalid date format. Use YYYY-MM-DD", Toast.LENGTH_SHORT).show();
        }

        return false; // Dates are not valid
    }

    public int calculateDuration(String startDateStr, String endDateStr) {
        // Define the date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        int duration = 0;

        try {
            // Parse the input strings to Date objects
            Date startDate = dateFormat.parse(startDateStr);
            Date endDate = dateFormat.parse(endDateStr);

            if (startDate != null && endDate != null) {
                // Calculate the difference in milliseconds
                long differenceInMillis = endDate.getTime() - startDate.getTime();
                if (differenceInMillis < 0) {
                    Toast.makeText(getContext(),
                            "End date must be after start date", Toast.LENGTH_SHORT).show();
                } else {
                    // Convert milliseconds to days
                    duration = (int) (differenceInMillis / (1000 * 60 * 60 * 24));
                }
            }
        } catch (ParseException e) {
            Toast.makeText(getContext(),
                    "Invalid date format. Use YYYY-MM-DD", Toast.LENGTH_SHORT).show();
        }

        return duration;
    }
}