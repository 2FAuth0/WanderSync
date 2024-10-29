package com.example.wandersync.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.wandersync.R;
import com.example.wandersync.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());
        setContentView(R.layout.fragment_logistics);

        BottomNavigationView menuBar = findViewById(R.id.bottom_nav);
        replaceFragment(new LogisticsFragment());
        menuBar.setOnItemSelectedListener(item -> {
            if (item.getItemId() == (R.id.logistics)) {
                replaceFragment(new LogisticsFragment());
            } else if (item.getItemId() == (R.id.accomodation)) {
                replaceFragment(new AccomodationFragment());
            } else if (item.getItemId() == R.id.desination) {
                replaceFragment(new DestinationFragment());
            } else if (item.getItemId() == R.id.community) {
                replaceFragment(new CommunityFragment());
            } else if (item.getItemId() == R.id.dining) {
                replaceFragment(new DiningFragment());
            }


            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}
