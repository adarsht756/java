import java.util.List;

import Models.User;
import Utilities.Filter.Filter;

/**
 * Driver
 */
public class Driver {

    public static void main(String[] args) {
        WalletApplication application = new WalletApplication();
        application.registerUser(new User.UserBuilder().setUserId("adarsht756")
                .setUsername("Adarsh Tiwari")
                .setPhoneNumber("9760851121").build());

        application.topUpWallet("adarsh", 1000, "UPI", "COLLECT", "adarsht756-1@oksbi");
        application.topUpWallet("adarsht756", 1000, "UPI", "COLLECT", "adarsht756i");
        application.topUpWallet("adarsht756", 1000, "UPI", "COLLECT", "adarsht756-1@oksbi");

        application.getAllTransactions("adarsht756");

        application.registerUser(new User.UserBuilder().setUserId("rishabh123")
                .setUsername("Risabh Mathur")
                .setPhoneNumber("9760851123").build());

        application.registerUser(new User.UserBuilder().setUserId("ayush123")
                .setUsername("Ayush Garg")
                .setPhoneNumber("9760851123").build());

        application.sendMoney("rishabh123", "adarsht756", 100);
        application.topUpWallet("rishabh123", 1000, "CARD", "VISA", "5419190300584227");
        application.sendMoney("rishabh123", "adarsht756", 100);
        application.sendMoney("rishabh123", "adarsht756", 45);

        application.getAllTransactionsWithSort("adarsht756", "timestamp");
        application.getAllTransactionsWithSort("rishabh123", "timestamp");

        application.topUpWallet("rishabh123", (double) 2000, "CARD", "VISA", "5419190300584227");
        application.getAllTransactions("adarsht756");
        application.getAllTransactionsWithFilterAndSort("rishabh123",
                List.of(new Filter("transactionAmount", (double) 2000, "<"),
                        new Filter("transactionAmount", (double) 90, ">")),
                "");

        application.sendMoney("rishabh123", "ayush123", 45);
        application.sendMoney("rishabh123", "ayush123", 600);

        application.getAllTransactionsWithFilterAndSort("rishabh123",
                List.of(new Filter("transactionAmount", (double) 2000, "<="),
                        new Filter("receiverId", "ayush123", "==")),
                "timestamp");
    }
}