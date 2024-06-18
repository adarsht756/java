package Interfaces;

import java.util.Set;

/**
 * PaymentMethodManager
 */
public interface PaymentMethodManager {

    public boolean addGlobalPaymentMethod(String paymentMethod, String paymentMethodType, Set<String> inputFields);

    public boolean removeGlobalPaymentMethod(String paymentMethod, String paymentMethodType);

    public boolean addMerchantPaymentMethod(String merchantID, String paymentMethodId);

    public boolean removeMerchantPaymentMethod(String merchantID, String paymentMethod, String paymentMethodType);

    public boolean removeMerchantPaymentMethod(String merchantID, String paymentMethod);
}