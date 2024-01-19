package controller;

import DAO.Utill.BOType;
import bo.BOFactory;
import bo.custom.CustomerBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dto.Customer;
import dto.Tm.CustomerTm;
import dto.Tm.UserTm;
import dto.User;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class CustomerFormController {

    @FXML
    private JFXTextField txtCustId;

    @FXML
    private JFXTextField txtCustName;

    @FXML
    private JFXTextField txtCustDetails;

    @FXML
    private JFXTextField txtCustEmail;

    @FXML
    private JFXTreeTableView<CustomerTm> tblecustomers;

    @FXML
    private TreeTableColumn<?, ?> colCustId;

    @FXML
    private TreeTableColumn<?, ?> colCustName;

    @FXML
    private TreeTableColumn<?, ?> colCustEmail;

    @FXML
    private TreeTableColumn<?, ?> colCustDetails;

    @FXML
    private TreeTableColumn<?, ?> colCustOption;
    private final CustomerBO customerBO = BOFactory.getInstance().getBoFactory(BOType.CUSTOMER);

    public void initialize() {
        colCustId.setCellValueFactory(new TreeItemPropertyValueFactory<>("CustId"));
        colCustName.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        colCustEmail.setCellValueFactory(new TreeItemPropertyValueFactory<>("email"));
        colCustDetails.setCellValueFactory(new TreeItemPropertyValueFactory<>("contactDetails"));
        colCustOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));
        loadCustomerTable();
        tblecustomers.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setData(newValue);
        });
    }
    private void setData(TreeItem<CustomerTm> newValue) {
        if (newValue != null) {
            txtCustId.setEditable(false);
            txtCustId.setText(newValue.getValue().getCustId());
            txtCustName.setText(newValue.getValue().getName());
            txtCustEmail.setText(newValue.getValue().getEmail());
            txtCustDetails.setText(newValue.getValue().getContactDetails());
        }
    }


    public void updateButtonOnAction(ActionEvent actionEvent) {
        boolean updated = false;
        try {
            updated = customerBO.updateCustomer(new Customer(
                    txtCustId.getText(),
                    txtCustName.getText(),
                    txtCustEmail.getText(),
                    txtCustDetails.getText()
            ));
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (updated) {
            new Alert(Alert.AlertType.INFORMATION, "User Updated!").show();
            loadCustomerTable();
            clearFields();
        }

    }

    public void saveButtonOnAction(ActionEvent actionEvent) {
        boolean saved = false;
        if (txtCustId.getText().isBlank() == false && txtCustName.getText().isBlank() == false
                & txtCustEmail.getText().isBlank() == false && txtCustDetails.getText().isBlank() == false){
            try {
                saved = customerBO.saveCustomer(new Customer(
                        txtCustId.getText(),
                        txtCustName.getText(),
                        txtCustEmail.getText(),
                        txtCustDetails.getText()
                ));
            } catch (SQLIntegrityConstraintViolationException e) {
                new Alert(Alert.AlertType.ERROR, "Duplicate ID").show();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }else{
            new Alert(Alert.AlertType.ERROR, "Please Enter Details").show();
        }

        if (saved) {
            new Alert(Alert.AlertType.INFORMATION, "Customer Saved!").show();
            loadCustomerTable();
            clearFields();
        }
    }

    private void clearFields() {
        txtCustId.clear();
        txtCustName.clear();
        txtCustEmail.clear();
        txtCustDetails.clear();
        txtCustId.setEditable(true);
    }

    private void loadCustomerTable() {
        ObservableList<CustomerTm> tmList = FXCollections.observableArrayList();
        try {
            List<Customer> allCustomers = customerBO.getAllCustomers();
            for (Customer customer:allCustomers) {

                JFXButton btn = new JFXButton("Delete");
                btn.setStyle("-fx-background-color: #F94C10; -fx-text-fill: #FFF6F6;");

                CustomerTm tm = new CustomerTm(
                        customer.getCustId(),
                        customer.getName(),
                        customer.getEmail(),
                        customer.getContactDetails(),
                        btn
                );
                btn.setOnAction(actionEvent -> {
                    deleteCustomer(tm.getCustId());
                });
                tmList.add(tm);
            }
            RecursiveTreeItem treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
            tblecustomers.setRoot(treeItem);
            tblecustomers.setShowRoot(false);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteCustomer(String custId) {
        try {
            boolean isDeleted = customerBO.deleteCustomer(custId);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Deleted!").show();
                loadCustomerTable();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

//============================Animation===================
    public void homeImageAction(MouseEvent event) {
        Stage stage = (Stage) tblecustomers.getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/EmployeeDashBoard.fxml"));
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

    public void playMouseEnterAnimation(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();

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
        }
    }
}
