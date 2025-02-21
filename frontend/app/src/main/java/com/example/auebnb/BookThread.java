package com.example.auebnb;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import app.src.Room;

public class BookThread extends Thread {

    private Handler handler;
    private String name;
    private String reserveDate;
    private String reservationName;
    private String accountUsername;
    private String accountPassword;
    private int numberOfChildren;
    private int numberOfAdults;
    private Room reservationRoom;
    public BookThread(Handler handler, String name, String reserveDate, String reservationName, int numberOfChildren, int numberOfAdults, Room room) {
        this.handler = handler;
        this.name = name;
        this.reserveDate = reserveDate;
        this.reservationName = reservationName;
        this.numberOfChildren = numberOfChildren;
        this.numberOfAdults = numberOfAdults;
        this.reservationRoom = room;
    }

    public void run() {
        Socket requestSocket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;

        try {
            requestSocket = new Socket("192.168.1.6", 5000);
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            in = new ObjectInputStream(requestSocket.getInputStream());

            out.writeObject("book");
            out.flush();

            out.writeObject(name);
            out.flush();

            out.writeObject(reserveDate);
            out.flush();

            out.writeObject(reservationName);
            out.flush();

            out.writeInt(numberOfAdults);
            out.flush();

            out.writeInt(numberOfChildren);
            out.flush();

            Boolean result = (Boolean) in.readObject();

            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putBoolean("resultOfBook", result);
            bundle.putString("name", name);
            bundle.putString("reserveDate", reserveDate);
            bundle.putString("reservationName", reservationName);
            msg.setData(bundle);
            handler.sendMessage(msg);

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
