import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class EasyMode {

    byte [][] cells = new byte[3][3];// to choose a random empty cell
    UI ui =new UI();


    // if user , put x or o ,then check winner

     void handleTileClick(int row, int col, Rectangle rectangle) {
         char[][] board = ui.getBoard();
         if (((ui.firstPlayer.equals("Computer") && ui.getCurrentPlayer() == 'X') || (ui.firstPlayer.equals(ui.getPlayer1Name().getName()) && ui.getCurrentPlayer() == 'X'))){
             randomIndex(board);
         }else {
             if (board[row][col] == ' ' && !ui.isGameFinished()) {
                 board[row][col] = ui.getCurrentPlayer();
                 cells[row][col]=1;
                 updateTileText(rectangle);
                 if (ui.checkWinner(row, col)) {
                     if (ui.getCurrentPlayer() == 'X') {
                         ui.getStatusLabel().setText("Player " + ui.firstPlayer + " wins!");
                     }else if ((ui.getPlayer1Name().getName().equals(ui.firstPlayer))){
                         ui.getStatusLabel().setText("Player " + ui.getPlayer2Name() + " wins!");
                     }else{
                         ui.getStatusLabel().setText("Player " + ui.getPlayer1Name().getName() + " wins!");
                     }
                     ui.getAlertWin().setContentText("Player " + ui.getCurrentPlayer() + " wins!");
                     ui.getAlertWin().showAndWait();
                     //endRound();
                 } else if (ui.isBoardFull()) {
                     ui.getStatusLabel().setText("It's a draw!");
                     //endRound();
                 } else {
                     ui.switchPlayer();
                     ui.checkTurn();
                 }
             }
         }
    }
    void handleTileClick(int row, int col, Button button) {
        if (!button.getText().isEmpty()) {
            return; // Cell already occupied
        }
        button.setText(ui.getCurrentPlayer()+"");
        if (ui.checkWinner(row,col)) {
            System.out.println("winner");
           /* showWinnerAlert();
            resetGame();*/
        } else if (ui.isBoardFull()) {
            System.out.println("isBoardfull");
           /* showDrawAlert();
            resetGame();*/
        } else {
            ui.switchPlayer();
            makeComputerMove();
           /* if (checkWinner(row,col)) {
                showWinnerAlert();
                resetGame();
            } else if (isBoardFull()) {
                showDrawAlert();
                resetGame();
            }*/
        }
    }
    // Helper method to generate a random index
    private static int getRandomIndex(int length) {
        Random random = new Random();
        return random.nextInt(length);
    }
    void randomIndex(char [][] board){
        int row = getRandomIndex(board.length);
        int col = getRandomIndex(board[0].length);
        Rectangle [][] rectangles=ui.getRectangles();
        int recCol=GridPane.getColumnIndex(rectangles[row][col]);
        int recRow=GridPane.getRowIndex(rectangles[row][col]);
        if (board[recRow][recCol] == ' ' && !ui.isGameFinished()){
            updateTileText_(rectangles[row][col],recRow,recCol);
        }
        ui.switchPlayer();
        // Access the element at the random indices

     /*   if (board[row][col] == ' ' && !ui.isGameFinished()) {
            board[row][col] = ui.getCurrentPlayer();
           // cells[row][col]=1;
            updateTileText(rectangle);
            if (ui.checkWinner(row, col)) {
                if (ui.getCurrentPlayer() == 'X') {
                    ui.getStatusLabel().setText("Player " + ui.firstPlayer + " wins!");
                }else if ((ui.getPlayer1Name().getName().equals(ui.firstPlayer))){
                    ui.getStatusLabel().setText("Player " + ui.getPlayer2Name() + " wins!");
                }else{
                    ui.getStatusLabel().setText("Player " + ui.getPlayer1Name() + " wins!");
                }
                ui.getAlertWin().setContentText("Player " + ui.getCurrentPlayer() + " wins!");
                ui.getAlertWin().showAndWait();
                //endRound();
            } else if (ui.isBoardFull()) {
                ui.getStatusLabel().setText("It's a draw!");
                //endRound();
            } else {
                ui.switchPlayer();

            }
        }*/

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
    private void updateTileText_(Rectangle rectangle,int row,int col) {//from rectangle array get the row col and check the border array if this position has played
        Label label = new Label(String.valueOf(ui.getCurrentPlayer()));
        label.setStyle("-fx-font-size: 40;");
        label.setAlignment(Pos.CENTER);
        Insets insets = new Insets(40,40,40,40);
        label.setPadding(insets);

        GridPane.setConstraints(label, col, row);
        ((GridPane) rectangle.getParent()).getChildren().add(label);

    }
    /**
     * New UI
     * */
    static NewUi newUi = new NewUi();
    static boolean playerTurn = true;
  /*  boolean computerIsFirst(){
        return !newUi.getFirstPlayer().equals("Computer");
    }*/
    static char[][] board = newUi.getBoard();
   static boolean isThereWinner = false;
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    protected void onButtonClick(int row, int col, Button button) {
      /*  if( newUi.getFirstPlayer().equals(newUi.getPlayer1Name().getName())){
            !newUi.getFirstPlayer().equals("Computer")
        }*/
        System.out.println("1");
        printBoard();
        System.out.println("2");
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
                    makeMove();
                    playerTurn = true;
                }
            }
        }
    }
/**
 * if first move was computer X */
    // AI makes a move using the minimax algorithm
     void makeMove() {
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
    void printBoard(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j <3 ; j++) {
                System.out.print(board[i][j]+" ");
            }
            System.out.println();
        }
    }

    private void makeComputerMove() {
        Random random = new Random();
        int row, col;

        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (!ui.buttons[row][col].getText().isEmpty());

        ui.buttons[row][col].setText(ui.getCurrentPlayer()+"");
      ui.  checkWinner(row,col);
       ui. switchPlayer();
    }
    private boolean checkWinner(char character) {
        // check rows and columns
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == character && board[i][1] == character && board[i][2] == character ||
                    board[0][i] == character && board[1][i] == character && board[2][i] == character) {
                if (character== 'X'){
                    if(newUi.getPlayer1_Object().isFirstPlayer()){
                        newUi.getPlayer1_Object().setWins(newUi.getPlayer1_Object().getWins()+1);
                        newUi.getPlayer1_Object().setWinner(true);
                    }else {
                        newUi.getPlayer2_Object().setWins(newUi.getPlayer2_Object().getWins()+1);
                        newUi.getPlayer2_Object().setWinner(true);
                    }
                }else {
                    if(!newUi.getPlayer1_Object().isFirstPlayer()){
                        newUi.getPlayer1_Object().setWins(newUi.getPlayer1_Object().getWins()+1);
                        newUi.getPlayer1_Object().setWinner(true);
                    }else {
                        newUi.getPlayer2_Object().setWins(newUi.getPlayer2_Object().getWins()+1);
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
                    newUi.getPlayer1_Object().setWins(newUi.getPlayer1_Object().getWins()+1);
                    newUi.getPlayer1_Object().setWinner(true);
                }else {
                    newUi.getPlayer2_Object().setWins(newUi.getPlayer2_Object().getWins()+1);
                    newUi.getPlayer2_Object().setWinner(true);
                }
            }else {
                if(!newUi.getPlayer1_Object().isFirstPlayer()){
                    newUi.getPlayer1_Object().setWins(newUi.getPlayer1_Object().getWins()+1);
                    newUi.getPlayer1_Object().setWinner(true);
                }else {
                    newUi.getPlayer2_Object().setWins(newUi.getPlayer2_Object().getWins()+1);
                    newUi.getPlayer2_Object().setWinner(true);
                }
            }
            System.out.println(newUi.getPlayer1_Object().getWins());
            System.out.println(newUi.getPlayer2_Object().getWins());
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
        /*for (var node : newUi.grid.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                return (Button) node;
            }
        }
        return null;*/
        return newUi.buttons[row][col];
    }

    // Announce the winner

    private void printWinner() {
        if (newUi.getPlayer1_Object().isWinner()){
            System.out.println();
            alert.setTitle("INFO");
            alert.setHeaderText(null);
            alert.setContentText(newUi.getPlayer1_Object().getName() + " wins!");
            alert.showAndWait();
        }else {
            System.out.println();
            alert.setTitle("INFO");
            alert.setHeaderText(null);
            alert.setContentText(newUi.getPlayer2_Object().getName() + " wins!");
            alert.showAndWait();
        }
        isThereWinner=true;
         if (newUi.getCurrentRound()>newUi.getRoundsNumber()/2 &&(newUi.getPlayer1_Object().getWins()==newUi.getCurrentRound()
         || newUi.getPlayer2_Object().getWins()==newUi.getCurrentRound())){

//        if (newUi.getPlayer1_Object().getWins()> newUi.getCurrentRound()/2){
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
      /*  if ((newUi.getPlayer1_Object().isFirstPlayer()&&newUi.getPlayer1_Object().getName().equals("Computer")
                || newUi.getPlayer2_Object().isFirstPlayer()&&newUi.getPlayer2_Object().getName().equals("Computer"))) {*/
        if (newUi.getPlayer2_Object().isFirstPlayer()){
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
    boolean isGameFinished() {
        return isThereWinner || isBoardFull();
    }


    public byte[][] getCells() {
        return cells;
    }

    public void setCells(byte[][] cells) {
        this.cells = cells;
    }

    public UI getUi() {
        return ui;
    }

    public void setUi(UI ui) {
        this.ui = ui;
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
