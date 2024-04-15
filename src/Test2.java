import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Random;

public class Test2 extends Application {
    private static final int SIZE = 3;
    private static final String PLAYER_X = "X";
    private static final String PLAYER_O = "O";

    private int rounds;
    private int currentRound;
    private String currentPlayer;
    private Button[][] buttons = new Button[SIZE][SIZE];

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        chooseNumberOfRounds(); // Prompt user for number of rounds
        chooseFirstPlayer();    // Prompt user to choose the first player

        GridPane gridPane = createGridPane();
        initializeButtons(gridPane);
        resetGame();

        Scene scene = new Scene(gridPane, 300, 300);
        primaryStage.setTitle("Tic-Tac-Toe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void chooseNumberOfRounds() {
        // Prompt user for the number of rounds
        // For simplicity, assuming valid input
        rounds = 3; // Change this line to read user input
    }

    private void chooseFirstPlayer() {
        // Prompt user to enter their name and choose the first player
        // For simplicity, assuming valid input
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Player Information");
        alert.setHeaderText("Enter Your Name and Choose the First Player");
        alert.setContentText("Enter your name:");
        String playerName = alert.showAndWait().map(ButtonType::getText).orElse("Player");
        alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Choose First Player");
        alert.setHeaderText("Hi, " + playerName + "! Choose the First Player");
        alert.setContentText("Choose 'OK' to be the first player (X).\nChoose 'Cancel' for the computer to be the first player (O).");

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            currentPlayer = PLAYER_X;
        } else {
            currentPlayer = PLAYER_O;
        }
    }

    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        return gridPane;
    }

    private void initializeButtons(GridPane gridPane) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Button button = new Button();
                button.setMinSize(100, 100);
                int finalRow = row;
                int finalCol = col;
                button.setOnAction(e -> handleButtonClick(button, finalRow, finalCol));
                buttons[row][col] = button;
                gridPane.add(button, col, row);
            }
        }
    }

    private void handleButtonClick(Button button,int row,int col) {
        if (!button.getText().isEmpty()) {
            return; // Cell already occupied
        }

        button.setText(currentPlayer);
        if (checkWinner(row,col)) {
            showWinnerAlert();
            resetGame();
        } else if (isBoardFull()) {
            showDrawAlert();
            resetGame();
        } else {
            switchPlayer();
            makeComputerMove();
            if (checkWinner(row,col)) {
                showWinnerAlert();
                resetGame();
            } else if (isBoardFull()) {
                showDrawAlert();
                resetGame();
            }
        }
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer.equals(PLAYER_X)) ? PLAYER_O : PLAYER_X;
    }

    private void makeComputerMove() {
        Random random = new Random();
        int row, col;
        do {
            row = random.nextInt(SIZE);
            col = random.nextInt(SIZE);
        } while (!buttons[row][col].getText().isEmpty());

        buttons[row][col].setText(currentPlayer);
        checkWinner(row,col);
        switchPlayer();
    }

    boolean checkWinner(int row, int col) {
        return checkRow(row) || checkColumn(col) || checkDiagonal() || checkAntiDiagonal();
    }

    private boolean checkRow(int row) {
        return buttons[row][0].getText().equals(currentPlayer) && buttons[row][1] .getText().equals(currentPlayer)&& buttons[row][2].getText().equals(currentPlayer);
    }

    private boolean checkColumn(int col) {
        return buttons[0][col] .getText().equals(currentPlayer) && buttons[1][col].getText().equals(currentPlayer) && buttons[2][col] .getText().equals(currentPlayer);
    }

    private boolean checkDiagonal() {
        return buttons[0][0] .getText().equals(currentPlayer) && buttons[1][1] .getText().equals(currentPlayer) && buttons[2][2] .getText().equals(currentPlayer);
    }

    private boolean checkAntiDiagonal() {
        return buttons[0][2] .getText().equals(currentPlayer) && buttons[1][1] .getText().equals(currentPlayer) && buttons[2][0] .getText().equals(currentPlayer);
    }
    private boolean checkDiagonals() {
        return (buttons[0][0].getText().equals(currentPlayer) &&
                buttons[1][1].getText().equals(currentPlayer) &&
                buttons[2][2].getText().equals(currentPlayer)) ||
                (buttons[0][2].getText().equals(currentPlayer) &&
                        buttons[1][1].getText().equals(currentPlayer) &&
                        buttons[2][0].getText().equals(currentPlayer));
    }

    private boolean isBoardFull() {
        for (Button[] row : buttons) {
            for (Button button : row) {
                if (button.getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void showWinnerAlert() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Round " + currentRound + " Result");
        alert.setHeaderText("Player " + currentPlayer + " wins!");
        alert.setContentText("Congratulations, " + currentPlayer + "!");

        if (currentRound < rounds) {
            alert.setContentText(alert.getContentText() + "\n\nStarting Round " + (currentRound + 1));
            currentRound++;
            resetGame();
        } else {
            alert.setContentText(alert.getContentText() + "\n\nGame Over");
            resetGame();
            currentRound = 1; // Reset current round for the next game
        }

        alert.showAndWait();
    }

    private void showDrawAlert() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Round " + currentRound + " Result");
        alert.setHeaderText("It's a draw!");

        if (currentRound < rounds) {
            alert.setContentText(alert.getContentText() + "\n\nStarting Round " + (currentRound + 1));
            currentRound++;
            resetGame();
        } else {
            alert.setContentText(alert.getContentText() + "\n\nGame Over");
            resetGame();
            currentRound = 1; // Reset current round for the next game
        }

        alert.showAndWait();
    }

    private void resetGame() {
        currentPlayer = PLAYER_X;
        for (Button[] row : buttons) {
            for (Button button : row) {
                button.setText("");
            }
        }
    }
}
