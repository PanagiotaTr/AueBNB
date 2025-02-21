package com.example.auebnb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import app.src.Filter;
import app.src.Room;

public class MainMenuActivity extends AppCompatActivity {

    ImageView buttonDrawerToggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView facebook;
    ImageView instagram;
    ImageView twitter;
    ImageView mail;
    private ArrayList<Room> rooms = new ArrayList<>();
    private String accountUsername;
    private String accountPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#7F1734"));

        accountUsername = (String) getIntent().getExtras().get("accountUsername");
        accountPassword = (String) getIntent().getExtras().get("accountPassword");

        drawerLayout = findViewById(R.id.drawerLayout);
        buttonDrawerToggle = findViewById(R.id.buttonDrawer);
        navigationView = findViewById(R.id.navigationView);

        buttonDrawerToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if(itemId == R.id.navMenu){
                    Toast.makeText(MainMenuActivity.this,"Menu Clicked",Toast.LENGTH_SHORT).show();
                }

                if(itemId == R.id.navSearch){
                    Intent i = new Intent(MainMenuActivity.this,SearchActivity.class);
                    i.putExtra("accountUsername", accountUsername);
                    i.putExtra("accountPassword", accountPassword);
                    startActivity(i);
                }

                if(itemId == R.id.navTop5){
                    Filter filter = new Filter("","",0,0,0);
                    Intent intent = new Intent(getApplicationContext(),RoomListViewActivity.class);
                    intent.putExtra("filter",filter);
                    intent.putExtra("spinner_position", 3);
                    intent.putExtra("second_spinner_position", 1);
                    intent.putExtra("accountUsername", accountUsername);
                    intent.putExtra("accountPassword", accountPassword);
                    startActivity(intent);
                }

                if(itemId == R.id.navAboutUs){
                    Intent intent = new Intent(getApplicationContext(),AboutUsActivity.class);
                    intent.putExtra("accountUsername", accountUsername);
                    intent.putExtra("accountPassword", accountPassword);
                    startActivity(intent);
                }

                if(itemId == R.id.navFavs){
                    Intent intent = new Intent(getApplicationContext(),FavoriteRoomListViewActivity.class);
                    intent.putExtra("accountUsername", accountUsername);
                    intent.putExtra("accountPassword", accountPassword);
                    startActivity(intent);

                }

                if(itemId == R.id.navReser){
                    Intent intent = new Intent(getApplicationContext(),ReservationsActivity.class);
                    intent.putExtra("accountUsername", accountUsername);
                    intent.putExtra("accountPassword", accountPassword);
                    startActivity(intent);
                }

                if(itemId == R.id.navLogOut){
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

                drawerLayout.close();

                return false;
            }
        });

        facebook = findViewById(R.id.facebook_MM);
        instagram = findViewById(R.id.instagram_MM);
        twitter = findViewById(R.id.twitter_MM);
        mail = findViewById(R.id.mail_MM);

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainMenuActivity.this, "https://www.facebook.com/aueBNB/", Toast.LENGTH_SHORT).show();
            }
        });

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainMenuActivity.this, "https://www.instagram.com/aueBNB/", Toast.LENGTH_SHORT).show();
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainMenuActivity.this, "https://www.twitter.com/aueBNB/", Toast.LENGTH_SHORT).show();
            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainMenuActivity.this, "contact@aueBNB.com", Toast.LENGTH_SHORT).show();
            }
        });

    }
}