package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Server implements Runnable {


    protected Socket clientSocket = null;
    private StandardTicket standardTicket = new StandardTicket();
    private VIPTicket vipTicket = new VIPTicket();

    public Server(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    //logic that its gonna perform is gonna go in run()
    @Override
    public void run() {

        try{

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //clientSocket.getInputStream(); --> getting input from client information
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            //if user from client side writes in VIP tickets

            String choosenString = in.readLine();
            if (choosenString.equalsIgnoreCase("VIP")) {

                if (vipTicket.getAvilableTickets() != 0) {

                    out.println("Thank you for buying a " + vipTicket.type() + " ticket.");
                    out.println("The cost for this ticket was " + vipTicket.price() + " kroner");
                    out.println("You have the ticket number: " + vipTicket.getAvilableTickets());

                    int newAmount = vipTicket.getAvilableTickets() -1;
                    vipTicket.setAvilableTickets(newAmount);

                    out.println("There's " + vipTicket.getAvilableTickets() + " " + vipTicket.type() + " tickets left.");

                }
                else
                    out.println("We're sorry but there's no more VIP tickets left!");


            }

            else if (choosenString.equalsIgnoreCase("standard")) {

                if (standardTicket.getNumberOfTickets() != 0) {


                    out.println("Thank you for buying a " + standardTicket.type() + " ticket.");
                    out.println("The cost for this ticket was " + standardTicket.price() + " kroner");
                    out.println("You have the ticket number: " + standardTicket.getNumberOfTickets());

                    int newAmount = standardTicket.getNumberOfTickets() -1;
                    standardTicket.setNumberOfTickets(newAmount);

                    out.println("There's " + standardTicket.getNumberOfTickets() + " "+  standardTicket.type() + " tickets left.");
                }
                else
                    out.println("Sorry, but there's no standard ticket left.");


            }




        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
