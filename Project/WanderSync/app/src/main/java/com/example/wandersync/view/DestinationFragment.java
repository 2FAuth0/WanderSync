package com.example.wandersync.view;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wandersync.R;

public class DestinationFragment extends Fragment {

    // UI Elements
    private TextView textViewTitle;
    private Button buttonOpenLogForm, buttonSaveLog, buttonCalculateDuration, buttonCancelLog, buttonSubmitLog;
    private LinearLayout logForm, vacationTimeForm;
    private EditText inputLocation, inputStartDate, inputEndDate, inputDuration, inputVacationStart, inputVacationEnd;
    private RecyclerView recyclerTravelLogs;

    public DestinationFragment() {
        // Required empty public constructor
    }

    public static DestinationFragment newInstance(String param1, String param2) {
        DestinationFragment fragment = new DestinationFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString("param1");
            String mParam2 = getArguments().getString("param2");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_destination, container, false);

        // Initialize UI elements
        textViewTitle = view.findViewById(R.id.textView);
        buttonOpenLogForm = view.findViewById(R.id.button_open_log_form);
        buttonCalculateDuration = view.findViewById(R.id.button_calculate_duration);
        buttonCancelLog = view.findViewById(R.id.button_cancel_log);
        buttonSubmitLog = view.findViewById(R.id.button_submit_log);

        logForm = view.findViewById(R.id.log_form);
        vacationTimeForm = view.findViewById(R.id.vacation_time_form);

        inputLocation = view.findViewById(R.id.input_location);
        inputStartDate = view.findViewById(R.id.input_start_date);
        inputEndDate = view.findViewById(R.id.input_end_date);

        recyclerTravelLogs = view.findViewById(R.id.recycler_travel_logs);

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
            inputLocation.setText("");
            inputStartDate.setText("");
            inputEndDate.setText("");
            logForm.setVisibility(View.GONE);
        });

        buttonSubmitLog.setOnClickListener(v -> {
            // Code to save log (implement as needed)
        });

        buttonCalculateDuration.setOnClickListener(v -> {
            // Code to calculate duration (implement as needed)
        });

        return view;
    }
}
