package com.example.wandersync.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.wandersync.R;
import com.example.wandersync.model.AccommodationReservation;
import com.example.wandersync.viewmodel.AccommodationViewModel;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccomodationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccomodationFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private int sortOrder = -1;
    private List<AccommodationReservation> currentReservations = new ArrayList<>();
    private Spinner spinnerRooms;
    private Spinner spinnerRoomType;
    private EditText inputLocation;
    private EditText inputCheckIn;
    private EditText inputCheckOut;
    private Button switchTripLeft;
    private Button switchTripRight;
    private Button addTrip;

    private int tripNumber = 0;
    private AccommodationViewModel accommodationViewModel;
    private RecyclerView recyclerAccommodations;
    private SimpleDateFormat sdf;


    public AccomodationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccomodationFragment.
     */
    public static AccomodationFragment newInstance(String param1, String param2) {
        AccomodationFragment fragment = new AccomodationFragment();
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
        accommodationViewModel = new ViewModelProvider(this).get(AccommodationViewModel.class);
        sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_accomodation, container, false);

        recyclerAccommodations = view.findViewById(R.id.recycler_accommodations);
        recyclerAccommodations.setLayoutManager(new LinearLayoutManager(getContext()));

        AccommodationReservationAdapter adapter =
                new AccommodationReservationAdapter(new ArrayList<>());
        recyclerAccommodations.setAdapter(adapter);

        accommodationViewModel.getAccommodationReservations().observe(getViewLifecycleOwner(),
                accommodationReservations -> {
                    Log.d("DiningFragment", "Number of reservations found:"
                            + String.valueOf(accommodationReservations.size()));
                    currentReservations = accommodationReservations;
                    adapter.setAccommodationList(accommodationReservations);
                });

        switchTripLeft = view.findViewById(R.id.switchTripLeft);
        switchTripRight = view.findViewById(R.id.switchTripRight);
        addTrip = view.findViewById(R.id.addTrip);

        switchTripRight.setOnClickListener(v -> {
            tripNumber++;
            accommodationViewModel.changeActiveTrip(tripNumber);
            accommodationViewModel.getAccommodationReservations().observe(getViewLifecycleOwner(),
                    accommodationReservations -> {
                        Log.d("AccommodationFragment", "Number of reservations found:"
                                + String.valueOf(accommodationReservations.size()));
                        currentReservations = accommodationReservations;
                        adapter.setAccommodationList(accommodationReservations);
                    });
        });
        switchTripLeft.setOnClickListener(v -> {
            tripNumber--;
            accommodationViewModel.changeActiveTrip(tripNumber);
            accommodationViewModel.getAccommodationReservations().observe(getViewLifecycleOwner(),
                    accommodationReservations -> {
                        Log.d("AccommodationFragment", "Number of reservations found:"
                                + String.valueOf(accommodationReservations.size()));
                        currentReservations = accommodationReservations;
                        adapter.setAccommodationList(accommodationReservations);
                    });

        });
        addTrip.setOnClickListener(v -> {
            accommodationViewModel.addTrip();
        });



        ImageButton sortButton = view.findViewById(R.id.button_sort_accommodations);

        sortButton.setOnClickListener(v -> {
            sortOrder *= -1;
            adapter.notifyDataSetChanged();
            currentReservations.sort((reservation1, reservation2) -> {
                try {
                    if (sdf.parse(reservation1.getCheckIn()).getTime()
                            > sdf.parse(reservation2.getCheckIn()).getTime()) {
                        return sortOrder;
                    } else {
                        return -1 * sortOrder;
                    }
                } catch (ParseException e) {
                    return 0;
                }
            });
            adapter.setAccommodationList(currentReservations);
        });

        FloatingActionButton buttonOpenAccomodationForm =
                view.findViewById(R.id.button_open_accomodation_form);
        LinearLayout accommodationForm = view.findViewById(R.id.accommodation_form);
        Button buttonAddAccommodation = view.findViewById(R.id.button_add_accommodation);
        inputLocation = view.findViewById(R.id.input_location);
        inputCheckIn = view.findViewById(R.id.input_check_in);
        inputCheckOut = view.findViewById(R.id.input_check_out);
        spinnerRooms = view.findViewById(R.id.spinner_rooms);
        spinnerRoomType = view.findViewById(R.id.spinner_room_type);

        buttonOpenAccomodationForm.setOnClickListener(v -> {
            if (accommodationForm.getVisibility() == View.GONE) {
                accommodationForm.setVisibility(View.VISIBLE);
            } else {
                accommodationForm.setVisibility(View.GONE);
            }
        });
        buttonAddAccommodation.setOnClickListener(v -> {
            String startDate = inputCheckIn.getText().toString();
            String endDate = inputCheckOut.getText().toString();
            String location = inputLocation.getText().toString();
            String numOfRooms = spinnerRooms.getSelectedItem().toString();
            String roomType = spinnerRoomType.getSelectedItem().toString();

            if (TextUtils.isEmpty(location)) {
                Toast.makeText(getContext(), "Location cannot be empty",
                        Toast.LENGTH_SHORT).show();
            } else if (areDatesValid(startDate, endDate)) {
                accommodationViewModel.addAccommodationReservation(location, startDate,
                        endDate, numOfRooms, roomType);
                accommodationForm.setVisibility(View.GONE);
                inputLocation.setText("");
                inputCheckIn.setText("");
                inputCheckOut.setText("");
                spinnerRooms.setSelection(0);
                spinnerRoomType.setSelection(0);
                Toast.makeText(getContext(), "Accommodation successfully booked",
                        Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public boolean areDatesValid(String startDate, String endDate) {

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
}