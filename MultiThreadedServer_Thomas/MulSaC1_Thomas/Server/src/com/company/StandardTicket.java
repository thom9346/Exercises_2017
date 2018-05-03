package com.company;

public class StandardTicket implements Tickets {

    private static int numberOfTickets = 50;

    @Override
    public String type() {
        return "Standard";
    }

    @Override
    public int price() {
        return 300;
    }

    @Override
    public int availableTickets() {
        return numberOfTickets;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        StandardTicket.numberOfTickets = numberOfTickets;
    }
}
