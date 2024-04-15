import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class HardMode {
    UI ui =new UI();

    void handleTileClick(int row, int col, Rectangle rectangle) {
        char[][] board = ui.getBoard();
        if (board[row][col] == ' ' && !ui.isGameFinished()) {
            board[row][col] = ui.getCurrentPlayer();
            updateTileText(rectangle);
            if (ui.checkWinner(row, col)) {
                ui.getStatusLabel().setText("Player " + ui.getCurrentPlayer() + " wins!");
                ui.getAlertWin().setContentText("Player " + ui.getCurrentPlayer() + " wins!");
                ui.getAlertWin().showAndWait();
                //endRound();
            } else if (ui.isBoardFull()) {
                ui.getStatusLabel().setText("It's a draw!");
                //endRound();
            } else {
                ui.switchPlayer();
            }
        }
    }
    void handleTileClick(int row, int col, Button buttons) {
        char[][] board = ui.getBoard();
        if (board[row][col] == ' ' && !ui.isGameFinished()) {
            board[row][col] = ui.getCurrentPlayer();
            //updateTileText(rectangle);
            buttons.setText(ui.getCurrentPlayer()+" ");
            if (ui.checkWinner(row, col)) {
                ui.getStatusLabel().setText("Player " + ui.getCurrentPlayer() + " wins!");
                ui.getAlertWin().setContentText("Player " + ui.getCurrentPlayer() + " wins!");
                ui.getAlertWin().showAndWait();
                //endRound();
            } else if (ui.isBoardFull()) {
                ui.getStatusLabel().setText("It's a draw!");
                //endRound();
            } else {
                ui.switchPlayer();
            }
        }
    }
    private void updateTileText(Rectangle rectangle) {
        Label label = new Label(String.valueOf(ui.getCurrentPlayer()));
        label.setStyle("-fx-font-size: 40;");
        label.setAlignment(Pos.CENTER);
        Insets insets = new Insets(40,40,40,40);
        label.setPadding(insets);

        GridPane.setConstraints(label, GridPane.getColumnIndex(rectangle), GridPane.getRowIndex(rectangle));
        ((GridPane) rectangle.getParent()).getChildren().add(label);
    }

    /**
     * New UI
     * */

    // Handle button click (player's move)
   static NewUi newUi = new NewUi();
    static  char [][] board= newUi.getBoard();
    static boolean playerTurn = true;
    static boolean isThereWinner = false;//for finish game || start a new round
    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    protected void onButtonClick(int row, int col, Button button) {
       /* if (board[row][col] == ' ' && playerTurn) {
            button.setText("X");
            board[row][col] = 'X';
            // Check if the player wins
            if (checkWinner('X')) {//if the user win
                printWinner();// put the name of user
            } else {
                playerTurn = false;//to let computer play
                makeMove(); // AI's move
            }

        }*/




        // check if this cell in board is empty & playerTurn & the game not finished
//        if (newUi.getPlayer1_Object().isFirstPlayer()) {
            if (board[row][col] == ' ' && !isGameFinished()) {
                if (playerTurn ) {
               /* button.setText("X");
                board[row][col] = 'X';*/
                    button.setText(newUi.getPlayer1_Object().getCharacter() + "");
                    board[row][col] = newUi.getPlayer1_Object().getCharacter();
                    // Check if the player wins
                    if (checkWinner(newUi.getPlayer1_Object().getCharacter())) {
                        printWinner();
//                    isThereWinner=true;
                    } else if (isBoardFull()) {
                        showDraw();
                    } else {
                        playerTurn = false;
                        makeMove(); // Computer move
                        playerTurn = true;
                    }
                } else {// if the turn for computer
                /*button.setText("O");
                board[row][col] = 'O';*/
                    button.setText(newUi.getPlayer2_Object().getCharacter() + "");
                    board[row][col] = newUi.getPlayer2_Object().getCharacter();
                    // Check if the player wins
                    if (checkWinner(newUi.getPlayer2_Object().getCharacter())) {
                        printWinner();
//                    isThereWinner=true;
                    } else if (isBoardFull()) {
                        showDraw();
                    } else {
                        playerTurn = false;
                        makeMove(); // AI's move
                        playerTurn = true;
                    }
                }
            }
/*        }
        else {
            if (!isGameFinished()) {
                makeMove();
            }
        }*/

    }

    // Computer makes a move using the minimax algorithm
    void makeMove() {
        int[] bestMove = bestMove(newUi.getPlayer2_Object().getCharacter());
        System.out.println("+++++++++++++++++");
      /*  for (int[] move : bestMoves) {
            for (int j = 0; j < bestMoves.length; j++) {
                System.out.print(move[j] + " ");
            }
            System.out.println();
        }*/

        int row = bestMove[0];
        int col = bestMove[1];

        // Check if the computer wins
        if (board[row][col] == ' ') {
            Button button = getButtonAt(row, col);
            if (button != null) {
                button.setText(newUi.getPlayer2_Object().getCharacter()+"");
            }
            board[row][col] = newUi.getPlayer2_Object().getCharacter();

            if (checkWinner(newUi.getPlayer2_Object().getCharacter())) {
                printWinner();
            } else if (isBoardFull()){
                showDraw();
            }else
                playerTurn = true;
        }
    }

    // Minimax algorithm to find the optimal move for the Computer

    private int[] bestMove(char currentPlayer) {
        int[] bestMove = new int[] { -1, -1 };

       int bestScore = Integer.MIN_VALUE;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = currentPlayer;
                    int score = evaluateMove(false);
                    board[i][j] = ' ';

                   if (score > bestScore){
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }

        return bestMove;
    }
    private int evaluateMove(boolean maxi) {
        if (checkWinner('X'))
            return -1;
       else if (checkWinner('O'))
            return 1;
        else if (isBoardFull())
            return 0;


        if (maxi) { // the computer in my case
            int maxScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = newUi.getPlayer2_Object().getCharacter();
                        int score = evaluateMove( false);
                        board[i][j] = ' ';
                        maxScore = Math.max(maxScore,score );
                    }
                }
            }
            return maxScore;
        } else {
            int minScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = newUi.getPlayer1_Object().getCharacter();
                        int score = evaluateMove( true);
                        board[i][j] = ' ';
                        minScore = Math.min(minScore, score);
                    }
                }
            }
            return minScore;
        }
    }

     void showProbabilities() {
        String result;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    int probability = cellProbability(i, j);
                    if(probability==0) {
                        result = "D";
                    }
                    else if (probability==-1) {
                        result = "L";
                    }
                    else {
                        result = "W";
                    }
                    Button button = getButtonAt(i,j);
                    assert button != null;
                    button.setText(result);

                    System.out.println(i+" "+j+" "+probability);
                }
            }
        }
    }
     int cellProbability(int row, int column) {
        char currentPlayer = newUi.getPlayer2_Object().getCharacter();
        board[row][column] = 'O';

        int probability = evaluateMove(false);

        board[row][column] = ' '; // Undo the move

        return probability;
    }
     void clearProbability() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    Button button = getButtonAt(i,j);
                    assert button != null;
                    button.setText("");
                }
            }
        }
    }

    // Check if a character has won
    private boolean checkWinner(char character) {
        // check rows and columns
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == character && board[i][1] == character && board[i][2] == character ||
                    board[0][i] == character && board[1][i] == character && board[2][i] == character) {
                if (character== 'X'){
                    if(newUi.getPlayer1_Object().isFirstPlayer()){
//                        newUi.getPlayer1_Object().setWins(newUi.getPlayer1_Object().getWins()+1);
                        newUi.getPlayer1_Object().setWinner(true);
                    }else {
//                        newUi.getPlayer2_Object().setWins(newUi.getPlayer2_Object().getWins()+1);
                        newUi.getPlayer2_Object().setWinner(true);
                    }
                }else {
                    if(!newUi.getPlayer1_Object().isFirstPlayer()){
//                        newUi.getPlayer1_Object().setWins(newUi.getPlayer1_Object().getWins()+1);
                        newUi.getPlayer1_Object().setWinner(true);
                    }else {
//                        newUi.getPlayer2_Object().setWins(newUi.getPlayer2_Object().getWins()+1);
                        newUi.getPlayer2_Object().setWinner(true);
                    }
                }
                System.out.println(newUi.getPlayer1_Object().getWins());
                System.out.println(newUi.getPlayer2_Object().getWins());
//                isThereWinner=false;
                return true;
            }
        }
        // check diagonals and anti diagonals

        if ((board[0][0] == character && board[1][1] == character && board[2][2] == character) ||
                (board[0][2] == character && board[1][1] == character && board[2][0] == character)){
            if (character== 'X'){
                if(newUi.getPlayer1_Object().isFirstPlayer()){
//                    newUi.getPlayer1_Object().setWins(newUi.getPlayer1_Object().getWins()+1);
                    newUi.getPlayer1_Object().setWinner(true);
                }else {
//                    newUi.getPlayer2_Object().setWins(newUi.getPlayer2_Object().getWins()+1);
                    newUi.getPlayer2_Object().setWinner(true);
                }
            }else {
                if(!newUi.getPlayer1_Object().isFirstPlayer()){
//                    newUi.getPlayer1_Object().setWins(newUi.getPlayer1_Object().getWins()+1);
                    newUi.getPlayer1_Object().setWinner(true);
                }else {
//                    newUi.getPlayer2_Object().setWins(newUi.getPlayer2_Object().getWins()+1);
                    newUi.getPlayer2_Object().setWinner(true);
                }
            }
         
//                isThereWinner=false;
            return true;
        }
        return false;
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
        for (var node : newUi.grid.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                return (Button) node;
            }
        }
        return null;
    }

    // Announce the winner

    private void printWinner() {
        if (!newUi.getPlayer1_Object().isWinner()){
            System.out.println();
            alert.setTitle("INFO");
            alert.setHeaderText(null);
            alert.setContentText(newUi.getPlayer1_Object().getName() + " wins!");
            alert.showAndWait();
            newUi.getPlayer1_Object().setWins(newUi.getPlayer1_Object().getWins()+1);
        }else {
            System.out.println();
            alert.setTitle("INFO");
            alert.setHeaderText(null);
            alert.setContentText(newUi.getPlayer2_Object().getName() + " wins!");
            alert.showAndWait();
            newUi.getPlayer2_Object().setWins(newUi.getPlayer2_Object().getWins()+1);
        }
        isThereWinner=true;
        System.out.println(newUi.getPlayer1_Object().getWins());
        System.out.println(newUi.getCurrentRound());
        if (newUi.getCurrentRound()>newUi.getRoundsNumber()/2 &&newUi.getPlayer2_Object().getWins()==newUi.getCurrentRound()){
//            if (newUi.getPlayer2_Object().getWins()> newUi.getCurrentRound()/2){
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setContentText(newUi.getPlayer1_Object().getName() +"are almost win you want continue ?");
            alert1.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
            alert1.showAndWait().ifPresent(response -> {
                if (response == ButtonType.CANCEL) {
                    System.exit(0);
                }
            });
        }
        newUi.endRound();
        if (newUi.getPlayer2_Object().isFirstPlayer()) {
            playerTurn=false;
            makeMove();
        }else {
            playerTurn=true;
        }
        // Add code to handle end of the game
    }
    private void showDraw() {
        System.out.println("it's Draw");
        alert.setTitle("INFO");
        alert.setHeaderText(null);
        alert.setContentText( "it's Draw");
        alert.showAndWait();
        System.out.println("it's Draw");
        isThereWinner=false;
        newUi.endRound();
        if (newUi.getPlayer2_Object().isFirstPlayer()){
            playerTurn=false;
            makeMove();
        }else {
            playerTurn=true;
        }
        // Add code to handle end of the game
    }
    void makeMoveRandom() {
        int row = getRandomRow();
        int col = getRandomCol();
        // Check if the AI wins

        if (board[row][col] == ' ') {
            Button button = getButtonAt(row, col);
            if (button != null) {
                button.setText(newUi.getPlayer2_Object().getCharacter()+"");
            }
            board[row][col] = newUi.getPlayer2_Object().getCharacter();

            if (checkWinner(newUi.getPlayer2_Object().getCharacter())) { // if computer win
                printWinner();
//                    isThereWinner=true;
            } else if (isBoardFull()) {
                showDraw();
            } else {
                playerTurn = true;//to let player play
                System.out.println("theres is no winner");
                printBoard();
                return;
            }


        } else if (!isBoardFull() ) {
            System.out.println("the board is not full");
            printBoard();
            makeMove();
        }else {
            showDraw();
        }


    }
    int getRandomRow(){
        return getRandomIndex(board.length);
    }
    int getRandomCol(){
        return getRandomIndex(board[0].length);
    }
    private static int getRandomIndex(int length) {
        Random random = new Random();
        return random.nextInt(length);
    }
    void printBoard(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j <3 ; j++) {
                System.out.print(board[i][j]+" ");
            }
            System.out.println();
        }
    }

    boolean isGameFinished() {
        return isThereWinner || isBoardFull();
    }

    public NewUi getNewUi() {
        return newUi;
    }

    public void setNewUi(NewUi newUi) {
        this.newUi = newUi;
    }
    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }
}
