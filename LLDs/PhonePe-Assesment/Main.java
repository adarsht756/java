import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import Model.Pair;
import Model.PaymentInstrument;

/**
 * Main
 */
public class Main {

    public static void main(String[] args) {
        PaymentGateway paymentGateway = new PaymentGateway();

        Scanner sc = new Scanner(System.in);

        String merchantId, merchantName, merchantEmail, paymentMethod, paymentMethodType, paymentMethodId,
                transactionId;
        List<Pair<String, String>> transactionDetails = new ArrayList<>();
        Set<String> inputFields = new HashSet<>();
        int choice = 0, transactionAmount = 0;

        boolean runner = true;
        while (runner) {
            paymentGateway.printOptions();
            try {
                choice = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Encountered invalid input.");
                sc.nextLine();
                continue;
            }
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Enter unique merchantId, merchant name, merchant email: ");
                    merchantId = sc.nextLine();
                    merchantName = sc.nextLine();
                    merchantEmail = sc.nextLine();
                    paymentGateway.addClient(merchantId, merchantName, merchantEmail);
                    break;
                case 2:
                    System.out.println("Enter registered merchantId");
                    merchantId = sc.nextLine();
                    paymentGateway.removeClient(merchantId);
                    break;
                case 3:
                    System.out.println("Enter registered merchantId");
                    merchantId = sc.nextLine();
                    paymentGateway.hasClient(merchantId);
                    break;
                case 4:
                    paymentGateway.listSupportedPayModes();
                    break;
                case 5:
                    System.out.println("Enter registered merchantId");
                    merchantId = sc.nextLine();
                    paymentGateway.listMerchantSupportedPayModes(merchantId);
                    break;
                case 6:
                    System.out.println(
                            "Enter payment method, payment method type, and input fields supported by payment instrument");
                    paymentMethod = sc.nextLine();
                    paymentMethodType = sc.nextLine();
                    inputFields.clear();
                    System.out.println("Enter fields for payment instrument and enter exit once done");
                    while (true) {
                        String field;
                        System.out.print("Enter field name: ");
                        field = sc.nextLine();
                        if (field.equals("exit"))
                            break;
                        inputFields.add(field);
                    }
                    paymentGateway.addSupportForPaymode(inputFields, paymentMethod, paymentMethodType);
                    break;
                case 7:
                    System.out.println("Enter payment method and payment method type");
                    paymentMethod = sc.nextLine();
                    paymentMethodType = sc.nextLine();
                    paymentGateway.removePaymode(paymentMethod, paymentMethodType);
                    break;
                case 8:
                    paymentGateway.listSupportedPayModes();
                    System.out.println(
                            "Enter registered merchant id and payment method id from above shown payment methods");
                    merchantId = sc.nextLine();
                    paymentMethodId = sc.nextLine();
                    paymentGateway.addMerchantPaymode(merchantId, paymentMethodId);
                    break;
                case 9:
                    System.out.println("Enter registered merchant id, payment method and payment method type");
                    merchantId = sc.nextLine();
                    paymentMethod = sc.nextLine();
                    paymentMethodType = sc.nextLine();
                    paymentGateway.removeMerchantPaymode(merchantId, paymentMethod, paymentMethodType);
                    break;
                case 10:
                    System.out.println("Here is the active traffic distribution per Bank");
                    paymentGateway.showDistribution();
                    break;
                case 11:
                    System.out.println(
                            "Enter merchantId, transactionId, transactionAmount, payment method, payment method type");
                    merchantId = sc.nextLine();
                    transactionId = sc.nextLine();
                    transactionAmount = sc.nextInt();
                    sc.nextLine();

                    paymentMethod = sc.nextLine();
                    paymentMethodType = sc.nextLine();
                    System.out.println(
                            "Now, enter transaction related input with field name and field value, and exit once done");
                    transactionDetails.clear();
                    PaymentInstrument paymentInstrument = paymentGateway.getPaymentInstrument(paymentMethod,
                            paymentMethodType);
                    if (paymentInstrument == null) {
                        System.out.println("Transaction failed, invalid input payment method and payment method type");
                        break;
                    }
                    for (String inputField : paymentInstrument.getInputFields()) {
                        System.out.println("Enter " + inputField + ": ");
                        String value = sc.nextLine();
                        transactionDetails.add(new Pair<String, String>(inputField, value));
                    }
                    paymentGateway.makePayment(merchantId, transactionId, transactionAmount, paymentInstrument,
                            transactionDetails);
                    break;
                case 12:
                    System.out.println(
                            "Enter bank name, payment method, payment method type and traffic percentage (Admin API)");
                    String bankName = sc.nextLine();
                    paymentMethod = sc.nextLine();
                    paymentMethodType = sc.nextLine();
                    int percentage = sc.nextInt();
                    sc.nextLine();
                    paymentGateway.addBank(bankName, paymentMethod, paymentMethodType, percentage);
                    break;
                case 13:
                    System.out.println("Thanks for working with us!");
                    runner = false;
                    break;
                default:
                    System.out.println("Please enter valid input\n");
                    break;
            }
        }
        sc.close();
    }
}