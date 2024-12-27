//Base class for all stocks in the exchange
public abstract class Stock {
    private String symbol;
    private String companyName;
    private double pricePerShare;
    private int availableShares;
    private int sharesSold;

    public Stock(String symbol, String companyName, double initPricePerShare, int initAvailableShares) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.pricePerShare = initPricePerShare;
        this.availableShares = initAvailableShares;
        this.sharesSold = 0;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public double getPricePerShare() {
        return pricePerShare;
    }

    public int getAvailableShares() {
        return availableShares;
    }

    public int getSharesSold() {
        return sharesSold;
    }

    public void updatePrice(double newPrice) {
        if (newPrice > 0) {
            pricePerShare = newPrice;
        }
    }

    public boolean buyShares(int quantity) {
        if (quantity > 0 && availableShares >= quantity) {
            availableShares -= quantity;
            sharesSold += quantity;
            return true;
        }
        return false;
    }

    public void addShares(int quantity) {
        if (quantity > 0) {
            availableShares += quantity;
        }
    }

    @Override
    public String toString() {
        return symbol + " - " + companyName + " ($" + pricePerShare + ", Shares: " + availableShares + ")";
    }
}