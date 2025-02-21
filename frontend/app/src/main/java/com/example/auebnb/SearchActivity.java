package com.example.auebnb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import app.src.Filter;
import app.src.Room;

public class SearchActivity extends AppCompatActivity {

    private EditText area_edt;
    private EditText date2_edt;
    private EditText date1_edt;
    private EditText guests_edt;
    private EditText price_edt;
    private EditText stars_edt;
    private Button search_btn;
    private ImageView checkin_cancel;
    private ImageView checkout_cancel;

    // μενου με spinners
    private Spinner spinnerMenu;
    private Spinner secondSpinner;
    private ArrayAdapter<CharSequence> secondAdapter;
    private TextView resultText;
    ImageView homeSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#7F1734"));

        String accountUsername = (String) getIntent().getExtras().get("accountUsername");
        String accountPassword = (String) getIntent().getExtras().get("accountPassword");

        area_edt = findViewById(R.id.search_area_edt);
        date2_edt = findViewById(R.id.search_date2_edt);
        date1_edt = findViewById(R.id.search_date1_edt);
        guests_edt = findViewById(R.id.search_guests_edt);
        price_edt = findViewById(R.id.search_price_edt);
        stars_edt = findViewById(R.id.search_stars_edt);
        search_btn = findViewById(R.id.search_btn);
        checkin_cancel = findViewById(R.id.checkin_cancel_btn);
        checkout_cancel = findViewById(R.id.checkout_cancel_btn);
        homeSearch = findViewById(R.id.home_Search);

        homeSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                intent.putExtra("accountUsername", accountUsername);
                intent.putExtra("accountPassword", accountPassword);
                startActivity(intent);
                finish();
            }
        });

        date1_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(date1_edt,checkin_cancel);
            }
        });

        date2_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(date2_edt,checkout_cancel);
            }
        });

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check(accountUsername, accountPassword);
            }
        });

        checkin_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkin_cancel.setVisibility(View.INVISIBLE);
                date1_edt.setText("");
            }
        });

        checkout_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkout_cancel.setVisibility(View.INVISIBLE);
                date2_edt.setText("");
            }
        });

        // μενου για spinners
        spinnerMenu = findViewById(R.id.spinner_menu);
        resultText = findViewById(R.id.result_text);
        secondSpinner = findViewById(R.id.second_spinner);
        secondSpinner.setVisibility(View.GONE);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMenu.setAdapter(adapter);

        spinnerMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        // Price
                        secondSpinner.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        // Stars
                        secondSpinner.setVisibility(View.VISIBLE);
                        break;
                    default:
                        secondSpinner.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        secondAdapter = ArrayAdapter.createFromResource(this,
                R.array.second_spinner_options, android.R.layout.simple_spinner_item);
        secondAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        secondSpinner.setAdapter(secondAdapter);

        secondSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedOption = secondAdapter.getItem(position).toString();
                if (selectedOption.equals("Low")) {

                } else if (selectedOption.equals("High")) {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void openDialog(EditText edt, ImageView img){
        DatePickerDialog dialog = new DatePickerDialog(this,R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String month_str = String.valueOf(month+1);
                String day_str = String.valueOf(dayOfMonth);
                if(month+1<10){
                    month_str = "0"+ (month+1);
                }
                if(dayOfMonth<10){
                    day_str = "0"+ dayOfMonth;
                }
                edt.setText(day_str + "/" + month_str + "/" + String.valueOf(year));
                img.setVisibility(View.VISIBLE);
            }
        },2024,4,17);
        dialog.show();
    }

    public void check(String accountUsername, String accountPassword){
        String area = area_edt.getText().toString();
        String date_start = date1_edt.getText().toString();
        String date_end = date2_edt.getText().toString();
        String guests = guests_edt.getText().toString();
        String price = price_edt.getText().toString();
        String stars = stars_edt.getText().toString();

//        if(area.isEmpty() && date_start.isEmpty() && date_end.isEmpty() && guests.isEmpty() && price.isEmpty() && stars.isEmpty()){
//            Toast.makeText(this,"Please fill in at least one field",Toast.LENGTH_LONG).show();
//            return;
//        }

        if((!date_start.isEmpty() && date_end.isEmpty()) || (date_start.isEmpty() && !date_end.isEmpty())){
            Toast.makeText(this,"Please fill in both dates to continue",Toast.LENGTH_LONG).show();
            return;
        }

        String date = "";

        if(!date_start.equals("")){
            date = date_start + " - " + date_end;
        }

        if(!date_start.isEmpty() && checkDate(date)){
            Toast.makeText(this,"Please provide correct dates",Toast.LENGTH_LONG).show();
        }

        int noOfPersons = 0;

        if(!guests.equals("")){
            noOfPersons = Integer.parseInt(guests);
        }

        double roomPrice = 0;

        if(!price.equals("")){
            roomPrice = Double.parseDouble(price);
        }

        float roomStars = 0;

        if(!stars.equals("")){
            roomStars = Float.parseFloat(stars);
        }

        Filter filter = new Filter(area,date,noOfPersons,roomPrice,roomStars);
        Intent intent = new Intent(getApplicationContext(),RoomListViewActivity.class);
        intent.putExtra("filter",filter);
        intent.putExtra("spinner_position", spinnerMenu.getSelectedItemPosition());
        intent.putExtra("second_spinner_position", secondSpinner.getSelectedItemPosition());
        intent.putExtra("accountUsername", accountUsername);
        intent.putExtra("accountPassword", accountPassword);
        startActivity(intent);
    }

    /**
     *
     * @param date
     * @return True if the date range is valid, else False (Considering 2024 as the only choice of year)
     */
    public boolean checkDate(String date){
        int day_start = Integer.parseInt(date.charAt(0)+"") * 10 + Integer.parseInt(date.charAt(1)+"");
        int month_start = Integer.parseInt(date.charAt(3)+"") * 10 + Integer.parseInt(date.charAt(4)+"");
        int day_end = Integer.parseInt(date.charAt(13)+"") * 10 + Integer.parseInt(date.charAt(14)+"");
        int month_end = Integer.parseInt(date.charAt(16)+"") * 10 + Integer.parseInt(date.charAt(17)+"");

        if(month_start>month_end){
            return true;
        }

        if(month_start==month_end && day_start>day_end){
            return true;
        }

        return false;
    }



}