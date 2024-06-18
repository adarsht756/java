package Models.Offers;
public class MinAmountOffer extends Offer {
    private int minAmountRequired;
    private int transactionCount;
    private int maxOfferAmount;

    public MinAmountOffer(String offerID, int minAmountRequired, int transactionCount, int maxOfferAmount, int offerPercent, boolean isPostTxnOffer) {
        super(offerID, offerPercent, isPostTxnOffer);
        this.transactionCount = transactionCount;
        this.minAmountRequired = minAmountRequired;
        this.maxOfferAmount = maxOfferAmount;
        // super.setOfferPercent(offerPercent);
        // super.setOfferId(offerID);
        // super.setIsPostTxnOffer(isPostTxnOffer);
    }

    public int getMinAmountRequired() {
        return this.minAmountRequired;
    }

    public int getTransactionCount() {
        return this.transactionCount;
    }

    public double getOfferAmount(double transactionAmount) {
        return Math.min(this.maxOfferAmount, (super.getOfferPercent() * transactionAmount) / 100);
    }

    public double getOfferAdjustedAmount (double transactionAmount, double offerAmount) {
        return transactionAmount - offerAmount;
    }
}