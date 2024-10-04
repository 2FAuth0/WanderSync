package com.example.wandersync.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wandersync.R;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        EditText userNameInput = findViewById(R.id.userNameInput);
        EditText passwordInput = findViewById(R.id.passwordInput);
        Button submitButton = findViewById(R.id.buttonSubmit);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = userNameInput.getText().toString();
                String password = passwordInput.getText().toString();

                // TODO: Rohit - add users to database once justin has set up database

                Intent intent = new Intent(CreateAccountActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
