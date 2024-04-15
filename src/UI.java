import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import static javafx.application.Application.launch;

public class UI extends Application {

    private static final int Board_Size = 3;
    Alert alertWin = new Alert(Alert.AlertType.CONFIRMATION);
    Object modeGameObject;
    String firstPlayer;
    Stage stage_ = new Stage();
    Stage stage = new Stage();
    private Player player1Name = new Player() ;
    private Player player2Name = new Player() ;
    private int totalRounds = 5;
    private int currentRound = 1;
    private char currentPlayer = 'X';
    private char[][] board = new char[Board_Size][Board_Size];
    private Rectangle[][] rectangles = new Rectangle[Board_Size][Board_Size];
    private Label statusLabel = new Label();
    private int player1Wins = 0;
    private int player2Wins = 0;
    private Label player1WinsLabel ;
    private Label player2WinsLabel ;
    private String gameMode = "";
     Button[][] buttons = new Button[Board_Size][Board_Size];

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        getGameSettings();
       // initializeBoard();
        GridPane gridPane = createGridPane();
        initializeButtons(gridPane);
        //displayBoard(stage_);
        Scene scene = new Scene(gridPane,600,600, Color.WHITE);

        stage_.setTitle("Tic-Tac-Toe");
        stage_.setScene(scene);
        //setStage(primaryStage);
        stage_.show();
    }

    public void start(){
       /* getGameSettings();
        initializeBoard();
        displayBoard(stage_);
        Scene scene = new Scene(createGameLayout(),600,600, Color.WHITE);*/
        getGameSettings();
        // initializeBoard();
        GridPane gridPane = createGridPane();
        initializeButtons(gridPane);
        //displayBoard(stage_);
        Scene scene = new Scene(gridPane,600,600, Color.WHITE);
        stage_.setTitle("Tic-Tac-Toe");
        stage_.setScene(scene);
        //setStage(primaryStage);
        stage_.show();
        checkTurn();
    }

    void checkTurn(){
        //randomIndex(board);
        if(firstPlayer.equals("Computer")){
            ((EasyMode)getModeGameObject()).randomIndex(board);
        }
    }

    private void getGameSettings() {
        // Prompt the user to choose the first player and enter player names
        chooseGameMode();
        // Prompt the user for the number of rounds
        totalRounds = getNumberOfRounds();
        choosePlayerNames();
    }

    private void chooseGameMode() {
        ChoiceDialog<String> gameModeDialog = new ChoiceDialog<>("Two Players", "Easy", "Hard");
        gameModeDialog.setHeaderText("Choose the game mode:");
        gameModeDialog.setContentText("Game Mode:");

        gameModeDialog.showAndWait().ifPresent(choice -> {
            gameMode = choice;
        });
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
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void choosePlayerNames() {
        TextInputDialog player1Dialog = new TextInputDialog("Player 1");
        if(gameMode.equals("Two Players")){
            player1Dialog.setHeaderText("Enter Player 1's name:");
            player1Dialog.setContentText("Player 1 Name:");

            player1Name .setName(player1Dialog.showAndWait().orElse("Player 1"));

            TextInputDialog player2Dialog = new TextInputDialog("Player 2");
            player2Dialog.setHeaderText("Enter Player 2's name:");
            player2Dialog.setContentText("Player 2 Name:");

            player2Name .setName(player2Dialog.showAndWait().orElse("Player 2"));
        } else {
            player1Dialog.setHeaderText("Enter Player name:");
            player1Dialog.setContentText("Player  Name:");

            player1Name .setName(player1Dialog.showAndWait().orElse("Player 1"));
            player2Name.setName("Computer");
        }
        player1WinsLabel = new Label(player1Name.getName() + " Wins: 0");
        player2WinsLabel = new Label( player2Name.getName() +" Wins: 0");
        chooseFirstPlayer();
    }

    void chooseFirstPlayer(){
        ChoiceDialog<String> firstPlayerDialog = new ChoiceDialog<>(player1Name.getName(), player2Name.getName());
        firstPlayerDialog.setHeaderText("Choose the first player:");
        firstPlayerDialog.setContentText("First Player:");
        Optional<String> result = firstPlayerDialog.showAndWait();
        result.ifPresent(choice -> {
            currentPlayer = (choice.equals("Player 1")) ? 'X' : 'O';
            if (result.get().equals(player1Name.getName())) {
                firstPlayer =player1Name.getName();
                player1Name.setCharacter('X');
                player2Name.setCharacter('O');
            }else {
                firstPlayer =player2Name.getName();
                player2Name.setCharacter('X');
                player1Name.setCharacter('O');
            }
        });
    }


        /**
         * */
    private void initializeBoard() {
        for (int i = 0; i < Board_Size; i++) {
            for (int j = 0; j < Board_Size; j++) {
                board[i][j] = ' ';
            }
        }
    }
    private void initializeButtons(GridPane gridPane) {
        for (int row = 0; row < Board_Size; row++) {
            for (int col = 0; col < Board_Size; col++) {
                Button button = new Button();
                button.setMinSize(100, 100);
                int finalRow = row;
                int finalCol = col;
                button.setOnAction(e -> {
                    if (gameMode.equals("Easy")  ) {
                        ((EasyMode) modeGameObject).handleTileClick( finalRow, finalCol,button);
                    } else if (gameMode.equals("Hard")) {
                        ((HardMode) modeGameObject).handleTileClick( finalRow, finalCol,button);
                    }else {
                        ((TwoPlayersMode) modeGameObject).handleTileClick( finalRow, finalCol,button);
                    }
                });
                buttons[row][col] = button;
                gridPane.add(button, col, row);
            }
        }
    }

    private GridPane createGameLayout() {
        GridPane gridPane = new GridPane();

        for (int i = 0; i < Board_Size; i++) {
            for (int j = 0; j < Board_Size; j++) {
                Rectangle tile = createTile(i, j);
                rectangles[i][j]=tile;
                gridPane.add(tile, j, i);
            }
        }

        statusLabel.setText("Current Round: " + currentRound + " | Current Player: " + player1Name.getName());
        gridPane.add(statusLabel, 0, Board_Size, Board_Size, 1);
        gridPane.setAlignment(Pos.CENTER);
        HBox winsBox = new HBox(10);
        winsBox.setAlignment(Pos.CENTER);
        winsBox.getChildren().addAll(player1WinsLabel, player2WinsLabel);
        gridPane.add(winsBox, 0, 4, 3, 1);

        return gridPane;
    }
    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
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

            rectangle.setOnMouseClicked(e -> {
                if (gameMode.equals("Easy") || gameMode.equals("Hard")) {
                    if (((firstPlayer.equals("Computer") && currentPlayer == 'X') || (firstPlayer.equals(player1Name.getName()) && currentPlayer == 'X'))) {
                        ((EasyMode) modeGameObject).handleTileClick(row, col, rectangle);
                    }
                } else {
                        ((TwoPlayersMode) modeGameObject).handleTileClick(row, col, rectangle);
                }
            });

        return rectangle;
    }

    boolean checkWinner(int row, int col) {
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

     boolean isBoardFull() {
        for (int i = 0; i < Board_Size; i++) {
            for (int j = 0; j < Board_Size; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

     void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        statusLabel.setText("Current Round: " + currentRound + " | Current Player: " +
                ((currentPlayer == 'X') ? player1Name : player2Name));
    }

    private void updateTileText(Rectangle rectangle) {
        Label label = new Label(String.valueOf(currentPlayer));
        label.setStyle("-fx-font-size: 40;");
        label.setAlignment(Pos.CENTER);
        Insets insets = new Insets(40,40,40,40);
        label.setPadding(insets);

        GridPane.setConstraints(label, GridPane.getColumnIndex(rectangle), GridPane.getRowIndex(rectangle));
        ((GridPane) rectangle.getParent()).getChildren().add(label);
    }

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
                getNumberOfRounds();
                chooseFirstPlayer();
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

     boolean isGameFinished() {
        return checkForWinner() || isBoardFull();
    }

    private boolean checkForWinner() {
        return checkRows() || checkColumns() || checkDiagonals();
    }

    private boolean checkRows() {
        for (int i = 0; i < Board_Size; i++) {
            if (checkRow(i)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkColumns() {
        for (int i = 0; i < Board_Size; i++) {
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

     boolean checkWinner() {
        return checkRow(0) || checkRow(1) || checkRow(2) ||
                checkColumn(0) || checkColumn(1) || checkColumn(2) ||
                checkDiagonal() || checkAntiDiagonal();
    }



    public char[][] getBoard() {
        return board;
    }

    public void setBoard(char[][] board) {
        this.board = board;
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(char currentPlayer) {
        this.currentPlayer = currentPlayer;
    }



    public int getTotalRounds() {
        return totalRounds;
    }

    public void setTotalRounds(int totalRounds) {
        this.totalRounds = totalRounds;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public Label getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(Label statusLabel) {
        this.statusLabel = statusLabel;
    }

    public Alert getAlertWin() {
        return alertWin;
    }

    public void setAlertWin(Alert alertWin) {
        this.alertWin = alertWin;
    }

    public int getPlayer1Wins() {
        return player1Wins;
    }

    public void setPlayer1Wins(int player1Wins) {
        this.player1Wins = player1Wins;
    }

    public int getPlayer2Wins() {
        return player2Wins;
    }

    public void setPlayer2Wins(int player2Wins) {
        this.player2Wins = player2Wins;
    }

    public Label getPlayer1WinsLabel() {
        return player1WinsLabel;
    }

    public void setPlayer1WinsLabel(Label player1WinsLabel) {
        this.player1WinsLabel = player1WinsLabel;
    }

    public Label getPlayer2WinsLabel() {
        return player2WinsLabel;
    }

    public void setPlayer2WinsLabel(Label player2WinsLabel) {
        this.player2WinsLabel = player2WinsLabel;
    }

    public Object getModeGameObject() {
        return modeGameObject;
    }

    public void setModeGameObject(Object modeGameObject) {
        this.modeGameObject = modeGameObject;
    }

    public Player getPlayer1Name() {
        return player1Name;
    }

    public void setPlayer1Name(Player player1Name) {
        this.player1Name = player1Name;
    }

    public Player getPlayer2Name() {
        return player2Name;
    }

    public void setPlayer2Name(Player player2Name) {
        this.player2Name = player2Name;
    }

    public Rectangle[][] getRectangles() {
        return rectangles;
    }

    public void setRectangles(Rectangle[][] rectangles) {
        this.rectangles = rectangles;
    }

}
