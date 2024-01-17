package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dto.User;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import DAO.Custom.Impl.UserModelImpl;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class LoginFormController {
    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;
    public Label loginMsgLabel;
    public UserModelImpl userModel = new UserModelImpl();

    public AnchorPane pane;

    public void handleLoginButtonAction(ActionEvent actionEvent) {

        if (txtUserName.getText().isBlank() == false && txtPassword.getText().isBlank() == false) {

            loginMsgLabel.setText("You Try to login");

            List<User> users = userModel.userValidation();
            boolean isAvailable = false;
            for (User user : users) {

                if (user.getUserID().equals(txtUserName.getText()) && user.getPassword().equals(txtPassword.getText())) {
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
                        loginMsgLabel.setText("Employee " + user.getUserID() + " : " + user.getName() + " Login");


                       Stage stage = (Stage) pane.getScene().getWindow();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EmployeeDashBoard.fxml"));
                        Scene scene = new Scene(loader.load());

                        EmployeeDashBoardController employeeController = loader.getController();
                        employeeController.setLoggedInUserId(txtUserName.getText(), user.getName());  // Pass the user ID

                        stage.setScene(scene);

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
    }
}
//public class LoginFormController {
//    // Existing code...
//
//    public void handleLoginButtonAction(ActionEvent actionEvent) {
//        // Existing code...
//
//        for (User user : users) {
//            if (user.getUserID().equals(txtUserName.getText()) && user.getPassword().equals(txtPassword.getText())) {
//                isAvailable = true;
//                if (Objects.equals(txtUserName.getText(), "Admin")) {
//                    // Existing code...
//
//                } else {
//                    loginMsgLabel.setText("Employee " + user.getUserID() + " : " + user.getName() + " Login");
//
//                    //---------To Employee DashBoard------------------
//                    Stage stage = (Stage) pane.getScene().getWindow();
//                    try {
//                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EmployeeDashBoard.fxml"));
//                        Scene scene = new Scene(loader.load());
//
//                        EmployeeDashBoardController employeeController = loader.getController();
//                        employeeController.setLoggedInUserId(txtUserName.getText());  // Pass the user ID
//
//                        stage.setScene(scene);
//                        stage.show();
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    new Alert(Alert.AlertType.CONFIRMATION, "LoginSuccess As Employee").show();
//                }
//            }
//        }
//
//        // Existing code...
//    }
//
//    // Existing code...
//}
