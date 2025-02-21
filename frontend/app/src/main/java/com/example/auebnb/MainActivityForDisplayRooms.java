package com.example.auebnb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import app.src.Room;

public class MainActivityForDisplayRooms extends AppCompatActivity {

    ImageView imageRoom;
    ImageView heart;
    TextView nameRoom;
    TextView areaPersonsOwner;
    TextView starsNum;
    TextView reviewsNum;
    TextView beforePrice;
    TextView afterPrice;
    RatingBar ratingBarStars;
    Button booking;
    Button rating;
    boolean isRedHeart = false;
    ImageView homeDis;
    TextView comments;

    public Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Boolean response = msg.getData().getBoolean("response");
            isRedHeart = response;
            if(response){
                heart.setImageResource(R.drawable.redheart);
            }else{
                heart.setImageResource(R.drawable.whiteheart);
            }
            return false;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_for_display_rooms);

        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#7F1734"));

        String accountUsername = (String) getIntent().getExtras().get("accountUsername");
        String accountPassword = (String) getIntent().getExtras().get("accountPassword");

        heart = findViewById(R.id.heart_Dis);
        imageRoom = findViewById(R.id.image_Dis);
        nameRoom = findViewById(R.id.roomName_Dis);
        areaPersonsOwner = findViewById(R.id.AreaNumberOfPersonsOwner_Dis);
        starsNum = findViewById(R.id.stars_Dis);
        reviewsNum = findViewById(R.id.reviews_Dis);
        beforePrice = findViewById(R.id.bprice_Dis);
        afterPrice = findViewById(R.id.aprice_Dis);
        ratingBarStars = findViewById(R.id.ratingBarStars_Dis);
        booking = findViewById(R.id.booking_Dis);
        rating = findViewById(R.id.rating_Dis);
        homeDis = findViewById(R.id.home_Dis);
        comments = findViewById(R.id.reviews_DisCom);

//        αυτά θα τα βάλουμε για να πάρουμε τις πληροφορίες από το άλλο activity - ονομασία του Extra "room"
        Intent intent = getIntent();
        Room room = (Room) intent.getSerializableExtra("room");

        RoomDisplayThread t = new RoomDisplayThread(accountPassword,accountUsername,room,2,handler);
        t.start();


        // room για τεσταρισμα
        // Room room = new Room("Salem", 2, "Argyroupoly", 560.0, 4.4f, 200, "", "", "Peggy");

        byte[] roomImage = room.getRoomImage();
        Bitmap roomImageBook = BitmapFactory.decodeByteArray(roomImage, 0, roomImage.length);
        imageRoom.setImageBitmap(roomImageBook);
        nameRoom.setText(room.getRoomName());
        areaPersonsOwner.setText(room.getArea() + "  ●  " + room.getNoOfPersons() + " Persons  ●  " + room.getOwner());
        starsNum.setText(String.format("%.1f",room.getStars()));
        reviewsNum.setText(room.getNoOfReviews() + " reviews");
        beforePrice.setText("€ " + room.getPrice() * 1.30);
        afterPrice.setText("€ " + (room.getPrice()));
        ratingBarStars.setRating((float) room.getStars());

        homeDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                intent.putExtra("accountUsername", accountUsername);
                intent.putExtra("accountPassword", accountPassword);
                startActivity(intent);
                finish();
            }
        });

        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RatingActivity.class);
                intent.putExtra("roomRating", room);
                intent.putExtra("accountUsername", accountUsername);
                intent.putExtra("accountPassword", accountPassword);
                startActivity(intent);
            }
        });

        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BookActivity.class);
                intent.putExtra("roomBook", room);
                intent.putExtra("accountUsername", accountUsername);
                intent.putExtra("accountPassword", accountPassword);
                startActivity(intent);
            }
        });

        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRedHeart) {
                    heart.setImageResource(R.drawable.redheart);
                    isRedHeart = true;
                    updateFavoriteRoomsOnServer(accountUsername, accountPassword, room);
                } else {
                    heart.setImageResource(R.drawable.whiteheart);
                    isRedHeart = false;
                    deleteFavoriteRoomsOnServer(accountUsername, accountPassword, room);
                }
            }
        });

        comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CommentsListViewActivity.class);
                intent.putExtra("room", room);
                intent.putExtra("accountUsername", accountUsername);
                intent.putExtra("accountPassword", accountPassword);
                startActivity(intent);
            }
        });

    }

    private void updateFavoriteRoomsOnServer(String username, String password, Room likedRoom) {
        RoomDisplayThread roomDisplayThread = new RoomDisplayThread(username, password, likedRoom, 0,null);
        roomDisplayThread.start();
    }

    private void deleteFavoriteRoomsOnServer(String username, String password, Room likedRoom) {
        RoomDisplayThread roomDisplayThread = new RoomDisplayThread(username, password, likedRoom, 3,null);
        roomDisplayThread.start();
    }

}