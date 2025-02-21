// package
package app.src;

// imports
import java.util.*;
import java.io.*;

// klash ReadFile pou ylopoiei thn anagnwsh enos arxeiou filename
public class ReadFile {
    static ArrayList<Room> listrooms = new ArrayList<>();

    public ArrayList<Room> readFile(String txtfile){
        BufferedReader reader = null;
        String line;

        try{
            reader = new BufferedReader(new FileReader(new File(txtfile)));
            line = reader.readLine();
            String r="";//roomName
            String np="";//noOfPersons
            String a="";//area
            String p="";//price
            String s="";//stars
            String nr="";//noOfReviews
            String dt="";
            String im="";//image
            String ow="";//owner
            
            while(line!=null){
                if(line.trim().equals("{")){
                    //dhmiourgoume antikeimeno dwmateiou gia ka8e perigrafh dwmateiou pou diabazoume
                    Room room= new Room();
                    line = reader.readLine();
                    while(!(line.trim().startsWith("}"))){
                        if(line!=null){
                            if(line.trim().startsWith("\"roomName\":")){
                                //epeidh pairnoume mazi me to string kai to komma
                                //briskoume to mhkos tou string kai pairnoyyme to string xwris to komma
                                r=line.trim().substring(12).trim();
                                int length=r.length();
                                room.setRoomName(r.substring(1,length-2));
                            }
                            if(line.trim().startsWith("\"noOfPersons\":") ){
                                np=line.trim().substring(15).trim();
                                int length=np.length();
                                room.setNoOfPersons(Integer.parseInt(np.substring(0,length-1)));
                            }
							if(line.trim().startsWith("\"area\":") ){
                                a=line.trim().substring(8).trim();
                                int length=a.length();
                                room.setArea(a.substring(1,length-2));
                            }
                            if(line.trim().startsWith("\"price\":")){
                                p=line.trim().substring(9).trim();
                                int length=p.length();
                                room.setPrice(Double.parseDouble(p.substring(0,length-1)));
                            }
                            if(line.trim().startsWith("\"stars\":")){
                                s=line.trim().substring(9).trim();
                                int length=s.length();
                                room.setStars(Float.parseFloat(s.substring(0,length-1)));
                            }
                            if(line.trim().startsWith("\"noOfReviews\":")){
                                nr=line.trim().substring(15).trim();
                                int length=nr.length();
                                room.setNoOfReviews(Integer.parseInt(nr.substring(0,length-1)));
                            }
                            if(line.trim().startsWith("\"date\":")){
                                dt=line.trim().substring(8).trim();
                                int length=dt.length();
                                room.getAvailableDates().add(dt.substring(1, length-2));
                            }
                           
                            if(line.trim().startsWith("\"roomImage\":")){
                                im=line.trim().substring(13).trim();
                                int length=im.length();
                                File myFile = new File(im.substring(1,length-2));
                                byte[] roomImage = new byte[(int) myFile.length()];
                                try {
                                    FileInputStream inputStream = new FileInputStream(myFile);
                                    inputStream.read(roomImage);
                                }catch(FileNotFoundException e){
                                    System.err.println("Image not found");
                                }catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                room.setRoomImage(roomImage);
                            }
                            if(line.trim().startsWith("\"owner\":")){
                                ow=line.trim().substring(9).trim();
                                int length=ow.length();
                                room.setOwner(ow.substring(1,length-1));
                            }

                        }
                        line = reader.readLine();    
                    }
                    listrooms.add(room);
                    //room.printRoomDetails();
                }
                line = reader.readLine();  
            }
        reader.close();
        }
        catch (IOException e) {
            System.out.println	("Error reading line ...");
		}
        return listrooms;
    }   
    public static void main(String[] args) {
		ReadFile app = new ReadFile ();
        ArrayList<Room> r= new ArrayList<> ();
        r=app.readFile(args[0]);
        for (Room item: r) {
            item.printRoomDetails();
        }
	}
    
}
