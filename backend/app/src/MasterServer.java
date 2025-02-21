// package
package app.src;

// imports
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Map;

// klash MasterServer
public class MasterServer {

    private ServerSocket providerSocket;
	private Socket connection = null;
	private ArrayList<Pair<ArrayList<Room>>> queryListRoom = new ArrayList<>();
	private ArrayList<Pair<Map<String, Integer>>> queryListMap = new ArrayList<>();

	// synartrhsh main pou xekina ton server
    public static void main(String[] args) {
        new MasterServer().openServer();
    }

	// methodos gia to anoigma tou server
    void openServer(){
        try {
			// arxikopoihsh monadikou anagnwristikou thread
            int id = 0;
			providerSocket = new ServerSocket(5000);

			while (true) {
				// apodoxh syndesewn apo clients
				connection = providerSocket.accept();
                
				// auxhsh monadikou anagnvristikou thread
                id++;
				// dhmiourgia neou thread gia epexergasia aithmatwn pelatwn
				Thread t = new MasterActions(connection,id,queryListRoom,queryListMap);
                // enarxh thread
				t.start();

			}
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} finally {
			try {
				// kleisimo tou ServerSocket
				providerSocket.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}

}
