package com.example.wandersync.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wandersync.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private int pieChartVisibility = View.GONE;
    private int form1Visibility = View.GONE;
    private int form2Visibility = View.GONE;

    public TestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TestFragment.
     */
    public static TestFragment newInstance(String param1, String param2) {
        TestFragment fragment = new TestFragment();
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
        return inflater.inflate(R.layout.fragment_test, container, false);
    }


    public void togglePieChartVisibility() {
        pieChartVisibility = (pieChartVisibility == View.VISIBLE) ? View.GONE : View.VISIBLE;
    }

    public int getPieChartVisibility() {
        return pieChartVisibility;
    }



    public void toggleForm1() {
        form1Visibility = (form1Visibility == View.VISIBLE) ? View.GONE : View.VISIBLE;
        if (form1Visibility == View.VISIBLE) {
            form2Visibility = View.GONE;
        }
    }

    public void toggleForm2() {
        form2Visibility = (form2Visibility == View.VISIBLE) ? View.GONE : View.VISIBLE;
        if (form2Visibility == View.VISIBLE) {
            form1Visibility = View.GONE;
        }
    }

    public int getForm1Visibility() {
        return form1Visibility;
    }

    public int getForm2Visibility() {
        return form2Visibility;
    }
}