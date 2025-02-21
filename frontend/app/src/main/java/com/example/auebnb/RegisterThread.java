package com.example.auebnb;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class RegisterThread extends Thread{

    private Handler handler;
    private String userName;
    private String password;

    public RegisterThread(Handler handler, String userName, String password) {
        this.handler = handler;
        this.userName = userName;
        this.password = password;
    }

    public void run() {
        Socket requestSocket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;

        try {
            requestSocket = new Socket("192.168.2.13", 5003);
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            in = new ObjectInputStream(requestSocket.getInputStream());

            out.writeObject("register");
            out.flush();

            out.writeObject(userName);
            out.flush();

            out.writeObject(password);
            out.flush();

            String registerResult = (String) in.readObject();

            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("registerResult", registerResult);
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
