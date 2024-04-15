import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

public class TwoPlayersMode {

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
           // updateTileText(rectangle);
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



    static NewUi newUi =new NewUi();
    static char[][] board = newUi.getBoard();
   static Boolean firstMove=true;
    public static boolean isThereWinner = false;
    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    protected void onButtonClick(int row, int col, Button button) {
      /*  if( newUi.getFirstPlayer().equals(newUi.getPlayer1Name().getName())){
            !newUi.getFirstPlayer().equals("Computer")
        }*/
        //printBoard();
        System.out.println(board[row][col]+"?");
        System.out.println(isThereWinner);
        System.out.println(firstMove);
        if (board[row][col] == ' ' && !isGameFinished()) {
            if (firstMove) {
                button.setText("X");
                board[row][col] = 'X';
                firstMove=false;
                // Check if the player wins
                if (checkWinner('X')) {
                    if (newUi.getPlayer1_Object().getCharacter() == 'X') {
                        newUi.getPlayer1_Object().setWinner(true);
                    }else {
                        newUi.getPlayer2_Object().setWinner(true);
                    }
                    printWinner();
//                    isThereWinner=true;
                } else if (isBoardFull()){
                    showDraw();
                }

                printBoard();
            }else {// if the turn for computer
                button.setText("O");
                board[row][col] = 'O';
                firstMove=true;
                // Check if the player wins
                if (checkWinner('O')) {
                    if (newUi.getPlayer1_Object().getCharacter() == 'O') {
                        newUi.getPlayer1_Object().setWinner(true);
                    }else {
                        newUi.getPlayer2_Object().setWinner(true);
                    }
                    printWinner();
//                    isThereWinner=true;
                } else if (isBoardFull()){
                    showDraw();
                }
                printBoard();
            }
        }

    }

    private boolean checkWinner(char character) {
        // check rows and columns
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == character && board[i][1] == character && board[i][2] == character ||
                    board[0][i] == character && board[1][i] == character && board[2][i] == character) {
                if (character== 'X'){
                    if(newUi.getPlayer1_Object().isFirstPlayer()){
                        newUi.getPlayer1_Object().setWins(newUi.getPlayer1_Object().getWins()+1);
                    }else {
                        newUi.getPlayer2_Object().setWins(newUi.getPlayer2_Object().getWins()+1);
                    }
                }else {
                    if(!newUi.getPlayer1_Object().isFirstPlayer()){
                        newUi.getPlayer1_Object().setWins(newUi.getPlayer1_Object().getWins()+1);
                    }else {
                        newUi.getPlayer2_Object().setWins(newUi.getPlayer2_Object().getWins()+1);
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
                }else {
                    newUi.getPlayer2_Object().setWins(newUi.getPlayer2_Object().getWins()+1);
                }
            }else {
                if(!newUi.getPlayer1_Object().isFirstPlayer()){
                    newUi.getPlayer1_Object().setWins(newUi.getPlayer1_Object().getWins()+1);
                }else {
                    newUi.getPlayer2_Object().setWins(newUi.getPlayer2_Object().getWins()+1);
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
    private static boolean isBoardFull() {
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
        newUi.endRound();
        firstMove=true;
        // Add code to handle end of the game
    }
    private void showDraw() {
        System.out.println("it's Draw");
        alert.setTitle("INFO");
        alert.setHeaderText(null);
        alert.setContentText( "it's Draw");
        alert.showAndWait();
        isThereWinner=false;
        newUi.endRound();
        firstMove=true;
        // Add code to handle end of the game
    }
   static boolean isGameFinished() {
        return isThereWinner || isBoardFull();
    }
    void printBoard(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j <3 ; j++) {
                System.out.print(board[i][j]+" ");
            }
            System.out.println();
        }
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
}
