package com.company;

public class VIPTicket implements Tickets {

    private static int avilableTickets =10; //static binds that variable not to a class instance, but to the class itself. If it wasn't static it'd return 10 each time a new object is created in main.


    @Override
    public String type() {
        return "VIP";
    }

    @Override
    public int price() {
        return 800;
    }

    @Override
    public int availableTickets() {
        return avilableTickets;
    }


    public int getAvilableTickets() {
        return avilableTickets;
    }

    public void setAvilableTickets(int avilableTickets) {
        this.avilableTickets = avilableTickets;
    }
}
