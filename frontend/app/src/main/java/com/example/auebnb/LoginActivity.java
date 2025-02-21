package com.example.auebnb;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextUsername;
    private EditText editTextPassword;

    public Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            String loginResult = msg.getData().getString("loginResult");
            String loginUsername = msg.getData().getString("username");
            String loginPassword = msg.getData().getString("password");

            if (loginResult.contains("successfully")) {
                Toast.makeText(getApplicationContext(), "Logged in successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                intent.putExtra("accountUsername", loginUsername);
                intent.putExtra("accountPassword", loginPassword);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_LONG).show();
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#7F1734"));

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);

        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(editTextUsername, editTextPassword);
            }
        });

        findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login(EditText editTextUsername, EditText editTextPassword) {

        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        LoginThread loginThread = new LoginThread(handler, username, password);
        loginThread.start();

    }

}
