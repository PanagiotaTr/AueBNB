package app.src;

import java.io.Serializable;

public class Pair<T> implements Serializable{
    private int id;
    private T message;

    public Pair(int id, T message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }


}
