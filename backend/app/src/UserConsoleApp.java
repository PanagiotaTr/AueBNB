// package
package app.src;

// imports
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;
import java.net.*;

// klash UserConsoleApp 
public class UserConsoleApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // emfanish logotypou
        System.out.println("*************************************************************************************\n");
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
            System.out.println("*************************************************************************************");
            System.out.println("*                                Application AueBNB                                 *");
            System.out.println("*************************************************************************************\n");
            System.out.println("*                   Select which function you want to implement                     *\n");
            System.out.println("1. Accommodation search"); // anazhthsh katalymatos
            System.out.println("2. Booking accommodation"); // krathsh katalymatos
            System.out.println("3. Accommodation rating"); // bathmologhsh katalymatos
            System.out.println("4. Exit\n");

            int selection = 0;
            try{
                System.out.print("Selection of number: ");
                selection = scanner.nextInt();
                System.out.println();
                System.out.println("*************************************************************************************\n");

            }
            catch(InputMismatchException e){
                System.out.println("*                           Wrong entrance! Try again!                              *");
                System.out.println("*************************************************************************************\n");
                System.exit(0);
            }
            if(selection == 4){
                // exit 
                System.out.println("*                          Exit from User Console App!                              *\n");

                System.out.println("*************************************************************************************\n");
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

                System.out.println("*************************************************************************************\n");
                break;
            }
            String service;
            try{
                requestSocket = new Socket("localhost",5000);
                out = new ObjectOutputStream(requestSocket.getOutputStream());
                in = new ObjectInputStream(requestSocket.getInputStream());
                switch (selection) {
                    case 1:
                        // anazhthsh katalymatos
                        int numberOfPersons = 0;
                        double priceD = 0;
                        float starsD = 0;
                        System.out.println("*              Fill in as many of the following filters as you wish                 *\n");
                        System.out.print("--> Area: ");
                        String area = scanner.nextLine();
                        area = scanner.nextLine();
                        System.out.print("--> Date (dd/mm/yyyy - dd/mm/yyyy): ");
                        String date = scanner.nextLine();
                        System.out.print("--> Number of persons: ");
                        String number = scanner.nextLine();
                        if(number.equals("")){
                            numberOfPersons = 0;
                        }else{
                            try{
                                numberOfPersons = Integer.parseInt(number);
                            }catch(InputMismatchException e){
                                System.out.println("*        You gave the wrong value for the number of people! Try again!              *\n");
                                System.out.println("*************************************************************************************\n");
                                System.exit(0);
                            }
                        }
                        System.out.print("--> Price: ");
                        String price = scanner.nextLine();
                        if(price.equals("")){
                            priceD = 0;
                        }else{
                            try{
                                priceD = Double.parseDouble(price);
                            }catch(InputMismatchException e){
                                System.out.println("*         You entered the wrong number in the price field! Try again!               *\n");
                                System.out.println("*************************************************************************************\n");
                                System.exit(0);
                            }
                        }
                        System.out.print("--> Stars: ");
                        String stars = scanner.nextLine();
                        System.out.println();
                        System.out.println("*************************************************************************************\n");

                        if(stars.equals("")){
                            starsD = 0;
                        }else{
                            try{
                                starsD = Float.parseFloat(stars);
                            }catch(InputMismatchException e){
                                System.out.println("*           You gave the wrong value to the star field! Try again!                  *\n");
                                System.out.println("*************************************************************************************\n");
                                System.exit(0);
                            }
                        }
                        service = "search";
                        Filter filter = new Filter(area, date, numberOfPersons, priceD, starsD);
                        out.writeObject(service);
                        out.flush();

                        out.writeObject(filter);
                        out.flush();

                        Pair<ArrayList<Room>> pair = (Pair<ArrayList<Room>>) in.readObject();

                        if (pair.getMessage().isEmpty()){
                            System.out.println("*                                 Nothing was found!                                *\n");
                        }
                        
                        else{
                        
                            System.out.println();
                            for(Room iterRoom : pair.getMessage()){
                                System.out.println("*************************************************************************************\n");
                                System.out.println("*                                Accommodation: " + iterRoom.getRoomName()  + "                              *\n");
                                System.out.println(iterRoom);
                            }

                        }

                        System.out.println("*************************************************************************************\n");
                        break;
                    
                    case 2:
                        // krathsh katalymatos
                        System.out.println("*        Select the name of the accommodation and the corresponding date            *\n");
                        System.out.print("--> Name: ");
                        String name = scanner.nextLine();
                        name = scanner.nextLine();
                        System.out.print("--> Date (dd/mm/yyyy - dd/mm/yyyy): ");
                        String reserveDate = scanner.nextLine();
                        System.out.println();

                        System.out.println("*        Enter the name of the person you want to make the reservation              *\n");
                        System.out.print("--> Person's name: ");
                        String reservationName = scanner.nextLine();
                        System.out.println();
                        System.out.println("*************************************************************************************\n");
                        

                        service = "book";
                        
                        out.writeObject(service);
                        out.flush();

                        out.writeObject(name);
                        out.flush();

                        out.writeObject(reserveDate);
                        out.flush();

                        out.writeObject(reservationName);
                        out.flush();

                        Boolean result = (Boolean) in.readObject(); // true an egine h krathsh, false an den egine
                        if(result){
                            System.out.println();
                            System.out.println("*************************************************************************************\n");
                            System.out.println("*                The booking of the accommodation was a success!                    *\n");

                            System.out.println("*                            Accommodation information                              *\n");
                            System.out.println("Name of accommondation  " + " | " + name);
                            System.out.println("Range                   " + " | " +  reserveDate);
                            System.out.println("Person's name           " + " | " +  reservationName);
                            System.out.println();
                            System.out.println("*************************************************************************************\n");


                        }
                        else{
                            System.out.println("*                        Failure to book accommodation                              *\n");
                            System.out.println("*************************************************************************************\n");
                        }
    
                        break;

                    case 3: 
                        // bathmologhsh katalymatos
                        double rate = 0;
                        System.out.println("*      Select the name of the accommodation and the corresponding star rating       *\n");
                        System.out.print("--> Name: ");
                        String rateName = scanner.nextLine();
                        rateName = scanner.nextLine();
                        System.out.print("--> Stars (1-5): ");
                        try{
                            rate = scanner.nextDouble();
                        } catch(InputMismatchException e){
                            System.out.println("*          You gave the wrong value to the star field! Try again!                  *");
                            System.exit(0);
                        }

                        System.out.println();
                        System.out.println("*************************************************************************************\n");

                        service = "rate";

                        out.writeObject(service);
                        out.flush();

                        out.writeObject(rateName);
                        out.flush();

                        out.writeObject(rate);
                        out.flush();

                        String rateResponse = (String) in.readObject(); 
                        System.out.println(rateResponse); 
                        System.out.println();

                        break;
                    default:
                        // lathos input
                        System.out.println("*                           Wrong entrance! Try again!                              *\n");
                        System.out.println("*************************************************************************************\n");
                        System.out.print("               _          __________        _          _ _       _                 \n");
                        System.out.print("              / \\        |                 / \\          |       | \\      |        \n");
                        System.out.print("             /   \\       |                /   \\         |       |  \\     |        \n");
                        System.out.print("            /     \\      |               /     \\        |       |   \\    |        \n");
                        System.out.print("           /" + "_______" + "\\     |   ______     /_______\\       |       |    \\   |        \n");
                        System.out.print("          /         \\    |         |   /         \\      |       |     \\  |        \n");
                        System.out.print("         /           \\   |         |  /           \\     |       |      \\ |        \n");
                        System.out.print("        /             \\   \\________| /             \\   _|_      |       \\|        \n");
                        //System.out.println();
                        //System.out.println("*************************************************************************************\n");

                        break;
                }
            }catch (UnknownHostException unknownHost) {
                System.err.println("                    *You are trying to connect to an unknown host!                  *\n");
                System.out.println("*************************************************************************************\n");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
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
