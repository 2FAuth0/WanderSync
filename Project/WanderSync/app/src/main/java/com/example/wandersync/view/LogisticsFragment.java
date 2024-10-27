package com.example.wandersync.view;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.wandersync.R;
import com.example.wandersync.model.TravelLog;
import com.example.wandersync.viewmodel.DestinationViewModel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogisticsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private DestinationViewModel destinationViewModel;

    private FirebaseUser user;
    private PieChart pieChart;
    private RecyclerView recyclerTravelLogs;


    public LogisticsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LogisticsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LogisticsFragment newInstance(String param1, String param2) {
        LogisticsFragment fragment = new LogisticsFragment();
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

    // TODO: update with contributors of a trip
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_logistics_edit, container, false);
        user = FirebaseAuth.getInstance().getCurrentUser();

        recyclerTravelLogs = view.findViewById(R.id.trip_contributors);
//        recyclerTravelLogs.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        TravelLogAdapter adapter = new TravelLogAdapter(new ArrayList<>());
//        recyclerTravelLogs.setAdapter(adapter);
//
//        destinationViewModel.getTravelLogs().observe(getViewLifecycleOwner(), new Observer<List<TravelLog>>() {
//            @Override
//            public void onChanged(List<TravelLog> travelLogs) {
//                adapter.setTravelLogs(travelLogs);
//            }
//        });

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
        destinationViewModel = new ViewModelProvider(this).get(DestinationViewModel.class);
        destinationViewModel.getAllottedDays().observe(getViewLifecycleOwner(), allotted -> {
            int planned = destinationViewModel.getPlannedDays().getValue() != null ? destinationViewModel.getPlannedDays().getValue() : 0;
            updatePieChart(allotted, planned);
        });

        destinationViewModel.getPlannedDays().observe(getViewLifecycleOwner(), planned -> {
            int allotted = destinationViewModel.getAllottedDays().getValue() != null ? destinationViewModel.getAllottedDays().getValue() : 100;
            updatePieChart(allotted, planned);
        });

        return view;
    }


    private void initPieChart(){
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