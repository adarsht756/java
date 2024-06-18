import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import Model.Driver;
import Model.DriverState;
import Model.Gender;
import Model.Location;
import Model.Ride;
import Model.User;

public class BookingService {
    private Map<String, User> userStore;
    private Map<String, Driver> driverStore;
    private RidesFinder ridesFinder;
    Scanner sc;

    public BookingService() {
        this.userStore = new HashMap<String, User>();
        this.driverStore = new HashMap<String, Driver>();
        this.ridesFinder = new RidesFinder();
        sc = new Scanner(System.in);
    }

    public void addDriver(String driverName, String gender, int age, String carName, String carNumber,
            Location location) {
        Driver driver = new Driver();
        driver.setDriverName(driverName);
        driver.setGender(gender == "M" ? Gender.MALE : Gender.FEMALE);
        driver.setAge(age);
        driver.setNumberPlate(carNumber);
        driver.setCarName(carName);
        driver.setLocation(location);
        driver.setDriverState(DriverState.AVAILABLE);
        this.driverStore.put(driverName, driver);
    }

    public void addUser(String username, String phoneNumber, String gender, int age, Location location) {
        User user = new User();
        user.setGender(gender == "M" ? Gender.MALE : Gender.FEMALE);
        user.setUserName(username);
        user.setPhoneNumber(phoneNumber);
        user.setLocation(location);
        user.setAge(age);
        this.userStore.put(username, user);
    }

    public void updateUser(String username, String phoneNumber) {
        if (this.userStore.containsKey(username)) {
            User user = this.userStore.get(username);
            user.setPhoneNumber(phoneNumber);
            this.userStore.put(username, user);
        } else {
            System.out.println("Invalid username: " + username + " , couldn't update");
        }
    }

    public void updateUserLocation(String username, Location location) {
        if (this.userStore.containsKey(username)) {
            User user = this.userStore.get(username);
            user.setLocation(location);
            this.userStore.put(username, user);
            System.out.println("User: " + username + " updated location is: " + location.x + " : " + location.y);
        } else {
            System.out.println("Invalid username: " + username + " , couldn't update location");
        }
    }

    public void updateDriverLocationAndEarnings(String driverName, Location location, Double fareEarnings) {
        if (this.driverStore.containsKey(driverName)) {
            Driver driver = this.driverStore.get(driverName);
            driver.setLocation(location);
            driver.setDriverEarnings(driver.getDriversEarnings() + fareEarnings);
            driver.setDriverState(DriverState.UNAVAILABLE);
            this.driverStore.put(driverName, driver);
            System.out.println("Driver: " + driverName + " updated location is: " + location.x + " : " + location.y);
        } else {
            System.out.println("Invalid driver name: " + driverName + " , couldn't update location");
        }
    }

    public List<Ride> findRides(Location sourceLocation, Location destinationLocation) {
        return ridesFinder.findRides(driverStore, sourceLocation, destinationLocation);
    }

    public void chooseRide(List<Ride> rides, String username, String driverName) {
        System.out.println("Ride started");
        Ride ride;
        for (Ride r : rides) {
            if (r.driver.getDriverName() == driverName) {
                ride = r;
                break;
            }
        }
        ride = rides.get(0); // default driver

        updateUserLocation(username, ride.destinationLocation);
        updateDriverLocationAndEarnings(driverName, ride.destinationLocation, ride.farePrice);

        System.out.println("Ride ended bill amount " + ride.farePrice + "$");

        toggleDriverState(driverName);
    }

    public void toggleDriverState(String driverName) {
        Driver driver = this.driverStore.get(driverName);
        if (driver.getDriverState() == DriverState.AVAILABLE) {
            driver.setDriverState(DriverState.UNAVAILABLE);
            System.out.println("Driver: " + driverName + " is unavailable");
        } else {
            driver.setDriverState(DriverState.AVAILABLE);
            System.out.println("Driver: " + driverName + " is available");
        }
        this.driverStore.put(driverName, driver);
    }

    public void requestRide(String username, Location sourceLocation, Location destinationLocation) {
        List<Ride> availableRides = findRides(sourceLocation, destinationLocation);
        if (availableRides.size() == 0) {
            System.out.println("No ride found [Since all the driver are more than 5 units away from user]");
            return;
        }
        for (int i = 0; i < availableRides.size(); ++i) {
            System.out.println("Press " + i + " to select driver " + availableRides.get(i).driver.getDriverName());
        }

        int choice = sc.nextInt();
        chooseRide(availableRides, username, availableRides.get(choice).driver.getDriverName());
    }

    public void printDriverEarnings() {
        for (Map.Entry<String, Driver> entry: this.driverStore.entrySet()) {
            Driver driver = entry.getValue();
            System.out.println(driver.getDriverName() + " earned $" + driver.getDriversEarnings());
        }
        sc.close();
    }
}