package Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Interfaces.PaymentMethodManager;
import Model.Pair;
import Model.PaymentInstrument;

/**
 * PaymentMethodManagerImpl
 */
public class PaymentMethodManagerImpl implements PaymentMethodManager {
    private HashMap<String, Set<String>> configuredMerchantPaymentMethods;
    private HashMap<String, PaymentInstrument> paymentMethods;
    private InputFieldValidator inputFieldValidator;

    public PaymentMethodManagerImpl() {
        this.configuredMerchantPaymentMethods = new HashMap<>();
        this.paymentMethods = new HashMap<>();
        this.inputFieldValidator = new InputFieldValidator();
    }

    public String buildPaymentMethodId(String paymentMethod, String paymentMethodType) {
        return paymentMethod + paymentMethodType;
    }

    public HashMap<String, PaymentInstrument> listSupportedPaymentMethods() {
        return this.paymentMethods;
    }

    public HashMap<String, PaymentInstrument> listMerchantSupportedPaymentMethods(String merchantId) {
        HashMap<String, PaymentInstrument> result = new HashMap<String, PaymentInstrument>();
        if (this.configuredMerchantPaymentMethods.containsKey(merchantId) == false)
            return result;
        for (String paymentMethodId : this.configuredMerchantPaymentMethods.get(merchantId)) {
            PaymentInstrument paymentInstrument = this.paymentMethods.get(paymentMethodId);
            result.put(paymentMethodId, paymentInstrument);
        }
        return result;
    }

    public boolean havePaymentMethod(String paymentMethod, String paymentMethodType) {
        String paymentMethodId = buildPaymentMethodId(paymentMethod, paymentMethodType);
        return this.paymentMethods.containsKey(paymentMethodId);
    }

    public boolean haveMerchantPaymentMethod(String merchantId, String paymentMethod, String paymentMethodType) {
        String paymentMethodId = buildPaymentMethodId(paymentMethod, paymentMethodType);
        if (this.configuredMerchantPaymentMethods.get(merchantId) == null)
            return false;
        return this.configuredMerchantPaymentMethods.get(merchantId).contains(paymentMethodId);
    }

    public boolean addGlobalPaymentMethod(String paymentMethod, String paymentMethodType, Set<String> inputFields) {
        String paymentMethodId = buildPaymentMethodId(paymentMethod, paymentMethodType);
        if (paymentMethods.containsKey(paymentMethodId)) {
            System.out.println("Payment method already exists where payment method is: " + paymentMethod
                    + " and payment method type is: " + paymentMethodType);
            return false;
        }

        if (inputFields.isEmpty()) {
            System.out.println("Encountered invalid input fields while adding payment method");
            return false;
        }
        PaymentInstrument paymentInstrument = new PaymentInstrument(paymentMethodId, paymentMethod, paymentMethodType,
                inputFields);
        this.paymentMethods.put(paymentMethodId, paymentInstrument);
        System.out.println("Payment method added successfully where payment method is: " + paymentMethod
                + " and payment method type is: " + paymentMethodType);
        return true;
    }

    public boolean removeGlobalPaymentMethod(String paymentMethod, String paymentMethodType) {
        String paymentMethodId = buildPaymentMethodId(paymentMethod, paymentMethodType);
        if (this.paymentMethods.containsKey(paymentMethodId) == false) {
            System.out.println("Payment method do not exists where payment method is: " + paymentMethod
                    + " and payment method type is: " + paymentMethodType);
            return false;
        }
        this.paymentMethods.remove(paymentMethodId);
        for (Map.Entry<String, Set<String>> mpm : this.configuredMerchantPaymentMethods.entrySet()) {
            Set<String> paymentMethodIds = mpm.getValue();
            String merchantID = mpm.getKey();
            if (paymentMethodIds.contains(paymentMethodId)) {
                removeMerchantPaymentMethod(merchantID, paymentMethodId);
            }
        }
        System.out.println("Payment method deleted successfully where payment method is: " + paymentMethod
                + " and payment method type is: " + paymentMethodType);
        return true;
    }

    public boolean addMerchantPaymentMethod(String merchantID, String paymentMethodId) {
        Set<String> merchantPaymentMethodIds = this.configuredMerchantPaymentMethods.get(merchantID);
        if (merchantPaymentMethodIds != null && !merchantPaymentMethodIds.isEmpty()
                && merchantPaymentMethodIds.contains(paymentMethodId) == true) {
            System.out.println(
                    "Merchant have already cofigured payment instrument where payementMethodId is " + paymentMethodId);
            return false;
        }
        if (merchantPaymentMethodIds == null) {
            merchantPaymentMethodIds = new HashSet<String>();
        }
        PaymentInstrument paymentInstrument = this.paymentMethods.get(paymentMethodId);
        if (paymentInstrument == null) {
            System.out.println("PG do not supports payment instrument with payment method id: " + paymentMethodId
                    + " please check with PG to conifgure new PM.");
            return false;
        }
        merchantPaymentMethodIds.add(paymentMethodId);
        this.configuredMerchantPaymentMethods.put(merchantID, merchantPaymentMethodIds);
        System.out.println(
                "Payment method added successfully for merchantID: " + merchantID + " where payment method is: "
                        + paymentInstrument.getPaymentMethod()
                        + " and payment method type is: " + paymentInstrument.getPaymentMethodType());
        return true;
    }

    public boolean removeMerchantPaymentMethod(String merchantID, String paymentMethodId) {
        this.configuredMerchantPaymentMethods.get(merchantID).remove(paymentMethodId);
        return true;
    }

    public boolean removeMerchantPaymentMethod(String merchantID, String paymentMethod, String paymentMethodType) {
        Set<String> merchantPaymentMethodIds = this.configuredMerchantPaymentMethods.get(merchantID);
        String paymentMethodId = buildPaymentMethodId(paymentMethod, paymentMethodType);
        if (merchantPaymentMethodIds == null || merchantPaymentMethodIds.isEmpty()
                || merchantPaymentMethodIds.contains(paymentMethodId) == false) {
            System.out.println(
                    "Merchant " + merchantID + " do not have configured PM where payment method is: " + paymentMethod
                            + " and payment method type is: " + paymentMethodType);
            return false;
        }
        System.out.println("Payment method deleted for merchant id: " + merchantID);
        removeMerchantPaymentMethod(merchantID, paymentMethodId);
        return true;
    }

    public boolean removeMerchantAndClearPMs(String merchantID) {
        if (this.configuredMerchantPaymentMethods.containsKey(merchantID))
            this.configuredMerchantPaymentMethods.remove(merchantID);
        return true;
    }

    public PaymentInstrument getPaymentInstrument(String paymentMethod, String paymentMethodType) {
        String paymentMethodId = buildPaymentMethodId(paymentMethod, paymentMethodType);
        if (this.paymentMethods.containsKey(paymentMethodId) == false)
            return null;
        return this.paymentMethods.get(paymentMethodId);
    }

    public List<String> validatePaymentInstrumentValue(List<Pair<String, String>> inputFields) {
        List<String> invalidFields = new ArrayList<>();

        for (Pair<String, String> inputField : inputFields) {
            String res = this.inputFieldValidator.paymentInstrumentValidator(inputField.getFirst(),
                    inputField.getSecond());
            if (res != null)
                invalidFields.add(res);
        }
        return invalidFields;
    }
}