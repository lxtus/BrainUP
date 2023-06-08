package com.junks.brainup.controller;

import com.junks.brainup.StageManager;
import com.junks.brainup.domain.User;
import com.junks.brainup.service.UserStorage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class RegistrationController {

    @FXML
    private CheckBox checkFemaleField;

    @FXML
    private CheckBox checkMaleField;

    @FXML
    private TextField countryField;

    @FXML
    private TextField loginField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField passField;

    @FXML
    private TextField surnameField;

    @FXML
    void toSignUp(ActionEvent event) {
        UserStorage.getInstance().addUser(toFillUser());
        UserStorage.getInstance().saveUsersToJson("src/main/resources/com/junks/brainup/save/users.json");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registration");
        alert.setHeaderText("Success");
        alert.showAndWait();
        StageManager.getInstance().getStage("registrationStage").close();
        StageManager.getInstance().getStage("loginStage").show();
    }

    private User toFillUser() {
        User user = new User(
                nameField.getText(),
                surnameField.getText(),
                passField.getText(),
                countryField.getText(),
                loginField.getText()
        );


        if (checkFemaleField.isSelected()) {
            user.setSex("Female");
        }

        if (checkMaleField.isSelected()) {
            user.setSex("Male");
        }


        return user;
    }


}
