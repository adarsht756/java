package Model;
public class Ride {
    public Driver driver;
    public User passenger;
    public Location sourceLocation;
    public Location destinationLocation;
    public Double farePrice;

    public Ride(Location sourceLocation, Location destinationLocation, Driver driver) {
        this.sourceLocation = sourceLocation;
        this.destinationLocation = destinationLocation;
        this.driver = driver;
        this.farePrice = farePriceCalculator(sourceLocation, destinationLocation);
    }

    public Double farePriceCalculator(Location sourceLocation, Location destinationLocation) {
        return Math.sqrt(Math.pow(destinationLocation.x - sourceLocation.x, 2) + Math.pow(destinationLocation.y - sourceLocation.y, 2));
    }
}