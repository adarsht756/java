package Models;

public class Transaction {
    private String senderId;
    private String receiverId;
    private double totalAmount;
    private double transactionAmount;
    private double offerAmount;
    private String timestamp;
    private String paymentMethod;
    private String paymentMethodType;

    public Transaction(TransactionBuilder tb) {
        this.senderId = tb.senderId;
        this.receiverId = tb.receiverId;
        this.totalAmount = tb.totalAmount;
        this.transactionAmount = tb.transactionAmount;
        this.offerAmount = tb.offerAmount;
        this.timestamp = tb.timestamp;
        this.paymentMethod = tb.paymentMethod;
        this.paymentMethodType = tb.paymentMethodType;
    }

    public void updateTransactionAmount(double amount) {
        this.transactionAmount = amount;
    }

    public void updateOfferAmount(double amount) {
        this.offerAmount = amount;
    }

    @Override
    public String toString() {
        return "SenderID: " + senderId + ", ReceiverID: " + receiverId + ", Total Amount: " + totalAmount
                + ",\nTransaction Amount: " + (transactionAmount < 0 ? transactionAmount * -1 : transactionAmount)
                + ", Offer Amount: " + offerAmount + ", Timestamp: "
                + timestamp + ",\nPayment Method: " + paymentMethod + ", Payment Method Type: " + paymentMethodType;
    }

    public double getTransactionAmount() {
        return this.transactionAmount;
    }

    public double getTotalAmount() {
        return this.totalAmount;
    }

    public static class TransactionBuilder {
        private String senderId;
        private String receiverId;
        private String timestamp;
        private double totalAmount;
        private double transactionAmount;
        private double offerAmount;
        private String paymentMethod;
        private String paymentMethodType;

        public TransactionBuilder setSenderId(String senderId) {
            this.senderId = senderId;
            return this;
        }

        public TransactionBuilder setReceiverId(String receiverId) {
            this.receiverId = receiverId;
            return this;
        }

        public TransactionBuilder setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public TransactionBuilder setTransactionAmount(double transactionAmount) {
            this.transactionAmount = transactionAmount;
            return this;
        }

        public TransactionBuilder setOfferAmount(double offerAmount) {
            this.offerAmount = offerAmount;
            return this;
        }

        public TransactionBuilder setTimestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public TransactionBuilder setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        public TransactionBuilder setPaymentMethodType(String paymentMethodType) {
            this.paymentMethodType = paymentMethodType;
            return this;
        }

        public Transaction build() {
            return new Transaction(this);
        }
    }
}