package com.junks.brainup.controller;

import com.junks.brainup.StageManager;
import com.junks.brainup.service.UserStorage;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SchulteGameController {

    @FXML
    private Button btn1;

    @FXML
    private Button btn2;

    @FXML
    private Button btn3;

    @FXML
    private Button btn4;

    @FXML
    private Button btn5;

    @FXML
    private Button btn6;

    @FXML
    private Button btn7;

    @FXML
    private Button btn8;

    @FXML
    private Button btn9;

    @FXML
    private Button btn10;

    @FXML
    private Button btn11;

    @FXML
    private Button btn12;

    @FXML
    private Button btn13;

    @FXML
    private Button btn14;

    @FXML
    private Button btn15;

    @FXML
    private Button btn16;

    @FXML
    private ProgressBar progressBar;

    private Timeline timer;
    private List<Integer> sequence;
    private int currentNumber;

    private int scoreWon;
    private int scoreLost;

    @FXML
    public void initialize() {
        scoreWon = 0;
        scoreLost = 0;
        initializeButtons();
        startGame();
    }

    private void initializeButtons() {
        List<Button> buttons = Arrays.asList(
                btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8,
                btn9, btn10, btn11, btn12, btn13, btn14, btn15, btn16
        );

        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 16; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);

        for (int i = 0; i < buttons.size(); i++) {
            Button button = buttons.get(i);
            int number = numbers.get(i);
            button.setText(String.valueOf(number));
            button.setOnAction(event -> handleButtonPress(button, number));
        }
    }

    private void startGame() {
        sequence = new ArrayList<>();
        currentNumber = 1;
        enableButtons(true);
        setupProgressBar();
        startTimer();
    }

    private void handleButtonPress(Button button, int number) {
        if (number == currentNumber) {
            sequence.add(number);
            currentNumber++;
            button.setDisable(true);
            progressBar.setProgress((double) currentNumber / 16);
            if (currentNumber > 16) {
                handleGameWon();
            }
        } else {
            handleGameLost();
        }
    }

    private void handleGameWon() {
        scoreWon++;
        enableButtons(false);
        resetGame();
        LoginController.loggedInUser.setScoreWon(LoginController.loggedInUser.getScoreWon() + scoreWon);
        UserStorage.getInstance().saveUsersToJson("src/main/resources/com/junks/brainup/save/users.json");
        showAlert(AlertType.INFORMATION, "Гра пройдена", "Ви успішно пройшли гру!");
        StageManager.getInstance().getStage("schulteStage").close();
        StageManager.getInstance().getStage("gameSceneStage").show();
    }

    private void handleGameLost() {
        //enableButtons(false);
        //resetGame();

        UserStorage.getInstance().saveUsersToJson("src/main/resources/com/junks/brainup/save/users.json");
        showAlert(AlertType.ERROR, "Неправильне число", "Ви натиснули неправильне число!");
    }

    private void resetGame() {
        sequence.clear();
        currentNumber = 1;
        timer.stop();
    }

    private void enableButtons(boolean enable) {
        List<Button> buttons = Arrays.asList(
                btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8,
                btn9, btn10, btn11, btn12, btn13, btn14, btn15, btn16
        );

        buttons.forEach(button -> button.setDisable(!enable));
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }


    private void setupProgressBar() {
        progressBar.setProgress(0);
        progressBar.setStyle("-fx-accent: lightblue;");
    }

    private void startTimer() {
        KeyFrame keyFrame = new KeyFrame(
                Duration.seconds(15),
                new KeyValue(progressBar.progressProperty(), 1)
        );

        timer = new Timeline(keyFrame);
        timer.setOnFinished(event -> handleTimeUp());
        timer.play();
    }

    private void handleTimeUp() {
        scoreLost++;

        enableButtons(false);
        resetGame();
        LoginController.loggedInUser.setScoreLost(LoginController.loggedInUser.getScoreLost() + scoreLost);
        UserStorage.getInstance().saveUsersToJson("src/main/resources/com/junks/brainup/save/users.json");
        showAlert(AlertType.ERROR, "Час вийшов", "Час вийшов! Гра завершена.");
        StageManager.getInstance().getStage("schulteStage").close();
        StageManager.getInstance().getStage("gameSceneStage").show();

    }

}
