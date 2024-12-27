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
import java.util.Scanner;
import javafx.scene.control.TextField;

public class StockExchangeApp extends Application{
    private StockExchange model;
    private StockExchangeView viewer;

    public void start(Stage primaryStage){
        model = StockExchange.createExchange();
        viewer = new StockExchangeView();

        viewer.getSalesTF().setText("0");
        viewer.getRevenueTF().setText("0.00");


        viewer.getStockLV().setItems(FXCollections.observableArrayList(model.getStockItems()));
        viewer.getMostPopLV().setItems(FXCollections.observableArrayList(model.getMostPopularStocks()));

        viewer.getAddButton().setDisable(true);
        viewer.getRemoveButton().setDisable(true);
        viewer.getCompButton().setDisable(true);


        primaryStage.setTitle(model.getName());

        Scene scene = new Scene(viewer.getOutput(), 800, 400);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }
    public static void main(String[] args){
        launch(args);
    }
}
