package com.example.ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductPage {
    ListView<HBox> products;
    ListView<HBox> productsBySearch(String search) throws SQLException {
        products = new ListView<>();
        ObservableList<HBox> productList = FXCollections.observableArrayList();
        ResultSet res = Main.connection.executeQuery("select * from product");
        while(res.next()) {
            if (res.getString("productName").toLowerCase().contains(search.toLowerCase())) {
                Label productId = new Label();
                Label name = new Label();
                Label price = new Label();
                Button buy = new Button();
                name.setMinWidth(50);
                price.setMinWidth(50);
                buy.setText("Buy");
                HBox productDetails = new HBox();

                buy.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if (Main.emailId.equals("")) {
                            Dialog<String> dialog = new Dialog<>();
                            dialog.setTitle("Login");
                            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                            dialog.getDialogPane().getButtonTypes().add(type);
                            dialog.setContentText("Please Login!");
                            dialog.showAndWait();
                        } else {
                            System.out.println("Buy button is getting clicked!");
                            Order order = new Order();
                            try {
                                order.placeOrder(productId.getText());
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });

                name.setText(res.getString("productName"));
                price.setText(res.getString("price"));
                productId.setText(res.getString("productId"));
                productDetails.getChildren().addAll(productId, name, price, buy);
                productList.add(productDetails);
            }
        }
        products.setItems(productList);
        return products;
    }
    ListView<HBox> products() throws SQLException {
        products = new ListView<>();
        ObservableList<HBox> productList = FXCollections.observableArrayList();
        ResultSet res = Main.connection.executeQuery("select * from product");
        while(res.next()) {
            Label productId = new Label();
            Label name = new Label();
            Label price = new Label();
            Button buy = new Button();
            name.setMinWidth(50);
            price.setMinWidth(50);
            buy.setText("Buy");
            HBox productDetails = new HBox();

            buy.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if(Main.emailId.equals("")){
                        Dialog<String> dialog = new Dialog<>();
                        dialog.setTitle("Login");
                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                        dialog.getDialogPane().getButtonTypes().add(type);
                        dialog.setContentText("Please Login!");
                        dialog.showAndWait();
                    }
                    else {
                        System.out.println("Buy button is getting clicked!");
                        Order order = new Order();
                        try {
                            order.placeOrder(productId.getText());
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });

            name.setText(res.getString("productName"));
            price.setText(res.getString("price"));
            productId.setText(res.getString("productId"));
            productDetails.getChildren().addAll(productId, name, price, buy);
            productList.add(productDetails);
        }
        products.setItems(productList);
        return products;
    }
}
