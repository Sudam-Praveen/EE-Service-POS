package controller;

import DAO.Utill.BOType;
import bo.BOFactory;
import bo.custom.AdminBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dto.Tm.UserTm;
import dto.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class AdminDashBoardController {


    @FXML
    private JFXTextField txtUserId;

    @FXML
    private JFXTextField txtUserName;

    @FXML
    private JFXTextField txtUserEmail;

    @FXML
    private JFXTextField txtUserPW;

    @FXML
    private JFXTextField txtDesc;

    @FXML
    private JFXTreeTableView<UserTm> tblUser;

    @FXML
    private TreeTableColumn<?, ?> colUserID;

    @FXML
    private TreeTableColumn<?, ?> colUserName;

    @FXML
    private TreeTableColumn<?, ?> colUserEmail;

    @FXML
    private TreeTableColumn<?, ?> colUserPW;

    @FXML
    private TreeTableColumn<?, ?> colUserDesc;

    @FXML
    private TreeTableColumn<?, ?> colUserOption;
    private final AdminBO adminBO = BOFactory.getInstance().getBoFactory(BOType.USER);


    public void initialize() {
        colUserID.setCellValueFactory(new TreeItemPropertyValueFactory<>("userID"));
        colUserName.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        colUserEmail.setCellValueFactory(new TreeItemPropertyValueFactory<>("email"));
        colUserPW.setCellValueFactory(new TreeItemPropertyValueFactory<>("password"));
        colUserDesc.setCellValueFactory(new TreeItemPropertyValueFactory<>("contactDetails"));
        colUserOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));
        loadUserTable();
        tblUser.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setData(newValue);
        });
    }

    private void setData(TreeItem<UserTm> newValue) {
        if (newValue != null) {
            txtUserId.setEditable(false);
            txtUserId.setText(newValue.getValue().getUserID());
            txtUserName.setText(newValue.getValue().getName());
            txtUserEmail.setText(newValue.getValue().getEmail());
            txtUserPW.setText(newValue.getValue().getPassword());
            txtDesc.setText(newValue.getValue().getContactDetails());
        }
    }

    public void employeeButtoAction(ActionEvent actionEvent) {
    }

    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) tblUser.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml"))));
        stage.show();
    }

    public void updateButtonOnAction(ActionEvent actionEvent) {
        boolean updated = false;
        try {
            updated = adminBO.updateUser(new User(
                    txtUserId.getText(),
                    txtUserName.getText(),
                    txtUserEmail.getText(),
                    txtUserPW.getText(),
                    txtDesc.getText()
            ));
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (updated) {
            new Alert(Alert.AlertType.INFORMATION, "User Updated!").show();
            loadUserTable();
            clearFields();
        }
    }

    public void addButtonOnAction(ActionEvent actionEvent) {
        boolean saved = false;
        if (txtUserId.getText().isBlank() == false & txtUserName.getText().isBlank() == false
                & txtUserPW.getText().isBlank() == false & txtUserEmail.getText().isBlank() == false
                & txtDesc.getText().isBlank() == false){
            try {
                saved = adminBO.saveUser(new User(
                        txtUserId.getText(),
                        txtUserName.getText(),
                        txtUserEmail.getText(),
                        txtUserPW.getText(),
                        txtDesc.getText()
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
            new Alert(Alert.AlertType.INFORMATION, "User Saved!").show();
            loadUserTable();
            clearFields();
        }
    }

    private void clearFields() {
    }

    private void loadUserTable() {
        ObservableList<UserTm> tmList = FXCollections.observableArrayList();

        try {
            List<User> allUsers = adminBO.getAllUsers();
            for (User user : allUsers) {
                JFXButton btn = new JFXButton("Delete");
                btn.setStyle("-fx-background-color: #F94C10; -fx-text-fill: #FFF6F6;");
                UserTm u = new UserTm(
                        user.getUserID(),
                        user.getName(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getContactDetails(),
                        btn
                );
                btn.setOnAction(actionEvent -> {
                    deleteCustomer(u.getUserID());
                });
                tmList.add(u);
            }


            RecursiveTreeItem<UserTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
            tblUser.setRoot(treeItem);
            tblUser.setShowRoot(false);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    private void deleteCustomer(String id) {
        try {
            boolean isDeleted = adminBO.deleteUser(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "User Deleted!").show();
                loadUserTable();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
