import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Model.Driver;
import Model.DriverState;
import Model.Location;
import Model.Ride;

public class RidesFinder {
    public List<Ride> findRides(Map<String, Driver> drivers, Location sourceLocation, Location destinationLocation) {
        List<Ride> rides = new ArrayList<Ride>();
        for (Map.Entry<String, Driver> obj: drivers.entrySet()) {
            Driver driver = obj.getValue();
            if (driver.getDriverState() == DriverState.AVAILABLE) {
                if (distanceCalculator(driver.getLocation(), sourceLocation) <=  5) {
                    // Driver will goto User's location
                    Ride ride = new Ride(driver.getLocation(), sourceLocation, driver);
                    // Driver will goto User's location to destination location
                    ride.farePrice += ride.farePriceCalculator(sourceLocation, destinationLocation);
                    rides.add(ride);
                }
            }
        }
        return rides;
    }

    private Double distanceCalculator(Location sourceLocation, Location destinationLocation) {
        return
            Math.sqrt(Math.pow(destinationLocation.x - sourceLocation.x, 2) + Math.pow(destinationLocation.y - sourceLocation.y, 2));
    }
}