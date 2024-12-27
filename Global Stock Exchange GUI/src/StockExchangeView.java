import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.event.*;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.TextField;

public class StockExchangeView extends Application {

    private StockExchange model;
    private TextField salesTF, revenueTF;
    private Button addButton, removeButton, compButton, resetButton;
    private ListView<String> stockLV, cartLV, mostPopLV;
    private Label storeSumLabel, salesLabel, revenueLabel, mostPopLabel, stockLabel, cartLabel, totalAmountLabel;

    private Pane output;

    public StockExchangeView() {
        model = StockExchange.createExchange();
        salesTF = new TextField();
        salesTF.relocate(83,57);
        salesTF.setPrefSize(100,30);

        revenueTF = new TextField();
        revenueTF.relocate(83, 102);
        revenueTF.setPrefSize(100,30);


        mostPopLV = new ListView();
        mostPopLV.relocate(17, 215);
        mostPopLV.setPrefSize(166,120);

        addButton = new Button("Add to Cart");
        addButton.relocate(293, 348);
        addButton.setPrefSize(113,37);
        addButton.setDisable(true);

        removeButton = new Button("Remove from Cart");
        removeButton.relocate(525, 345);
        removeButton.setPrefSize(122, 37);
        removeButton.setDisable(true);

        compButton = new Button("Complete Sale");
        compButton.relocate(647, 345);
        compButton.setPrefSize(122,37);
        compButton.setDisable(true);

        resetButton = new Button("Reset Store");
        resetButton.relocate(52, 348);
        resetButton.setPrefSize(113, 37);

        stockLV = new ListView<>();
        stockLV.relocate(207, 59);
        stockLV.setPrefSize(268, 276);

        cartLV = new ListView<>();
        cartLV.relocate(507, 59);
        cartLV.setPrefSize(268, 276);

        storeSumLabel = new Label("Stock Summary");
        storeSumLabel.relocate(50, 20);

        salesLabel = new Label("# Sales:");
        salesLabel.relocate(30, 60);

        revenueLabel = new Label("Revenue:");
        revenueLabel.relocate(30, 105);



        mostPopLabel = new Label("Most Popular Stocks:");
        mostPopLabel.relocate(50, 190);

        stockLabel = new Label("Market Stock:");
        stockLabel.relocate(300, 20);

        cartLabel = new Label("Current Purchases ($0.00):");
        cartLabel.relocate(600, 20);


        output = new Pane();
        output.getChildren().addAll(salesTF, revenueTF, mostPopLV, addButton,
                removeButton, compButton, resetButton, stockLV, cartLV,
                storeSumLabel, salesLabel, revenueLabel,
                mostPopLabel, stockLabel, cartLabel);

        stockLV.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                addButton.setDisable(stockLV.getSelectionModel().getSelectedItem() == null);
            }
        });

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String index = stockLV.getSelectionModel().getSelectedItem();
                for (Stock stock : model.getStocks()) {
                    if (stock.toString().equals(index)) {
                        if (model.addToPortfolio(stock, 1)) { // Add stock only if successful
                            cartLV.setItems(FXCollections.observableArrayList(model.getPortfolioItems())); // Update portfolio view
                            stockLV.setItems(FXCollections.observableArrayList(model.getStockItems())); // Refresh stock view
                            compButton.setDisable(model.getPortfolio().isEmpty());
                            cartLabel.setText("Portfolio Value ($" + model.calculatePortfolioValue() + "):");
                        }
                        break;
                    }
                }
            }
        });



        cartLV.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                removeButton.setDisable(cartLV.getSelectionModel().getSelectedItem() == null);
            }
        });

        removeButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String index = cartLV.getSelectionModel().getSelectedItem();
                for (Map.Entry<Stock, Integer> entry : model.getPortfolio().entrySet()) {
                    if ((entry.getValue() + " x " + entry.getKey().toString()).equals(index)) {
                        Stock stock = entry.getKey();
                        if (model.removeFromPortfolio(stock, 1)) { // Remove stock only if successful
                            cartLV.setItems(FXCollections.observableArrayList(model.getPortfolioItems())); // Refresh portfolio view
                            stockLV.setItems(FXCollections.observableArrayList(model.getStockItems())); // Refresh stock view
                            compButton.setDisable(model.getPortfolio().isEmpty());
                            cartLabel.setText("Portfolio Value ($" + model.calculatePortfolioValue() + "):");
                        }
                        break;
                    }
                }
            }
        });



        compButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                model.completeTrade();
                double total = model.calculatePortfolioValue(); // Calculate the total portfolio value
                salesTF.setText(Integer.toString(model.getTotalTrades())); // Update total trades
                revenueTF.setText(String.format("%.2f", model.getRevenue())); // Update revenue
                cartLV.setItems(FXCollections.observableArrayList(model.getPortfolioItems())); // Refresh portfolio view
                compButton.setDisable(model.getPortfolio().isEmpty()); // Disable button if portfolio is empty
                cartLabel.setText("Portfolio Value ($" + String.format("%.2f", total) + "):"); // Update portfolio label
                stockLV.setItems(FXCollections.observableArrayList(model.getStockItems())); // Refresh stock view
                mostPopLV.setItems(FXCollections.observableArrayList(model.getMostPopularStocks())); // Update most popular stocks
            }
        });

        resetButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                model = StockExchange.createExchange();

                salesTF.setText("0");
                revenueTF.setText("0.00");


                stockLV.setItems(FXCollections.observableArrayList(model.getStockItems()));
                cartLV.setItems(FXCollections.observableArrayList(model.getPortfolioItems()));
                mostPopLV.setItems(FXCollections.observableArrayList(model.getMostPopularStocks()));

                addButton.setDisable(true);
                removeButton.setDisable(true);
                compButton.setDisable(true);
            }
        });



    }

    public void start(Stage primaryStage){
        Scene scene = new Scene (output, 800,400);

        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

    }



    public TextField getSalesTF() {
        return salesTF;
    }

    public TextField getRevenueTF() {
        return revenueTF;
    }



    public ListView<String> getMostPopLV() {
        return mostPopLV;
    }

    public Button getAddButton() {
        return addButton;
    }

    public Button getRemoveButton() {
        return removeButton;
    }

    public Button getCompButton() {
        return compButton;
    }

    public Button getResetButton() {
        return resetButton;
    }

    public ListView<String> getStockLV() {
        return stockLV;
    }

    public ListView<String> getCartLV() {
        return cartLV;
    }

    public Pane getOutput() {
        return output;
    }
}
