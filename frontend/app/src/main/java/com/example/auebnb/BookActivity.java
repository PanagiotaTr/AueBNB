package com.example.auebnb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import app.src.Room;

public class BookActivity extends AppCompatActivity {

    ImageView imageRoom;
    TextView roomName;
    TextView areaOwner;
    TextView starsPrice;
    EditText fillName;
    EditText checkIn;
    EditText checkOut;
    EditText adults;
    EditText children;
    Button removeAdults;
    Button addAdults;
    Button removeChildren;
    Button addChildren;
    Button bookingConfirmation;
    TextView  showNumberOfPersons;
    ImageView homeBook;
    private String accountUsername;
    private String accountPassword;
    int numberOfAdults = 1;
    int numberOfChildren = 0;
    Room roomChoiceForBook;
    String firstDate;
    String lastDate;


    public Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            boolean result = msg.getData().getBoolean("resultOfBook");
            if (result) {
                Toast.makeText(getApplicationContext(), "The booking was made successfully!", Toast.LENGTH_LONG).show();
                RoomDisplayThread thread = new RoomDisplayThread(accountUsername,accountPassword,roomChoiceForBook, firstDate + " - " + lastDate,4,null);
                thread.start();
                Intent intent = new Intent(getApplicationContext(), MainActivityForDisplayRooms.class);
                intent.putExtra("room",roomChoiceForBook);
                intent.putExtra("accountUsername", accountUsername);
                intent.putExtra("accountPassword", accountPassword);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Booking failed! The room is not available.", Toast.LENGTH_LONG).show();
            }
            return true;
        }
    });



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_for_book);

        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#7F1734"));

        //παίρνω τις πληροφορίες από το προηγούμενο activity
        // Room roomChoiceForBook = new Room("Salem", 2, "Argyroupoly", 560.0, 4.4f, 200, "", "", "Peggy");
        accountUsername = (String) getIntent().getExtras().get("accountUsername");
        accountPassword = (String) getIntent().getExtras().get("accountPassword");

        roomChoiceForBook = (Room) getIntent().getExtras().getSerializable("roomBook");
        int numberOfRersons = roomChoiceForBook.getNoOfPersons();
        String nameOfRoom = roomChoiceForBook.getRoomName();

        // παράδειγμα
        // int numberOfPersons = 10;

        imageRoom = findViewById(R.id.image_Book);
        roomName = findViewById(R.id.roomName_Book);
        areaOwner = findViewById(R.id.areaOwner_Book);
        starsPrice = findViewById(R.id.starsPrice_Book);
        fillName = findViewById(R.id.name_Book);
        checkIn = findViewById(R.id.checkIn_Book);
        checkOut = findViewById(R.id.checkOut_Book);
        adults = findViewById(R.id.adults_Book);
        children = findViewById(R.id.children_Book);
        removeAdults = findViewById(R.id.decrease_adults_Book);
        addAdults = findViewById(R.id.increase_adults_Book);
        removeChildren = findViewById(R.id.decrease_children_Book);
        addChildren = findViewById(R.id.increase_children_Book);
        bookingConfirmation = findViewById(R.id.bookConfirmation_Book);
        showNumberOfPersons = findViewById(R.id.showNumberOfPersons_Book);
        homeBook = findViewById(R.id.home_Book);

        //ορίζω τις πληροφορίες του επιλεγμένου καταλύματος
        byte[] roomImage = roomChoiceForBook.getRoomImage();
        Bitmap roomImageBook = BitmapFactory.decodeByteArray(roomImage, 0, roomImage.length);
        imageRoom.setImageBitmap(roomImageBook);
        roomName.setText(nameOfRoom);
        areaOwner.setText(roomChoiceForBook.getArea() + "  ●  " + roomChoiceForBook.getOwner());
        starsPrice.setText("⭐" + String.format("%.1f",roomChoiceForBook.getStars()) +  "    € " + roomChoiceForBook.getPrice());
        showNumberOfPersons.setText("Number of Persons (" + roomChoiceForBook.getNoOfPersons() + ")");

        // βάζω min = 1 στους adults και min = 0 στα children
        adults.setText(String.valueOf(numberOfAdults));
        children.setText(String.valueOf(numberOfChildren));

        homeBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                intent.putExtra("accountUsername", accountUsername);
                intent.putExtra("accountPassword", accountPassword);
                startActivity(intent);
                finish();
            }
        });

        // κλικ στην αρχική ημερομηνία
        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(checkIn);
            }
        });

        // κλικ στην τελική ημερομηνία
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(checkOut);
            }
        });

        // κλικ στο κουμπί για την προσθήκη adults
        addAdults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkNumberOfPersons(numberOfAdults + 1, numberOfChildren, numberOfRersons)) {
                    numberOfAdults++;
                    adults.setText(String.valueOf(numberOfAdults));
                } else {
                    Toast.makeText(BookActivity.this, "You have exceeded the number of " + numberOfRersons + " people", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // κλικ στο κουμπί για την αφαίρεση adults
        removeAdults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOfAdults > 1) {
                    numberOfAdults--;
                    adults.setText(String.valueOf(numberOfAdults));
                }
            }
        });

        // κλικ στο κουμπί για την προσθήκη children
        addChildren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkNumberOfPersons(numberOfAdults, numberOfChildren + 1, numberOfRersons)) {
                    numberOfChildren++;
                    children.setText(String.valueOf(numberOfChildren));
                } else {
                    Toast.makeText(BookActivity.this, "You have exceeded the number of " + numberOfRersons + " people", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // κλικ στο κουμπί για την αφαίρεση children
        removeChildren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOfChildren > 0) {
                    numberOfChildren--;
                    children.setText(String.valueOf(numberOfChildren));
                }
            }
        });

        // κλικ στην επιβεβαίωση της κράτησης
        bookingConfirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmptyGaps(nameOfRoom);
            }
        });

    }

    private void openDialog(EditText edt){
        DatePickerDialog dialog = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String month_str = String.valueOf(month+1);
                String day_str = String.valueOf(dayOfMonth);
                if(month+1 < 10){
                    month_str = "0"+ (month+1);
                }
                if(dayOfMonth < 10){
                    day_str = "0"+ dayOfMonth;
                }
                edt.setText(day_str + "/" + month_str + "/" + String.valueOf(year));
            }
        },2024,5,20);
        dialog.show();
    }

    public void checkEmptyGaps(String roomNameForBooking){
        String nameOfPerson = fillName.getText().toString();
        firstDate = checkIn.getText().toString();
        lastDate = checkOut.getText().toString();
        int numAdults = Integer.parseInt(adults.getText().toString());
        int numChildren = Integer.parseInt(children.getText().toString());

        if (nameOfPerson.isEmpty()){
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_LONG).show();
            return;
        }

        if (firstDate.isEmpty()){
            Toast.makeText(this, "Please enter the original date", Toast.LENGTH_LONG).show();
            return;
        }

        if (lastDate.isEmpty()){
            Toast.makeText(this, "Please enter the final date", Toast.LENGTH_LONG).show();
            return;
        }

        String dateofBooking = firstDate + " - " + lastDate;
        Boolean invalidDate = checkDate(dateofBooking);

        if (invalidDate == false){
            Toast.makeText(this, "You entered an invalid date range", Toast.LENGTH_LONG).show();
        }
        else{
            BookThread bookThread = new BookThread(handler, roomNameForBooking, dateofBooking, nameOfPerson, numChildren, numAdults,roomChoiceForBook);
            bookThread.start();
        }
    }

    public boolean checkDate(String date){
        int day_start = Integer.parseInt(date.charAt(0)+"") * 10 + Integer.parseInt(date.charAt(1)+"");
        int month_start = Integer.parseInt(date.charAt(3)+"") * 10 + Integer.parseInt(date.charAt(4)+"");
        int day_end = Integer.parseInt(date.charAt(13)+"") * 10 + Integer.parseInt(date.charAt(14)+"");
        int month_end = Integer.parseInt(date.charAt(16)+"") * 10 + Integer.parseInt(date.charAt(17)+"");

        if(month_start > month_end){
            return false;
        }

        if(month_start == month_end && day_start > day_end){
            return false;
        }

        return true;
    }

    public boolean checkNumberOfPersons(int numberOfAdults, int numberOfChildren, int numberOfPersons){

        if ((numberOfAdults + numberOfChildren) <= numberOfPersons) {
            return true;
        }

        return false;
    }

}
