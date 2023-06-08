package com.junks.brainup.controller;

import com.junks.brainup.StageManager;
import com.junks.brainup.service.UserStorage;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;

import java.util.Random;

public class CalculatorGameController {

    @FXML
    private Button btn1;

    @FXML
    private Button btn2;

    @FXML
    private Button btn3;

    @FXML
    private Button btn4;

    @FXML
    private ProgressIndicator progressBar;

    @FXML
    private Label result;

    @FXML
    private Label task;

    private Random random;
    private int correctAnswer;
    private Button[] answerButtons;
    private Task<Void> timerTask;
    private int questionCount;
    private int scoreWon;
    private int scoreLost;

    @FXML
    private void initialize() {
        random = new Random();
        answerButtons = new Button[]{btn1, btn2, btn3, btn4};
        progressBar.setProgress(0.0);
        questionCount = 0;
        scoreWon = 0;
        scoreLost = 0;
        generateQuestion();
    }

    @FXML
    private void selectAnswer(ActionEvent event) {
        Button selectedButton = (Button) event.getSource();
        int userAnswer = Integer.parseInt(selectedButton.getText());

        stopTimer();

        if (userAnswer == correctAnswer) {
            scoreWon++;
            result.setText("Correct!");
        } else {
            scoreLost++;
            result.setText("Wrong!");
        }

        questionCount++;
        if (questionCount == 15) {
            showAlert("The game is over! Amount of correct answers: " + scoreWon + ", amount of wrong answers: " + scoreLost);
            closeWindow();
        } else {
            generateQuestion();
        }
    }

    private void generateQuestion() {
        int number1 = random.nextInt(10) + 1;
        int number2 = random.nextInt(10) + 1;

        int operatorIndex = random.nextInt(4);

        if (operatorIndex == 0) {
            task.setText(number1 + " + " + number2 + " = ?");
            correctAnswer = number1 + number2;
        } else if (operatorIndex == 1) {
            task.setText(number1 + " - " + number2 + " = ?");
            correctAnswer = number1 - number2;
        } else if (operatorIndex == 2) {
            task.setText(number1 + " * " + number2 + " = ?");
            correctAnswer = number1 * number2;
        } else {
            task.setText(number1 * number2 + " / " + number2 + " = ?");
            correctAnswer = number1;
        }

        setAnswerOptions();
        startTimer();
    }

    private void setAnswerOptions() {
        int correctButtonIndex = random.nextInt(4);

        for (int i = 0; i < 4; i++) {
            if (i == correctButtonIndex) {
                setButtonAnswer(answerButtons[i], correctAnswer);
            } else {
                int randomAnswer = generateRandomAnswer();
                setButtonAnswer(answerButtons[i], randomAnswer);
            }
        }
    }

    private int generateRandomAnswer() {
        int sign = random.nextBoolean() ? 1 : -1;
        int randomNumber = random.nextInt(10) + 1;

        if (task.getText().contains("+")) {
            return correctAnswer + (sign * randomNumber);
        } else if (task.getText().contains("-")) {
            return correctAnswer - (sign * randomNumber);
        } else if (task.getText().contains("*")) {
            return correctAnswer * (sign * randomNumber);
        } else {
            return correctAnswer / (sign * randomNumber);
        }
    }

    private void setButtonAnswer(Button button, int answer) {
        button.setText(String.valueOf(answer));
        button.setOnAction(this::selectAnswer);
    }

    private void startTimer() {
        progressBar.setProgress(0.0);
        stopTimer();
        timerTask = createTimerTask();
        new Thread(timerTask).start();
    }

    private void stopTimer() {
        if (timerTask != null) {
            timerTask.cancel();
        }
    }

    private Task<Void> createTimerTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (int i = 10; i >= 0; i--) {
                    if (isCancelled()) {
                        break;
                    }

                    final int secondsLeft = i;
                    Thread.sleep(1000);
                    double progress = (double) secondsLeft / 10.0;
                    Platform.runLater(() -> progressBar.setProgress(progress));

                    if (secondsLeft == 0) {
                        stopTimer();
                        Platform.runLater(() -> {
                            scoreLost++;
                            showAlert("Time is over! Amount of correct answers: " + scoreWon + ", amount of wrong answers: " + scoreLost);
                            generateQuestion();
                        });
                        break;
                    }
                }
                return null;
            }
        };
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Attention!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setOnHidden(event -> generateQuestion());
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) btn1.getScene().getWindow();

        if (scoreWon == 15) {
            LoginController.loggedInUser.setScoreWon(LoginController.loggedInUser.getScoreWon() + 1);
        }
        else {
            LoginController.loggedInUser.setScoreLost(LoginController.loggedInUser.getScoreLost() + 1);
        }

        UserStorage.getInstance().saveUsersToJson("src/main/resources/com/junks/brainup/save/users.json");
        StageManager.getInstance().getStage("mainStage").show();
        stopTimer();
        stage.close();
    }


}
