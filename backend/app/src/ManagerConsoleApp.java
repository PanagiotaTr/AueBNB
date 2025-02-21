// package
package app.src;

// imports
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.io.*;
import java.net.*;

// klash ManagerConsoleApp
public class ManagerConsoleApp{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // emfanish logotypou
        System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo\n");
        System.out.print("         _                                  _______      _            ______       \n");
        System.out.print("        / \\                                |       \\    | \\        | |      \\      \n");
        System.out.print("       /   \\                               |        \\   |  \\       | |       \\     \n");
        System.out.print("      /     \\                              |         \\  |   \\      | |        \\    \n");
        System.out.print("     /       \\                  _________  |        /   |    \\     | |        /     \n");
        System.out.print("    /_________\\    |         | /         \\ |_______/    |     \\    | |_______/      \n");
        System.out.print("   /           \\   |         ||__________/ |       \\    |      \\   | |      \\      \n");
        System.out.print("  /             \\  |         ||            |        \\   |       \\  | |       \\     \n");
        System.out.print(" /               \\ |         ||            |         \\  |        \\ | |        \\    \n");
        System.out.print("/                 \\ \\_______/  \\_________  |_________/  |         \\| |________/    \n");
        System.out.println();

        while(true){
            Socket requestSocket = null;
            ObjectOutputStream out = null;
            ObjectInputStream in = null;

            // emfanish menou epilogwn 
            System.out.println();
            System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
            System.out.println("o                                Application AueBNB                                 o");
            System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo\n");
            System.out.println("o                   Select which function you want to implement                     o\n");
            System.out.println("1. Initiallize workers data"); 
            System.out.println("2. Show your accommodation bookings"); // Emfanisi twn krathsewn twn katalumatwn sas
            System.out.println("3. Show reservations for a specific period of time"); // Emfanish krathsewn gia sugkekrimeno xroniko diasthma
            System.out.println("4. Exit\n");  

            int selection = 0;
            
            try{
                System.out.print("Selection of number: ");
                selection = scanner.nextInt();
                System.out.println();
                System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo\n");
            }
            catch(InputMismatchException e){
                System.out.println("o                           Wrong entrance! Try again!                              o");
                System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo\n");
                System.exit(0);
            }
            if(selection == 4){
                // exit
                System.out.println("o                             Exit from Console App!                                o");

                System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo\n");
                System.out.print("                  ____________                              ______________             \n");
                System.out.print("                 |                \\      /        |               |                   \n");
                System.out.print("                 |                 \\    /         |               |                   \n");
                System.out.print("                 |                  \\  /          |               |                   \n");
                System.out.print("                 |                   \\/           |               |                   \n");
                System.out.print("                 |____________        \\           |               |                   \n");
                System.out.print("                 |                   / \\          |               |                   \n");
                System.out.print("                 |                  /   \\         |               |                   \n");
                System.out.print("                 |                 /     \\        |               |                   \n");
                System.out.print("                 |____________    /       \\       |               |                   \n");
                System.out.println();

                System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo\n");
                break;
            }
            String input;
            String service;
            try{
                requestSocket = new Socket("localhost",5000);
                out = new ObjectOutputStream(requestSocket.getOutputStream());
                in = new ObjectInputStream(requestSocket.getInputStream());
                switch (selection) {
                    case 1:
                        // arxikopoihsh twn workers apo ena arxeio
                        // epilogh na dwsoume etoimo arxeio pou periexei 50 katalymata
                        String filename = System.getProperty("user.dir") + "\\data\\workerFiles.json"; 


                        // System.out.print("Insert the json file name: ");
                        // input = scanner.nextLine();
                        // input = scanner.nextLine();
                        // String filename = System.getProperty("user.dir") + "\\data\\" + input;

                        ReadFile read = new ReadFile();
                        ArrayList<Room> roomInit = read.readFile(filename);
                        service = "initWorkers";
                        out.writeObject(service);
                        out.flush();

                        out.writeObject(roomInit);
                        out.flush();
                        break;
                    case 2:
                        // Emfanisi twn krathsewn twn katalumatwn enos owner
                        System.out.println("o                           Give the name of the owner                              o\n");
                        System.out.print("--> Name of owner: ");
                        input = scanner.nextLine();
                        input = scanner.nextLine();

                        service = "bookingsByOwner";
                        
                        out.writeObject(service);
                        out.flush();

                        out.writeObject(input);
                        out.flush();

                        Pair<ArrayList<Room>> pair = (Pair<ArrayList<Room>>) in.readObject();

                        if (pair.getMessage().isEmpty()){
                            System.out.println("o                                 Nothing was found!                                o\n");
                        }
                        else{
                            System.out.println();
                            System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo\n");
                            System.out.println("o                                  Owner: " + input  + "                                   o\n");
                            System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo\n");
                            String roomName;
                            for(Room iterRoom : pair.getMessage()){ //iterRoom.getRoomName()
                                roomName = iterRoom.getRoomName();
                                if (roomName.length() == 5){
                                    System.out.println("Name of accommodation: " + roomName + "  | Reservations: " + iterRoom.getReservationsDates());
                                }
                                else{
                                    System.out.println("Name of accommodation: " + roomName + " | Reservations: " + iterRoom.getReservationsDates());
                                }
                            }
                        }

                        System.out.println();
                        System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo\n");

                        break;

                    case 3: 
                        // Emfanish krathsewn gia sugkekrimeno xroniko diasthma
                        System.out.println("o                      Give the specific period you want                            o\n");
                        System.out.print("--> Specific period (dd/mm/yyyy - dd/mm/yyyy): ");
                        input = scanner.nextLine();
                        input = scanner.nextLine();
                        
                        service = "bookingsByDate";

                        out.writeObject(service);
                        out.flush();

                        out.writeObject(input);
                        out.flush();


                        Pair<Map<String, Integer>> pairMap = (Pair<Map<String, Integer>>) in.readObject();


                        System.out.println();
                        System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo\n");
                        System.out.println("o                            Range: " + input  + "                         o\n");
                        System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo\n");

                        for(Map.Entry<String, Integer> entry : pairMap.getMessage().entrySet()){
                            System.out.println("Name of area: " + entry.getKey() + " = " + entry.getValue() + " reservations");
                        }

                        System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo\n");

                        break;

                    default:
                        // lathos input 
                        System.out.println("o                           Wrong entrance! Try again!                              o\n");
                        System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo\n");
                        System.out.print("               _          __________        _          _ _       _                 \n");
                        System.out.print("              / \\        |                 / \\          |       | \\      |        \n");
                        System.out.print("             /   \\       |                /   \\         |       |  \\     |        \n");
                        System.out.print("            /     \\      |               /     \\        |       |   \\    |        \n");
                        System.out.print("           /" + "_______" + "\\     |   ______     /_______\\       |       |    \\   |        \n");
                        System.out.print("          /         \\    |         |   /         \\      |       |     \\  |        \n");
                        System.out.print("         /           \\   |         |  /           \\     |       |      \\ |        \n");
                        System.out.print("        /             \\   \\________| /             \\   _|_      |       \\|        \n");
                        
                        break;
                }

            }catch (UnknownHostException unknownHost) {
                System.err.println("o                   *You are trying to connect to an unknown host!                  o\n");
                System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo\n");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                    out.close();
                    requestSocket.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
        scanner.close();
    }
}
