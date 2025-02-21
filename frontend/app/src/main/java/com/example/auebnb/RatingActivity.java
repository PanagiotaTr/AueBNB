package com.example.auebnb;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import app.src.Room;

// έχω βάλει ένα EditText για σχόλια (αν το θέλουμε ~ αλλαγή και στο backend)
public class RatingActivity extends AppCompatActivity {

    ImageView imageRoom;
    TextView roomName;
    TextView areaOwner;
    TextView starsPrice;
    RatingBar ratingBar;
    EditText comments;
    Button ratingConfirmation;
    ImageView homeRate;
    boolean isRated = false;
    private String accountUsername;
    private String accountPassword;
    private Room roomChoiceForRate;

    public Handler myHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {

            Bundle bundle = message.getData();
            String responseAsString = bundle.getString("responceAsString");

            if (responseAsString.contains("successfully")){
                //Emfanizei th sumbloseira me ena Toast
                Toast.makeText(getApplicationContext(), "Your rate was successfully added!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivityForDisplayRooms.class);
                intent.putExtra("room",roomChoiceForRate);
                intent.putExtra("accountUsername", accountUsername);
                intent.putExtra("accountPassword", accountPassword);
                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(getApplicationContext(), "Error in rating", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#7F1734"));

        roomChoiceForRate = (Room) getIntent().getExtras().getSerializable("roomRating");
        accountUsername = (String) getIntent().getExtras().get("accountUsername");
        accountPassword = (String) getIntent().getExtras().get("accountPassword");

        ratingBar = findViewById(R.id.ratingBar_Rate);
        imageRoom = findViewById(R.id.image_Rate);
        roomName = findViewById(R.id.roomName_Rate);
        areaOwner = findViewById(R.id.areaOwner_Rate);
        starsPrice = findViewById(R.id.starsPrice_Rate);
        comments = findViewById(R.id.comment_Rate);
        ratingConfirmation = findViewById(R.id.rateConfirmation_Rate);
        homeRate = findViewById(R.id.home_Rate);

        //ορίζω τις πληροφορίες του επιλεγμένου καταλύματος
        byte[] roomImage = roomChoiceForRate.getRoomImage();
        Bitmap roomImageBook = BitmapFactory.decodeByteArray(roomImage, 0, roomImage.length);
        imageRoom.setImageBitmap(roomImageBook);
        roomName.setText(roomChoiceForRate.getRoomName());
        areaOwner.setText(roomChoiceForRate.getArea() + "  ●  " + roomChoiceForRate.getOwner());
        starsPrice.setText("⭐" + String.format("%.1f",roomChoiceForRate.getStars()) +  "    € " + roomChoiceForRate.getPrice());

        homeRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                intent.putExtra("accountUsername", accountUsername);
                intent.putExtra("accountPassword", accountPassword);
                startActivity(intent);
                finish();
            }
        });

        //akroash allagwn sth bathmologia
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                isRated = true;//Exei ginei ajiologhsh
            }
        });

        ratingConfirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRated) { // Exei ginei ajiologhsh
                    String roomName = roomChoiceForRate.getRoomName();
                    String comment = comments.getText().toString();
                    RatingThread t = new RatingThread(myHandler, ratingBar.getRating(), roomName,comment);
                    t.start();
                } else {
                    Toast.makeText(RatingActivity.this, "Please rate first!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}