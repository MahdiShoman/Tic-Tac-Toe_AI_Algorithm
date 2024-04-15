import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * its a tic tac toe between two players
 * it need to save the score of every player (optional)*/
public class Test extends Application {
    private static final int BOARD_SIZE = 3;
    private String player1Name = "Player 1";
    private String player2Name = "Player 2";
    private int totalRounds = 5;
    private int currentRound = 1;
    private char currentPlayer = 'X';
    private char[][] board = new char[BOARD_SIZE][BOARD_SIZE];
    private Label statusLabel = new Label();
    Alert alertWin = new Alert(Alert.AlertType.CONFIRMATION);
    private int player1Wins = 0;
    private int player2Wins = 0;
    private Label player1WinsLabel = new Label("Player 1 Wins: 0");
    private Label player2WinsLabel = new Label("Player 2 Wins: 0");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        getGameSettings();
        initializeBoard();
        displayBoard(primaryStage);

        Scene scene = new Scene(createGameLayout(),600,600, Color.WHITE);

        primaryStage.setTitle("Tic-Tac-Toe");
        primaryStage.setScene(scene);
        setStage(primaryStage);
        primaryStage.show();
    }
    private void getGameSettings() {
        // Prompt the user for the number of rounds
        totalRounds = getNumberOfRounds();

        // Prompt the user to choose the first player and enter player names
        chooseFirstPlayer();
    }

    private int getNumberOfRounds() {
        TextInputDialog roundsDialog = new TextInputDialog("3");
        roundsDialog.setHeaderText("Enter the number of rounds:");
        roundsDialog.setContentText("Number of Rounds:");

        while (true) {
            try {
                String input = roundsDialog.showAndWait().orElse("3");
                int rounds = Integer.parseInt(input);

                if (rounds > 0) {
                    return rounds;
                } else {
                    throw new IllegalArgumentException("Please enter a positive integer.");
                }
            } catch (NumberFormatException e) {
                showAlert("Invalid input. " + e.getMessage());
            } catch (IllegalArgumentException e) {
                showAlert("Invalid input. " + e.getMessage());
            }
        }
    }

    private void chooseFirstPlayer() {
        TextInputDialog player1Dialog = new TextInputDialog("Player 1");
        player1Dialog.setHeaderText("Enter Player 1's name:");
        player1Dialog.setContentText("Player 1 Name:");

        player1Name = player1Dialog.showAndWait().orElse("Player 1");

        TextInputDialog player2Dialog = new TextInputDialog("Player 2");
        player2Dialog.setHeaderText("Enter Player 2's name:");
        player2Dialog.setContentText("Player 2 Name:");

        player2Name = player2Dialog.showAndWait().orElse("Player 2");

        getFirstPlayer();
    }
    void getFirstPlayer(){
        ChoiceDialog<String> firstPlayerDialog = new ChoiceDialog<>(player1Name, player2Name);
        firstPlayerDialog.setHeaderText("Choose the first player:");
        firstPlayerDialog.setContentText("First Player:");

        firstPlayerDialog.showAndWait().ifPresent(choice -> {
            currentPlayer = (choice.equals("Player 1")) ? 'X' : 'O';
        });
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void initializeBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = ' ';
            }
        }
    }

    private GridPane createGameLayout() {
        GridPane gridPane = new GridPane();

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Rectangle tile = createTile(i, j);
                gridPane.add(tile, j, i);
            }
        }

        statusLabel.setText("Current Round: " + currentRound + " | Current Player: " + player1Name);
        gridPane.add(statusLabel, 0, BOARD_SIZE, BOARD_SIZE, 1);

        HBox winsBox = new HBox(10);
        winsBox.setAlignment(Pos.CENTER);
        winsBox.getChildren().addAll(player1WinsLabel, player2WinsLabel);
        gridPane.add(winsBox, 0, 4, 3, 1);

        return gridPane;
    }

    private void updateWinsLabels() {
        player1WinsLabel.setText(player1Name + " Wins: " + player1Wins);
        player2WinsLabel.setText(player2Name + " Wins: " + player2Wins);
    }


    private Rectangle createTile(int row, int col) {
        Rectangle rectangle = new Rectangle(160, 160);
        rectangle.setFill(Color.LIGHTGRAY);
        rectangle.setStroke(Color.BLACK);


        rectangle.setOnMouseClicked(e -> handleTileClick(row, col, rectangle));

        return rectangle;
    }

    private void handleTileClick(int row, int col, Rectangle rectangle) {
        if (board[row][col] == ' ' && !isGameFinished()) {
            board[row][col] = currentPlayer;
            updateTileText(rectangle);
            if (checkWinner(row, col)) {
                statusLabel.setText("Player " + currentPlayer + " wins!");
                alertWin.setContentText("Player " + currentPlayer + " wins!");
                alertWin.showAndWait();
                endRound();
            } else if (isBoardFull()) {
                statusLabel.setText("It's a draw!");
                endRound();
            } else {
                switchPlayer();
            }
        }
    }

    private boolean checkWinner(int row, int col) {
        return checkRow(row) || checkColumn(col) || checkDiagonal() || checkAntiDiagonal();
    }

    private boolean checkRow(int row) {
        return board[row][0] == currentPlayer && board[row][1] == currentPlayer && board[row][2] == currentPlayer;
    }

    private boolean checkColumn(int col) {
        return board[0][col] == currentPlayer && board[1][col] == currentPlayer && board[2][col] == currentPlayer;
    }

    private boolean checkDiagonal() {
        return board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer;
    }

    private boolean checkAntiDiagonal() {
        return board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        statusLabel.setText("Current Round: " + currentRound + " | Current Player: " +
                ((currentPlayer == 'X') ? player1Name : player2Name));
    }

    private void updateTileText(Rectangle rectangle) {
        Label label = new Label(String.valueOf(currentPlayer));
        label.setStyle("-fx-font-size: 40;");
        label.setAlignment(Pos.CENTER);
        Insets insets = new Insets(40);
        label.setPadding(insets);

        GridPane.setConstraints(label, GridPane.getColumnIndex(rectangle), GridPane.getRowIndex(rectangle));
        ((GridPane) rectangle.getParent()).getChildren().add(label);
    }
    Stage stage = new Stage();
    private void endRound() {
        if (currentRound < totalRounds) {
            if (checkWinner()) {
                updateWinCount();
            }
            currentRound++;
            currentPlayer = (currentRound % 2 == 1) ? 'X' : 'O';
            initializeBoard();

            // Update statusLabel to show the current round and player names
            statusLabel.setText("Round: " + currentRound + " | Current Player: " + (currentPlayer == 'X' ? player1Name : player2Name));
            updateWinsLabels();

            // Create a new scene with the updated layout
            Scene newScene = new Scene(createGameLayout(),600,600, Color.WHITE);
            // Get the current scene and set the updated layout
            Scene currentScene = statusLabel.getScene();
            GridPane updatedLayout = createGameLayout();
            currentScene.setRoot(updatedLayout);
            // Get the primaryStage from the current scene and set the new scene
           /* Stage primaryStage = (Stage) statusLabel.getScene().getWindow();
            primaryStage.setScene(newScene);*/
            stage.setScene(newScene);
            stage.show();
        } else {
            statusLabel.setText("Game Over!");
            //
            //if hit yes
            ChoiceDialog<String> firstPlayerDialog = new ChoiceDialog<>("Yes", "No");
            firstPlayerDialog.setHeaderText("Restart Game ?");
            firstPlayerDialog.setContentText("Choose:");
            AtomicBoolean isRestart= new AtomicBoolean(false);
            firstPlayerDialog.showAndWait().ifPresent(choice -> {
                isRestart.set(choice.equals("Yes"));
            });
            if (isRestart.get()) {
                totalRounds=0;
                currentRound=0;
                getNumberOfRounds();
                getFirstPlayer();
                initializeBoard();
                displayBoard(stage);
            }else {
                System.exit(0);
            }

        }
    }


    private void displayBoard(Stage primaryStage) {
        GridPane gridPane = createGameLayout();
       gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(5, 5, 5, 5));
        Scene scene = new Scene(gridPane, 600, 600);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Tic-Tac-Toe");
        primaryStage.show();
    }

    private boolean isGameFinished() {
        return checkForWinner() || isBoardFull();
    }

    private boolean checkForWinner() {
        return checkRows() || checkColumns() || checkDiagonals();
    }

    private boolean checkRows() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (checkRow(i)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkColumns() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (checkColumn(i)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonals() {
        return checkDiagonal() || checkAntiDiagonal();
    }
    private void updateWinCount() {
        if (currentPlayer == 'X') {
            player1Wins++;
        } else {
            player2Wins++;
        }
        updateWinsLabels();
    }

    private boolean checkWinner() {
        return checkRow(0) || checkRow(1) || checkRow(2) ||
                checkColumn(0) || checkColumn(1) || checkColumn(2) ||
                checkDiagonal() || checkAntiDiagonal();
    }

}
