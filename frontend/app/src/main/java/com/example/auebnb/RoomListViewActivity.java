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
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import app.src.Filter;
import app.src.Room;

public class RoomListViewActivity extends AppCompatActivity {

    private ListView listView;
    private Filter filter;
    private ArrayList<Room> rooms = new ArrayList<>();
    private RoomListViewAdapter adapter;
    private int spinnerPosition;
    private int secondSpinnerPosition;
    ImageView homeResults;


    public Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            rooms = (ArrayList<Room>) msg.getData().getSerializable("rooms");
            if(rooms.size() == 0){
                Toast.makeText(getApplicationContext(),"No rooms found based on your filters",Toast.LENGTH_LONG).show();
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
        setContentView(R.layout.activity_room_list_view);

        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#7F1734"));

        String accountUsername = (String) getIntent().getExtras().get("accountUsername");
        String accountPassword = (String) getIntent().getExtras().get("accountPassword");

//        ArrayList<Room> rooms = new ArrayList<>();
//        rooms.add(new Room("Hilton",2,"Athina",100,4.3f,433,"","01/01/2024 - 01/05/2024","George"));
//        rooms.add(new Room("Amalia",3,"Kifisia",150,3.3f,1325,"","01/01/2024 - 01/05/2024","Mhtsos"));
        filter = (Filter) getIntent().getExtras().getSerializable("filter");
        spinnerPosition = (Integer) getIntent().getExtras().getInt("spinner_position");
        secondSpinnerPosition =  (Integer) getIntent().getExtras().getInt("second_spinner_position");

        listView = (ListView) findViewById(R.id.room_list_view);
        homeResults = findViewById(R.id.home_Results);

        homeResults.setOnClickListener(new View.OnClickListener() {
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

        search(filter, spinnerPosition, secondSpinnerPosition);
    }

    public void search(Filter filter, int spinnerPosition, int secondSpinnerPosition){
        SearchThread thread = new SearchThread(handler,filter, spinnerPosition, secondSpinnerPosition);
        thread.start();
    }

}