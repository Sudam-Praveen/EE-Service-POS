package controller;

import DAO.Utill.BOType;
import bo.BOFactory;
import bo.custom.LoginBO;
import bo.custom.impl.LoginBOImpl;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dto.User;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import DAO.Custom.Impl.LoginDAOImpl;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class LoginFormController {
    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;
    public Label loginMsgLabel;

    public LoginBO loginBO = BOFactory.getInstance().getBoFactory(BOType.LOGIN);
    public AnchorPane pane;

    public void handleLoginButtonAction(ActionEvent actionEvent) {

        if (txtUserName.getText().isBlank() == false && txtPassword.getText().isBlank() == false) {

            loginMsgLabel.setText(" Wrong login");

            List<User> users = loginBO.getUsers();
            boolean isAvailable = false;
            for (User user : users) {

                if (user.getUserID().equals(txtUserName.getText()) & loginBO.isPasswordCorrect(txtUserName.getText(),txtPassword.getText())) {
                    isAvailable = true;
                    if (Objects.equals(txtUserName.getText(), "Admin")) {
                        loginMsgLabel.setText("Admin Login");
                        new Alert(Alert.AlertType.CONFIRMATION, "LoginSuccess as Admin").show();

                        //---------To Admin DashBoard------------------
                        Stage stage = (Stage) pane.getScene().getWindow();
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminDashBoard.fxml"));
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




                    } else {
                        //------------To Employee Dashboard-----------------

                        loginMsgLabel.setText("Employee " + user.getUserID() + " : " + user.getName() + " Login");


                       Stage stage = (Stage) pane.getScene().getWindow();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EmployeeDashBoard.fxml"));
                        Scene scene = new Scene(loader.load());

                        EmployeeDashBoardController employeeController = loader.getController();
                        employeeController.setLoggedInUserId(txtUserName.getText(), user.getName());  // Pass the user ID

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
                        new Alert(Alert.AlertType.CONFIRMATION, "LoginSuccess As Employee").show();
                    }
                }
            }
            if (!isAvailable) {
                new Alert(Alert.AlertType.ERROR, "Wrong Username Password").show();
            }
        } else {
            loginMsgLabel.setText("Please Enter Username and Password.");
        }
    }

    private void userValidation() {
    }

    private void validateLogin() {

    }

    public void handleCancelButtonAction(ActionEvent actionEvent) {
        Platform.exit();
    }
}
