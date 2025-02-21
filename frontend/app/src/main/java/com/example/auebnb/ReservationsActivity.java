package com.example.auebnb;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import app.src.Room;

public class ReservationsActivity extends AppCompatActivity {

    private ListView listView;
    private HashMap<Room, ArrayList<String>> reservationRooms;
    private ReservationListViewAdapter adapter;
    private ImageView homeReser;

    public Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            reservationRooms = (HashMap<Room, ArrayList<String>>) msg.getData().getSerializable("reservationRooms");

            if (reservationRooms == null || reservationRooms.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Your reservation list is empty", Toast.LENGTH_LONG).show();
                return false;
            } else {
                adapter = new ReservationListViewAdapter(getLayoutInflater(), reservationRooms);
                listView.setAdapter(adapter);
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#7F1734"));

        String accountUsername = getIntent().getStringExtra("accountUsername");
        String accountPassword = getIntent().getStringExtra("accountPassword");

        listView = findViewById(R.id.reserroom_list_view);
        homeReser = findViewById(R.id.home_Reser);

        homeReser.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
            intent.putExtra("accountUsername", accountUsername);
            intent.putExtra("accountPassword", accountPassword);
            startActivity(intent);
            finish();
        });


        findReservations(accountUsername, accountPassword);
    }

    private void findReservations(String accountUsername, String accountPassword) {
        RoomDisplayThread roomDisplayThread = new RoomDisplayThread(accountUsername, accountPassword, null, 5, handler);
        roomDisplayThread.start();
    }
}
