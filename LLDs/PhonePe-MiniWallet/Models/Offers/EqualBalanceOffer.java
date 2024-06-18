package Models.Offers;

public class EqualBalanceOffer extends Offer {
    private int maxOfferAmount;

    public EqualBalanceOffer(String offerID, int maxOfferAmount, int offerPercent, boolean isPostTxnOffer) {
        super(offerID, offerPercent, isPostTxnOffer);
        this.maxOfferAmount = maxOfferAmount;
        // super.setOfferId(offerID);
        // super.setOfferPercent(offerPercent);
        // super.setIsPostTxnOffer(isPostTxnOffer);
    }

    public double getOfferAmount(double transactionAmount) {
        return Math.min(this.maxOfferAmount, (super.getOfferPercent() * transactionAmount) / 100);
    }

    public double getOfferAdjustedAmount(double transactionAmount, double offerAmount) {
        return transactionAmount - offerAmount;
    }
}