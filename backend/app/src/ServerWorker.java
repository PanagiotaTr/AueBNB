// package
package app.src;

// imports
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.*;

// klash ServerWorker
public class ServerWorker {
    private static final Logger LOGGER = Logger.getLogger(ServerWorker.class.getName());

    // synartrhsh main pou xekina ton server
    public static void main(String[] args) {
        // elegxos an exei dwthei port ws parametros sthn grammh entolwn
        if(args.length != 1){
            System.out.println("Den dwsate port gia ton worker server");
            System.exit(0);
        }
        // xekinhma tou server me to analogo port
        new ServerWorker().openServer(Integer.parseInt(args[0]));
        // new ServerWorker().openServer(5010);
    }

    // antikeimena ServerSocket kai Socket gia thn epikoinwnia tou server
    ServerSocket providerSocket;
    Socket connection = null;
    // ArrayList pou krata ta antikeimena twn dwmatiwn
    static ArrayList<Room> listrooms = new ArrayList<>();

    // methodos gia to anoigma tou server
    void openServer(int port){
        // ReadFile app = new ReadFile();
        // listrooms = app.ReadFile(System.getProperty("user.dir") + "\\json\\worker1.json");

        try {
            // dhmiourgia antikeimenou ServerSocket gia na akouei eiserxomenes syndeseis
            providerSocket = new ServerSocket(port);
            LOGGER.info("Server started. Listening on port " + port + " ...");

            while (true) {
                // apodoxh aithmatwn syndeshs
                connection = providerSocket.accept();
                LOGGER.info("Client connected: " + connection.getInetAddress().getHostAddress());
                // dhmiourgia enos neou thread gia thn epexergasia tou aithmatos tou pelath 
                Thread t = new ActionsForClientWorkerObject(connection, listrooms);
                // ekinhsh thread
                t.start();
                /*for (Room room : listrooms){
                    room.printRoomDetails();
                }*/
            }
        } catch (IOException ioException) {
            LOGGER.log(Level.SEVERE, "An error occurred while running the server", ioException);
        } finally {
            try {
                // kleisimo tou ServerSocket 
                providerSocket.close();
            } catch (IOException ioException) {
                LOGGER.log(Level.SEVERE, "Error while closing server socket", ioException);
            }
        }
    }
}
