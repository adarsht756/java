package Interfaces;

import Model.Transaction;

public interface PaymentProcessor {
    public boolean processPayment(Transaction transaction);
}