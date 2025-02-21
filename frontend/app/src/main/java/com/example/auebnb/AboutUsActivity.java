package com.example.auebnb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutUsActivity extends AppCompatActivity {

    ImageView homeAboutUs;
    TextView infoGiot;
    TextView infoGior;
    TextView infoDan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#7F1734"));

        String accountUsername = (String) getIntent().getExtras().get("accountUsername");
        String accountPassword = (String) getIntent().getExtras().get("accountPassword");

        homeAboutUs = findViewById(R.id.home_AboutUs);
        infoGiot = findViewById(R.id.info_Giot);
        infoDan = findViewById(R.id.info_Dan);
        infoGior = findViewById(R.id.info_Gior);


        homeAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                intent.putExtra("accountUsername", accountUsername);
                intent.putExtra("accountPassword", accountPassword);
                startActivity(intent);
                finish();
            }
        });

        infoGiot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AboutUsActivityGiot.class);
                startActivity(intent);
            }
        });

        infoDan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AboutUsActivityDan.class);
                startActivity(intent);
            }
        });

        infoGior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AboutUsActivityGior.class);
                startActivity(intent);
            }
        });

    }
}