package com.example.wandersync.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.wandersync.R;
import com.example.wandersync.model.AccommodationReservation;
import com.example.wandersync.viewmodel.AccommodationViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
    private List<AccommodationReservation> currentPosts = new ArrayList<>();
    private EditText inputDestination;
    private EditText inputStart;
    private EditText inputEnd;
    private EditText inputAccommodations;
    private EditText inputDiningReservations;
    private EditText inputNotes;
    private FloatingActionButton openTravelPostForm;
    private LinearLayout travelPostForm;
    private AccommodationViewModel accommodationViewModel; // TODO: change once Justin commits
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
        inputNotes = view.findViewById(R.id.input_notes);
        travelPostForm = view.findViewById(R.id.travel_post_form);

        // Connect FloatingActionButton
        openTravelPostForm = view.findViewById(R.id.button_open_accomodation_form);

        recyclerTravelPosts = view.findViewById(R.id.recyler_travel_posts);
        recyclerTravelPosts.setLayoutManager(new LinearLayoutManager(getContext()));

        AccommodationReservationAdapter adapter =
                new AccommodationReservationAdapter(new ArrayList<>());
        recyclerTravelPosts.setAdapter(adapter);

//        accommodationViewModel.getAccommodationReservations().observe(getViewLifecycleOwner(),
//                accommodationReservations -> {
//                    Log.d("DiningFragment", "Number of reservations found:"
//                            + String.valueOf(accommodationReservations.size()));
//                    currentReservations = accommodationReservations;
//                    adapter.setAccommodationList(accommodationReservations);
//                }); // TODO: change once Justin commits

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
        return view;
    }
}