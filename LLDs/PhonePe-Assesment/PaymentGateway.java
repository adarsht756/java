import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Model.Bank;
import Model.Pair;
import Model.PaymentInstrument;
import Model.Transaction;
import Services.MerchantServiceImpl;
import Services.PaymentMethodManagerImpl;
import Services.Router;

/**
 * PaymentGateway
 */
public class PaymentGateway {
    private PaymentMethodManagerImpl paymentMethodManager;
    private MerchantServiceImpl merchantService;
    private Router routerService;

    public String getCurrentTimestamp() {
        return new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
    }

    public PaymentGateway() {
        this.paymentMethodManager = new PaymentMethodManagerImpl();
        this.merchantService = new MerchantServiceImpl(paymentMethodManager);
        this.routerService = new Router();
    }

    public boolean addClient(String merchantId, String merchantName, String merchantEmail) {
        return this.merchantService.addMerchant(merchantId, merchantName, merchantEmail, getCurrentTimestamp());
    }

    public boolean removeClient(String merchantID) {
        return this.merchantService.removeMerchant(merchantID);
    }

    public boolean hasClient(String merchantId) {
        return this.merchantService.hasMerchant(merchantId);
    }

    public void listSupportedPayModes() {
        HashMap<String, PaymentInstrument> globalPaymentMethods = this.paymentMethodManager
                .listSupportedPaymentMethods();
        printPaymentMethods(globalPaymentMethods);
    }

    public void listMerchantSupportedPayModes(String merchantId) {
        if (this.merchantService.hasMerchant(merchantId) == false) {
            System.out.println("No merchant registerd with id: " + merchantId);
            return;
        }
        HashMap<String, PaymentInstrument> globalPaymentMethods = this.paymentMethodManager
                .listMerchantSupportedPaymentMethods(merchantId);
        printPaymentMethods(globalPaymentMethods);
    }

    private void printPaymentMethods(HashMap<String, PaymentInstrument> paymentMethods) {
        if (paymentMethods == null || paymentMethods.isEmpty()) {
            System.out.println("No payment methods are configured");
            return;
        }
        System.out.println("Following are the payment methods supported:");
        for (Map.Entry<String, PaymentInstrument> entry : paymentMethods.entrySet()) {
            String paymentMethodId = entry.getKey();
            PaymentInstrument pm = entry.getValue();
            System.out.println("Payment method id: " + paymentMethodId + ", payment method: " + pm.getPaymentMethod()
                    + ", payment method type: " + pm.getPaymentMethodType());
        }
        System.out.println();
    }

    public void removePaymode(String paymentMethod, String paymentMethodType) {
        this.paymentMethodManager.removeGlobalPaymentMethod(paymentMethod, paymentMethodType);
    }

    public void removeMerchantPaymode(String merchantId, String paymentMethod, String paymentMethodType) {
        if (this.merchantService.hasMerchant(merchantId) == false) {
            System.out.println("Merchant do not exists with merchant id: " + merchantId);
            return;
        }
        this.paymentMethodManager.removeMerchantPaymentMethod(merchantId, paymentMethod, paymentMethodType);
    }

    public void addMerchantPaymode(String merchantId, String paymentMethodId) {
        if (this.merchantService.hasMerchant(merchantId) == false) {
            System.out.println("Merchant do not exists with merchant id: '" + merchantId + "'");
            return;
        }
        this.paymentMethodManager.addMerchantPaymentMethod(merchantId, paymentMethodId);
    }

    public void showDistribution() {
        Map<Bank, Integer> distribution = this.routerService.getDistribution();
        if (distribution == null || distribution.isEmpty()) {
            System.out.println("No banks are conifgured, please reach out Admin to configure.");
            return;
        }
        System.out.println("Below are the banks with their active traffic distribution" + distribution);
    }

    public PaymentInstrument getPaymentInstrument(String paymentMethod, String paymentMethodType) {
        return this.paymentMethodManager.getPaymentInstrument(paymentMethod, paymentMethodType);
    }

    public void makePayment(String merchantId, String transactionId, double transactionAmount,
            PaymentInstrument paymentInstrument, List<Pair<String, String>> inputFields) {

        String paymentMethodType = paymentInstrument.getPaymentMethodType(),
                paymentMethod = paymentInstrument.getPaymentMethod();

        if (basicTransactionCheck(merchantId, paymentMethod,
                paymentMethodType) == false) {
            return;
        }

        if (paymentInstrument.validator(inputFields) == false) {
            System.out.println("Input fields validation failed");
            return;
        }
        List<String> invalidValuesIfAny = this.paymentMethodManager.validatePaymentInstrumentValue(inputFields);
        if (invalidValuesIfAny.isEmpty() == false) {
            System.out.print("Transaction failed because of following fields have invalid input: ");
            for (String invalidField : invalidValuesIfAny)
                System.out.print(invalidField + ", ");
            System.out.println();
            return;
        }
        Bank bank = routerService.route(paymentInstrument);
        Transaction transaction = new Transaction.TransactionBuilder().transactionAmount(transactionAmount)
                .paymentMethod(paymentMethod).paymentMethodType(paymentMethodType).Bank(bank)
                .timestamp(getCurrentTimestamp()).merchantId(merchantId).transactionId(transactionId).build();

        if (bank == null) {
            System.out.println("No bank available to process the transaction");
            return;
        }

        if (bank.processPayment(transaction)) {
            System.out.println("Payment successful, with Transaction details as: " + transaction);
        } else {
            System.out.println("Transaction failed, try again later");
        }
    }

    public void addSupportForPaymode(Set<String> inputFields, String paymentMethod,
            String paymentMethodType) {
        this.paymentMethodManager.addGlobalPaymentMethod(paymentMethod, paymentMethodType, inputFields);
    }

    public boolean basicTransactionCheck(String merchantId, String paymentMethod, String paymentMethodType) {
        if (this.merchantService.hasMerchant(merchantId) == false) {
            System.out.println("Merchant do not exists with merchant id: " + merchantId);
            return false;
        }

        if (this.paymentMethodManager.haveMerchantPaymentMethod(merchantId, paymentMethod,
                paymentMethodType) == false) {
            System.out.println("Merchant do not have configured the passed payment method");
            return false;
        }

        return true;
    }

    public void addBank(String bankName, String paymentMethod, String paymentMethodType, int percent) {
        PaymentInstrument paymentInstrument = this.paymentMethodManager.getPaymentInstrument(paymentMethod,
                paymentMethodType);
        if (paymentInstrument == null) {
            System.out.println("Payment method not configured");
            return;
        }
        Bank bank = new Bank(bankName);
        this.routerService.addBank(paymentInstrument, bank, percent);
        System.out.println("Bank " + bankName + " for payment method: " + paymentInstrument.getPaymentMethod()
                + " added distribution of " + percent + "%");
    }

    public void addBanks() {
        addBank("HDFC", "CARD", "DEBIT", 30);
        addBank("ICICI", "CARD", "DEBIT", 30);
        addBank("ICICI", "UPI", "COLLECT", 40);
    }

    public void printOptions() {
        System.out.println("\nPress 1  for on boarding new client");
        System.out.println("Press 2  for on de-boarding existing client");
        System.out.println("Press 3  to check if client exists");
        System.out.println("Press 4  to list payment methods supported by PG");
        System.out.println("Press 5  to list payment methods configured under your merchant account");
        System.out.println("Press 6  to add new payment method to PG (Admin API)");
        System.out.println("Press 7  to delete existing payment method from PG (Admin API)");
        System.out.println("Press 8  to configure new payment method for your merchant account");
        System.out.println("Press 9  to delete existing payment method for your merchant account");
        System.out.println("Press 10 to show active distribution of traffic via Banks (Admin API)");
        System.out.println("Press 11 to initiate new transaction");
        System.out.println("Press 12 to add Bank for processing transactions with traffic percentage");
        System.out.println("Press 13 to exit\n");
    }

    // listSupportedPaymodes() - show paymodes support by PG. if a client is passed
    // as parameter, all supported paymodes for the clients should be shown.
    // addSupportForPaymode() - add paymodes support in the PG. If a client is
    // passed as parameter, add paymode for the clients.
    // removePaymode() - remove paymode ( both from PG or client basis parameter)
    // showDistribution() - show active distribution percentage of router
    // makePayment( //necessary payment details

}