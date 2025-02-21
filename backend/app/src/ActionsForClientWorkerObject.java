// package
package app.src;

// imports
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// klash ActionsForClientWorkerObject
public class ActionsForClientWorkerObject extends Thread {
    ObjectInputStream in;
	ObjectOutputStream out;
    ArrayList<Room> listrooms;
    
    public ActionsForClientWorkerObject(Socket connection, ArrayList<Room> listrooms) {
        
		try {
			out = new ObjectOutputStream(connection.getOutputStream());
			in = new ObjectInputStream(connection.getInputStream());
            this.listrooms=listrooms;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public void run() {
		try {
			// diavazoume thn kathgoria pou exei epilegei 
            String category =(String) in.readObject();
            
            //prosthkh dwmatiou
            if(category.equals("addRoom")){
               
                // diavazoume to antikeimeno room 
                Room room=(Room) in.readObject();
                synchronized(listrooms){
                    listrooms.add(room);
                }
                // System.out.println("The room has been succesfully added.");
                // room.printRoomDetails();

            }//gia krathseis twn katalymatwn enow idiokthth
            else if(category.equals("bookingsByOwner")){
                Pair<String> pair=(Pair<String>) in.readObject();
                int id=pair.getId();
                String owner=pair.getMessage();
                ArrayList<Room> reserveddates=new ArrayList<>();

                    //gia kathe dwmatio ston worker
                for(Room room : listrooms){
                    //elenje an to dwmatio einai toy idiokthth
                    if(owner.equals(room.getOwner())){
                        //pare tis krathseiw apo auto to dwmatio
                        //gia kathe krathsh pou exei to dwmatio toy idiokthth prosthesai thn sto reserveddates
                        //etsi to reserveddates tha exei tis krathseiw apo ola ta dwmatia tou idiokthth
                        reserveddates.add(room);
                    }
                }

                //auto einai gia debug ama thelw na dw oti douleuei
                /*Pair<ArrayList<String>> p=new Pair(id,reserveddates);
                for(String d : reserveddates){
                    System.out.println(d);
                }*/
                Pair<ArrayList<Room>> p=new Pair<>(id,reserveddates);
                new ClientWorkerBookingByOwner("bookingsByOwner",p).start();
                //out.writeObject(p);
                //out.flush();


            }// gia ena diasthma epistrefei to plhthos twn krathsevn ana perioxh
            else if(category.equals("bookingsByDate")){
                Pair<String> pair= (Pair<String>) in.readObject();
                int id=pair.getId();
                String date=pair.getMessage();
                //dhmiorgw lista me monadikew perioxew twn dwmatiwn
                Map<String, Integer> map = new HashMap<>();
                int count = 0;
                for(Room room : listrooms){
                    if(!map.containsKey(room.getArea())){
                        map.put(room.getArea(),0);
                    }
                    if(!room.isAvailable(date)){
                        count = room.countReservesByDate(date);
                        map.put(room.getArea(), map.get(room.getArea()) + count);
                    }
                }


                //epistrefw ws teliko to id kai lista me perioxew kai count
                Pair<Map<String,Integer>> allareaspair=new Pair<>(id,map);
                new ClientWorkerBookingByDate("bookingsByDate",allareaspair).start();
                

            }//gia anazhthsh me filtra
            else if(category.equals("search")){
                Pair<Filter> pair=(Pair<Filter>) in.readObject();
                int id=pair.getId();
                Filter filter=pair.getMessage();
                //ftiaxnw mia lista me ta dwmatia pou ikanopoioun filters
                ArrayList<Room> roomfilter=new ArrayList<>();
                for(Room room : listrooms){
                    //an ikanopoiei filter to prosthetw
                    if (filter.matchFilters(room)){
                        roomfilter.add(room);
                    }
                }
                Pair<ArrayList<Room>> pairfilter= new Pair(id,roomfilter);
                new ClientWorkerSearch("search",pairfilter).start();
            }//krathsh
            else if (category.equals("book")){
                String roomName= (String) in.readObject();
                String date=(String) in.readObject();
                String reservationName = (String) in.readObject();
                int adults = in.readInt();
                int children = in.readInt();
                Boolean roomFound = false;
                //gia kathe dwmatio ths listas pou exeo o worker
                for(Room room : listrooms){
                    //an to onoma pou edwse o xrhsthw einai idio me tou dwmatiou
                    if(roomName.equals(room.getRoomName())){
                            //an to dwmatio einai diathesimo ekeinew thw meres
                        synchronized(room){
                            if(room.isAvailable(date)){
                                //kane krathsh
                                room.book(date,reservationName,adults,children);
                                out.writeObject(true);//NEW
                                out.flush();
                            }
                            else{
                                out.writeObject(false);//NEW
                                out.flush();
                            }
                        }
                        roomFound = true;
                        break; 
                    }
                }


                if (!roomFound){
                    out.writeObject(roomFound);
                    out.flush();
                }
                
                // System.out.printf("Room %s has been booked for the dates: %s",roomName,date);


            }//bathmologhsh
            else if(category.equals("rate")){
                String roomName= (String) in.readObject();
                float newstarreview=(float)in.readObject();
                String comment = (String) in.readObject();
                Room r=null;
                Boolean found = false;
                for (Room room : listrooms){
                    if (roomName.equals(room.getRoomName())){
                        synchronized(room){
                            r=room;
                            float mo=((room.getStars()*room.getNoOfReviews())+newstarreview)/(room.getNoOfReviews()+1);
                            int prevnumber=room.getNoOfReviews();
                                //aujanoume ari8mo review efoson egine kainourgia ba8mologia
                            room.setNoOfReviews(prevnumber+1);
                                //enhmervnoume ta stars me kainourgio mo
                            room.setStars(mo);
                            room.addComment(comment);
                            System.out.println(room);//TEMP TEMP TEMPT TEMP TEMP TMPETP TEMP TEMP
                        }
                        found = true;
                        break;
                    }   
                }
                if(found == true){
                    out.writeObject("*                       Your rate was successfully added!                          *");
                    out.flush();
                }
                else{
                    out.writeObject("*                             No accommodation found!                               *");
                    out.flush();
                }
            }
		

		} catch (IOException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
			try {
				in.close();
				out.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}

    
}
