package Models.Offers;
public abstract class Offer {
    private int offerPercent;
    public String offerID;
    public boolean isPostTxnOffer;

    public abstract double getOfferAmount(double transacionAmount);

    public abstract double getOfferAdjustedAmount(double transacionAmount, double offerAmount);

    protected Offer(String offerID, int percent, boolean isPostTxnOffer) {
        this.offerID = offerID;
        this.offerPercent = percent;
        this.isPostTxnOffer = isPostTxnOffer;
    }

    public void setOfferPercent(int percent) {
        this.offerPercent = percent;
    }

    public void setOfferId(String offerID) {
        this.offerID = offerID;
    }

    public void setIsPostTxnOffer(boolean f) {
        this.isPostTxnOffer = f;
    }

    public boolean getIsPostTxnOffer() {
        return this.isPostTxnOffer;
    }

    public String getOfferId() {
        return this.offerID;
    }

    public int getOfferPercent() {
        return this.offerPercent;
    }
}