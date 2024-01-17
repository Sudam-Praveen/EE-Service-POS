package controller;

import DAO.Utill.BOType;
import bo.BOFactory;
import bo.custom.ItemBO;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dto.Customer;
import dto.Item;
import dto.Tm.ItemTm;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.ResourceBundle;

public class ItemFormController {
    public Label lblUserID;


    @FXML
    private JFXTextField txtItemCode;

    @FXML
    private JFXTextField txtProductName;

    @FXML
    private  JFXComboBox<String> cmbCategory;

    @FXML
    private JFXTextField txtIntPrice;

    @FXML
    private JFXTreeTableView<ItemTm> tblItems;

    @FXML
    private TreeTableColumn<?, ?> colUserId;

    @FXML
    private TreeTableColumn<?, ?> colItemCode;

    @FXML
    private TreeTableColumn<?, ?> colCategory;

    @FXML
    private TreeTableColumn<?, ?> colPdctName;

    @FXML
    private TreeTableColumn<?, ?> colIntPrice;

    @FXML
    private TreeTableColumn<?, ?> colOption;
    private final ItemBO itemBO = BOFactory.getInstance().getBoFactory(BOType.ITEM);

    public void initialize() {
        lblUserID.setText(EmployeeDashBoardController.loggedInUserId);
        loadComboBox();
        colUserId.setCellValueFactory(new TreeItemPropertyValueFactory<>("userId"));
        colItemCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemCode"));
        colCategory.setCellValueFactory(new TreeItemPropertyValueFactory<>("category"));
        colPdctName.setCellValueFactory(new TreeItemPropertyValueFactory<>("productName"));
        colIntPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("initialPrice"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));
        loadItemTable();
        tblItems.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setData(newValue);
        });

    }

    private void setData(TreeItem<ItemTm> newValue) {
        if (newValue != null) {
            txtItemCode.setEditable(false);
            txtItemCode.setText(newValue.getValue().getItemCode());
            cmbCategory.setValue(newValue.getValue().getCategory());
            txtProductName.setText(newValue.getValue().getProductName());
            txtIntPrice.setText(String.valueOf(newValue.getValue().getInitialPrice()));
        }
    }

    void loadComboBox(){
        ObservableList<String> list = FXCollections.observableArrayList();
            list.add("Electronics");
            list.add("Electrical");
        cmbCategory.setItems(list);
    }

    public void updateButtonOnAction(ActionEvent actionEvent) {
        boolean updated = false;
        try {
            if(cmbCategory.getValue().isBlank()==false && txtItemCode.getText().isBlank()==false
            && txtIntPrice.getText().isBlank()==false && txtIntPrice.getText().isBlank()==false){
                updated = itemBO.updateItem(new Item(
                        lblUserID.getText(),
                        txtItemCode.getText(),
                        txtProductName.getText(),
                        cmbCategory.getValue().toString(),
                        Double.parseDouble(txtIntPrice.getText())
                ));
            }else{
                new Alert(Alert.AlertType.INFORMATION, "refreshed").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (updated) {
            new Alert(Alert.AlertType.INFORMATION, "Item Updated!").show();
            loadComboBox();
            loadItemTable();
            clearFields();
        }
    }

    public void saveButtonOnAction(ActionEvent actionEvent) {
        boolean saved = false;
        if (txtItemCode.getText().isBlank() == false & cmbCategory.getValue().isBlank() == false
                & txtProductName.getText().isBlank() == false & txtIntPrice.getText().isBlank() == false){
            try {
                 saved = itemBO.saveItem(new Item(
                         lblUserID.getText(),
                         txtItemCode.getText(),
                         txtProductName.getText(),
                        cmbCategory.getValue().toString(),
                        Double.parseDouble(txtIntPrice.getText())
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
            new Alert(Alert.AlertType.INFORMATION, "Item Saved!").show();
            loadItemTable();
            clearFields();
        }



    }

    private void loadItemTable() {
        ObservableList<ItemTm> tmList = FXCollections.observableArrayList();
        try {
            List<Item> allItems = itemBO.getAllItems();
            for (Item item:allItems) {
                JFXButton btn = new JFXButton("Delete");
                btn.setStyle("-fx-background-color: #F94C10; -fx-text-fill: #FFF6F6;");
                tmList.add(new ItemTm(
                        item.getUserId(),
                        item.getItemCode(),
                        item.getCategory(),
                        item.getProductName(),
                        item.getInitialPrice(),
                        btn

                ));
                btn.setOnAction(actionEvent -> {
                    deleteItem(item.getItemCode());
                });
            }
            RecursiveTreeItem treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
            tblItems.setRoot(treeItem);
            tblItems.setShowRoot(false);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteItem(String itemCode) {
        try {
            boolean isDeleted = itemBO.deleteItem(itemCode);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Item Deleted!").show();
                loadItemTable();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        txtItemCode.clear();
        txtProductName.clear();
        txtIntPrice.clear();

        txtItemCode.setEditable(true);


    }


    //============================Animation===================
    public void homeImageAction(MouseEvent event) {
        Stage stage = (Stage) lblUserID.getScene().getWindow();
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





