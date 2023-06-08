package com.junks.brainup.controller;

import com.junks.brainup.Main;
import com.junks.brainup.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameSceneController {

    @FXML
    void profileGameButton(ActionEvent event) throws IOException {

    }

    @FXML
    void profileProfileButton(ActionEvent event) {
        StageManager.getInstance().getStage("gameSceneStage").close();
        StageManager.getInstance().getStage("mainStage").show();
    }


    @FXML
    void toChoseCalculator(ActionEvent event) throws IOException {
        StageManager.getInstance().getStage("gameSceneStage").close();
        Stage calculatorStage = new Stage();
        StageManager.getInstance().addStage("calculatorStage", calculatorStage);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxmls/calculatorGame.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 400);
        calculatorStage.setTitle("BrainUP");
        calculatorStage.setResizable(false);
        calculatorStage.setScene(scene);
        calculatorStage.show();

    }

    @FXML
    public void toMnemonics(ActionEvent event) throws IOException {
        StageManager.getInstance().getStage("gameSceneStage").close();
        Stage mnemonicsStage = new Stage();
        StageManager.getInstance().addStage("mnemonicsStage", mnemonicsStage);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxmls/mnemonicsGame.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 225, 350);
        mnemonicsStage.setTitle("BrainUP");
        mnemonicsStage.setResizable(false);
        mnemonicsStage.setScene(scene);
        mnemonicsStage.show();
    }
    @FXML
    public void toShulte(ActionEvent event) throws IOException {
        StageManager.getInstance().getStage("gameSceneStage").close();
        Stage schulteStage = new Stage();
        StageManager.getInstance().addStage("schulteStage", schulteStage);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxmls/schulteGame.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 400);
        schulteStage.setTitle("BrainUP");
        schulteStage.setResizable(false);
        schulteStage.setScene(scene);
        schulteStage.show();
    }
}
