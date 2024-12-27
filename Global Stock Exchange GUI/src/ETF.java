// Class for Exchange-Traded Funds (ETFs)
public class ETF extends Fund {
    public ETF(String symbol, String companyName, double initPricePerShare, int initAvailableShares) {
        super(symbol, companyName, initPricePerShare, initAvailableShares);
    }

    @Override
    public String toString() {
        return "ETF: " + super.toString();
    }
}