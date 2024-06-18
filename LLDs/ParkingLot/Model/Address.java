package Model;

public class Address {
    String street;
    String block;
    String city;
    String state;
    String country;

    public static Address createAndGetAddress(String street, String block, String city, String state, String country) {
        Address address = new Address();
        address.street = street;
        address.block = block;
        address.city = city;
        address.state = state;
        address.country = country;
        return address;
    }
}