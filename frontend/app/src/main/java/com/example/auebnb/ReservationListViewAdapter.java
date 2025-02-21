package com.example.auebnb;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.src.Room;

public class ReservationListViewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<HashMap.Entry<Room, ArrayList<String>>> reservationList;

    public ReservationListViewAdapter(LayoutInflater inflater, HashMap<Room, ArrayList<String>> reservationMap) {
        this.inflater = inflater;
        this.reservationList = new ArrayList<>(reservationMap.entrySet());
    }

    @Override
    public int getCount() {
        return reservationList.size();
    }

    @Override
    public Object getItem(int position) {
        return reservationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.reservation_list_item, container, false);
        }

        HashMap.Entry<Room, ArrayList<String>> entry = reservationList.get(position);
        Room currentRoom = entry.getKey();
        ArrayList<String> bookingDates = entry.getValue();

        ((ImageView) convertView.findViewById(R.id.reservationlist_room_image))
                .setImageBitmap(BitmapFactory.decodeByteArray(currentRoom.getRoomImage(), 0, currentRoom.getRoomImage().length));
        ((TextView) convertView.findViewById(R.id.reservationlist_room_name)).setText(currentRoom.getRoomName());
        ((RatingBar) convertView.findViewById(R.id.reservationlist_room_rating)).setRating(currentRoom.getStars());
        ((TextView) convertView.findViewById(R.id.reservationlist_room_rating_numb)).setText(String.format("%.1f", currentRoom.getStars()));

        String rating;
        if (currentRoom.getStars() <= 1) {
            rating = "Very bad";
        } else if (currentRoom.getStars() > 1 && currentRoom.getStars() <= 2) {
            rating = "Bad";
        } else if (currentRoom.getStars() > 2 && currentRoom.getStars() <= 3) {
            rating = "Good";
        } else if (currentRoom.getStars() > 3 && currentRoom.getStars() <= 4) {
            rating = "Very Good";
        } else {
            rating = "Excellent";
        }
        ((TextView) convertView.findViewById(R.id.reservationlist_room_rating_text)).setText(rating);
        ((TextView) convertView.findViewById(R.id.reservationlist_room_area)).setText(currentRoom.getArea());
        ((TextView) convertView.findViewById(R.id.reservationlist_room_num_reviews)).setText(currentRoom.getNoOfReviews() + " reviews");
        ((TextView) convertView.findViewById(R.id.reservationlist_room_persons)).setText(currentRoom.getNoOfPersons() + " persons");


        String temp = bookingDates.toString();

        ((TextView) convertView.findViewById(R.id.reservationlist_room_date)).setText(temp);

        return convertView;
    }
}
