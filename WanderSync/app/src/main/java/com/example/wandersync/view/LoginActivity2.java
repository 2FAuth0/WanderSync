package com.example.wandersync.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.wandersync.R;

public class LoginActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        EditText userNameInput = findViewById(R.id.userNameInput);
//        EditText passwordInput = findViewById(R.id.passwordInput);

        Button loginButton = findViewById(R.id.buttonLogin);
        Button createAccountButton = findViewById(R.id.buttonCreateAccount);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String userName = userNameInput.getText().toString();
//                String password = passwordInput.getText().toString();

                if (true) { // TODO: for Justin, Replace true with authentication logic
                    Intent intent = new Intent(LoginActivity2.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    TextView errorMessage = findViewById(R.id.errorMessage);
                    errorMessage.setText("Username or Password is Incorrect");
                }

            }
        });

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity2.this, CreateActivity2.class);
                startActivity(intent);
            }
        });
    }
}