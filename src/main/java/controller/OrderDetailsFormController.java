package controller;

import DAO.Utill.BOType;
import bo.BOFactory;
import bo.custom.OrderBO;
import bo.custom.OrderDetailsBO;
import com.jfoenix.controls.JFXTextField;
import dto.Customer;
import dto.Item;
import dto.Order;
import dto.OrderDetails;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class OrderDetailsFormController {

    public Label mainText;
    public AnchorPane pane;
    public Button btnconfirm;
    public Button btnTotal;
    @FXML
    private ImageView imgBack;

    @FXML
    private JFXTextField txtAdditionalCost;

    @FXML
    private JFXTextField txtAdditionalItem;

    @FXML
    private Label lblItemName;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblTotal;

    @FXML
    private JFXTextField txtFinishedDate;

    @FXML
    private Label lblItemCode;

    @FXML
    private Label lblCustId;

    @FXML
    private Label lblCustEmail;

    @FXML
    private Label lblCustName;

    @FXML
    private Label lblUserID;
    public Order order;
    public Customer customer;
    public Item item;
    private final OrderDetailsBO orderDetailsBO = BOFactory.getInstance().getBoFactory(BOType.ORDER_DETAIL);
    private final OrderBO orderBO = BOFactory.getInstance().getBoFactory(BOType.ORDER);
    public void setData(Order order, Customer customer, Item item) {
        this.order = order;
        this.customer = customer;
        this.item = item;
        if (order != null && customer != null && item != null) {
            lblOrderId.setText(order.getOrderID());
            lblUserID.setText(EmployeeDashBoardController.loggedInUserId);
            lblItemCode.setText(item.getItemCode());
            lblItemName.setText(item.getProductName());
            lblCustId.setText(customer.getCustId());
            lblCustName.setText(customer.getName());
            lblCustEmail.setText(customer.getEmail());
            new Alert(Alert.AlertType.INFORMATION, "label Set.").show();
        } else {
            new Alert(Alert.AlertType.INFORMATION, "object Not Set.").show();
        }
    }

    public void initialize() {
        btnconfirm.setDisable(true);
        btnTotal.setDisable(false);
    }

    public void confirmBUttonOnAction(ActionEvent actionEvent) {



        boolean saved = false;
        if (txtAdditionalItem.getText().isBlank() == false && txtAdditionalCost.getText().toString().isBlank() == false
                && txtFinishedDate.getText().isBlank() == false) {
            try {
                saved = orderDetailsBO.saveOrderDetails(new OrderDetails(
                        lblOrderId.getText().toString(),
                        lblUserID.getText().toString(),
                        lblItemCode.getText().toString(),
                        lblItemName.getText().toString(),
                        lblCustName.getText().toString(),
                        lblCustEmail.getText().toString(),
                        txtFinishedDate.getText(),
                        txtAdditionalItem.getText(),
                        Double.parseDouble(txtAdditionalCost.getText()),
                        Double.parseDouble(lblTotal.getText())

                ));

            } catch (SQLIntegrityConstraintViolationException e) {
                new Alert(Alert.AlertType.ERROR, "Duplicate ID").show();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }


        } else {
            new Alert(Alert.AlertType.ERROR, "Please Enter Details").show();
        }

        if (saved) {
            new Alert(Alert.AlertType.INFORMATION, "Order Placed!").show();
            order.setProcessing_zone("Closed");
            try {
                orderBO.updateOrder(order);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            clearFields();
        }
    }
    public void getTotalButtonOnAction(ActionEvent actionEvent) {
        double total= item.getInitialPrice()+Double.parseDouble(txtAdditionalCost.getText());
        lblTotal.setText(String.format("%.2f",total));
        btnconfirm.setDisable(false);
    }
    private void clearFields() {
        btnTotal.setDisable(true);
        txtAdditionalCost.clear();
        txtAdditionalItem.clear();
        txtFinishedDate.clear();

    }


    public void backIconAction(MouseEvent event) {
        Stage stage = (Stage) lblOrderId.getScene().getWindow();
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/PlaceOrderForm.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.centerOnScreen();
            TranslateTransition tt = new TranslateTransition(Duration.millis(350), scene.getRoot());
            tt.setFromX(-scene.getWidth());
            tt.setToX(0);
            tt.play();

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playMouseEnterAnimation(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();
            mainText.setText("Back To Place Order");
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
            mainText.setText("Admin Form");
            icon.setEffect(null);
        }
    }



}
