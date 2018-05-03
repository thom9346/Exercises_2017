package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {

    public static void main(String[] args) {

        String hostName = "127.0.0.1"; //localhost
        int portNumber = 44444;
        Socket clientSocket;
        PrintWriter out;
        BufferedReader in;
        InputStreamReader inputStreamReader;

        //added later:
        BufferedReader stdIn; //read information in from the console window

        try{

            System.out.println("Client program");
            clientSocket = new Socket(hostName,portNumber);
            // Create out IO streams
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
            in = new BufferedReader(inputStreamReader);
            stdIn = new BufferedReader(new InputStreamReader(System.in)); //other kind of scanner

            System.out.println("Enter either 'VIP' for VIP tickets or 'STANDARD' for standard tickets ");
            out.println(stdIn.readLine()); //out.println SENDS something to the server


            System.out.println("Server says: " + in.readLine()); //reads the first "out.println" from the server program
            System.out.println("Server says: " + in.readLine()); //second line
            System.out.println("Server says: " + in.readLine()); // 3
            System.out.println("Server says: " + in.readLine()); // 4th

        }catch (UnknownHostException e) {
            System.exit(1); //exits the program if unknown host(something goes wrong)

        }catch (IOException e) {

            System.out.println(e.getMessage());

        }//end try catch

    } // end main
}
