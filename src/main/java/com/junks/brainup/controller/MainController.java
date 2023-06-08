package com.junks.brainup.controller;

import com.junks.brainup.Main;
import com.junks.brainup.StageManager;
import com.junks.brainup.domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private Label countryValue;

    @FXML
    private TextField profileNameField;

    @FXML
    private TextField profileSurnameField;

    @FXML
    private Label sex;

    @FXML
    private Label won;

    @FXML
    private Label lost;

    @FXML
    private Label allGames;

    @FXML
    void profileGameButton(ActionEvent event) throws IOException {
        Stage gameSceneStage = new Stage();
        StageManager.getInstance().addStage("gameSceneStage", gameSceneStage);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxmls/gameScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 400);
        gameSceneStage.setTitle("BrainUP");
        gameSceneStage.setResizable(false);
        gameSceneStage.setScene(scene);
        StageManager.getInstance().getStage("mainStage").close();
        StageManager.getInstance().getStage("gameSceneStage").show();
    }

    @FXML
    void profileProfileButton(ActionEvent event) {

    }

    @FXML
    public void initialize() {
        User user = LoginController.loggedInUser;

        sex.setText(user.getSex());
        profileNameField.setText(user.getFirstName());
        profileSurnameField.setText(user.getLastName());
        countryValue.setText(user.getCountry());

        won.setText(String.valueOf(user.getScoreWon()));
        lost.setText(String.valueOf(user.getScoreLost()));
        allGames.setText(String.valueOf(user.getScoreLost()
                + user.getScoreWon()));


    }

}
