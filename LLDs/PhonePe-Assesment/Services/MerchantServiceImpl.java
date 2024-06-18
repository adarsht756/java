package Services;

import java.util.HashMap;

import Interfaces.MerchantService;
import Model.Merchant;

/**
 * MerchantServiceImpl
 */
public class MerchantServiceImpl implements MerchantService {
    private HashMap<String, Merchant> merchants;
    private PaymentMethodManagerImpl paymentMethodManager;

    public MerchantServiceImpl(PaymentMethodManagerImpl paymentMethodManager) {
        this.paymentMethodManager = paymentMethodManager;
        this.merchants = new HashMap<>();
    }

    public boolean addMerchant(String merchantID, String merchantName, String email, String timestamp) {
        if (merchants.containsKey(merchantID)) {
            System.out.println("Merchant ID '" + merchantID + "' already exists, please choose another one");
            return false;
        }
        Merchant merchant = new Merchant.MerchantBuilder().merchantID(merchantID).merchantName(merchantName)
                .email(email).timestamp(timestamp).build();
        this.merchants.put(merchantID, merchant);
        System.out.println("Merchant created successfully where merchant id is: " + merchantID
                + ", merchant name is: " + merchantName + " and merchant email is: " + email + " at " + timestamp);
        return true;
    }

    public boolean removeMerchant(String merchantID) {
        if (this.merchants.containsKey(merchantID) == false) {
            System.out.println("Merchant with merchant ID " + merchantID + " do not exists.");
            return false;
        }this.merchants.remove(merchantID);
        this.paymentMethodManager.removeMerchantAndClearPMs(merchantID);
        System.out.println("Merchant with merchant id " + merchantID + " deleted successfully");
        return true;
    }

    public boolean addMerchantPaymentMethod(String merchantID, String paymentMethod, String paymentMethodType) {
        if (this.paymentMethodManager.havePaymentMethod(paymentMethod, paymentMethodType) == false) {
            System.out.println("Payment method do not exists, please check with PG");
            return false;
        }
        return this.paymentMethodManager.addMerchantPaymentMethod(merchantID, paymentMethod + paymentMethodType);
    }

    public boolean removeMerchantPaymentMethod(String merchantID, String paymentMethod, String paymentMethodType) {
        return this.paymentMethodManager.removeMerchantPaymentMethod(merchantID, paymentMethod, paymentMethodType);
    }

    public boolean hasMerchant(String merchantId) {
        return this.merchants.containsKey(merchantId);
    }
}