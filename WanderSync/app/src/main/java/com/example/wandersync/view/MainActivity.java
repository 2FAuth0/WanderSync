package com.example.wandersync.view;

import android.graphics.Paint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wandersync.R;
import com.example.wandersync.viewmodel.LoginViewModel;

public class MainActivity extends AppCompatActivity {
    // TODO: Abhi splash screen or welcome screen here
    private final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //LoginViewModel viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
//        binding.setVariable(BR.viewModel, viewModel);
//        binding.setLifecycleOwner(this);

        //find button by id
        Button signInButton = findViewById(R.id.btn_signIn);

        //set an on clock listened to the button
        signInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //create intent
                Intent intent = new Intent(MainActivity.this, LoginActivity2.class);

                startActivity(intent);
            }
        });

        //find button by id
        Button exitButton = findViewById(R.id.btn_exit);
        exitButton.setPaintFlags(exitButton.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        //set an on clock listened to the button
        exitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //create intent
                finish();
                System.exit(0);
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG, "onStart called");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "onResume called");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "onPause called");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG, "onStop called");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy called");
    }
}