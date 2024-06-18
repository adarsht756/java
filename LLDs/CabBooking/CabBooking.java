import Model.Location;

public class CabBooking {
    static BookingService bookingService;
    public static void main(String[] args) {
        bookingService = new BookingService();
        bookingService.addUser("Abhishek", "12321312", "M", 23, new Location(9, 0));
        bookingService.addUser("Rahul", "123213122", "F", 25, new Location(10, 0));
        bookingService.addUser("Nandini", "123213123", "M", 25, new Location(15, 6));

        bookingService.addDriver("Driver1", "M", 22, "Swift", "UK-23-2BSD3", new Location(10, 1));
        bookingService.addDriver("Driver2", "M", 29, "Swift", "UK-23-2DSD3", new Location(11, 10));
        bookingService.addDriver("Driver3", "M", 29, "Swift", "UK-23-2DSD4", new Location(5, 3));

        bookingService.requestRide("Abhishek", new Location(9, 0), new Location(10, 1));
        System.out.println();
        bookingService.requestRide("Rahul", new Location(10, 0), new Location(15, 3));
        System.out.println();
        bookingService.toggleDriverState("Driver1");
        bookingService.requestRide("Nandini", new Location(15, 5), new Location(20, 4));
        System.out.println();
        bookingService.printDriverEarnings();
    }


}