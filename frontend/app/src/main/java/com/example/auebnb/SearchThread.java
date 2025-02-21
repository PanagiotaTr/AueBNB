package com.example.auebnb;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import app.src.Filter;
import app.src.Pair;
import app.src.Room;

public class SearchThread extends Thread{
    private Filter filter;
    private Handler handler;
    private int spinnerPosition;
    private int secondSpinnerPosition;

    public SearchThread(Handler handler, Filter filter, int spinnerPosition, int secondSpinnerPosition) {
        this.handler = handler;
        this.filter = filter;
        this.spinnerPosition = spinnerPosition;
        this.secondSpinnerPosition = secondSpinnerPosition;
    }

    @Override
    public void run() {
        Socket requestSocket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try{
            requestSocket = new Socket("192.168.1.43",5000);
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            in = new ObjectInputStream(requestSocket.getInputStream());

            out.writeObject("search");
            out.flush();

            out.writeObject(filter);
            out.flush();

            Pair<ArrayList<Room>> pair1 = (Pair<ArrayList<Room>>) in.readObject();
            Pair<ArrayList<Room>> pair = applyFilters(pair1, spinnerPosition, secondSpinnerPosition);
            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putSerializable("rooms",pair.getMessage());
            msg.setData(bundle);
            handler.sendMessage(msg);

        }catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public Pair<ArrayList<Room>> applyFilters(Pair<ArrayList<Room>> pair, int spinnerPosition, int secondSpinnerPosition) {
        ArrayList<Room> rooms = new ArrayList<>(pair.getMessage());

        switch (spinnerPosition) {
            case 0:
                return pair;
            case 1:
                // Price
                Collections.sort(rooms, new Comparator<Room>() {
                    @Override
                    public int compare(Room room1, Room room2) {
                        if (secondSpinnerPosition == 0) {
                            return Double.compare(room1.getPrice(), room2.getPrice());
                        } else {
                            return Double.compare(room2.getPrice(), room1.getPrice());
                        }
                    }
                });
                break;
            case 2:
                // Stars
                Collections.sort(rooms, new Comparator<Room>() {
                    @Override
                    public int compare(Room room1, Room room2) {
                        if (secondSpinnerPosition == 0) {
                            return Float.compare(room1.getStars(), room2.getStars());
                        } else {
                            return Float.compare(room2.getStars(), room1.getStars());
                        }
                    }
                });
                break;
            case 3:
                // Top 5 Popular Rooms
                Collections.sort(rooms, new Comparator<Room>() {
                    @Override
                    public int compare(Room room1, Room room2) {
                        if (secondSpinnerPosition == 0) {
                            return Float.compare(room1.getStars(), room2.getStars());
                        } else {
                            return Float.compare(room2.getStars(), room1.getStars());
                        }
                    }
                });
                List<Room> top5Rooms = new ArrayList<>(rooms.subList(0, Math.min(5, rooms.size())));
                return new Pair<>(pair.getId(), new ArrayList<>(top5Rooms));

        }

        return new Pair<>(pair.getId(), rooms);
    }



}
