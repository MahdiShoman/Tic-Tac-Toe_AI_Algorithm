import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Test4 extends Application {
    private char[][] board = {
            {' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '}
    };

    private boolean playerTurn = true;

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        // Create buttons for each cell in the game grid
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = createButton(i, j);
                grid.add(button, j, i);
            }
        }

        Scene scene = new Scene(grid, 300, 300);
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Create a button for a specific cell in the game grid
    private Button createButton(int row, int col) {
        Button button = new Button();
        button.setMinSize(100, 100);
        button.setOnAction(e -> onButtonClick(button, row, col));
        return button;
    }

    // Handle button click (player's move)
    private void onButtonClick(Button button, int row, int col) {
        if (board[row][col] == ' ' && playerTurn) {
            // Alternate between 'X' and 'O' for each player's turn
            button.setText(playerTurn ? "X" : "O");
            board[row][col] = playerTurn ? 'X' : 'O';

            // Check if the current player wins
            if (checkWinner(playerTurn ? 'X' : 'O')) {
                announceWinner("Player " + (playerTurn ? "X" : "O"));
            } else if (isBoardFull()) {
                // Check if the game is a draw
                announceWinner("It's a draw!");
            } else {
                // Switch to the next player's turn
                playerTurn = !playerTurn;
            }
        }
    }

    // Check if a player has won
    private boolean checkWinner(char player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player ||
                    board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true;
            }
        }

        return (board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
                (board[0][2] == player && board[1][1] == player && board[2][0] == player);
    }

    // Check if the board is full
    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    // Announce the winner or a draw
    private void announceWinner(String result) {
        System.out.println(result);
        // Add code to handle end of the game
    }

    // Main method to launch the JavaFX application
    public static void main(String[] args) {
        launch(args);
    }
}
