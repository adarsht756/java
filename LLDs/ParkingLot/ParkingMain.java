import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Model.Address;
import Model.ParkingSlotType;
import Model.Vehicle;
import Model.VehicleCategory;

public class ParkingMain {
    public static void main (String args[]) throws InterruptedException {
        String nameOfParkingLot = "Adarsh parking wale";
        Address address = new Address().createAndGetAddress("VGS Layout", "Ejipura", "Bengaluru", "Karnataka", "India");
        HashMap<ParkingSlotType, HashMap<String, ParkingSlot>> allSlots = new HashMap<>();
        HashMap<String, ParkingSlot> compactSlot = new HashMap<>();
        compactSlot.put("C1", new ParkingSlot("C1", ParkingSlotType.Compact));
        compactSlot.put("C2", new ParkingSlot("C2", ParkingSlotType.Compact));
        compactSlot.put("C3", new ParkingSlot("C3", ParkingSlotType.Compact));
        allSlots.put(ParkingSlotType.Compact, compactSlot);

        HashMap<String,ParkingSlot> largeSlot = new HashMap<>();
        largeSlot.put("L1",new ParkingSlot("L1",ParkingSlotType.Large));
        largeSlot.put("L2",new ParkingSlot("L2",ParkingSlotType.Large));
        largeSlot.put("L3",new ParkingSlot("L3",ParkingSlotType.Large));
        allSlots.put(ParkingSlotType.Large,largeSlot);

        ParkingFloor parkingFloor = new ParkingFloor("1", allSlots);
        List<ParkingFloor> parkingFloors = new ArrayList<>();
        parkingFloors.add(parkingFloor);
        ParkingLot parkingLot = ParkingLot.getInstance(nameOfParkingLot, address, parkingFloors);

        Vehicle vehicle = new Vehicle(VehicleCategory.Hatchback, "UK07-90");
        Ticket ticket = parkingLot.assignTicket(vehicle);
        System.out.println(" ticket number: " + vehicle.getVehicleNumber());

        Thread.sleep(10000);
        double price = parkingLot.scanAndPay(ticket);
        System.out.println(" price is: " + price);
    }
}