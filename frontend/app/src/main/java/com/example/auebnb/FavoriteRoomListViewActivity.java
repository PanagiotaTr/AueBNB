package com.example.auebnb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;

import app.src.Room;

public class FavoriteRoomListViewActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Room> rooms;
    private RoomListViewAdapter adapter;
    ImageView homeFav;

    public Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            rooms = (ArrayList<Room>) msg.getData().getSerializable("rooms");
            if(rooms.size() == 0){
                Toast.makeText(getApplicationContext(),"Your favorite list is empty",Toast.LENGTH_LONG).show();
                return false;
            }else{
                adapter = new RoomListViewAdapter(getLayoutInflater(),rooms);
                listView.setAdapter(adapter);
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_room_list_view);

        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#7F1734"));

        String accountUsername = (String) getIntent().getExtras().get("accountUsername");
        String accountPassword = (String) getIntent().getExtras().get("accountPassword");

        listView = (ListView) findViewById(R.id.favroom_list_view);
        homeFav = findViewById(R.id.home_Fav);

        homeFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                intent.putExtra("accountUsername", accountUsername);
                intent.putExtra("accountPassword", accountPassword);
                startActivity(intent);
                finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),MainActivityForDisplayRooms.class);
                intent.putExtra("room",rooms.get(position));
                intent.putExtra("accountUsername", accountUsername);
                intent.putExtra("accountPassword", accountPassword);
                startActivity(intent);
            }
        });

        findFavorites(accountUsername, accountPassword);
    }

    private void findFavorites(String accountUsername, String accountPassword) {
        RoomDisplayThread roomDisplayThread = new RoomDisplayThread(accountUsername, accountPassword, null, 1, handler);
        roomDisplayThread.start();
    }
}