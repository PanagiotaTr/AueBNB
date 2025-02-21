// package
package app.src;

// imports
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

// klash MasterActions
public class MasterActions extends Thread {
    private ObjectInputStream in;
	private ObjectOutputStream out;
    private int id;
    private ArrayList<Pair<ArrayList<Room>>> queryListRoom;
    private ArrayList<Pair<Map<String, Integer>>> queryListMap;
    private int WORKERS;

    // constructor gia MasterActions
    public MasterActions(Socket connection, int id, ArrayList<Pair<ArrayList<Room>>> queryListRoom, ArrayList<Pair<Map<String, Integer>>> queryListMap){
        this.id = id;
        this.queryListRoom = queryListRoom;
        this.queryListMap = queryListMap;
        try{    
            out = new ObjectOutputStream(connection.getOutputStream());
            in = new ObjectInputStream(connection.getInputStream());
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

    // methodos pou ekteleitai otan xekinaei to thread 
    public void run(){
        String service = null;
        try{
            init();
            //System.out.println("Thread with id: " + this.getId() + " has this id: " + id); Gia debug klp
            service = (String) in.readObject();
            switch (service) {
                case "initWorkers":
                    ArrayList<Room> initRooms = (ArrayList<Room>) in.readObject();
                    for(Room room : initRooms){
                        int hash = Math.abs(room.getRoomName().hashCode() % WORKERS);
				        new MasterClient(hash, "addRoom", room, null, null,"", "", "",0,null,0,0,"").start();
                    }
                    break;
                case "bookingsByOwner":
                    String name = (String) in.readObject();
                    Pair<String> pair = new Pair<>(id, name);

                    for (int k = 0; k < WORKERS; k++){
                        new MasterClient(k,service,null,pair,null,"","","",0,null,0,0,"").start();
                    }

                    boolean notFound = true;
                    while(notFound) {
                        synchronized (queryListRoom) {
                            for(Pair<ArrayList<Room>> iterPair : queryListRoom){
                                if(iterPair.getId() == id){
                                    notFound = false;
                                    out.writeObject(iterPair);
                                    break;
                                }
                            }
                        }
                    }

                    break;
                case "bookingsByDate":
                    String date = (String) in.readObject();
                    Pair<String> pair2 = new Pair<>(id, date);
                    
                    for (int k = 0; k < WORKERS; k++){
                        new MasterClient(k,service,null,pair2,null,"","","",0,null,0,0,"").start();
                    }
                
                    boolean notFound2 = true;
                    while(notFound2) {
                        synchronized (queryListMap) {
                            for(Pair<Map<String,Integer>> iterPair : queryListMap){
                                if(iterPair.getId() == id){
                                    notFound2 = false;
                                    out.writeObject(iterPair);
                                    break;
                                }
                            }
                        }
                    }
                    break;
                case "search":
                    Filter filters = (Filter) in.readObject();
                    Pair<Filter> pair3 = new Pair<>(id, filters);
                    
                    for (int k = 0; k < WORKERS; k++){
                        new MasterClient(k,service,null,null,pair3,"","","",0,null,0,0,"").start();
                    }

                    boolean notFound3 = true;
                    while(notFound3) {
                        synchronized (queryListRoom) {
                            for(Pair<ArrayList<Room>> iterPair : queryListRoom){
                                if(iterPair.getId() == id){
                                    notFound3 = false;
                                    out.writeObject(iterPair);
                                    break;
                                }
                            }
                        }
                    }


                    break;
                case "book":
                    String bookRoomName = (String) in.readObject();
                    String bookDate = (String) in.readObject();
                    String reservationName = (String)in.readObject();
                    int adults = in.readInt();
                    int children = in.readInt();
                    int workerNum = Math.abs(bookRoomName.hashCode() % WORKERS);
                    
                    new MasterClient(workerNum,service,null,null,null,bookRoomName,reservationName,bookDate,0,out,adults,children,"").start();

                    break;
                case "rate":
                    String rateRoomName = (String) in.readObject();
                    float rate =(Float) in.readObject();
                    String comment = (String) in.readObject();
                    int workerRateNum = Math.abs(rateRoomName.hashCode() % WORKERS);

                    new MasterClient(workerRateNum,service,null,null,null,rateRoomName,"","",rate,out,0,0,comment).start();

                    break;
                case "querySearchReturn":
                    Pair<ArrayList<Room>> pairRoom = (Pair<ArrayList<Room>>) in.readObject(); 
                    synchronized(queryListRoom){   
                        queryListRoom.add(pairRoom);
                    }
                    break;
                case "queryBookByOwnerReturn":
                    Pair<ArrayList<Room>> ownerRooms = (Pair<ArrayList<Room>>) in.readObject();
                    synchronized (queryListRoom){
                        queryListRoom.add(ownerRooms);
                    }
                    break;
                case "queryBookByDateReturn":
                    Pair<Map<String, Integer>> resByAreas = (Pair<Map<String, Integer>>) in.readObject();
                    synchronized (queryListMap){
                        queryListMap.add(resByAreas);
                    }
                    break;
                default:
                    break;
            }

        } catch (IOException e) {
			e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally{
            if(!service.equals("book") && !service.equals("rate") && !service.equals("addRoom")){
                try {
                    in.close();
                    out.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    // methodos gia thn arxikopoihsh twn ports kai twn hosts apo arxeio txt
    private void init(){
        FileReader cfgReader = null;
        try{
            cfgReader = new FileReader(System.getProperty("user.dir") + "\\data\\masterCFG.txt");
            Properties properties = new Properties();
            properties.load(cfgReader);

            WORKERS = Integer.parseInt(properties.getProperty("workers"));

            
        }catch(IOException e){
            System.err.println("IOException in MasterActions init");
        }catch (Exception e) {
            System.err.println("Exception in MasterActions init");
        } finally {
            try { 
                if (cfgReader != null){
                    cfgReader.close();
                } 
            } catch (IOException ioException) { 
                System.err.println("IOException in MasterActions init finally");  
            }
        }
    }


}