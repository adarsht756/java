package Model;

import java.util.Random;

import Interfaces.PaymentProcessor;

public class Bank implements PaymentProcessor {
    private String name;

    public Bank(String name) {
        this.name = name;
    }

    public boolean processPayment(Transaction transaction) {
        // Simulate random success or failure
        return new Random().nextBoolean();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
