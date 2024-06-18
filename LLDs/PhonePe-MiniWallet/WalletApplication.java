import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import Models.PaymentInstrumentValidator;
import Models.Transaction;
import Models.User;
import Models.Wallet;
import Models.Offers.EqualBalanceOffer;
import Models.Offers.MinAmountOffer;
import Models.Offers.Offer;
import Utilities.GenericComparator;
import Utilities.Utility;
import Utilities.Filter.Filter;
import Utilities.Filter.FilterType;
import Utilities.Filter.GenericFilter;

public class WalletApplication {
    private HashMap<String, User> users;
    private HashMap<String, Wallet> wallets;
    private HashMap<String, List<Transaction>> userTransactions;
    private List<Offer> offers;
    private Utility utility;

    private PaymentInstrumentValidator paymentInstrumentValidator;

    public WalletApplication() {
        this.users = new HashMap<String, User>();
        this.wallets = new HashMap<String, Wallet>();
        this.paymentInstrumentValidator = new PaymentInstrumentValidator();
        this.userTransactions = new HashMap<String, List<Transaction>>();
        this.utility = new Utility();

        this.offers = new ArrayList<Offer>();
        this.offers.add(new MinAmountOffer("offerMin100", 100, 1, 50, 10, false));
        this.offers.add(new EqualBalanceOffer("offerEqual", 50, 20, true));
    }

    public void registerUser(User user) {
        if (users.containsKey(user.getUserId())) {
            System.out.println("User ID: " + user.getUserId() + " already exists, please choose another one");
            return;
        }
        users.put(user.getUserId(), user);
        String walletId = user.getUserId() + "-" + user.getUsername();
        Wallet newWallet = new Wallet(walletId, user.getUserId());
        wallets.put(walletId, newWallet);
        System.out.println("Wallet created successfully for user " + user.getUsername());
    }

    public double fetchBalance(String userID) {
        if (utility.userDoNotExist(userID, users)) {
            utility.userDonotExistErrMsg(userID);
            return 0;
        }
        String walletId = utility.buildWalletId(users.get(userID));
        return wallets.get(walletId).getBalance();
    }

    private Transaction getUpdatedTransactionAfterApplyingOffer(User sender,
            Transaction transaction) {
        for (Offer offer : offers) {
            if (sender.getOfferIdsUsed().contains(offer.offerID) || offer.getIsPostTxnOffer())
                continue;
            if (offer instanceof MinAmountOffer) {
                MinAmountOffer myOffer = (MinAmountOffer) offer;
                double totalAmount = transaction.getTotalAmount();
                if (totalAmount >= myOffer.getMinAmountRequired()) {
                    double offerAmount = myOffer.getOfferAmount(totalAmount);
                    double txnAmountWithOfferAdjusted = myOffer.getOfferAdjustedAmount(totalAmount, offerAmount);
                    transaction.updateOfferAmount(offerAmount);
                    transaction.updateTransactionAmount(txnAmountWithOfferAdjusted);
                    sender.addUsedOfferID(offer.offerID);
                    users.put(sender.getUserId(), sender);
                }
            }
        }
        return transaction;
    }

    private void applyPostTransactionOffers(String payerID, String payeeID, Transaction senderTransaction,
            Transaction receiverTransaction) {
        for (Offer offer : offers) {
            if (offer.isPostTxnOffer) {
                if (offer instanceof EqualBalanceOffer) {
                    EqualBalanceOffer myOffer = (EqualBalanceOffer) offer;
                    String senderWalletlId = utility.buildWalletId(users.get(payerID)),
                            receieverWalletId = utility.buildWalletId(users.get(payeeID));

                    if (wallets.get(senderWalletlId).getBalance() == wallets.get(receieverWalletId).getBalance()) {
                        double offerAmount = myOffer.getOfferAmount(senderTransaction.getTransactionAmount());
                        double txnAmountWithOfferAdjusted = myOffer.getOfferAdjustedAmount(offerAmount,
                                senderTransaction.getTransactionAmount());

                        Transaction transaction = new Transaction.TransactionBuilder().setSenderId(payerID)
                                .setReceiverId(payerID)
                                .setTotalAmount(txnAmountWithOfferAdjusted)
                                .setTransactionAmount(txnAmountWithOfferAdjusted).setOfferAmount(0)
                                .setPaymentMethod("SELF")
                                .setPaymentMethodType("OFFER").setTimestamp(utility.getCurrentTimestamp())
                                .build();
                        addUserTransactionAndUpdateWallet(payerID, transaction, false);
                    }
                }
            }
        }
    }

    private void addUserTransactionAndUpdateWallet(String userID, Transaction transaction, boolean isSender) {
        List<Transaction> transactions = userTransactions.get(userID);
        if (transactions == null) {
            transactions = new ArrayList<>();
        }
        if (isSender)
            transaction.updateTransactionAmount(-1 * transaction.getTransactionAmount());
        transactions.add(transaction);
        userTransactions.put(userID, transactions);
        updateUserWallet(userID, transaction.getTransactionAmount());
    }

    public void updateUserWallet(String userID, double amount) {
        String walletId = utility.buildWalletId(users.get(userID));
        Wallet wallet = wallets.get(walletId);

        wallet.updateBalance(amount);
        wallets.put(walletId, wallet);
        System.out.println("UserID: " + userID + " updated balance is: " + wallet.getBalance());
    }

    public void topUpWallet(String userID, double amount, String paymentMethod, String paymentMethodType,
            String pmValue) {
        if (utility.userDoNotExist(userID, users)) {
            utility.userDonotExistErrMsg(userID);
            return;
        }
        if (amount <= 0) {
            utility.invalidAmountErr();
            return;
        }
        if (paymentInstrumentValidator.paymentInstrumentValidator(paymentMethod, paymentMethodType, pmValue) == false) {
            System.out.println("Top up failed. User entered invalid card number or invalid upi id as: " + pmValue);
            return;
        }

        Transaction transaction = new Transaction.TransactionBuilder().setSenderId(userID).setReceiverId(userID)
                .setTotalAmount(amount).setTransactionAmount(amount).setOfferAmount(0).setPaymentMethod(paymentMethod)
                .setPaymentMethodType(paymentMethodType).setTimestamp(utility.getCurrentTimestamp())
                .build();

        addUserTransactionAndUpdateWallet(userID, transaction, false);
        System.out.println("User " + userID + " wallet top up successful\n");
    }

    public void sendMoney(String payerID, String payeeID, double amount) {
        if (!utility.basicTransactionValidation(payerID, payeeID, amount, users))
            return;

        User sender = users.get(payerID);
        if (wallets.get(utility.buildWalletId(sender)).getBalance() >= amount) {
            Transaction senderTransaction = new Transaction.TransactionBuilder().setSenderId(payerID)
                    .setReceiverId(payeeID)
                    .setTotalAmount(amount).setTransactionAmount(amount).setOfferAmount(0).setPaymentMethod("WALLET")
                    .setPaymentMethodType("PHONEPE").setTimestamp(utility.getCurrentTimestamp())
                    .build();

            Transaction receiverTransaction = new Transaction.TransactionBuilder().setSenderId(payerID)
                    .setReceiverId(payeeID)
                    .setTotalAmount(amount).setTransactionAmount(amount).setOfferAmount(0).setPaymentMethod("WALLET")
                    .setPaymentMethodType("PHONEPE").setTimestamp(utility.getCurrentTimestamp())
                    .build();

            Transaction senderTransactionAfterApplyingOffer = getUpdatedTransactionAfterApplyingOffer(
                    sender, senderTransaction);
            addUserTransactionAndUpdateWallet(payerID, senderTransactionAfterApplyingOffer, true);
            addUserTransactionAndUpdateWallet(payeeID, receiverTransaction, false);
            applyPostTransactionOffers(payerID, payeeID, senderTransactionAfterApplyingOffer, receiverTransaction);
            System.out
                    .println(sender.getUsername() + " sent "
                            + -1 * senderTransactionAfterApplyingOffer.getTransactionAmount()
                            + " Rs to " + users.get(payeeID).getUsername());
        }
    }

    public void getAllTransactions(String userID) {
        if (utility.userDoNotExist(userID, users)) {
            utility.userDonotExistErrMsg(userID);
            return;
        }

        if (!userTransactions.containsKey(userID)) {
            System.out.println("User do not have any transactions");
            return;
        }
        System.out.println(users.get(userID).getUsername() + " have following transactions:\n");

        List<Transaction> transactions = userTransactions.get(userID);
        for (Transaction txn : transactions)
            System.out.println(txn + "\n---------------------------------------------------------------------");
    }

    public void getAllTransactionsWithSort(String userID, String fieldNameToSort) {
        if (utility.userDoNotExist(userID, users)) {
            utility.userDonotExistErrMsg(userID);
            return;
        }

        if (!userTransactions.containsKey(userID)) {
            System.out.println("User do not have any transactions");
            return;
        }
        System.out.println(users.get(userID).getUsername() + " have following transactions:\n");
        List<Transaction> transactions = userTransactions.get(userID);
        Collections.sort(transactions, new GenericComparator<>(fieldNameToSort));
        for (Transaction txn : transactions)
            System.out.println(txn + "\n---------------------------------------------------------------------");
    }

    public void getAllTransactionsWithFilterAndSort(String userID, List<Filter> filters, String fieldNameToSort) {
        if (utility.userDoNotExist(userID, users)) {
            utility.userDonotExistErrMsg(userID);
            return;
        }

        if (!userTransactions.containsKey(userID)) {
            System.out.println("User do not have any transactions");
            return;
        }

        List<Transaction> transactions = userTransactions.get(userID);
        for (Filter filter : filters) {
            String predicateVal = filter.predicate;
            List<FilterType> filterTypes = new ArrayList<FilterType>();
            if (predicateVal.charAt(0) == '>')
                filterTypes.add(FilterType.GREATER_THAN);
            if (predicateVal.charAt(0) == '<')
                filterTypes.add(FilterType.LESS_THAN);
            if (predicateVal.charAt(0) == '=' || (predicateVal.length() == 2 && predicateVal.charAt(1) == '='))
                filterTypes.add(FilterType.EQUAL_TO);
            GenericFilter<Transaction> predicate = new GenericFilter<>(filter.name, filterTypes,
                    filter.value);
            transactions = predicate.filter(transactions);
        }
        if (!fieldNameToSort.isEmpty())
            Collections.sort(transactions, new GenericComparator<>(fieldNameToSort));

        if (transactions.isEmpty()) {
            System.out.println("User " + users.get(userID).getUsername() + " have no transaction with applied filters");
            return;
        }
        System.out.print(users.get(userID).getUsername() + " have following transactions where ");

        for (Filter filter : filters) {
            System.out.print(filter.name + " " + filter.predicate + " " + filter.value + ", ");
        }
        System.out.println();
        for (Transaction txn : transactions)
            System.out.println(txn + "\n---------------------------------------------------------------------");
    }
}