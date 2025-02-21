// package
package app.src;

// imports
import java.io.*;
import java.net.*;
import java.util.Properties;

// klash MasterClient
public class MasterClient extends Thread{
    private int WORKER1PORT, WORKER2PORT, WORKER3PORT,WORKERS;
    private String WORKER1HOST, WORKER2HOST, WORKER3HOST;
    private int workerNum;
    private Room room;
    private String service;
    private Pair<String> pair;
    private Pair<Filter> pairFilter;
    private String name,date;
    private float rate;
    private String reservationName;
    private ObjectOutputStream outConsole;
    private int children;
    private int adults;
    private String comment;

    // constructor gia MasterClient
    public MasterClient(int workerNum,String service,Room room,Pair<String> pair,Pair<Filter> pairFilter,String name,String reservationName,String date, float rate,ObjectOutputStream outConsole,int adults,int children, String comment){
        this.workerNum = workerNum;
        this.room = room;
        this.service = service;
        this.pair = pair;
        this.pairFilter = pairFilter;
        this.name = name;
        this.date = date;
        this.rate = rate;
        this.reservationName =reservationName;
        this.outConsole =outConsole;
        this.adults = adults;
        this.children = children;
        this.comment = comment;
    }

    // methodos pou ekteleitai otan xekinaei to thread 
    public void run(){
        init();
        int workerPort;
        String workerHost;
        if(workerNum == 0){                      
            workerPort = WORKER1PORT;                      
            workerHost = WORKER1HOST;
        }
        else if(workerNum == 1){
            workerPort = WORKER2PORT;
            workerHost = WORKER2HOST;
        }
        else{
            workerPort = WORKER3PORT;
            workerHost = WORKER3HOST;
        }

        Socket requestSocket = null;
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		try {
            // connection me ton worker
			requestSocket = new Socket(workerHost, workerPort);
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			in = new ObjectInputStream(requestSocket.getInputStream());

            // ektelesh energeiwn analoga thn kathgoria tou service
            switch (service) {
                case "addRoom":
                    out.writeObject(service);
                    out.flush();

                    out.writeObject(room);
                    out.flush();
                    break;
                case "bookingsByOwner":
                    out.writeObject(service);
                    out.flush();

                    out.writeObject(pair);
                    out.flush();
                    break;
                case "bookingsByDate":
                    out.writeObject(service);
                    out.flush();

                    out.writeObject(pair);
                    out.flush();
                    break;
                case "search":
                    out.writeObject(service);
                    out.flush();

                    out.writeObject(pairFilter);
                    out.flush();
                    break;
                case "book":
                    out.writeObject(service);
                    out.flush();

                    out.writeObject(name);
                    out.flush();

                    out.writeObject(date);
                    out.flush();
                    
                    out.writeObject(reservationName);
                    out.flush();

                    out.writeInt(adults);
                    out.flush();

                    out.writeInt(children);
                    out.flush();

                    Boolean response = (Boolean) in.readObject();//NEW
                    outConsole.writeObject(response);//NEW
                    outConsole.flush();//NEW
                    break;
                case "rate":
                    out.writeObject(service);
                    out.flush();

                    out.writeObject(name);
                    out.flush();

                    out.writeObject(rate);
                    out.flush();

                    out.writeObject(comment);
                    out.flush();

                    String rateResponse = (String)in.readObject();
                    outConsole.writeObject(rateResponse);
                    outConsole.flush();
                    break;
                default:
                    break;
            }
        }catch (IOException e) {
			e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
			try {
				in.close();
				out.close();
                if(outConsole!=null){
                    outConsole.close();
                }
			} catch (IOException ioException) {
				ioException.printStackTrace();
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

            WORKER1HOST = properties.getProperty("worker1host");
            WORKER2HOST = properties.getProperty("worker2host");
            WORKER3HOST = properties.getProperty("worker3host");

            WORKER1PORT = Integer.parseInt(properties.getProperty("worker1port"));
            WORKER2PORT = Integer.parseInt(properties.getProperty("worker2port"));
            WORKER3PORT = Integer.parseInt(properties.getProperty("worker3port"));
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
