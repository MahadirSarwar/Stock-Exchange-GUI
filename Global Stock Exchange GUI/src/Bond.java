// Class for Bonds
public class Bond extends Fund {
    private double maturityDate; // Example of a unique attribute

    public Bond(String symbol, String companyName, double initPricePerShare, int initAvailableShares, double maturityDate) {
        super(symbol, companyName, initPricePerShare, initAvailableShares);
        this.maturityDate = maturityDate;
    }

    public double getMaturityDate() {
        return maturityDate;
    }

    @Override
    public String toString() {
        return "Bond: " + super.toString() + " (Maturity: " + maturityDate + ")";
    }
}