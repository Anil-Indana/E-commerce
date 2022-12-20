package com.example.ecommerce;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {
    public static DatabaseConnection connection;
    public static Group root;
    public static String emailId;
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        emailId = "";
        connection = new DatabaseConnection();
        root = new Group();
        Header header = new Header();
        ProductPage productPage = new ProductPage();
        AnchorPane productPane = new AnchorPane();
        productPane.setLayoutX(130);
        productPane.setLayoutY(100);
        productPane.getChildren().add(productPage.products());
        root.getChildren().addAll(header.root,productPane);
        stage.setTitle("E-commerce");
        stage.setScene(new Scene(root,500,500));
        stage.show();
        stage.setOnCloseRequest(e ->{
            try {
                connection.con.close();
                System.out.println("Connection closed");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}