// package
package app.src;

// imports
import java.io.Serializable;

// klash Filter
public class Filter implements Serializable {
    // idiothtes Filtrou
    private String area =""; // perioxh
    private String date = ""; // hmeromhnia
    private int numOfPersons = 0; // arithmos atomwn 
    private double price = 0; // timh
    private float stars = 0 ; // asteria

    // constructor gia Filter
    public Filter(String area, String date, int numOfPersons, double price, float stars) {
        this.area = area;
        this.date = date;
        this.numOfPersons = numOfPersons;
        this.price = price;
        this.stars = stars;
    }

    // methodoi prosvashs stis idiothtes
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNumOfPersons() {
        return numOfPersons;
    }

    public void setNumOfPersons(int numOfPersons) {
        this.numOfPersons = numOfPersons;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }

    // elegxos an to filtro exei kapoio krithrio
    public boolean hasFilter(){
        return (!area.equals("")) || (!date.equals("")) || (numOfPersons != 0) || (price != 0) || (stars != 0);
    }

    // elegxos an ena room ikanopoiei ta krithria tou filtrou
    public boolean matchFilters(Room room){
        return ((area.equals("") || area.equals(room.getArea())) && (date.equals("") || room.isAvailable(date))
                && (numOfPersons == 0 || room.getNoOfPersons()>= numOfPersons) && (Double.compare(price,0.0) == 0 || Double.compare(price, room.getPrice())>=0)
                && (Float.compare(stars,0) == 0 || Float.compare(room.getStars(), stars)>=0));
    }

    // epistrofh string gia thn perigrafh tou filtrou
    @Override
    public String toString() {
        return "Filter [area=" + area + ", date=" + date + ", numOfPersons=" + numOfPersons + ", price=" + price
                + ", stars=" + stars + "]";
    }



}
