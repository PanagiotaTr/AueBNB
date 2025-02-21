package com.example.auebnb;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 5000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#7F1734"));

        ImageView imageView = findViewById(R.id.imageView);

        ObjectAnimator flipAnimator = ObjectAnimator.ofFloat(imageView, View.ROTATION_Y, 0f, 360f);
        flipAnimator.setDuration(2500);
        flipAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        flipAnimator.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Αντί για το SearchActivity.class έχω βάλει το καινούργιο activity που έφτιαξα
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DURATION);
    }
}
