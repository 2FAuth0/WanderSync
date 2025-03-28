package com.example.wandersync.view;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.wandersync.R;
import com.example.wandersync.viewmodel.DestinationViewModel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class LogisticsFragment extends Fragment {

    private DestinationViewModel destinationViewModel;

    private FirebaseUser user;
    private PieChart pieChart;
    private FloatingActionButton invite;
    private FloatingActionButton takeNotes;
    private EditText notesText;
    private EditText userToInvite;
    private Button addNote;
    private Button sendInvite;
    private Button switchTripLeft;
    private Button switchTripRight;
    private Button addTrip;
    private LinearLayout notesForm;
    private LinearLayout inviteForm;
    private RecyclerView recyclerContributors;
    private NoteAdapter contributorAdapter;
    private RecyclerView recyclerNotes;
    private NoteAdapter notesAdapter;
    private int tripNumber = 0;


    public LogisticsFragment() {
        // Required empty public constructor
    }

    public static LogisticsFragment newInstance(String param1, String param2) {
        LogisticsFragment fragment = new LogisticsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_logistics, container, false);
        user = FirebaseAuth.getInstance().getCurrentUser();

        notesText = view.findViewById(R.id.enter_notes);
        userToInvite = view.findViewById(R.id.input_user);
        addNote = view.findViewById(R.id.btn_add_note);
        sendInvite = view.findViewById(R.id.btn_invite);
        invite = view.findViewById(R.id.invite_users);
        notesForm = view.findViewById(R.id.add_notes_form);
        inviteForm = view.findViewById(R.id.invite_users_form);

        switchTripLeft = view.findViewById(R.id.switchTripLeft);
        switchTripRight = view.findViewById(R.id.switchTripRight);
        addTrip = view.findViewById(R.id.addTrip);

        switchTripRight.setOnClickListener(v -> {
            tripNumber++;
            destinationViewModel.changeActiveTrip(tripNumber);
            destinationViewModel.getContributors().observe(
                    getViewLifecycleOwner(), contributors -> {
                        contributorAdapter.setContributors(contributors);
                    });
            destinationViewModel.getNotes().observe(getViewLifecycleOwner(), notes -> {
                notesAdapter.setContributors(notes);
            });
        });
        switchTripLeft.setOnClickListener(v -> {
            tripNumber--;
            destinationViewModel.changeActiveTrip(tripNumber);
            destinationViewModel.getContributors().observe(
                    getViewLifecycleOwner(), contributors -> {
                        contributorAdapter.setContributors(contributors);
                    });
            destinationViewModel.getNotes().observe(getViewLifecycleOwner(), notes -> {
                notesAdapter.setContributors(notes);
            });
        });
        addTrip.setOnClickListener(v -> {
            destinationViewModel.addTrip();
        });

        recyclerContributors = view.findViewById(R.id.trip_contributors);
        recyclerContributors.setLayoutManager(new LinearLayoutManager(requireContext()));
        contributorAdapter = new NoteAdapter();
        recyclerContributors.setAdapter(contributorAdapter);

        recyclerNotes = view.findViewById(R.id.trip_notes);
        recyclerNotes.setLayoutManager(new LinearLayoutManager(requireContext()));
        notesAdapter = new NoteAdapter();
        recyclerNotes.setAdapter(notesAdapter);

        destinationViewModel = new ViewModelProvider(this).get(DestinationViewModel.class);

        destinationViewModel.getContributors().observe(getViewLifecycleOwner(), contributors -> {
            contributorAdapter.setContributors(contributors);
        });

        destinationViewModel.getNotes().observe(getViewLifecycleOwner(), notes -> {
            notesAdapter.setContributors(notes);
        });

        invite.setOnClickListener(v -> {
            if (inviteForm.getVisibility() == View.GONE) {
                inviteForm.setVisibility(View.VISIBLE);
            } else {
                inviteForm.setVisibility(View.GONE);
            }
            if (notesForm.getVisibility() == View.VISIBLE) {
                notesForm.setVisibility(View.GONE);
            }
        });
        sendInvite.setOnClickListener(v -> {
            destinationViewModel.addUserToTrip(String.valueOf(userToInvite.getText()), tripNumber);
            inviteForm.setVisibility(View.GONE);
            userToInvite.setText("");
        });



        takeNotes = view.findViewById(R.id.add_notes);
        takeNotes.setOnClickListener(v -> {
            if (notesForm.getVisibility() == View.GONE) {
                notesForm.setVisibility(View.VISIBLE);
            } else {
                notesForm.setVisibility(View.GONE);
            }
            if (inviteForm.getVisibility() == View.VISIBLE) {
                inviteForm.setVisibility(View.GONE);
            }
        });
        addNote.setOnClickListener(v -> {
            destinationViewModel.addNote(String.valueOf(notesText.getText()), tripNumber);
            notesForm.setVisibility(View.GONE);
            notesText.setText("");
        });

        pieChart = view.findViewById(R.id.piechart);
        initPieChart();
        Button showChart = view.findViewById(R.id.btn_graph);

        showChart.setOnClickListener(v -> {
            if (pieChart.getVisibility() == View.GONE) {
                pieChart.setVisibility(View.VISIBLE);
            } else {
                pieChart.setVisibility(View.GONE);
            }
        });

        destinationViewModel.getAllottedDays().observe(getViewLifecycleOwner(), allotted -> {
            int planned = destinationViewModel.getPlannedDays().getValue()
                    != null ? destinationViewModel.getPlannedDays().getValue() : 0;
            updatePieChart(allotted, planned);
        });

        destinationViewModel.getPlannedDays().observe(getViewLifecycleOwner(), planned -> {
            int allotted = destinationViewModel.getAllottedDays().getValue()
                    != null ? destinationViewModel.getAllottedDays().getValue() : 100;
            updatePieChart(allotted, planned);
        });

        return view;
    }


    private void initPieChart() {
        //using percentage as values instead of amount
        pieChart.setUsePercentValues(true);

        //remove the description label on the lower left corner, default true if not set
        pieChart.getDescription().setEnabled(false);

        //enabling the user to rotate the chart, default true
        pieChart.setRotationEnabled(true);
        //adding friction when rotating the pie chart
        pieChart.setDragDecelerationFrictionCoef(0.9f);
        //setting the first entry start from right hand side, default starting from top
        pieChart.setRotationAngle(0);

        //highlight the entry when it is tapped, default true if not set
        pieChart.setHighlightPerTapEnabled(true);
        //adding animation so the entries pop up from 0 degree
        // pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        //setting the color of the hole in the middle, default white
        //pieChart.setHoleColor(Color.parseColor("#000000"));

    }

    private void updatePieChart(int allotted, int planned) {
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        String label = "Allotted vs Planned";

        //float plannedPercent = (float) (planned*1.0/(allotted + planned));

        pieEntries.add(new PieEntry(planned, "Planned Days"));
        pieEntries.add(new PieEntry(allotted - planned, "Remaining"));

        // Set up data and colors
        PieDataSet pieDataSet = new PieDataSet(pieEntries, label);
        pieDataSet.setColors(Color.parseColor("#50C878"), Color.parseColor("#D3D3D3"));
        pieDataSet.setValueTextSize(15);
        PieData pieData = new PieData(pieDataSet);
        pieData.setDrawValues(true);

        pieChart.setData(pieData);
        pieChart.setEntryLabelColor(Color.BLACK);
        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        pieChart.invalidate(); // Refresh chart
    }

}