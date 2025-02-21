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

import app.src.Filter;
import app.src.Pair;
import app.src.Room;

public class RatingThread extends Thread {

    private Handler handler;
    private Float rate;
    private String roomName;
    private String comment;

    public RatingThread(Handler handler, Float rate , String roomName,String comment){
        this.handler = handler;
        this.rate = rate;
        this.roomName=roomName;
        this.comment = comment;
    }

    @Override
    public void run() {
        Socket requestSocket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try{
            requestSocket = new Socket("192.168.1.6",5000);
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            in = new ObjectInputStream(requestSocket.getInputStream());

            out.writeObject("rate");
            out.flush();

            out.writeObject(roomName);
            out.flush();

            out.writeObject(rate);
            out.flush();

            out.writeObject(comment);
            out.flush();

            String rateResponse = (String) in.readObject();
            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putSerializable("responceAsString",rateResponse);
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
}
