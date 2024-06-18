import java.util.HashMap;
import java.util.Map;

import Model.ParkingSlotType;
import Model.Vehicle;
import Model.VehicleCategory;

public class ParkingFloor {
    String name;
    HashMap<ParkingSlotType, HashMap<String, ParkingSlot>> parkingSlots;

    public ParkingFloor(String name, HashMap<ParkingSlotType, HashMap<String, ParkingSlot>> parkingSlots) {
        this.name = name;
        this.parkingSlots = parkingSlots;
    }

    public ParkingSlot getRelevantSlotForVehicleAndPark(Vehicle vehicle) {
        VehicleCategory vehicleCategory = vehicle.getVehicleCategory();
        ParkingSlotType parkingSlotType = pickCorrectSlot(vehicleCategory);
        HashMap<String, ParkingSlot> relevantParkingSlot = parkingSlots.get(parkingSlotType);
        ParkingSlot slot = null;
        for (Map.Entry<String, ParkingSlot> m: relevantParkingSlot.entrySet()) {
            if (m.getValue().isAvailable) {
                slot = m.getValue();
                slot.addVehicle(vehicle);
                break;
            }
        }
        return slot;
    }

    public ParkingSlotType pickCorrectSlot(VehicleCategory vehicleCategory) {
        if (vehicleCategory == VehicleCategory.TwoWheeler) return ParkingSlotType.TwoWheeler;
        else if(vehicleCategory.equals(VehicleCategory.Hatchback) || vehicleCategory.equals(VehicleCategory.Sedan)) return ParkingSlotType.Compact;
        else if(vehicleCategory.equals(VehicleCategory.SUV)) return ParkingSlotType.Medium;
        else if(vehicleCategory.equals(VehicleCategory.Bus)) return ParkingSlotType.Large;

        return null;
    }
}