package com.example.ecommerce;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

public class Order {
    void placeOrder(String productID) throws SQLException {
        ResultSet res = Main.connection.executeQuery("Select max(orderID) from orders");
        int orderId = 1;
        if(res.next()){
            orderId = res.getInt("max(orderID)") + 1;
        }
        Timestamp ts = new Timestamp(Calendar.getInstance().getTime().getTime());
        String query = String.format("Insert into orders values(%s,%s,'%s','%S')",orderId,productID,Main.emailId,ts);
        int response = Main.connection.executeUpdate(query);
        if(response > 0){
//            System.out.println("order is placed");
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Order");
            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.setContentText("Your order is placed");
            dialog.showAndWait();
        }
        else{
            System.out.println("order not placed");
        }
    }
}
