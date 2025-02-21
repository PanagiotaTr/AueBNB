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

import app.src.Room;

public class RoomListViewAdapter extends BaseAdapter {
    private LayoutInflater inflater;

    private ArrayList<Room> rooms;

    public RoomListViewAdapter(LayoutInflater inflater, ArrayList<Room> rooms){
        this.inflater = inflater;
        this.rooms = rooms;
    }

    @Override
    public int getCount() {
        return rooms.size();
    }

    @Override
    public Object getItem(int i) {
        return rooms.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.room_list_item, container, false);
        }

        Room currentRoom = rooms.get(position);
        ((ImageView)convertView.findViewById(R.id.list_room_image))
                .setImageBitmap(BitmapFactory.decodeByteArray(currentRoom.getRoomImage(), 0,currentRoom.getRoomImage().length));
        ((TextView)convertView.findViewById(R.id.list_room_name)).setText(currentRoom.getRoomName());
        ((RatingBar)convertView.findViewById(R.id.list_room_rating)).setRating((currentRoom.getStars()));
        ((TextView)convertView.findViewById(R.id.list_room_rating_numb)).setText(String.format("%.1f",currentRoom.getStars()));
        String rating;
        if(currentRoom.getStars()<=1){
            rating = "Very bad";
        } else if (currentRoom.getStars()>1 && currentRoom.getStars()<=2) {
            rating = "Bad";
        } else if (currentRoom.getStars()>2 && currentRoom.getStars()<=3) {
            rating = "Good";
        } else if (currentRoom.getStars()>3 && currentRoom.getStars()<=4) {
            rating = "Very Good";
        }else {
            rating = "Excellent";
        }
        ((TextView)convertView.findViewById(R.id.list_room_rating_text)).setText(rating);
        ((TextView)convertView.findViewById(R.id.list_room_area)).setText(currentRoom.getArea());
        ((TextView)convertView.findViewById(R.id.list_room_num_reviews)).setText(currentRoom.getNoOfReviews() + " reviews");
        ((TextView)convertView.findViewById(R.id.list_room_persons)).setText(currentRoom.getNoOfPersons() + " persons");
        ((TextView)convertView.findViewById(R.id.list_room_price)).setText(String.format("%.0f",currentRoom.getPrice()) + "€");

        return convertView;
    }
}
