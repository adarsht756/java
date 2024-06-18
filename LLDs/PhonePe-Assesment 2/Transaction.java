public class Transaction {

    private String paymentMethodType;
    private String transactionId;
    private String timestamp;
    private String merchantId;
    private double transactionAmount;
    private Bank Bank;
    private String paymentMethod;

    private Transaction(TransactionBuilder builder) {
        this.paymentMethodType = builder.paymentMethodType;
        this.transactionId = builder.transactionId;
        this.timestamp = builder.timestamp;
        this.merchantId = builder.merchantId;
        this.transactionAmount = builder.transactionAmount;
        this.Bank = builder.Bank;
        this.paymentMethod = builder.paymentMethod;
    }

    public String getPaymentMethodType() {
        return paymentMethodType;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public Bank getBank() {
        return Bank;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public static class TransactionBuilder {

        private String paymentMethodType;
        private String transactionId;
        private String timestamp;
        private String merchantId;
        private double transactionAmount;
        private Bank Bank;
        private String paymentMethod;

        public TransactionBuilder paymentMethodType(String paymentMethodType) {
            this.paymentMethodType = paymentMethodType;
            return this;
        }

        public TransactionBuilder transactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public TransactionBuilder timestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public TransactionBuilder merchantId(String merchantId) {
            this.merchantId = merchantId;
            return this;
        }

        public TransactionBuilder transactionAmount(double transactionAmount) {
            this.transactionAmount = transactionAmount;
            return this;
        }

        public TransactionBuilder Bank(Bank Bank) {
            this.Bank = Bank;
            return this;
        }

        public TransactionBuilder paymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        public Transaction build() {
            return new Transaction(this);
        }
    }
}
