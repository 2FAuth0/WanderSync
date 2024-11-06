package com.example.wandersync.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wandersync.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    private Spinner spinnerRooms;
    private Spinner spinnerRoomType;
    private EditText inputLocation;
    private EditText inputCheckIn;
    private EditText inputCheckOut;


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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_accomodation, container, false);

        FloatingActionButton buttonOpenAccomodationForm = view.findViewById(R.id.button_open_accomodation_form);
        LinearLayout accommodationForm = view.findViewById(R.id.accommodation_form);
        Button buttonAddAccommodation = view.findViewById(R.id.button_add_accommodation);
        inputLocation = view.findViewById(R.id.input_location);
        inputCheckIn = view.findViewById(R.id.input_check_in);
        inputCheckOut = view.findViewById(R.id.input_check_out);
        spinnerRooms = view.findViewById(R.id.spinner_rooms);
        spinnerRoomType = view.findViewById(R.id.spinner_room_type);

        buttonOpenAccomodationForm.setOnClickListener(v -> {
            // Code to open the form for adding new accommodation
            accommodationForm.setVisibility(View.VISIBLE);
        });
        buttonAddAccommodation.setOnClickListener(v -> {
            String startDate = inputCheckIn.getText().toString();
            String endDate = inputCheckOut.getText().toString();
            String location = inputLocation.getText().toString();
            String numOfRooms = spinnerRooms.getSelectedItem().toString();
            String roomType = spinnerRoomType.getSelectedItem().toString();

            // TODO Justin: upload these variables to the accomodation database (which is connected to the user)
            if (TextUtils.isEmpty(location)) {
                Toast.makeText(getContext(), "Location cannot be empty", Toast.LENGTH_SHORT).show();
            } else if (areDatesValid(startDate, endDate)) {
                accommodationForm.setVisibility(View.GONE);
                inputLocation.setText("");
                inputCheckIn.setText("");
                inputCheckOut.setText("");
                spinnerRooms.setSelection(0);
                spinnerRoomType.setSelection(0);
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
}