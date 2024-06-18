import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        HashMap<String, PaymentInstrument> globalPaymentMethods = this.paymentMethodManager
                .listSupportedPaymentMethods();
        printPaymentMethods(globalPaymentMethods);
    }

    private void printPaymentMethods(HashMap<String, PaymentInstrument> paymentMethods) {
        for (Map.Entry<String, PaymentInstrument> entry : paymentMethods.entrySet()) {
            String paymentMethodId = entry.getKey();
            PaymentInstrument pm = entry.getValue();
            System.out.println("Payment method id: " + paymentMethodId + ", payment method" + pm.getPaymentMethod()
                    + ", payment method type: " + pm.getPaymentMethodType());
        }
        System.out.println();
    }

    public void removePaymode(String paymentMethod, String paymentMethodType) {
        this.paymentMethodManager.removeGlobalPaymentMethod(paymentMethod, paymentMethodType);
    }

    public void removeMerchantPaymode(String merchantId, String paymentMethod, String paymentMethodType) {
        this.paymentMethodManager.removeMerchantPaymentMethod(merchantId, paymentMethod, paymentMethodType);
    }

    public void showDistribution() {
        System.out.println(this.routerService.getDistribution());
    }

    public void makePayment(String merchantId, String transactionId, double transactionAmount, String paymentMethod,
            String paymentMethodType, List<Pair<String, String>> inputFields) {
        if (basicTransactionCheck(merchantId, paymentMethod, paymentMethodType) == false) {
            return;
        }
        PaymentInstrument paymentInstrument = this.paymentMethodManager.getPaymentInstrument(paymentMethod,
                paymentMethodType);
        Bank bank = routerService.route(paymentInstrument);
        if (paymentInstrument.validator(inputFields) == false) {
            System.out.println("Input fields validation failed");
            return;
        }
        Transaction transaction = new Transaction.TransactionBuilder().transactionAmount(transactionAmount)
                .paymentMethod(paymentMethodType).paymentMethodType(paymentMethodType).Bank(bank)
                .timestamp(getCurrentTimestamp()).merchantId(merchantId).transactionId(transactionId).build();
        if (bank == null) {
            System.out.println("No bank available to process the transaction");
            return;
        }
        if (bank.processPayment(transaction)) {
            System.out.println("Payment successfull, with Transaction details as: " + transaction);
        } else {
            System.out.println("Transaction failed, try again later");
        }
    }

    public void addSupportForPaymode(String maybeMerchantId, Set<String> inputFields, String paymentMethod,
            String paymentMethodType) {
        if (maybeMerchantId == null) {
            this.paymentMethodManager.addGlobalPaymentMethod(paymentMethod, paymentMethodType, inputFields);
        } else {
            this.paymentMethodManager.addMerchantPaymentMethod(maybeMerchantId, paymentMethod, paymentMethodType);
        }
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
        Bank bank = new Bank(bankName);
        this.routerService.addBank(paymentInstrument, bank, percent);
        System.out.println("Bank " + bankName + " for payment method" + paymentInstrument.getPaymentMethod()
                + " added distribution of " + percent + "%");
    }

}