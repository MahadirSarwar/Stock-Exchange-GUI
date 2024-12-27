// Class representing ETF or Bond
public abstract class Fund extends Stock {
    public Fund(String symbol, String companyName, double initPricePerShare, int initAvailableShares) {
        super(symbol, companyName, initPricePerShare, initAvailableShares);
    }
}
