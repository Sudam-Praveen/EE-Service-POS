package controller;

import DAO.Utill.BOType;
import bo.BOFactory;
import bo.custom.CustomerBO;
import bo.custom.ItemBO;
import bo.custom.OrderBO;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dto.Customer;
import dto.Item;
import dto.Order;
import dto.Tm.ItemTm;
import dto.Tm.OrderTm;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class PlaceOrderFormController {


    public Label lblUserID1;
    public TreeTableColumn colOrderID;
    public TreeTableColumn colItemCode;
    public TreeTableColumn colPdctName;
    public TreeTableColumn colStatus;
    public TreeTableColumn colProcZone;
    public TreeTableColumn colOption;
    public TreeTableColumn colConfirm;
    public JFXTextField txtAddedDate;
    public TreeTableColumn colUserID;
    public TreeTableColumn colDate;
    public JFXComboBox cmbComplete;
    public Button btnComplete;
    @FXML
    private JFXTextField txtProductName;

    @FXML
    private JFXTextField txtCustName;

    @FXML
    private JFXTextField txtCustEmail;

    @FXML
    private JFXTreeTableView<OrderTm> tbleOrders;

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

    @FXML
    private Label lblUserID;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblTodayDate;

    @FXML
    private JFXComboBox<String> cmdCustId;

    @FXML
    private JFXComboBox<String> cmbItemCode;

    @FXML
    private JFXTextField txtAddedName;
    private List<Item> items;
    private List<Customer> customers;
    private ItemBO itemBo = BOFactory.getInstance().getBoFactory(BOType.ITEM);
    private CustomerBO customerBo = BOFactory.getInstance().getBoFactory(BOType.CUSTOMER);
    private final OrderBO orderBo = BOFactory.getInstance().getBoFactory(BOType.ORDER);
    private Order orderDto = null;


    public void initialize() {
        lblUserID.setText(EmployeeDashBoardController.loggedInUserId);
        loadCustomerID();
        loadItemCode();
        idGenerate();
        calculateDate();
        loadOrderTable();
        btnComplete.setDisable(true);
        colOrderID.setCellValueFactory(new TreeItemPropertyValueFactory<>("orderID"));
        colUserID.setCellValueFactory(new TreeItemPropertyValueFactory<>("userId"));
        colPdctName.setCellValueFactory(new TreeItemPropertyValueFactory<>("productName"));
        colDate.setCellValueFactory(new TreeItemPropertyValueFactory<>("date"));
        colProcZone.setCellValueFactory(new TreeItemPropertyValueFactory<>("processZone"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btnDelete"));
        colConfirm.setCellValueFactory(new TreeItemPropertyValueFactory<>("btnConfirm"));
        cmdCustId.getSelectionModel().selectedItemProperty().addListener((observableValue, s, id) -> {
            for (Customer dto : customers) {
                if (dto.getCustId().equals(id)) {
                    txtCustName.setText(dto.getName());
                    txtCustEmail.setText(dto.getEmail());
                }

            }
        });
        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observableValue, s, code) -> {
            for (Item dto : items) {
                if (dto.getItemCode().equals(code)) {
                    txtProductName.setText(dto.getProductName());

                }
            }
        });
        tbleOrders.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            loadComboBox();
            try {
                setData(newValue);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        });
    }

    private void setData(TreeItem<OrderTm> newValue) throws SQLException, ClassNotFoundException {
        if (newValue != null) {
            cmbComplete.setValue(newValue.getValue().getProcessZone().getText());
            setValuesObject(newValue.getValue().getOrderID());
            btnComplete.setDisable(false);
        }
    }

    public void setValuesObject(String id) throws SQLException, ClassNotFoundException {
        List<Order> allOrders = orderBo.getAllOrders();

        for (Order order : allOrders) {
            if (order.getOrderID().equals(id)) {
                orderDto = order;
            }
        }

    }

    void loadComboBox() {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("Completed");
        cmbComplete.setItems(list);
    }

    private void calculateDate() {
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.ZERO, actionEvent -> lblTodayDate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
        ), new KeyFrame(Duration.seconds(60))); // Update every minute
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void loadCustomerID() {
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            customers = customerBo.getAllCustomers();
            for (Customer dto : customers) {
                list.add(dto.getCustId());
            }
            cmdCustId.setItems(list);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadItemCode() {
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            items = itemBo.getAllItems();
            for (Item dto : items) {
                list.add(dto.getItemCode());
            }
            cmbItemCode.setItems(list);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void idGenerate() {
        lblOrderId.setText(orderBo.generateID());
    }

    public void completeButtonOnAction(ActionEvent actionEvent) {

        // Create a confirmation dialog
        String confirmationMessage = String.format("Are you sure you want to COMPLETE this order (%s)?",orderDto.getOrderID());
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION, confirmationMessage);
        confirmationDialog.setTitle("Confirmation");

        // Show the dialog and wait for the user's response
        confirmationDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // User clicked "Yes"
                orderDto.setProcessing_zone(cmbComplete.getValue().toString());
                boolean updated = false;
                try {
                    updated = orderBo.updateOrder(new Order(
                            orderDto.getOrderID(),
                            orderDto.getUserID(),
                            orderDto.getCustId(),
                            orderDto.getDate(),
                            orderDto.getProductName(),
                            orderDto.getProcessing_zone()
                    ));

                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                if (updated) {
                    new Alert(Alert.AlertType.INFORMATION, "Order Completed!").show();
                    orderDto = null;
                    clearFields();
                    loadOrderTable();
                    btnComplete.setDisable(true);
                }
            } else {
                // User clicked "No" or closed the dialog
                // You can optionally handle this case
            }
        });

    }


    public void addOrderButtonAction(ActionEvent actionEvent) {


        boolean saved = false;
        if (cmdCustId.getValue().toString().isBlank() == false && txtCustName.getText().isBlank() == false
                && txtCustEmail.getText().isBlank() == false && cmbItemCode.getValue().toString().isBlank() == false
                && txtProductName.getText().isBlank() == false && txtAddedDate.getText().isBlank() == false) {
            try {
                saved = orderBo.saveOrder(new Order(
                        lblOrderId.getText().toString(),
                        lblUserID.getText().toString(),
                        cmdCustId.getValue().toString(),
                        txtAddedDate.getText(),
                        txtProductName.getText(),
                        "Processing"

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
            new Alert(Alert.AlertType.INFORMATION, "Order Saved!").show();
            loadOrderTable();
            clearFields();
            idGenerate();
        }

    }

    private void clearFields() {
        txtProductName.clear();
        txtAddedDate.clear();
        txtCustEmail.clear();
        txtCustName.clear();
        btnComplete.setDisable(true);
    }

    private void loadOrderTable() {
//------------------load table------------
        btnComplete.setDisable(true);
        ObservableList<OrderTm> tmlist = FXCollections.observableArrayList();
        try {
            List<Order> allOrders = orderBo.getAllOrders();

            for (Order order : allOrders) {
                Label procZone = null;
                try {
                    String dateString = order.getDate();

                    if (order != null) {
                        LocalDate enteredDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        LocalDate currentDate = LocalDate.now();
                        long daysDifference = ChronoUnit.DAYS.between(enteredDate, currentDate);
                        // long daysDifference = Math.abs(currentDate.until(enteredDate).getDays());
                        // Set the label text based on the order status
                        String labelText = order.getProcessing_zone();

                        // Set the default style
                        String style = "-fx-text-fill: #181818; -fx-font-size: 14px; -fx-font-weight: 600;";

                        // Check the conditions
                        if (daysDifference > 10) {
                            procZone = new Label("Pending(>10)");
                            style += "-fx-background-color: #ff0008;";
                        } else if (daysDifference > 5) {
                            procZone = new Label("Pending(5-10)");
                            style += "-fx-background-color: #FFA500;";
                        } else {
                            procZone = new Label("Processing(<5)");
                            style += "-fx-background-color: #efdc06;";
                        }
                        if (labelText.equals("Completed")) {
                            procZone = new Label("Completed");
                            style += "-fx-background-color: #1b7e12;";
                        }


                        // Create the label with the calculated style
                        if (procZone != null) {
                            procZone.setStyle(style);
                        }
                    } else {
                        new Alert(Alert.AlertType.INFORMATION, "Order is null.").show();
                    }
                } catch (Exception e) {
                    new Alert(Alert.AlertType.INFORMATION, "Invalid date format. Please use yyyy-MM-dd.").show();
                }


                JFXButton btn = new JFXButton("Delete");

                btn.setStyle("-fx-background-color: #F94C10; -fx-text-fill: #FFF6F6; -fx-font-weight: bold;");

                JFXButton btnC = new JFXButton("Confirm");
                btnC.setStyle("-fx-background-color: #1014f9; -fx-text-fill: #FFF6F6; -fx-font-weight: bold;");


                OrderTm orderTm = new OrderTm(
                        order.getOrderID(),
                        order.getUserID(),
                        order.getProductName(),
                        order.getDate(),
                        procZone,
                        btn,
                        btnC
                );
                btn.setOnAction(actionEvent -> {
                    deleteOrderTable(order.getOrderID());
                });
                btnC.setOnAction(actionEvent -> {


                });

                tmlist.add(orderTm);
                RecursiveTreeItem treeItem = new RecursiveTreeItem<>(tmlist, RecursiveTreeObject::getChildren);
                tbleOrders.setRoot(treeItem);
                tbleOrders.setShowRoot(false);
            }


        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    private void deleteOrderTable(String orderID) {
        // Create a confirmation dialog
        String confirmationMessage = String.format("Are you sure you want to delete this order (%s)?", orderID);
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION, confirmationMessage);
        confirmationDialog.setTitle("Confirmation");
        confirmationDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // User clicked "Yes"
                try {
                    boolean isDeleted = orderBo.deleteOrderTable(orderID);
                    if (isDeleted) {
                        new Alert(Alert.AlertType.INFORMATION, "Order Deleted!").show();
                        loadOrderTable();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
                    }
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
            } else {
                // User clicked "No" or closed the dialog
                // You can optionally handle this case
            }
        });
    }


    //============================Animation===================
    public void homeImageAction(MouseEvent event) {
        Stage stage = (Stage) tbleOrders.getScene().getWindow();

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
