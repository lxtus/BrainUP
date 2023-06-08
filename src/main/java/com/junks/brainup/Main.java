package com.junks.brainup;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        Stage loginStage = stage;
        StageManager.getInstance().addStage("loginStage",loginStage);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxmls/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 350);
        loginStage.setTitle("BrainUP");
        loginStage.setResizable(false);
        loginStage.setScene(scene);
        loginStage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}

