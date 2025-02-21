// package
package app.src;

// imports
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

// klash ReducerServer
public class ReducerServer{

	// synartrhsh main pou xekina ton server
    public static void main(String[] args) {
		new ReducerServer().openServer();
	}

	ServerSocket providerSocket;
	Socket connection = null;
	// lista gia thn apothikeysh twn dwmatiwn gia to search
	ArrayList<Pair<ArrayList<Room>>> queryListRoom= new ArrayList<>();
	// lista gia thn apothikeysh twn krathsewn 
	ArrayList<Pair<Map<String, Integer>>> reservationsMap = new ArrayList<>();
	// lista gia thn apothikeysh twn krathsewn ana idiokthth
	ArrayList<Pair<ArrayList<Room>>> ownerReservationsList = new ArrayList<>();
	
	// methodos gia to anoigma tou server
	void openServer() {

		//queryListRoom.add(new Pair<ArrayList<Room>>(1, new ArrayList<Room>())); // comment example
		//reservationsMap.add(new Pair<Map<String, Integer>>(1, new HashMap<>()));// example for reservations
		// example for owners

		try {
			// dhmiourgia enos server socket sto port 5002
			providerSocket = new ServerSocket(5002);

			while (true) {
				// apodoxh eiserxomenwn syndesewn
				connection = providerSocket.accept();

				// dhmiourgia neou thread gia thn epexergasia tou aithmatos tou pelath
				Thread t = new ActionsForClientsReducerObject(connection, queryListRoom, reservationsMap, ownerReservationsList);
				// ekkinish tou thread
				t.start();

			}
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} finally {
			try {
				// kleisimo tou server socket
				providerSocket.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}
}