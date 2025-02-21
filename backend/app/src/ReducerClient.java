// package
package app.src;

// imports
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

// klash ReducerClient
public class ReducerClient extends Thread{
	private String MASTERHOST;
	private int MASTERPORT;
    private Pair<ArrayList<Room>> pairList;
	private Pair<Map<String, Integer>> reservationList;
	private Pair<ArrayList<Room>> ownerList;
    private String service;

	// constructor gia ReducerClient
	// dexetai thn kathgoria kai tis listes poy tha xrhsimopoihthoun analoga me aythn
    ReducerClient(String service, Pair<ArrayList<Room>> pairList, Pair<Map<String, Integer>> reservationList, Pair<ArrayList<Room>> ownerList){
        this.service = service;
        this.pairList = pairList;
		this.reservationList =reservationList;
		this.ownerList = ownerList;
    }
	
	// methodos pou ekteleitai otan xekinaei to thread 
    public void run() {
		init();
		Socket requestSocket = null;
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		try {
			// syndesh ston Master server
			requestSocket = new Socket(MASTERHOST, MASTERPORT); // 5000
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			in = new ObjectInputStream(requestSocket.getInputStream());
			
			// apostolh kathgorias
            out.writeObject(service);
            out.flush();
			switch(service){
				case "querySearchReturn":
				// apostolh listas me dwmatia anazhthshs
					out.writeObject(pairList);
					out.flush();
					break;
				case "queryBookByDateReturn":
				// apostolh listas me tis krathseis twn dwmatiwn
					out.writeObject(reservationList);
					out.flush();
					break;
				case "queryBookByOwnerReturn":
				// apostolh listas me tis krathseis twn dwmatiwn ana idiokthth
					out.writeObject(ownerList);
					out.flush();
					break;
			}
		} catch (UnknownHostException unknownHost) {
			System.err.println("You are trying to connect to an unknown host!");
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} finally {
			try {
				in.close();	out.close();
				requestSocket.close();
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

            MASTERHOST = properties.getProperty("masterhost");
            MASTERPORT = Integer.parseInt(properties.getProperty("masterport"));
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
