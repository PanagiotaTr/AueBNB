package com.example.auebnb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import app.src.Room;

public class CommentsListViewActivity extends AppCompatActivity {

    private ImageView home;
    private ImageView roomImage;
    private TextView roomName;
    private TextView area_owner;
    private TextView stars_price;
    private ListView comment_list_view;
    private String accountUsername;
    private String accountPassword;
    private Room room;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coments_list_view);

        home = findViewById(R.id.ct_home);
        roomImage = findViewById(R.id.ct_roomImage);
        roomName = findViewById(R.id.ct_roomName);
        area_owner = findViewById(R.id.ct_area_owner);
        stars_price = findViewById(R.id.ct_stars_price);
        comment_list_view = findViewById(R.id.ct_list_view);

        accountUsername = (String) getIntent().getExtras().get("accountUsername");
        accountPassword = (String) getIntent().getExtras().get("accountPassword");
        room = (Room) getIntent().getExtras().get("room");

        roomImage.setImageBitmap(BitmapFactory.decodeByteArray(room.getRoomImage(),0,room.getRoomImage().length));
        roomName.setText(room.getRoomName());
        area_owner.setText(room.getArea() + "  ●  " + room.getOwner());
        stars_price.setText(String.format("%.1f",room.getStars()) + "⭐" + "    " + room.getPrice() + " €" );

        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#7F1734"));

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                intent.putExtra("accountUsername", accountUsername);
                intent.putExtra("accountPassword", accountPassword);
                startActivity(intent);
                finish();
            }
        });

        CommentListViewAdapter adapter = new CommentListViewAdapter(getLayoutInflater(),room.getComments());
        comment_list_view.setAdapter(adapter);
    }

}