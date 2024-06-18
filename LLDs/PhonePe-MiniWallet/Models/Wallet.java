package Models;

public class Wallet {
    private String walletId;
    public String userId;
    private double balance;

    public Wallet(String walletId, String userId) {
        this.walletId = walletId;
        this.userId = userId;
        this.balance = 0;
    }

    public String getWalletId() {
        return this.walletId;
    }

    public double updateBalance(double balanceChange) {
        this.balance += balanceChange;
        return this.balance;
    }

    public double getBalance() {
        return this.balance;
    }
}