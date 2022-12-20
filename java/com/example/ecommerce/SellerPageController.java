package com.example.ecommerce;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SellerPageController {
    @FXML
    TextField name;
    @FXML
    TextField price;
    @FXML
    TextField sellerid;
    @FXML
    public void AddProduct() throws SQLException {
        int productId = 1;
        ResultSet response2 = Main.connection.executeQuery("select max(productId) from product;");
        if(response2.next()){
            productId = response2.getInt("max(productId)")+1;
        }

        String query = String.format("Insert into product values(%s,'%s',%s,'%s')",productId,name.getText(),price.getText(),sellerid.getText());
        int response = Main.connection.executeUpdate(query);
        if(response > 0) System.out.println("New product added");
    }
}
