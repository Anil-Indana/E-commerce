package com.example.ecommerce;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPageController {
    @FXML
    TextField email;
    @FXML
    PasswordField password;
    @FXML
    public void login(MouseEvent e) throws SQLException, IOException {
        String query = String.format("select * from user where emailId = '%s' and pass = '%s'",email.getText(),password.getText());
        ResultSet res = Main.connection.executeQuery(query);

        if(res.next()) {
            Main.emailId = res.getString("emailId");
            String userType = res.getString("userType");
            if(userType.equals("seller")){
                AnchorPane sellerpage = FXMLLoader.load(getClass().getResource("sellerpage.fxml"));
                Main.root.getChildren().add(sellerpage);
            }
            else{
                System.out.println("Logged in as Buyer");
                Header header = new Header();
                ProductPage productPage = new ProductPage();
                AnchorPane productPane = new AnchorPane();
                productPane.getChildren().add(productPage.products());
                productPane.setLayoutX(130);
                productPane.setLayoutY(100);
                Main.root.getChildren().clear();
                Main.root.getChildren().addAll(header.root,productPane);
            }
        }
        else {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Login");
            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.setContentText("Login failed,Please enter a valid email/password");
            dialog.showAndWait();
        }
    }
}
