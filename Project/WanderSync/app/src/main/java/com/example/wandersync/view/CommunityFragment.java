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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wandersync.R;
import com.example.wandersync.model.AccommodationReservation;
import com.example.wandersync.model.DiningReservation;
import com.example.wandersync.model.TravelLog;
import com.example.wandersync.model.TravelPost;
import com.example.wandersync.viewmodel.CommunityViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    private CommunityViewModel communityViewModel;
    private RecyclerView recyclerTravelPosts;
    private SimpleDateFormat sdf;

    private LinearLayout accommodationForm;
    private LinearLayout diningForm;
    private TextView textAccommodations;
    private TextView textDiningReservations;
    private EditText inputAccommodationLocation;
    private EditText inputAccommodationCheckIn;
    private EditText inputAccommodationCheckOut;
    private EditText inputAccommodationNumRooms;
    private EditText inputAccommodationRoomType;
    private EditText inputDiningLocation;
    private EditText inputDiningTiming;
    private EditText inputDiningWebsite;
    private List<AccommodationReservation> accommodationsList = new ArrayList<>();
    private List<DiningReservation> diningReservationsList = new ArrayList<>();

    public CommunityFragment() {
        // Required empty public constructor
    }
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
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);

        inputDestination = view.findViewById(R.id.input_destination);
        inputStart = view.findViewById(R.id.input_start);
        inputEnd = view.findViewById(R.id.input_end);
        inputTransportation = view.findViewById(R.id.input_transportation);
        inputNotes = view.findViewById(R.id.input_notes);
        travelPostForm = view.findViewById(R.id.travel_post_form);
        postTravelButton = view.findViewById(R.id.button_add_accommodation);
        openTravelPostForm = view.findViewById(R.id.button_open_accomodation_form);
        recyclerTravelPosts = view.findViewById(R.id.recyler_travel_posts);

        accommodationForm = view.findViewById(R.id.accommodation_form);
        diningForm = view.findViewById(R.id.dining_form);
        textAccommodations = view.findViewById(R.id.text_accommodations);
        textDiningReservations = view.findViewById(R.id.text_dining_reservations);

        inputAccommodationLocation = view.findViewById(R.id.input_accommodation_location);
        inputAccommodationCheckIn = view.findViewById(R.id.input_accommodation_check_in);
        inputAccommodationCheckOut = view.findViewById(R.id.input_accommodation_check_out);
        inputAccommodationNumRooms = view.findViewById(R.id.input_accommodation_num_rooms);
        inputAccommodationRoomType = view.findViewById(R.id.input_accommodation_room_type);

        inputDiningLocation = view.findViewById(R.id.input_dining_location);
        inputDiningTiming = view.findViewById(R.id.input_dining_timing);
        inputDiningWebsite = view.findViewById(R.id.input_dining_website);

        Button buttonAddAccommodation = view.findViewById(R.id.button_add_accommodation);
        Button buttonAddDining = view.findViewById(R.id.button_add_dining);
        Button buttonSaveAccommodation = view.findViewById(R.id.button_save_accommodation);
        Button buttonSaveDining = view.findViewById(R.id.button_save_dining);
        Button buttonPostTravel = view.findViewById(R.id.button_post_travel);

        buttonAddAccommodation.setOnClickListener(v -> showAccommodationForm());
        buttonAddDining.setOnClickListener(v -> showDiningForm());
        buttonSaveAccommodation.setOnClickListener(v -> saveAccommodation());
        buttonSaveDining.setOnClickListener(v -> saveDiningReservation());
        buttonPostTravel.setOnClickListener(v -> postTravel());

        recyclerTravelPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        TravelPostAdapter adapter = new TravelPostAdapter(new ArrayList<>());
        recyclerTravelPosts.setAdapter(adapter);

        communityViewModel.getTravelPosts().observe(getViewLifecycleOwner(), travelPosts -> {
            Log.d("CommunityFragment", "Number of posts found: "
                    + String.valueOf(travelPosts.size()));
            currentPosts = travelPosts;
            adapter.setTravelLogs(travelPosts);
        });

        openTravelPostForm.setOnClickListener(v -> {
            if (travelPostForm.getVisibility() == View.GONE) {
                travelPostForm.setVisibility(View.VISIBLE);
            } else {
                travelPostForm.setVisibility(View.GONE);
            }
        });

        return view;
    }

    private void showAccommodationForm() {
        travelPostForm.setVisibility(View.GONE);
        accommodationForm.setVisibility(View.VISIBLE);
    }

    private void showDiningForm() {
        travelPostForm.setVisibility(View.GONE);
        diningForm.setVisibility(View.VISIBLE);
    }

    private void saveAccommodation() {
        String location = inputAccommodationLocation.getText().toString();
        String checkIn = inputAccommodationCheckIn.getText().toString();
        String checkOut = inputAccommodationCheckOut.getText().toString();
        String numRooms = inputAccommodationNumRooms.getText().toString();
        String roomType = inputAccommodationRoomType.getText().toString();

        AccommodationReservation accommodation = new AccommodationReservation(
                "temp_id", location, checkIn, checkOut, numRooms, roomType
        );
        accommodationsList.add(accommodation);

        updateAccommodationsText();
        clearAccommodationForm();
        accommodationForm.setVisibility(View.GONE);
        travelPostForm.setVisibility(View.VISIBLE);
    }

    private void saveDiningReservation() {
        String location = inputDiningLocation.getText().toString();
        String timing = inputDiningTiming.getText().toString();
        String website = inputDiningWebsite.getText().toString();

        DiningReservation diningReservation = new DiningReservation(
                "temp_id", location, timing, website
        );
        diningReservationsList.add(diningReservation);

        updateDiningReservationsText();
        clearDiningForm();
        diningForm.setVisibility(View.GONE);
        travelPostForm.setVisibility(View.VISIBLE);
    }

    private void updateAccommodationsText() {
        StringBuilder sb = new StringBuilder();
        for (AccommodationReservation accommodation : accommodationsList) {
            sb.append(accommodation.getLocation()).append(", ");
        }
        textAccommodations.setText(sb.toString());
    }

    private void updateDiningReservationsText() {
        StringBuilder sb = new StringBuilder();
        for (DiningReservation dining : diningReservationsList) {
            sb.append(dining.getLocation()).append(", ");
        }
        textDiningReservations.setText(sb.toString());
    }

    private void clearAccommodationForm() {
        inputAccommodationLocation.setText("");
        inputAccommodationCheckIn.setText("");
        inputAccommodationCheckOut.setText("");
        inputAccommodationNumRooms.setText("");
        inputAccommodationRoomType.setText("");
    }

    private void clearDiningForm() {
        inputDiningLocation.setText("");
        inputDiningTiming.setText("");
        inputDiningWebsite.setText("");
    }

    private void postTravel() {
        String destination = inputDestination.getText().toString();
        String start = inputStart.getText().toString();
        String end = inputEnd.getText().toString();
        String notes = inputNotes.getText().toString();
        String transportation = inputTransportation.getText().toString();

        if (areDatesValid(start, end)) {
            int duration = calculateDuration(start, end);
            String id = "1@1.com";
            List<TravelLog> travelLogs = new ArrayList<>();
            List<String> transportations = new ArrayList<>();
            transportations.add(transportation);

            TravelPost travelPost = new TravelPost(
                    id, travelLogs,
                    accommodationsList,
                    diningReservationsList,
                    transportations,
                    notes,
                    destination);
            communityViewModel.addTravelPost(travelPost);

            travelPostForm.setVisibility(View.GONE);
            clearTravelPostForm();
        }
    }

    private void clearTravelPostForm() {
        inputDestination.setText("");
        inputStart.setText("");
        inputEnd.setText("");
        inputNotes.setText("");
        inputTransportation.setText("");
        textAccommodations.setText("");
        textDiningReservations.setText("");
        accommodationsList.clear();
        diningReservationsList.clear();
    }

    public boolean areDatesValid(String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        sdf.setLenient(false);

        try {
            Date start = sdf.parse(startDate);
            Date end = sdf.parse(endDate);

            if (start != null && end != null && start.before(end)) {
                return true;
            } else {
                Toast.makeText(getContext(),
                        "End date must be after start date", Toast.LENGTH_SHORT).show();
            }
        } catch (ParseException e) {
            Toast.makeText(getContext(),
                    "Invalid date format. Use YYYY-MM-DD", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    public int calculateDuration(String startDateStr, String endDateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        int duration = 0;

        try {
            Date startDate = dateFormat.parse(startDateStr);
            Date endDate = dateFormat.parse(endDateStr);

            if (startDate != null && endDate != null) {
                long differenceInMillis = endDate.getTime() - startDate.getTime();
                if (differenceInMillis < 0) {
                    Toast.makeText(getContext(),
                            "End date must be after start date", Toast.LENGTH_SHORT).show();
                } else {
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