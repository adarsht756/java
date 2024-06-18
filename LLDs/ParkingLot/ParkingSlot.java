import Model.ParkingSlotType;
import Model.Vehicle;

public class ParkingSlot {
    String name;
    Vehicle vehicle;
    boolean isAvailable = true;
    ParkingSlotType parkingSlotType;

    public ParkingSlot (String name, ParkingSlotType parkingSlotType) {
        this.name = name;
        this.parkingSlotType = parkingSlotType;
    }

    void addVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.isAvailable = false;
    }

    void removeVehicle(Vehicle vehicle) {
        this.vehicle = null;
        this.isAvailable = true;
    }

    public ParkingSlotType getParkingSlotType() {
        return this.parkingSlotType;
    }
}