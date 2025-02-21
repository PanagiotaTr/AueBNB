package app.src;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class AccountActions extends Thread {

    private Socket connection;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Map<String, User> users;

    public AccountActions(Socket connection, Map<String, User> users) {
        this.connection = connection;
        this.users = users;
        try {
            out = new ObjectOutputStream(connection.getOutputStream());
            in = new ObjectInputStream(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            String service = (String) in.readObject();
            switch (service) {
                case "register":
                    register();
                    break;
                case "login":
                    login();
                    break;
                case "getFavoriteRooms":
                    getFavoriteRooms();
                    break;
                case "addFavoriteRooms":
                    addFavoriteRooms();
                    break;
                case "checkIfFavorite":
                    checkIfFavorite();
                    break;
                case "removeFavoriteRooms":
                    removeFavoriteRooms();
                    break;
                case "addReservations":
                    addReservationRooms();
                    break;
                case "getReservations":
                    getReservationRooms();
                    break;
                default:
                    break;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void register() throws IOException, ClassNotFoundException {
        String username = (String) in.readObject();
        String password = (String) in.readObject();

        synchronized (users) {
            if (users.containsKey(username)) {
                out.writeObject("User already exists");
            } else {
                users.put(username, new User(username, password));
                out.writeObject("User registered successfully");
            }
        }
    }

    private void login() throws IOException, ClassNotFoundException {
        String username = (String) in.readObject();
        String password = (String) in.readObject();

        synchronized (users) {
            User user = users.get(username);
            if (user != null && user.getPassword().equals(password)) {
                out.writeObject("Logged in successfully");
            } else {
                out.writeObject("Invalid username or password");
            }
        }
    }

    private void getFavoriteRooms() throws IOException, ClassNotFoundException {
        String username = (String) in.readObject();

        synchronized (users) {
            User user = users.get(username);
            out.writeObject(user.getFavoriteRooms());
            out.flush();
        }
    }

    private void getReservationRooms() throws IOException, ClassNotFoundException {
        String username = (String) in.readObject();

        synchronized (users) {
            User user = users.get(username);
            out.writeObject(user.getReservationRooms());
            out.flush();
        }
    }

    private void addFavoriteRooms() throws IOException, ClassNotFoundException {
        String username = (String) in.readObject();
        Room favoriteRoom = (Room) in.readObject();

        synchronized (users) {
            User user = users.get(username);
            user.addFavoriteRooms(favoriteRoom);
        }
    }

    private void removeFavoriteRooms() throws IOException, ClassNotFoundException {
        String username = (String) in.readObject();
        Room favoriteRoom = (Room) in.readObject();

        synchronized (users) {
            User user = users.get(username);
            user.removeFavoriteRooms(favoriteRoom);
        }
    }

    private void addReservationRooms() throws IOException, ClassNotFoundException {
        String username = (String) in.readObject();
        Room reservationRoom = (Room) in.readObject();
        String bookingDate = (String) in.readObject();

        synchronized (users) {
            User user = users.get(username);
            user.addReservationRoom(reservationRoom, bookingDate);
        }
    }

    private void checkIfFavorite() throws IOException, ClassNotFoundException {
        String username = (String) in.readObject();
        Room room = (Room) in.readObject();

        synchronized (users) {
            User user = users.get(username);
            if (user.getFavoriteRooms().contains(room)) {
                out.writeObject(true);
                out.flush();
            } else {
                out.writeObject(false);
                out.flush();
            }
        }
    }
}
