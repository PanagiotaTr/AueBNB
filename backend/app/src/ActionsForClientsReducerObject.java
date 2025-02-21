// package
package app.src;

// imports 
import java.io.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

// klash ActionsForClientsReducerObject
public class ActionsForClientsReducerObject extends Thread{

    ObjectInputStream in;
	ObjectOutputStream out;
    ArrayList<Pair<ArrayList<Room>>> queryListRoom;
    ArrayList<Pair<Map<String, Integer>>> reservationsMap;
    ArrayList<Pair<ArrayList<Room>>> ownerReservationsList;
    private int WORKERS;

    // constructor pou arxikopiei ta streams kai tis listes pou tha xrhsimopoihthoun
    public ActionsForClientsReducerObject(Socket connection, ArrayList<Pair<ArrayList<Room>>> queryListRoom, ArrayList<Pair<Map<String, Integer>>> reservationsMap, ArrayList<Pair<ArrayList<Room>>> ownerReservationsList) { //temp + extra array
        //TODO Auto-generated constructor stub
        try {
			out = new ObjectOutputStream(connection.getOutputStream());
			in = new ObjectInputStream(connection.getInputStream());
            this.queryListRoom = queryListRoom;
            this.reservationsMap = reservationsMap;
            this.ownerReservationsList = ownerReservationsList;

		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    // methodos pou ekteleitai otan xekinaei to thread 
    public void run(){
        try {
            init();
            // diabazoume to eidos ths kathgorias 
            String service = (String) in.readObject();

            // an h kathgoria einai anazhthsh
            if(service.equals("search")){

                int counter = 0;
                ArrayList<Room> roomList = new ArrayList<>();

                // diabazoume ta dedomena anazhthshs
                Pair<ArrayList<Room>> workerPair = (Pair<ArrayList<Room>>) in.readObject();
                int id = workerPair.getId();


                // sygxronismenh eisagwgh sthn lista anazhthshs
                synchronized(queryListRoom){
                    queryListRoom.add(workerPair);

                    // anazhthsh sthn lista anazhthshs
                    for (int i = 0; i < queryListRoom.size(); i++){
                        if (queryListRoom.get(i).getId() == id){
                            roomList.addAll(queryListRoom.get(i).getMessage());
                            counter++;
                        }
                    }

                } 

                // an exoume parei oles tis apanthseis
                if (counter == WORKERS){
                    //Pair pair = new Pair(id, roomList); // orisma katw alla kai constructor 
                    // ektypwsh elegxou
                    System.out.println("search check"); // comment
                    // ekkinhsei neou thread gia thn epistrofh twn apotelesmatwn 
                    new ReducerClient("querySearchReturn", new Pair<ArrayList<Room>>(id, roomList), null, null).start(); //klhsh
                }

            }

            // an h kathgoria einai krathseis ana hmeromhnia
            else if (service.equals("bookingsByDate")){

                int counter = 0;
                Map<String, Integer> list = new HashMap<>();

                // diabazoume ta dedomena twn krathsewn 
                Pair<Map<String, Integer>> workerReservList = (Pair<Map<String, Integer>>) in.readObject();
                int id = workerReservList.getId();
                
                // sygxronismenh eisagwgh sto map twn krathseewn 
                synchronized(reservationsMap){
                    reservationsMap.add(workerReservList);

                    // anazhthsh sto map twn krathsewn 
                    for (Pair<Map<String, Integer>> reservList : reservationsMap) {
                        if (reservList.getId() == id){
                            for (Map.Entry<String, Integer> entry : reservList.getMessage().entrySet()) {
                                String key = entry.getKey();
                                Integer value = entry.getValue();
                                if (list.containsKey(key)) {
                                    list.put(key, list.get(key) + value); 
                                } else {
                                    list.put(key, value);
                                }
                            }
                            counter++;
                        }
                    }

                }

                // an exoume oles tis apanthseis
                if (counter == WORKERS){
                    // ektypwsh elegxou
                    System.out.println("bookingsByDate check"); // comment
                    // ekkinhsei neou thread gia thn epistrofh twn apotelesmatwn
                    new ReducerClient("queryBookByDateReturn",  null, new Pair<Map<String, Integer>>(id, list), null).start(); //klhsh
                }
                
            }
            // an h kathgoria einai krathseiw ana idiokthth
            else if (service.equals("bookingsByOwner")){

                int counter = 0;
                ArrayList<Room> ownerList = new ArrayList<>();

                // diabazoume ta dedomena krathsewn ana idiokthth
                Pair<ArrayList<Room>> ownerPair = (Pair<ArrayList<Room>>) in.readObject();
                int id = ownerPair.getId();
                
                // sygxronismenh eisagvgh sthn lista krathsewn ana idiokthth
                synchronized(ownerReservationsList){
                    ownerReservationsList.add(ownerPair);

                    // anazhthsh sthn lista krathsewn ana idiokthth
                    for (int i = 0; i < ownerReservationsList.size(); i++){
                        if (ownerReservationsList.get(i).getId() == id){
                            ownerList.addAll(ownerReservationsList.get(i).getMessage());
                            counter++;
                        }
                    }

                }

                // an exoume oles tis apanthseis
                if (counter == WORKERS){
                    // ektypwsh elegxou
                    System.out.println("bookingsByOwner check"); // comment
                    // ekkinhsh neou thread gia thn epistrofh twn apotelesmatwn
                    new ReducerClient("queryBookByOwnerReturn", null, null, new Pair<ArrayList<Room>>(id, ownerList)).start(); //klhsh
                }

            }
            else{
                // ektypwsh mhnymatos sfalmatos
                System.out.println("Error given service...");
            }
        } catch (ClassNotFoundException e) {
            // an den brethei to antikeimeno 
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}

    // methodos gia thn arxikopoihsh twn ports kai twn hosts apo arxeio txt
    private void init(){
        FileReader cfgReader = null;
        try{
            cfgReader = new FileReader(System.getProperty("user.dir") + "\\data\\reducerCFG.txt");
            Properties properties = new Properties();
            properties.load(cfgReader);

            WORKERS = Integer.parseInt(properties.getProperty("workers"));

            
        }catch(IOException e){
            System.err.println("IOException in ActionsForClientsReducerObject init");
        }catch (Exception e) {
            System.err.println("Exception in ActionsForClientsReducerObject init");
        } finally {
            try { 
                if (cfgReader != null){
                    cfgReader.close();
                } 
            } catch (IOException ioException) { 
                System.err.println("IOException in ActionsForClientsReducerObject init finally");  
            }
        }
    }

    
}
