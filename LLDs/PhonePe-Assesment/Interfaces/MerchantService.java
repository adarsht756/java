package Interfaces;

/**
 * MerchantService
 */
public interface MerchantService {
    public boolean addMerchant(String merchantID, String merchantName, String email, String timestamp);

    public boolean removeMerchant(String merchantID);

    public boolean addMerchantPaymentMethod(String merchantID, String paymentMethod, String paymentMethodType);

    public boolean removeMerchantPaymentMethod(String merchantID, String paymentMethod, String paymentMethodType);

    public boolean hasMerchant(String merchantId);
}