package com.example.auebnb;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import app.src.Room;

public class RoomDisplayThread extends Thread {
    private Handler handler;
    private String username;
    private String password;
    private Room favoriteRoom;
    private int selectionMenu;
    private String rangeOfBooking;

    public RoomDisplayThread(String username, String password, Room favoriteRooms, int selectionMenu, Handler handler) {
        this.username = username;
        this.password = password;
        this.selectionMenu = selectionMenu;
        this.favoriteRoom = favoriteRooms;
        this.handler = handler;
    }

    public RoomDisplayThread(String username, String password, Room favoriteRooms, String rangeOfBooking, int selectionMenu, Handler handler) {
        this.username = username;
        this.password = password;
        this.selectionMenu = selectionMenu;
        this.favoriteRoom = favoriteRooms;
        this.rangeOfBooking = rangeOfBooking;
        this.handler = handler;
    }

    @Override
    public void run() {
        getUserFromServer(username);
    }

    private void getUserFromServer(String username) {
        Socket requestSocket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;

        try {
            requestSocket = new Socket("192.168.1.6", 5003);
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            in = new ObjectInputStream(requestSocket.getInputStream());

            if (selectionMenu == 1) {
                out.writeObject("getFavoriteRooms");
                out.flush();

                out.writeObject(username);
                out.flush();

                ArrayList<Room> favoriteRooms = (ArrayList<Room>) in.readObject();
                Message msg = new Message();
                Bundle bundle = new Bundle();
                bundle.putSerializable("rooms", favoriteRooms);
                msg.setData(bundle);
                handler.sendMessage(msg);
            } else if (selectionMenu == 0) {
                out.writeObject("addFavoriteRooms");
                out.flush();

                out.writeObject(username);
                out.flush();

                out.writeObject(favoriteRoom);
                out.flush();
            } else if (selectionMenu == 2) {
                out.writeObject("checkIfFavorite");
                out.flush();

                out.writeObject(username);
                out.flush();

                out.writeObject(favoriteRoom);
                out.flush();

                Boolean response = (Boolean) in.readObject();
                Bundle bundle = new Bundle();
                Message msg = new Message();
                bundle.putBoolean("response", response);
                msg.setData(bundle);
                handler.sendMessage(msg);
            } else if (selectionMenu == 3) {
                out.writeObject("removeFavoriteRooms");
                out.flush();

                out.writeObject(username);
                out.flush();

                out.writeObject(favoriteRoom);
                out.flush();
            } else if (selectionMenu == 4) {
                out.writeObject("addReservations");
                out.flush();

                out.writeObject(username);
                out.flush();

                out.writeObject(favoriteRoom);
                out.flush();

                out.writeObject(rangeOfBooking);
                out.flush();
            } else if (selectionMenu == 5) {
                out.writeObject("getReservations");
                out.flush();

                out.writeObject(username);
                out.flush();

                HashMap<Room, ArrayList<String>> reservationRooms = (HashMap<Room, ArrayList<String>>) in.readObject();
                Message msg = new Message();
                Bundle bundle = new Bundle();
                bundle.putSerializable("reservationRooms", reservationRooms);
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
                if (requestSocket != null) {
                    requestSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
