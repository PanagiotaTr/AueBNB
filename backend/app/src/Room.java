package app.src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

// klash Room
public class Room implements Serializable{

    String roomName;
    int noOfPersons;
    String area;
    float stars;
    double price;
    int noOfReviews;
    byte[] roomImage;
    ArrayList<String> availableDates = new ArrayList<>();
    ArrayList<Reservation> reservationsDates = new ArrayList<>();
    String owner;
    ArrayList<String> comments = new ArrayList<>();

    //constructor gia Room
    public Room(){

    }

    // constructor pou dinetai ws eisodos ena arxeio etsi wste na pragmatopoihthei diabasma arxeiou
    public Room(String filename){

        BufferedReader reader = null;
        String line;

        try{
            reader = new BufferedReader(new FileReader(new File(System.getProperty("user.dir")+ "\\data\\" + filename)));
            line = reader.readLine();
            String r="";//roomName
            String np="";//noOfPersons
            String a="";//area
            String p="";//price
            String s="";//stars
            String nr="";//noOfReviews
            String dt="";//date
            String im="";//image
            String ow="";//owner

            while(line!=null){
                if(line.trim().equals("{")){
                    //dhmiourgoume antikeimeno dwmateiou gia ka8e perigrafh dwmateiou pou diabazoume
                    line = reader.readLine();
                    while(!(line.trim().startsWith("}"))){
                        if(line!=null){
                            if(line.trim().startsWith("\"roomName\":")){
                                //epeidh pairnoume mazi me to string kai to komma
                                //briskoume to mhkos tou string kai pairnoyyme to string xwris to komma
                                r=line.trim().substring(12).trim();
                                int length=r.length();
                                this.roomName = r.substring(1,length-2);
                            }
                            if(line.trim().startsWith("\"noOfPersons\":") ){
                                np=line.trim().substring(15).trim();
                                int length=np.length();
                                this.noOfPersons = Integer.parseInt(np.substring(0,length-1));
                            }
                            if(line.trim().startsWith("\"area\":") ){
                                a=line.trim().substring(8).trim();
                                int length=a.length();
                                this.area = a.substring(1,length-2);
                            }
                            if(line.trim().startsWith("\"price\":")){
                                p=line.trim().substring(9).trim();
                                int length=p.length();
                                this.price = Double.parseDouble(p.substring(0,length-1));
                            }
                            if(line.trim().startsWith("\"stars\":")){
                                s=line.trim().substring(9).trim();
                                int length=s.length();
                                this.stars = Float.parseFloat(s.substring(0,length-1));
                            }
                            if(line.trim().startsWith("\"noOfReviews\":")){
                                nr=line.trim().substring(15).trim();
                                int length=nr.length();
                                this.noOfReviews = Integer.parseInt(nr.substring(0,length-1));
                            }
                            if(line.trim().startsWith("\"roomImage\":")){
                                im=line.trim().substring(13).trim();
                                int length=im.length();
                                File myFile = new File(im.substring(1,length-2));
                                this.roomImage = new byte[(int) myFile.length()];
                                try (FileInputStream inputStream = new FileInputStream(myFile)) {
                                    inputStream.read(this.roomImage);
                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                            if(line.trim().startsWith("\"date\":")){
                                dt=line.trim().substring(8).trim();
                                int length=dt.length();
                                dt = dt.substring(1, length-2);
                                this.availableDates.add(dt);
                            }
                            if(line.trim().startsWith("\"owner\":")){
                                ow=line.trim().substring(9).trim();
                                int length=ow.length();
                                this.owner = ow.substring(1,length-1);
                            }

                        }
                        line = reader.readLine();
                    }
                    //room.printRoomDetails();
                }
                line = reader.readLine();
            }
            reader.close();
        }
        catch (IOException e) {
            System.out.println	("Error reading line ...");
        }
    }

    // constructor gia Room pou dexetai oles tis plhrofories enos room
    public Room(String roomName,int noOfPersons,String area,double price,float stars,int noOfReviews,String roomImage, String rangeDates, String owner){
        this.roomName=roomName;
        this.noOfPersons=noOfPersons;
        this.area=area;
        this.price=price;
        this.stars=stars;
        this.noOfReviews=noOfReviews;
        this.availableDates.add(rangeDates);
        //this.roomImage= BitmapFactory.decodeFile(roomName);
        File myFile = new File(roomImage);
        this.roomImage = new byte[(int) myFile.length()];
        try (FileInputStream inputStream = new FileInputStream(myFile)) {
            inputStream.read(this.roomImage);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.owner = owner;
    }

    public void addComment(String comment){
        comments.add(comment);
    }

    //setters
    public void setRoomName(String roomName){
        this.roomName=roomName;
    }
    public void setNoOfPersons(int noOfPersons){
        this.noOfPersons=noOfPersons;
    }
    public void setArea(String area){
        this.area=area;
    }
    public void setPrice(double price){
        this.price=price;
    }
    public void setStars(float stars){
        this.stars=stars;
    }
    public void setNoOfReviews(int noOfReviews){
        this.noOfReviews=noOfReviews;
    }
    public void setRoomImage(byte[] roomImage){
        this.roomImage=roomImage;
    }
    public void setAvailableDates(ArrayList<String> availableDates) {
        this.availableDates = availableDates;
    }
    public void setReservationsDates(ArrayList<Reservation> reservationsDates) {
        this.reservationsDates = reservationsDates;
    }
    public void setOwner(String owner){
        this.owner=owner;
    }

    //getters
    public String getRoomName(){
        return roomName;
    }
    public int getNoOfPersons(){
        return noOfPersons;
    }
    public String getArea(){
        return area;
    }
    public double getPrice(){
        return price;
    }
    public float getStars(){
        return stars;
    }
    public int getNoOfReviews(){
        return noOfReviews;
    }
    public byte[] getRoomImage(){
        return roomImage;
    }
    public ArrayList<String> getAvailableDates() {
        return availableDates;
    }
    public ArrayList<Reservation> getReservationsDates() {
        return reservationsDates;
    }
    public String getOwner(){
        return owner;
    }
    public ArrayList<String> getComments(){
        return new ArrayList<>(comments);
    }
    public String splitDateRangeFirstPart(String range){
        String[] dateParts = range.split(" - ");
        return dateParts[0].trim();
    }
    public String splitDateRangeSecondPart(String range){
        String[] dateParts = range.split(" - ");
        return dateParts[1].trim();
    }
    public int dayOfDate(String range){
        String[] dateParts = range.split("/");
        return Integer.parseInt(dateParts[0]);
    }
    public int monthOfDate(String range){
        String[] dateParts = range.split("/");
        return Integer.parseInt(dateParts[1]);
    }
    public int yearOfDate(String range){
        String[] dateParts = range.split("/");
        return Integer.parseInt(dateParts[2]);
    }

    // methodos gia thn diathesimothta enos room pou epistrefei true/false
    public Boolean isAvailable(String range){
        int dayUS = Integer.parseInt(range.charAt(0)+"") * 10 + Integer.parseInt(range.charAt(1)+"");
        int monthUS = Integer.parseInt(range.charAt(3)+"") * 10 + Integer.parseInt(range.charAt(4)+"");
        int dayUE = Integer.parseInt(range.charAt(13)+"") * 10 + Integer.parseInt(range.charAt(14)+"");
        int monthUE = Integer.parseInt(range.charAt(16)+"") * 10 + Integer.parseInt(range.charAt(17)+"");
        int dayS;
        int monthS;
        int dayE;
        int monthE;

        for(int i = 0; i < availableDates.size(); i++){
            dayS = Integer.parseInt(availableDates.get(i).charAt(0)+"") * 10 + Integer.parseInt(availableDates.get(i).charAt(1)+"");
            monthS = Integer.parseInt(availableDates.get(i).charAt(3)+"") * 10 + Integer.parseInt(availableDates.get(i).charAt(4)+"");
            dayE = Integer.parseInt(availableDates.get(i).charAt(13)+"") * 10 + Integer.parseInt(availableDates.get(i).charAt(14)+"");
            monthE = Integer.parseInt(availableDates.get(i).charAt(16)+"") * 10 + Integer.parseInt(availableDates.get(i).charAt(17)+"");
            if(((monthUS > monthS) || (monthUS == monthS && dayUS >= dayS)) && ((monthUE < monthE) || (monthUE == monthE && dayUE <= dayE))){
                return true;
            }
        }
        return false;
    }

    // methodos gia thn pragmatopoihsh krathshs enos dwmatiou
    public void book(String range,String name,int adults,int children){
        int dayUS = Integer.parseInt(range.charAt(0)+"") * 10 + Integer.parseInt(range.charAt(1)+"");
        int monthUS = Integer.parseInt(range.charAt(3)+"") * 10 + Integer.parseInt(range.charAt(4)+"");
        int dayUE = Integer.parseInt(range.charAt(13)+"") * 10 + Integer.parseInt(range.charAt(14)+"");
        int monthUE = Integer.parseInt(range.charAt(16)+"") * 10 + Integer.parseInt(range.charAt(17)+"");
        int dayS;
        int monthS;
        int dayE;
        int monthE;

        for(int i = 0; i < availableDates.size(); i++){
            dayS = Integer.parseInt(availableDates.get(i).charAt(0)+"") * 10 + Integer.parseInt(availableDates.get(i).charAt(1)+"");
            monthS = Integer.parseInt(availableDates.get(i).charAt(3)+"") * 10 + Integer.parseInt(availableDates.get(i).charAt(4)+"");
            dayE = Integer.parseInt(availableDates.get(i).charAt(13)+"") * 10 + Integer.parseInt(availableDates.get(i).charAt(14)+"");
            monthE = Integer.parseInt(availableDates.get(i).charAt(16)+"") * 10 + Integer.parseInt(availableDates.get(i).charAt(17)+"");
            if(((monthUS > monthS) || (monthUS == monthS && dayUS >= dayS)) && ((monthUE < monthE) || (monthUE == monthE && dayUE <= dayE))){
                //dayS <= dayUS && monthS <= monthUS && dayUE <= dayE && monthUE <= monthE (PROHGOUMENO)
                System.out.println(range + " belongs in " + availableDates.get(i)); //comment
                String newDay;
                String newMonth;
                if(dayS == dayUS && monthS == monthUS && dayUE == dayE && monthUE == monthE ){
                    availableDates.remove(i);
                }
                else if(dayS == dayUS && monthS == monthUS && ((monthUE < monthE) || (monthUE == monthE && dayUE <= dayE))){
                    availableDates.remove(i);
                    newDay = dayUE + 1 <10 ? "0" + (dayUE + 1) : Integer.toString(dayUE + 1);
                    newMonth = monthUE < 10? "0" + monthUE : Integer.toString(monthUE);
                    if(dayUE + 1 > 30){
                        newDay = "01";
                        newMonth = monthUE + 1 < 10? "0" + (monthUE+1) : Integer.toString(monthUE+1);
                    }
                    String tempDay = dayE < 10 ? "0" + dayE : Integer.toString(dayE);
                    String tempMonth = monthE < 10 ? "0" + monthE : Integer.toString(monthE);
                    availableDates.add(newDay + "/" + newMonth + "/" + "2024" + " - " + tempDay + "/" + tempMonth + "/" + "2024");
                }
                else if(((monthUS > monthS) || (monthUS == monthS && dayUS >= dayS)) && dayUE == dayE && monthUE == monthE){
                    availableDates.remove(i);
                    newDay = dayUS-1 < 10? "0" +(dayUS-1) :  Integer.toString(dayUS-1);
                    newMonth = monthUS < 10? "0" + monthUS : Integer.toString(monthUS);
                    if(dayUS-1 < 1){
                        newDay = "30";
                        newMonth = monthUS - 1 < 10? "0" + (monthUS - 1) : Integer.toString(monthUS - 1);
                    }
                    String tempDay = dayS < 10 ? "0" + dayS : Integer.toString(dayS);
                    String tempMonth = monthS < 10 ? "0" + monthS : Integer.toString(monthS);
                    availableDates.add(tempDay + "/" + tempMonth + "/" + "2024" + " - " + newDay + "/" + newMonth + "/" + "2024");
                }
                else{
                    String newDayPlus;
                    String newMonthPlus;
                    availableDates.remove(i);
                    newDay = dayUS-1 < 10? "0" +(dayUS-1) :  Integer.toString(dayUS-1);
                    newMonth = monthUS < 10? "0" + monthUS : Integer.toString(monthUS);
                    if(dayUS-1 < 1){
                        newDay = "30";
                        newMonth = monthUS - 1 < 10? "0" + (monthUS - 1) : Integer.toString(monthUS - 1);
                    }
                    newDayPlus = dayUE + 1 <10 ? "0" + (dayUE + 1) : Integer.toString(dayUE + 1);
                    newMonthPlus = monthUE < 10? "0" + monthUE : Integer.toString(monthUE);
                    if(dayUE + 1 > 30){
                        newDayPlus = "01";
                        newMonthPlus = monthUE + 1 < 10? "0" + (monthUE+1) : Integer.toString(monthUE+1);
                    }
                    String tempDay = dayS < 10 ? "0" + dayS : Integer.toString(dayS);
                    String tempMonth = monthS < 10 ? "0" + monthS : Integer.toString(monthS);
                    String tempDay2 = dayE < 10 ? "0" + dayE : Integer.toString(dayE);
                    String tempMonth2 = monthE < 10 ? "0" + monthE : Integer.toString(monthE);
                    availableDates.add(tempDay + "/" + tempMonth + "/" + "2024" + " - " + newDay + "/" + newMonth + "/" + "2024");
                    availableDates.add(newDayPlus + "/" + newMonthPlus + "/" + "2024" + " - " + tempDay2 + "/" + tempMonth2 + "/" + "2024");
                }
                reservationsDates.add(new Reservation(name, range,adults,children));
            }
        }
    }

    //emfanish leptomeriwn dwmatiou
    public void printRoomDetails(){
        System.out.println("--------------------Room Details-----------------");
        System.out.printf("RoomName: %s\n",getRoomName());
        System.out.printf("Number of persons: %d\n",getNoOfPersons());
        System.out.printf("Area: %s\n",getArea());
        System.out.printf("Price: %f\n",getPrice());
        System.out.printf("Stars of the reviews: %f\n",getStars());
        System.out.printf("Number of reviews: %d\n",getNoOfReviews());
        //System.out.printf("The room is available: %s\n", getReservationsDates());
        System.out.println("-------------------------------------------------");
        //image//////////////////
    }

    // methodos toString gia thn emfanish sthn konsola
    @Override
    public String toString() {
        return "roomName = " + roomName + "\n" +
                "noOfPersons = " + noOfPersons + "\n"+
                "area = " + area + "\n" +
                "stars = " + stars + "\n" +
                "price = " + price + "\n" +
                "noOfReviews = " + noOfReviews + "\n" +
                "roomImage = " + roomImage + "\n" +
                "availableDates = " + availableDates + "\n" +
                "reservationsDates = " + reservationsDates + "\n" +
                "owner = " + owner + "\n";
    }

    // methodos pou metrei ths krathseis poy exei ena dwmatio me bash to dwsmeno dateRange
    public int countReservesByDate(String dateRange){
        int count = 0;
        int dayUS = Integer.parseInt(dateRange.charAt(0)+"") * 10 + Integer.parseInt(dateRange.charAt(1)+"");
        int monthUS = Integer.parseInt(dateRange.charAt(3)+"") * 10 + Integer.parseInt(dateRange.charAt(4)+"");
        int dayUE = Integer.parseInt(dateRange.charAt(13)+"") * 10 + Integer.parseInt(dateRange.charAt(14)+"");
        int monthUE = Integer.parseInt(dateRange.charAt(16)+"") * 10 + Integer.parseInt(dateRange.charAt(17)+"");
        int dayS;
        int monthS;
        int dayE;
        int monthE;
        for(Reservation res: reservationsDates){
            dayS = Integer.parseInt(res.getDate().charAt(0)+"") * 10 + Integer.parseInt(res.getDate().charAt(1)+"");
            monthS = Integer.parseInt(res.getDate().charAt(3)+"") * 10 + Integer.parseInt(res.getDate().charAt(4)+"");
            dayE = Integer.parseInt(res.getDate().charAt(13)+"") * 10 + Integer.parseInt(res.getDate().charAt(14)+"");
            monthE = Integer.parseInt(res.getDate().charAt(16)+"") * 10 + Integer.parseInt(res.getDate().charAt(17)+"");
            if(!((monthUE < monthS || (monthUE == monthS && dayUE < dayS))) && !((monthUS > monthE || (monthUS == monthE && dayUS > dayE)))){
                count++;
            }
        }
        return count;
    }

    // methodos equals pou epalitheuei ta dedomena
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Room room = (Room) o;
        return roomName.equals(room.roomName) && noOfPersons == room.noOfPersons && area.equals(room.area) && owner.equals(room.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomName, noOfPersons, area, price, owner);
    }
}
