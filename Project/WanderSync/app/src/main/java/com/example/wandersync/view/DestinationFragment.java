package com.example.wandersync.view;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.wandersync.R;
import com.example.wandersync.model.TravelLog;
import com.example.wandersync.viewmodel.DestinationViewModel;

public class DestinationFragment extends Fragment {

    // UI Elements
    // tagging

    private Button buttonOpenLogForm;
    private Button  buttonCancelDuration;
    private Button  buttonCalculateDuration;
    private Button  buttonCancelLog;
    private Button  buttonSubmitLog;
    private Button  buttonOpenCalculateDurationForm;
    private Button switchTripLeft;
    private Button switchTripRight;
    private Button addTrip;
    private LinearLayout logForm;
    private LinearLayout  calculateDurationForm;
    private EditText  inputLocation;
    private EditText  inputStartDate;
    private EditText  inputEndDate;
    private EditText  inputVacationStart;
    private EditText  inputVacationEnd;
    private EditText inputDuration;
    private RecyclerView recyclerTravelLogs;
    private DestinationViewModel destinationViewModel;
    private int tripNumber = 0;

    public DestinationFragment() {
        // Required empty public constructor
    }

    public static DestinationFragment newInstance() {
        DestinationFragment fragment = new DestinationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        destinationViewModel = new ViewModelProvider(this).get(DestinationViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_destination, container, false);



        buttonOpenLogForm = view.findViewById(R.id.button_open_log_form);
        buttonCancelLog = view.findViewById(R.id.button_cancel_log);
        buttonSubmitLog = view.findViewById(R.id.button_submit_log);
        buttonCancelDuration = view.findViewById(R.id.button_cancel_duration_form);
        buttonCalculateDuration = view.findViewById(R.id.button_calculate_duration);
        buttonOpenCalculateDurationForm =
                view.findViewById(R.id.button_open_calculate_duration_form);


        logForm = view.findViewById(R.id.log_form);
        calculateDurationForm = view.findViewById(R.id.calculate_duration_form);

        inputLocation = view.findViewById(R.id.input_location);
        inputStartDate = view.findViewById(R.id.input_start_date);
        inputEndDate = view.findViewById(R.id.input_end_date);

        inputVacationStart = view.findViewById(R.id.vacation_start_date);
        inputVacationEnd = view.findViewById(R.id.vacation_end_date);
        inputDuration = view.findViewById(R.id.vacation_duration);

        recyclerTravelLogs = view.findViewById(R.id.recycler_travel_logs);
        recyclerTravelLogs.setLayoutManager(new LinearLayoutManager(getContext()));

        TravelLogAdapter adapter = new TravelLogAdapter(new ArrayList<>());
        recyclerTravelLogs.setAdapter(adapter);


        switchTripLeft = view.findViewById(R.id.switchTripLeft);
        switchTripRight = view.findViewById(R.id.switchTripRight);
        addTrip = view.findViewById(R.id.addTrip);

        switchTripRight.setOnClickListener(v -> {
            tripNumber++;
            destinationViewModel.changeActiveTrip(tripNumber);
            destinationViewModel.getTravelLogs().observe(getViewLifecycleOwner(),
                new Observer<List<TravelLog>>() {
                    @Override
                    public void onChanged(List<TravelLog> travelLogs) {
                        adapter.setTravelLogs(travelLogs);
                    }
                });
        });
        switchTripLeft.setOnClickListener(v -> {
            tripNumber--;
            destinationViewModel.changeActiveTrip(tripNumber);
            destinationViewModel.getTravelLogs().observe(getViewLifecycleOwner(),
                new Observer<List<TravelLog>>() {
                    @Override
                    public void onChanged(List<TravelLog> travelLogs) {
                        adapter.setTravelLogs(travelLogs);
                    }
                });

        });
        addTrip.setOnClickListener(v -> {
            destinationViewModel.addTrip();
        });


        destinationViewModel.getTravelLogs().observe(getViewLifecycleOwner(),
                new Observer<List<TravelLog>>() {
                @Override
                public void onChanged(List<TravelLog> travelLogs) {
                    adapter.setTravelLogs(travelLogs);
                }
            });


        // Setup button click listeners
        buttonOpenLogForm.setOnClickListener(v -> {
            if (logForm.getVisibility() == View.GONE) {
                logForm.setVisibility(View.VISIBLE);
            } else {
                logForm.setVisibility(View.GONE);
            }
        });

        buttonCancelLog.setOnClickListener(v -> {
            // Clear input fields and hide the form
            inputVacationStart.setText("");
            inputVacationEnd.setText("");
            inputEndDate.setText("");
            logForm.setVisibility(View.GONE);
        });

        buttonSubmitLog.setOnClickListener(v -> {
            String location = inputLocation.getText().toString();
            String startDate = inputStartDate.getText().toString();
            String endDate = inputEndDate.getText().toString();
            String duration = String.valueOf(calculateDuration(startDate, endDate));


            if (TextUtils.isEmpty(location)) {
                Toast.makeText(getContext(), "Location cannot be empty", Toast.LENGTH_SHORT).show();
            } else if (areDatesValid(startDate, endDate)) {
                destinationViewModel.addTravelLog(
                        tripNumber, location, startDate, endDate, duration);

                logForm.setVisibility(View.GONE);
                inputLocation.setText("");
                inputStartDate.setText("");
                inputEndDate.setText("");
            }
        });

        buttonOpenCalculateDurationForm.setOnClickListener(v -> {
            if (calculateDurationForm.getVisibility() == View.GONE) {
                calculateDurationForm.setVisibility(View.VISIBLE);
            } else {
                calculateDurationForm.setVisibility(View.GONE);
            }
        });

        buttonCancelDuration.setOnClickListener(v -> {
            // Clear input fields and hide the form
            inputDuration.setText("");
            inputVacationStart.setText("");
            inputVacationEnd.setText("");
            calculateDurationForm.setVisibility(View.GONE);
        });

        buttonCalculateDuration.setOnClickListener(v -> {
            String startDate = inputVacationStart.getText().toString();
            String endDate = inputVacationEnd.getText().toString();
            String durationStr = inputDuration.getText().toString();
            int duration = 0;
            if (durationStr.isEmpty() && startDate.isEmpty()
                    || durationStr.isEmpty() && endDate.isEmpty()
                    || startDate.isEmpty() && endDate.isEmpty()) {
                Toast.makeText(getContext(),
                        "Populate at least 2 fields to calculate dates or duration.",
                        Toast.LENGTH_SHORT).show();
            } else if (durationStr.isEmpty()) {
                duration = calculateDuration(startDate, endDate);
                if (duration > 0) {
                    inputDuration.setText(String.valueOf(duration));
                }
            } else {
                try {
                    duration = Integer.parseInt(durationStr);
                    if (duration <= 0) {
                        Toast.makeText(getContext(),
                                "Duration must be a positive whole number.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        if (startDate.isEmpty()) {
                            startDate = calculateDate(endDate, -1 * duration);
                            inputVacationStart.setText(startDate);
                        } else if (endDate.isEmpty()) {
                            endDate = calculateDate(startDate, duration);
                            inputVacationEnd.setText(endDate);
                        }
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(),
                            "Duration must be a positive whole number.", Toast.LENGTH_SHORT).show();
                }
            }

            if (!(durationStr.isEmpty() || startDate.isEmpty() || endDate.isEmpty())) {
                destinationViewModel.addVacationTime(startDate, endDate, duration);
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

    private String calculateDate(String dateStr, int duration) {
        // Define the date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            // Parse the input strings to Date objects
            Date date = dateFormat.parse(dateStr);

            if (date != null) {
                // Calculate the difference in milliseconds
                long milliDate = date.getTime() + duration * (1000 * 60 * 60 * 24);
                Date newDate = new Date(milliDate);
                return dateFormat.format(newDate);
            }
        } catch (ParseException e) {
            Toast.makeText(getContext(),
                    "Invalid date format. Use YYYY-MM-DD", Toast.LENGTH_SHORT).show();
        }


        return "";
    }


}
