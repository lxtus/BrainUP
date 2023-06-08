package com.junks.brainup.controller;

import com.junks.brainup.Main;
import com.junks.brainup.StageManager;
import com.junks.brainup.domain.User;
import com.junks.brainup.service.UserStorage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    public static User loggedInUser;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    void toLogin(ActionEvent event) throws IOException {
        UserStorage.getInstance().loadUsersFromJson("src/main/resources/com/junks/brainup/save/users.json");

        if (UserStorage.getInstance().getUsers().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Users not found");
            alert.showAndWait();
        } else {
            loggedInUser = null;

            for (User user : UserStorage.getInstance().getUsers()) {
                if (user.getLogin().equals(loginField.getText())
                        && user.getPassword().equals(passwordField.getText())) {
                    loggedInUser = user;
                    break;
                }
            }

            if (loggedInUser != null) {
                Stage mainStage = new Stage();
                StageManager.getInstance().addStage("mainStage", mainStage);
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxmls/main.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 300, 400);
                mainStage.setTitle("BrainUP");
                mainStage.setResizable(false);
                mainStage.setScene(scene);
                mainStage.show();
                StageManager.getInstance().getStage("loginStage").hide();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect");
                alert.showAndWait();
            }
        }
    }

    @FXML
    void toRegister(ActionEvent event) throws IOException {
        Stage registrationStage = new Stage();
        StageManager.getInstance().addStage("registrationStage", registrationStage);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxmls/signUP.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        registrationStage.setTitle("BrainUP");
        registrationStage.setResizable(false);
        registrationStage.setScene(scene);
        registrationStage.show();
        StageManager.getInstance().getStage("loginStage").hide();
    }

}
