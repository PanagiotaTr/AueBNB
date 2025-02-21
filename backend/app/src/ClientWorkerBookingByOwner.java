// package
package app.src;

// imports
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Properties;

// klash ClientWorkerBookingByOwner
public class ClientWorkerBookingByOwner extends Thread {
	private String REDUCERHOST;
	private int REDUCERPORT;
    String category="";
    Pair<ArrayList<Room>> pair;

	// constructor gia ClientWorkerBookingByOwner
    public ClientWorkerBookingByOwner(String category,Pair<ArrayList<Room>> pair){
        this.category=category;
        this.pair=pair;
    }

	// methodos pou ektelei to thread 
    public void run() {
		// arxikopoihsh twn parametrwn REDUCERHOST, REDUCERPORT
		init();
		Socket requestSocket = null;
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		try {
			// dhmiourgia syndeshs me ton reducer
			requestSocket = new Socket(REDUCERHOST, REDUCERPORT);
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			in = new ObjectInputStream(requestSocket.getInputStream());

			// apostolh ths kathgorias kai tou pair ston reducer
			out.writeObject(category);
			out.flush();

			out.writeObject(pair);
			out.flush();

			
	
		} catch (UnknownHostException unknownHost) {
			System.err.println("You are trying to connect to an unknown host!");
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}finally {
			// kleisimo eisodwn, exodwn, socket
			try {
				in.close();	out.close();
				requestSocket.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}
	
	// methodos gia thn arxikopoihsh twn parametrwn REDUCERHOST, REDUCERPORT apo arxeio txt
	private void init(){
		FileReader cfgReader = null;
        try{
            cfgReader = new FileReader(System.getProperty("user.dir") + "\\data\\workerCFG.txt");
            Properties properties = new Properties();
            properties.load(cfgReader);

            REDUCERHOST = properties.getProperty("reducerhost");
            REDUCERPORT = Integer.parseInt(properties.getProperty("reducerport"));
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
