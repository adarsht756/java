package Services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * InputFieldValidator
 */
public class InputFieldValidator {
    public String paymentInstrumentValidator(String inputField, String value) {

        if (inputField.equals("card_number") && !cardValidator(value))
            return inputField;

        if (inputField.equals("upi_id") && !upiIdValidator(value))
            return inputField;

        if (inputField.equals("expiry_month") && !monthValidator(value))
            return inputField;

        if (inputField.equals("expiry_year") && !yearValidator(value))
            return inputField;

        if (inputField.equals("cvv") && !cvvValidator(value))
            return inputField;

        return null;
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

    public boolean yearValidator(String value) {
        return value.length() == 4;
    }

    public boolean monthValidator(String value) {
        int month = Integer.parseInt(value);
        return month >= 1 && month <= 12;
    }

    public boolean cvvValidator(String value) {
        return value.length() == 3;
    }
}