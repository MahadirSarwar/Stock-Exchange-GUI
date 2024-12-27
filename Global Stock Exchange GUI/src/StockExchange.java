// Class representing a stock exchange
// Has an array of stocks that represent the items the exchange can trade

import java.util.*;

public class StockExchange {

    public final int MAX_STOCKS = 999; // Maximum number of stocks the exchange can have
    private int curStocks;
    private String name;
    private Stock[] stocks;
    private double revenue;
    private int curPortfolioItems;
    private HashMap<Stock, Integer> portfolio;
    private int totalTrades;

    public StockExchange(String initName) {
        revenue = 0.0;
        name = initName;
        stocks = new Stock[MAX_STOCKS];
        curStocks = 0;
        curPortfolioItems = 0;
        portfolio = new HashMap<>();
        totalTrades = 0;
    }

    public String getName() {
        return name;
    }

    public boolean addStock(Stock newStock) {
        if (curStocks < MAX_STOCKS) {
            stocks[curStocks] = newStock;
            curStocks++;
            return true;
        }
        return false;
    }

    public String[] getStockItems() {
        String[] stockItems = new String[curStocks];
        for (int i = 0; i < curStocks; i++) {
            stockItems[i] = stocks[i].toString();
        }
        return stockItems;
    }

    public boolean addToPortfolio(Stock stock, int quantity) {
        if (curPortfolioItems < MAX_STOCKS) {
            int current = portfolio.getOrDefault(stock, 0);
            if (stock.buyShares(quantity)) {
                portfolio.put(stock, current + quantity);
                curPortfolioItems += quantity;
                revenue += stock.getPricePerShare() * quantity;
                totalTrades++;
                return true;
            }
        }
        return false;
    }

    public HashMap<Stock, Integer> getPortfolio() {
        return portfolio;
    }

    public String[] getPortfolioItems() {
        String[] portfolioItems = new String[portfolio.size()];
        int i = 0;
        for (Map.Entry<Stock, Integer> entry : portfolio.entrySet()) {
            portfolioItems[i] = entry.getValue() + " x " + entry.getKey().toString();
            i++;
        }
        return portfolioItems;
    }

    public boolean removeFromPortfolio(Stock stock, int quantity) {
        if (portfolio.containsKey(stock) && portfolio.get(stock) >= quantity) {
            portfolio.put(stock, portfolio.get(stock) - quantity);
            stock.addShares(quantity);
            curPortfolioItems -= quantity;
            revenue -= stock.getPricePerShare() * quantity;
            totalTrades--;
            return true;
        }
        return false;
    }

    public double calculatePortfolioValue() {
        double total = 0.0;
        for (Map.Entry<Stock, Integer> entry : portfolio.entrySet()) {
            total += entry.getKey().getPricePerShare() * entry.getValue();
        }
        return total;
    }

    public String[] getMostPopularStocks() {
        Stock[] sortedStocks = new Stock[curStocks];
        System.arraycopy(stocks, 0, sortedStocks, 0, curStocks);

        Arrays.sort(sortedStocks, (a, b) -> b.getSharesSold() - a.getSharesSold());

        String[] mostPopularStocks = new String[Math.min(3, sortedStocks.length)];
        for (int i = 0; i < mostPopularStocks.length; i++) {
            mostPopularStocks[i] = sortedStocks[i].toString();
        }
        return mostPopularStocks;
    }

    public double getRevenue() {
        return revenue;
    }

    public int getTotalTrades() {
        return totalTrades;
    }

    public static StockExchange createExchange() {
        StockExchange exchange = new StockExchange("Global Stock Exchange");
        exchange.addStock(new IndividualStock("AAPL", "Apple Inc.", 175.5, 1000));
        exchange.addStock(new ETF("SPY", "S&P 500 ETF", 450.0, 500));
        exchange.addStock(new Bond("US10Y", "10-Year Treasury", 100.0, 1000, 2033));
        exchange.addStock(new IndividualStock("TSLA", "Tesla Inc.", 250.3, 1200));
        exchange.addStock(new IndividualStock("GOOGL", "Alphabet Inc.", 2800.0, 800));
        exchange.addStock(new ETF("QQQ", "NASDAQ 100 ETF", 360.0, 600));
        exchange.addStock(new Bond("CORP10Y", "Corporate 10-Year", 95.0, 1500, 2030));
        exchange.addStock(new IndividualStock("MSFT", "Microsoft Corp.", 310.0, 900));
        return exchange;
    }
    public List<Stock> getStocks() {
        return Arrays.asList(Arrays.copyOf(stocks, curStocks)); // Return only the valid stocks
    }

    public void completeTrade() {
        portfolio.clear(); // Clear the portfolio (cart equivalent)
        curPortfolioItems = 0; // Reset portfolio item count
    }

}
