package Model;

public class Vehicle {
    VehicleCategory vehicleCategory;
    String vehicleNumber;

    public Vehicle(VehicleCategory vehicleCategory, String vehicleNumber) {
        this.vehicleCategory = vehicleCategory;
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleNumber() {
        return this.vehicleNumber;
    }

    public VehicleCategory getVehicleCategory() {
        return this.vehicleCategory;
    }
}