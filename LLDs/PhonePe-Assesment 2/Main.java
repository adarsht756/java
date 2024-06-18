import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Main
 */
public class Main {

    public static void main(String[] args) {
        PaymentGateway paymentGateway = new PaymentGateway();

        paymentGateway.addClient("flipkart", "Flipkart", "flipkart@aa.com");
        paymentGateway.addClient("myntra", "Myntra", "myntra@aa.com");
        paymentGateway.addClient("jio", "Jio", "jio@aa.com");
        paymentGateway.addClient("jio", "Jio", "jio@aa.com");
        paymentGateway.removeClient("jio");

        paymentGateway.hasClient("jio");
        paymentGateway.hasClient("myntra");

        Set<String> inputFields = new HashSet<>();
        inputFields.add("card_holder");
        inputFields.add("card_number");
        inputFields.add("expiry_month");
        inputFields.add("expiry_year");
        inputFields.add("cvv");

        paymentGateway.addSupportForPaymode(null, inputFields, "CARD", "DEBIT");
        paymentGateway.addSupportForPaymode(null, inputFields, "CARD", "DEBIT");
        inputFields.clear();
        inputFields.add("upi_id");
        paymentGateway.addSupportForPaymode(null, inputFields, "UPI", "COLLECT");

        inputFields.clear();
        inputFields.add("netbanking_id");
        inputFields.add("password");
        paymentGateway.addSupportForPaymode(null, inputFields, "NB", "NB_HDFC");
        paymentGateway.addSupportForPaymode("flipkart", null, "UPI", "COLLECT");

        paymentGateway.addBank("HDFC", "CARD", "DEBIT", 30);
        paymentGateway.addBank("ICICI", "CARD", "DEBIT", 30);
        paymentGateway.addBank("ICICI", "UPI", "COLLECT", 30);

        paymentGateway.listSupportedPayModes();
        paymentGateway.listMerchantSupportedPayModes("flipkart");

        paymentGateway.showDistribution();

        List<Pair<String, String>> transactionDetails = new ArrayList<>();
        transactionDetails.add(new Pair<String, String>("card_holder", "adarsh"));
        transactionDetails.add(new Pair<String, String>("card_number", "5419190300584227"));
        transactionDetails.add(new Pair<String, String>("expiry_month", "09"));
        transactionDetails.add(new Pair<String, String>("expiry_year", "2025"));
        transactionDetails.add(new Pair<String, String>("cvv", "321"));

        paymentGateway.makePayment("flipkart", "fp-txn-1", 20, "CARD", "DEBIT", transactionDetails);

        transactionDetails.clear();
        transactionDetails.add(new Pair<String, String>("netbanking_id", "something@123"));
        transactionDetails.add(new Pair<String, String>("password", "pass@123"));

        paymentGateway.makePayment("flipkart", "fp-txn-2", 34, "UPI", "COLLECT", transactionDetails);

        paymentGateway.removeMerchantPaymode("flipkart", "UPI", "COLLECT");
    }
}