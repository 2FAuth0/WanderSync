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
import android.widget.Toast;

import com.example.wandersync.R;
import com.example.wandersync.viewmodel.DiningViewModel;
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

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
    private SimpleDateFormat sdf;
    private DiningViewModel diningViewModel;

    private SingleDateAndTimePicker inputTime;
    private EditText inputLocation;
    private EditText inputWebsite;
    private RecyclerView recyclerDining;

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
        diningViewModel = new ViewModelProvider(this).get(DiningViewModel.class);
        sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm", Locale.getDefault());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dining, container, false);

        FloatingActionButton buttonOpenReservationForm =
                view.findViewById(R.id.button_add_reservation);
        LinearLayout reservationForm = view.findViewById(R.id.reservation_form);
        Button buttonAddReservation = view.findViewById(R.id.button_submit_reservation);


        recyclerDining = view.findViewById(R.id.reservation_list);
        recyclerDining.setLayoutManager(new LinearLayoutManager(getContext()));

        DiningReservationAdapter adapter =
                new DiningReservationAdapter(new ArrayList<>());
        recyclerDining.setAdapter(adapter);

        diningViewModel.getDiningReservations().observe(getViewLifecycleOwner(),
            diningReservations -> {
                Log.d("DiningFragment", "Number of reservations found:" + String.valueOf(diningReservations.size()));
                diningReservations.sort((reservation1, reservation2) -> {
                    try {
                        if (sdf.parse(reservation1.getTiming()).getTime()
                                > sdf.parse(reservation2.getTiming()).getTime()) {
                            return 1;
                        } else {
                            return -1;
                        }
                    } catch (ParseException e) {
                        return 0;
                    }
                });
                adapter.setDiningList(diningReservations);
            });



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
            String timing = sdf.format(date);
            String location = inputLocation.getText().toString();
            String website = inputWebsite.getText().toString();


            if (TextUtils.isEmpty(location)) {
                Toast.makeText(getContext(), "Location cannot be empty", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(website)) {
                Toast.makeText(getContext(), "Website cannot be empty",
                        Toast.LENGTH_SHORT).show();
            } else {
                diningViewModel.addDiningReservation(location,website,timing);
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