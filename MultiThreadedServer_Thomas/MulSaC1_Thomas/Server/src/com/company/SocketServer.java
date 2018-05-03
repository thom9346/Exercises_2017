package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    int portNumber = 44444;
    ServerSocket serverSocket = null;

    public void runServer(){

        try {

            serverSocket = new ServerSocket(portNumber);

        }catch (IOException e) {
            System.out.println("Catch from runserver: "+ e.getMessage());
        }

        //infite loop(it's always gonna look for connections)
        while (true){

            try{

                Socket clientSocket = serverSocket.accept(); //accepts clients connection
                Server server = new Server(clientSocket);

                new Thread(server).start(); //makes a new thread of the mortage object.
                //.start() comes from the Thread library


            }catch (IOException e){
                System.out.println("From true loop in server: " + e.getMessage());

            }
        }

    }
}


