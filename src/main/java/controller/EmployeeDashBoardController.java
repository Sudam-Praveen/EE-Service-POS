package controller;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.EventObject;
import java.util.ResourceBundle;

public class EmployeeDashBoardController {


    public Label lblMenu;
    public Label lblDescription;
    public Label lblUser;
    public ImageView imgCustomer;
    public ImageView imgItem;
    public ImageView imgOrder;
    public ImageView imgViewOrders;

    public ImageView imgBack;
    public AnchorPane root;
    public static String loggedInUserId;
    public static String userName;


    public void setLoggedInUserId(String userId, String username) {
        this.loggedInUserId = userId;
        this.userName = username;
        lblUser.setText("Authorized User " + loggedInUserId + "-" + userName);  // Set the user ID to the label

    }

    public void initialize() {
        if (loggedInUserId != null && userName != null) {
            lblUser.setText("Authorized User " + loggedInUserId + "-" + userName);
        }
    }


    public void backIconAction(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) lblMenu.getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/LoginForm.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

            TranslateTransition tt = new TranslateTransition(Duration.millis(350), scene.getRoot());
            tt.setFromX(-scene.getWidth());
            tt.setToX(0);
            tt.play();

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void navigate(MouseEvent event) throws IOException {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();

            if (icon != null && icon.getId() != null) {

                Parent root = null;
                FXMLLoader loader = new FXMLLoader();
                switch (icon.getId()) {
                    case "imgCustomer":
                        root = loader.load(this.getClass().getResource("/view/CustomerForm.fxml"));
                        break;
                    case "imgItem":
                        root = loader.load(this.getClass().getResource("/view/ItemForm.fxml"));
                        break;
                case "imgOrder":
                    root = FXMLLoader.load(this.getClass().getResource("/view/PlaceOrderForm.fxml"));
                break;
//                case "imgViewOrders":
//                    root = FXMLLoader.load(this.getClass().getResource("/view/SearchOrdersForm.fxml"));
//                    break;
                }

                if (root != null) {
                    Scene subScene = new Scene(root);
                    Stage primaryStage = (Stage) this.root.getScene().getWindow();
                    primaryStage.setScene(subScene);
                    primaryStage.sizeToScene();
                    primaryStage.centerOnScreen();

                    TranslateTransition tt = new TranslateTransition(Duration.millis(350), subScene.getRoot());
                    tt.setFromX(-subScene.getWidth());
                    tt.setToX(0);
                    tt.play();
                }
            }

        }

    }

    public void playMouseEnterAnimation(MouseEvent event) {

        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();

            switch (icon.getId()) {
                case "imgBack":
                    lblMenu.setText("Back to Login");
                    lblDescription.setText("Click to Login Page");
                    break;
                case "imgCustomer":
                    lblMenu.setText("Manage Customers");
                    lblDescription.setText("Click to add, edit, delete, search or view customers");
                    break;
                case "imgItem":
                    lblMenu.setText("Manage Items");
                    lblDescription.setText("Click to add, edit, delete, search or view items");
                    break;
                case "imgOrder":
                    lblMenu.setText("Place Orders");
                    lblDescription.setText("Click here if you want to place a new order");
                    break;
                case "imgViewOrders":
                    lblMenu.setText("Search Orders");
                    lblDescription.setText("Click if you want to search orders");
                    break;
            }

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.CORNFLOWERBLUE);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            icon.setEffect(glow);
        }
    }

    public void playMouseExitAnimation(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();

            icon.setEffect(null);
            lblMenu.setText("Welcome");
            lblDescription.setText("Please select one of above main operations to proceed");
        }
    }
}


