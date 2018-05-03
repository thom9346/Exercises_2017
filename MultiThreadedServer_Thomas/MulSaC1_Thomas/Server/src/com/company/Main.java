package com.company;

public class Main {

    public static void main(String[] args) {


        System.out.println("Server is running...");

        SocketServer s = new SocketServer();
        s.runServer();
    }
}
