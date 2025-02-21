package app.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String username;
    private String password;
    private ArrayList<Room> favoriteRooms;
    private HashMap<Room, ArrayList<String>> reservationRooms;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.favoriteRooms = new ArrayList<>();
        this.reservationRooms = new HashMap<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Room> getFavoriteRooms() {
        return favoriteRooms;
    }

    public void addFavoriteRooms(Room room) {
        favoriteRooms.add(room);
    }

    public void removeFavoriteRooms(Room room) {
        favoriteRooms.remove(room);
    }

    public HashMap<Room, ArrayList<String>> getReservationRooms() {
        return reservationRooms;
    }

    public void addReservationRoom(Room room, String date) {
        if (!reservationRooms.containsKey(room)){
            ArrayList<String> temp = new ArrayList<>();
            temp.add(date);
            reservationRooms.put(room, temp);
        }else{
            reservationRooms.get(room).add(date);
        }
    }
}
