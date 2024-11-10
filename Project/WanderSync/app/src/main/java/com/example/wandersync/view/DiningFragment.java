package com.example.wandersync.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.wandersync.R;
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DiningFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiningFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private SingleDateAndTimePicker inputTime;
    private EditText inputLocation;
    private EditText inputWebsite;
    private RecyclerView upcomingRecycler;
    private RecyclerView pastRecycler;

    public DiningFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DiningFragment.
     */
    public static DiningFragment newInstance(String param1, String param2) {
        DiningFragment fragment = new DiningFragment();
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
        View view = inflater.inflate(R.layout.fragment_dining, container, false);

        FloatingActionButton buttonOpenReservationForm =
                view.findViewById(R.id.button_add_reservation);
        LinearLayout reservationForm = view.findViewById(R.id.reservation_form);
        Button buttonAddReservation = view.findViewById(R.id.button_submit_reservation);
        ImageButton buttonSortUpcoming = view.findViewById(R.id.button_sort_upcoming);
        ImageButton buttonSortPast = view.findViewById(R.id.button_sort_past);

        /*Start recycler logic*/

        upcomingRecycler = view.findViewById(R.id.upcoming_reservation_list);
        upcomingRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        pastRecycler = view.findViewById(R.id.past_reservation_list);
        pastRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        // pseudocode for sorting out the lists
        ArrayList<Date> examples = new ArrayList<>(); // will be the recycler values
        ArrayList<Date> upcoming = new ArrayList<>();
        ArrayList<Date> past = new ArrayList<>();
        long currentTimeMillis = System.currentTimeMillis();

        // Current date
        Date curr = new Date(currentTimeMillis);

        /*
         * The following are  just examples to populate the 'examples' list. In practice,
         * this list will just be all the entires from the form.
         */
        // Date 5 days in the past (5 * 24 * 60 * 60 * 1000 milliseconds)
        Date pastDate1 = new Date(currentTimeMillis - (5L * 24 * 60 * 60 * 1000));
        examples.add(pastDate1);

        // Date 10 days in the past
        Date pastDate2 = new Date(currentTimeMillis - (10L * 24 * 60 * 60 * 1000));
        examples.add(pastDate2);

        // Date 3 days in the future
        Date futureDate1 = new Date(currentTimeMillis + (3L * 24 * 60 * 60 * 1000));
        examples.add(futureDate1);

        // Date 7 days in the future
        Date futureDate2 = new Date(currentTimeMillis + (7L * 24 * 60 * 60 * 1000));
        examples.add(futureDate2);

        // loop through all the dates and then sort them
        for (Date d : examples) {
            if (d.before(curr)) {
                past.add(d);
            } else {
                upcoming.add(d);
            }
        }

        past.sort(null);
        upcoming.sort(null);


        buttonSortUpcoming.setOnClickListener(v -> {
            // might implement this elsewhere and then return the sorted list
            Collections.reverse(upcoming);
            Toast.makeText(getContext(), "Upcoming reservations sorted.",
                    Toast.LENGTH_SHORT).show();
        });
        buttonSortPast.setOnClickListener(v -> {
            // might implement this elsewhere and then return the sorted list
            Collections.reverse(past);
            Toast.makeText(getContext(), "Past reservations sorted.",
                    Toast.LENGTH_SHORT).show();
        });

        /*End recycler logic*/

        buttonOpenReservationForm.setOnClickListener(v -> {
            if (reservationForm.getVisibility() == View.GONE) {
                reservationForm.setVisibility(View.VISIBLE);
            } else {
                reservationForm.setVisibility(View.GONE);
            }
        });

        inputTime = view.findViewById(R.id.input_time);
        inputLocation = view.findViewById(R.id.input_location);
        inputWebsite = view.findViewById(R.id.input_website);

        buttonAddReservation.setOnClickListener(v -> {
            Date date = inputTime.getDate();
            String location = inputLocation.getText().toString();
            String website = inputWebsite.getText().toString();

            // TODO Justin: upload these variables to the dining database
            if (TextUtils.isEmpty(location)) {
                Toast.makeText(getContext(), "Location cannot be empty", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(website)) {
                Toast.makeText(getContext(), "Website cannot be empty",
                        Toast.LENGTH_SHORT).show();
            } else {
                reservationForm.setVisibility(View.GONE);
                Date reset = new Date();
                inputTime.setDefaultDate(reset);
                inputLocation.setText("");
                inputWebsite.setText("");
                Toast.makeText(getContext(), "Reservation successfully added",
                        Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}