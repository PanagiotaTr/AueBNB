package app.src;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class AccountServer {

    private ServerSocket providerSocket;
    private Socket connection = null;
    private Map<String, User> users = new HashMap<>();

    public static void main(String[] args) {
        new AccountServer().openServer();
    }

    void openServer(){
        try {
            providerSocket = new ServerSocket(5003);
            while (true) {
                connection = providerSocket.accept();
                new AccountActions(connection, users).start();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            try {
                if (providerSocket != null) {
                    providerSocket.close();
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
