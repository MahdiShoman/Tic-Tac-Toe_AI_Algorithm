import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Test3 extends Application {
    private char[][] board = {
            {' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '}
    };

    private boolean playerTurn = true;
    GridPane grid = new GridPane();
    @Override
    public void start(Stage primaryStage) {

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
            button.setText("X");
            board[row][col] = 'X';
            // Check if the player wins
            if (checkWinner('X')) {
                showWinner("Player X");
            } else {
                playerTurn = false;
                makeMove(); // AI's move
            }

        }
    }

    // AI makes a move using the minimax algorithm
    private void makeMove() {
        int[] bestMove = minimax();
        int row = bestMove[0];
        int col = bestMove[1];

        // Check if the AI wins
        if (board[row][col] == ' ') {
            Button button = getButtonAt(row, col);
            button.setText("O");
            board[row][col] = 'O';

            if (checkWinner('O')) {
                showWinner("Player O");
            } else {
                playerTurn = true;
            }
        }
    }

    // Minimax algorithm to find the optimal move for the AI
    private int[] minimax() {
        int[] bestMove = {-1, -1};
        int bestEval = Integer.MIN_VALUE;

        // Iterate through all possible moves
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = 'O';
                    int eval = minimax(0, false);
                    board[i][j] = ' ';

                    // Choose the move with the maximum score
                    if (eval > bestEval) {
                        bestEval = eval;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }

        return bestMove;
    }

    // Recursive part of the minimax algorithm
    private int minimax(int depth, boolean maximizingPlayer) {
        Integer score = evaluate();

        // Base case: if the game is over, return the score
        if (score != null) {
            return score;
        }

        // Recursive case
        if (maximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'O';
                        int eval = minimax(depth + 1, false);
                        board[i][j] = ' ';
                        maxEval = Math.max(maxEval, eval);
                    }
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'X';
                        int eval = minimax(depth + 1, true);
                        board[i][j] = ' ';
                        minEval = Math.min(minEval, eval);
                    }
                }
            }
            return minEval;
        }
    }

    // Evaluate the current state of the game
    private Integer evaluate() {
        if (checkWinner('X')) {
            return -1; // X wins
        } else if (checkWinner('O')) {
            return 1;  // O wins
        } else if (isBoardFull()) {
            return 0;  // Draw
        } else {
            return null;  // Game is still ongoing
        }
    }

    // Check if a player has won
    private boolean checkWinner(char player) {
        // check rows and columns
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player ||
                    board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true;
            }
        }
        // check diagonals and anti diagonals
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

    // Get the button at a specific row and column
    private Button getButtonAt(int row, int col) {
        for (var node : grid.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                return (Button) node;
            }
        }
        return null;
    }

    // Announce the winner
    private void showWinner(String winner) {
        System.out.println(winner + " wins!");
        // Add code to handle end of the game
    }

    // Main method to launch the JavaFX application
    public static void main(String[] args) {
        launch(args);
    }
}
