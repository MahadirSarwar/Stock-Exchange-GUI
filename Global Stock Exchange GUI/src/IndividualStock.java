// Class representing a single individual stock
public class IndividualStock extends Stock {
    public IndividualStock(String symbol, String companyName, double initPricePerShare, int initAvailableShares) {
        super(symbol, companyName, initPricePerShare, initAvailableShares);
    }

    @Override
    public String toString() {
        return "Individual Stock: " + super.toString();
    }
}
