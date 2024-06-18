package Model;
import java.util.List;
import java.util.Set;

import Interfaces.PaymentInstrumentInterface;

/**
 * PaymentInstrumentImpl
 */
public class PaymentInstrument implements PaymentInstrumentInterface {

    private String paymentMethodId;
    private String paymentMethod;
    private String paymentMethodType;
    Set<String> inputFields; // inputFieldValue, intputFieldType

    public PaymentInstrument(String paymentMethodId, String paymentMethod, String paymentMethodType, Set<String> inputFields) {
        this.paymentMethod = paymentMethod;
        this.paymentMethodType = paymentMethodType;
        this.inputFields = inputFields;
        this.paymentMethodId = paymentMethodId;
    }

    public String getPaymentMethod() {
        return this.paymentMethod;
    }

    public String getPaymentMethodId() {
        return this.paymentMethodId;
    }

    public String getPaymentMethodType() {
        return this.paymentMethodType;
    }

    public Set<String> getInputFields() {
        return this.inputFields;
    }

    public boolean validator(List<Pair<String, String>> inputFields) {
        if (inputFields.size() != this.inputFields.size())
            return false;
        for (Pair<String, String> field: inputFields) {
            String fieldName = field.getFirst();
            if (this.inputFields.contains(fieldName) == false)
                return false;
        }
        return true;
    }

}