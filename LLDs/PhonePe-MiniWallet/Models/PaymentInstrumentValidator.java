package Models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PaymentInstrumentValidator
 */
public class PaymentInstrumentValidator {
    public boolean paymentInstrumentValidator(String paymentMethod, String paymentMethodType, String value) {
        if (paymentMethod.equals("CARD"))
            return cardValidator(value);
        if (paymentMethod.equals("UPI")) {
            if (paymentMethodType.equals("COLLECT"))
                return upiIdValidator(value);
            else
                return true;
        }
        System.out.println("Payment method not supported");
        return false;
    }

    public boolean cardValidator(String cardNumber) {
        String regex = "^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\\d{3})\\d{11})$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(cardNumber);
        return m.matches();
    }

    public boolean upiIdValidator(String upiId) {
        String regex = "^[a-zA-Z0-9.-]{2,256}@[a-zA-Z][a-zA-Z]{2,64}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(upiId);
        return m.matches();
    }
}