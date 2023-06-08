package com.junks.brainup.controller;

import com.junks.brainup.StageManager;
import com.junks.brainup.service.UserStorage;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class MnemonicsGameController {

    @FXML
    private Label example;

    @FXML
    private TextField number2;

    @FXML
    private TextField number1;

    @FXML
    private TextField number3;

    @FXML
    private TextField number4;

    private int scoreWon;
    private int scoreLost;

    @FXML
    private Label time;

    private final Random random = new Random();
    private String targetNumber;

    private Timeline timer;
    private final int timerDuration = 1;

    private FadeTransition fadeInTransition;
    private FadeTransition fadeOutTransition;

    @FXML
    public void initialize() {
        generateTargetNumber();
        displayTargetNumber();
        number1.setVisible(false);
        number2.setVisible(false);
        number3.setVisible(false);
        number4.setVisible(false);
        scoreLost = 0;
        scoreWon = 0;
        setupTransitions();
        startTimer();
    }

    private void generateTargetNumber() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(random.nextInt(10));
        }
        targetNumber = sb.toString();
    }

    private void displayTargetNumber() {
        example.setText(targetNumber);
    }

    private void setupTransitions() {
        fadeInTransition = new FadeTransition(Duration.seconds(0.5), example);
        fadeInTransition.setFromValue(0);
        fadeInTransition.setToValue(1);

        fadeOutTransition = new FadeTransition(Duration.seconds(0.5), example);
        fadeOutTransition.setFromValue(1);
        fadeOutTransition.setToValue(0);
    }

    @FXML
    public void startTimer() {
        if (timer != null) {
            timer.stop();
        }

        resetTextFields();

        AtomicInteger timeLeft = new AtomicInteger(timerDuration);
        time.setText(String.valueOf(timeLeft.get()));
        time.setVisible(true);

        timer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            timeLeft.getAndDecrement();
            time.setText(String.valueOf(timeLeft.get()));

            if (timeLeft.get() == 0) {
                enableInputFields(true);
                time.setVisible(false);
                timer.stop();
                hideTargetNumber();
                showInputFields();
            }
        }));

        timer.setCycleCount(timerDuration);
        timer.play();
    }

    private void resetTextFields() {
        number1.setText("");
        number2.setText("");
        number3.setText("");
        number4.setText("");
        number1.setVisible(false);
        number2.setVisible(false);
        number3.setVisible(false);
        number4.setVisible(false);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }

    private void enableInputFields(boolean enable) {
        number1.setVisible(enable);
        number2.setVisible(enable);
        number3.setVisible(enable);
        number4.setVisible(enable);
    }

    private void showInputFields() {
        fadeInTransition.setNode(number1);
        fadeInTransition.setOnFinished(event -> {
            fadeInTransition.setNode(number2);
            fadeInTransition.setOnFinished(event1 -> {
                fadeInTransition.setNode(number3);
                fadeInTransition.setOnFinished(event2 -> {
                    fadeInTransition.setNode(number4);
                    fadeInTransition.play();
                });
                fadeInTransition.play();
            });
            fadeInTransition.play();
        });

        fadeInTransition.play();
    }

    private void hideTargetNumber() {
        fadeOutTransition.setOnFinished(event -> {
            example.setVisible(false);
            example.setOpacity(1);
        });

        fadeOutTransition.play();
    }

    @FXML
    public void fillTextField(ActionEvent event) {
        Button button = (Button) event.getSource();
        String value = button.getText();

        if (number1.isVisible() && number1.getText().isEmpty()) {
            number1.setText(value);
        } else if (number2.isVisible() && number2.getText().isEmpty()) {
            number2.setText(value);
        } else if (number3.isVisible() && number3.getText().isEmpty()) {
            number3.setText(value);
        } else if (number4.isVisible() && number4.getText().isEmpty()) {
            number4.setText(value);
        }
    }

    @FXML
    public void deleteNumber(ActionEvent event) {
        if (!number4.getText().isEmpty()) {
            number4.clear();
        } else if (!number3.getText().isEmpty()) {
            number3.clear();
        } else if (!number2.getText().isEmpty()) {
            number2.clear();
        } else if (!number1.getText().isEmpty()) {
            number1.clear();
        }
    }

    @FXML
    private void checkAnswer() {
        String userInput = number1.getText() + number2.getText() + number3.getText() + number4.getText();
        if (userInput.equals(targetNumber)) {
            scoreWon++;
            LoginController.loggedInUser.setScoreWon(LoginController.loggedInUser.getScoreLost() + 1);
            UserStorage.getInstance().saveUsersToJson("src/main/resources/com/junks/brainup/save/users.json");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Congratulations");
            alert.setHeaderText(null);
            alert.setContentText("You entered the correct number!");

            alert.showAndWait();

            resetGame();
        } else {
            scoreLost++;
            LoginController.loggedInUser.setScoreLost(LoginController.loggedInUser.getScoreLost() + 1);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Wrong Answer");
            alert.setHeaderText(null);
            alert.setContentText("You entered the wrong number. Please try again.");

            alert.showAndWait();

            resetGame();
        }
    }

    private void resetGame() {
        resetTextFields();
        stopTimer();
        generateTargetNumber();
        displayTargetNumber();
        setupTransitions();
        startTimer();
        example.setVisible(true);
    }

    @FXML
    void toClose(ActionEvent event) {
        stopTimer();
        StageManager.getInstance().getStage("mnemonicsStage").close();
        StageManager.getInstance().getStage("mainStage").show();
    }
}
