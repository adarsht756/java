package Model;

public class Merchant {
    private String timestamp;
    private String email;
    private String merchantName;
    private String merchantID;

    private Merchant(MerchantBuilder builder) {
        this.timestamp = builder.timestamp;
        this.email = builder.email;
        this.merchantName = builder.merchantName;
        this.merchantID = builder.merchantID;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getEmail() {
        return email;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public String getMerchantID() {
        return merchantID;
    }

    public static class MerchantBuilder {

        private String timestamp;
        private String email;
        private String merchantName;
        private String merchantID;

        public MerchantBuilder timestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public MerchantBuilder email(String email) {
            this.email = email;
            return this;
        }

        public MerchantBuilder merchantName(String merchantName) {
            this.merchantName = merchantName;
            return this;
        }

        public MerchantBuilder merchantID(String merchantID) {
            this.merchantID = merchantID;
            return this;
        }

        public Merchant build() {
            return new Merchant(this);
        }
    }
}
