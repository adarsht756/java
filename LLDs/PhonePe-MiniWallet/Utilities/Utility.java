package Utilities;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import Models.User;

/**
 * Utility
 */
public class Utility {

    public void userDonotExistErrMsg(String userID) {
        System.out.println("User with ID: " + userID + " doesn't exist, please pass valid user id");
    }

    public void invalidAmountErr() {
        System.out.println("Please enter valid amount");
    }

    public boolean userDoNotExist(String userID, HashMap<String, User> users) {
        return !users.containsKey(userID);
    }

    public String getCurrentTimestamp() {
        return new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
    }

    public String buildWalletId(User user) {
        return user.getUserId() + "-" + user.getUsername();
    }

    public boolean basicTransactionValidation(String senderID, String receiverID, double amount,
            HashMap<String, User> users) {
        if (userDoNotExist(senderID, users)) {
            userDonotExistErrMsg(senderID);
            return false;
        }
        if (userDoNotExist(receiverID, users)) {
            userDonotExistErrMsg(receiverID);
            return false;
        }
        if (amount <= 0) {
            invalidAmountErr();
            return false;
        }
        return true;
    }
}