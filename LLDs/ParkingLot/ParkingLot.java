import java.util.HashMap;
import java.util.List;

import Model.Address;
import Model.ParkingSlotType;
import Model.Vehicle;

public class ParkingLot {
    private String nameOfParkingLot;
    private Address address;
    private List<ParkingFloor> parkingFloors;
    private static ParkingLot parkingLot = null;

    private ParkingLot(String nameOfParkingLot, Address address, List<ParkingFloor> parkingFloors) {
        this.nameOfParkingLot = nameOfParkingLot;
        this.address = address;
        this.parkingFloors = parkingFloors;
    }

    public static ParkingLot getInstance (String nameOfParkingLot, Address address, List<ParkingFloor> parkingFloors) {
        if (parkingLot == null)
            parkingLot = new ParkingLot(nameOfParkingLot, address, parkingFloors);
        return parkingLot;
    }

    public void addFloor(String name, HashMap<ParkingSlotType, HashMap<String, ParkingSlot>> parkingSlot) {
        ParkingFloor parkingFloor = new ParkingFloor(name, parkingSlot);
        this.parkingFloors.add(parkingFloor);
    }

    public void removeFloor(ParkingFloor parkingFloor) {
        parkingFloors.remove(parkingFloor);
    }

    public Ticket assignTicket(Vehicle vehicle) {
        ParkingSlot parkingSlot = getParkingSlotForVehicleAndPark(vehicle);
        if (parkingSlot == null) return null;
        Ticket parkingTicket = Ticket.createTicket(vehicle, parkingSlot);
        return parkingTicket;
    }

    public double scanAndPay(Ticket ticket){
        long endTime = System.currentTimeMillis();
        ticket.getParkingSlot().removeVehicle(ticket.getVehicle());
        int duration = (int) (endTime-ticket.getStartTime())/1000;
        double price = ticket.getParkingSlot().getParkingSlotType().getPriceForParking(duration);
        //persist record to database
        return price;
    }

    public ParkingSlot getParkingSlotForVehicleAndPark(Vehicle vehicle) {
        ParkingSlot parkingSlot = null;
        for (ParkingFloor floor: parkingFloors) {
            parkingSlot = floor.getRelevantSlotForVehicleAndPark(vehicle);
            if (parkingSlot != null) break;
        }
        return parkingSlot;
    }
}