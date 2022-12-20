package com.example.ecommerce;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;

public class HeaderController {
    @FXML
    Label email;
    @FXML
    public void initialize(){
        if(!Main.emailId.equals("")){
            loginbutton.setOpacity(0);
            email.setText(Main.emailId);
        }
    }
    @FXML
    Button loginbutton,logoutbutton;
    public void login(MouseEvent e) throws IOException {
        AnchorPane loginpage = FXMLLoader.load(getClass().getResource("loginpage.fxml"));
        Main.root.getChildren().add(loginpage);
    }
    @FXML
    TextField searchText;
    @FXML
    public void search(MouseEvent e) throws IOException, SQLException {
        ProductPage productPage = new ProductPage();

        Header header = new Header();
        AnchorPane productPane = new AnchorPane();
        productPane.getChildren().add(productPage.productsBySearch(searchText.getText()));
        productPane.setLayoutX(130);
        productPane.setLayoutY(100);
        Main.root.getChildren().clear();
        Main.root.getChildren().addAll(header.root,productPane);
    }
    @FXML
    public void logoutappear(MouseEvent e){
        if(logoutbutton.getOpacity() == 0){
            logoutbutton.setOpacity(1);
        }
        else{
            logoutbutton.setOpacity(0);
        }
    }
    @FXML
    public void logout(MouseEvent e) throws IOException {
        if (logoutbutton.getOpacity() == 1) {
            Main.emailId = "";
            logoutbutton.setOpacity(0);
            Header header = new Header();
            Main.root.getChildren().add(header.root);
        }
    }
}
