import Model.Vehicle;

public class Ticket {
    String ticketNumber;
    long startTime;
    long endTime;
    Vehicle vehicle;
    ParkingSlot parkingSlot;

    public static Ticket createTicket (Vehicle vehicle, ParkingSlot parkingSlot) {
        Ticket ticket = new Ticket();
        ticket.vehicle = vehicle;
        ticket.parkingSlot = parkingSlot;
        ticket.startTime = System.currentTimeMillis();
        ticket.ticketNumber = vehicle.getVehicleNumber() + System.currentTimeMillis();
        return ticket;
    }

    public ParkingSlot getParkingSlot() {
        return this.parkingSlot;
    }

    public Vehicle getVehicle() {
        return this.vehicle;
    }

    public long getStartTime() {
        return this.startTime;
    }
}