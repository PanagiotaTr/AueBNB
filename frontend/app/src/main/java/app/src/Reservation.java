package app.src;

// package

// imports
import java.io.Serializable;

// klash Reservation pou ylopoiei to Seriazable interface gia na metatrapei
// se byte stream kai na metaferthei mesw tou diktiou
public class Reservation implements Serializable{
    private String name;
    private String date;
    private int adults;
    private int children;

    // constructor gia Reservation
    public Reservation(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public Reservation(String name, String date, int adults, int children) {
        this.name = name;
        this.date = date;
        this.adults=adults;
        this.children=children;
    }

    // methodos get pou epistrefei to onoma ths krathshs
    public String getName() {
        return name;
    }

    // methodos get pou epistrefei thn hmeromhnia ths krathshs
    public String getDate() {
        return date;
    }

    public int getAdults() {
        return adults;
    }
    public int getChildren() {
        return children;
    }

    // methodos toString gia thn anaparastash ths krathshs
    @Override
    public String toString() {
        return "Reservation [ Name:" + name + ", Date:" + date + ", Adults:" + adults + ", Children:" + children + "]";
    }
}
