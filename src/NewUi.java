import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class NewUi extends Application {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    private static Player player1_Object = new Player() ;
    private static Player player2_Object = new Player() ;

    private int roundsNumber = 5;
    private int currentRound = 1;// to display it to user on screen
    private char currentTurnChar = 'X';
    private Label player1WinsLabel ;
    private Label player2WinsLabel ;
    private String gameMode = "";
    Object modeGameObject;//it's come from main
    GridPane grid = new GridPane();
    String firstPlayer="";
   static TextArea textArea = new TextArea();
    static char[][] board = {
            {' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '}
    };

        @Override
        public void start(Stage primaryStage) {
           /* getGameSettings();

            grid.setAlignment(Pos.CENTER);
            grid.setHgap(10);
            grid.setVgap(10);

            // Create buttons for each cell in the game gridPane
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    Button button = createButton(i, j);
                    grid.add(button, j, i);
                }
            }

            Scene scene = new Scene(grid, 600, 600);
            primaryStage.setTitle("Tic Tac Toe");
            primaryStage.setScene(scene);
            primaryStage.show();*/
        }
    Stage primaryStage = new Stage();
        void start(){
            getGameSettings();
            Main.enterGameModeObject();
            //turnPlaySetting();
            //firstPlayer.getName().equals("Computer")

            Scene scene = new Scene(createBoard(), 800, 600);
            primaryStage.setTitle("Tic Tac Toe");
            primaryStage.setScene(scene);
            primaryStage.show();
            if (firstPlayer.equals("Computer")) {
                if (modeGameObject instanceof EasyMode ) {
                    ((EasyMode)modeGameObject).makeMove();
                }else {
                    ((HardMode)modeGameObject).makeMove();
                }

            }
            showProb.setOnAction(actionEvent -> {
                ((HardMode)modeGameObject).showProbabilities();
            });
            clearProb.setOnAction(actionEvent -> {
                ((HardMode)modeGameObject).clearProbability();
            });
        }
        BorderPane borderPane = new BorderPane();
        Button showProb = new Button("Show");
        Button clearProb = new Button("Clear");
        BorderPane createBoard(){

            // for end round //??
            if (player1_Object.isFirstPlayer()&&player1_Object.getName().equals("Computer")
                    || player2_Object.isFirstPlayer()&&player2_Object.getName().equals("Computer")){
                if (gameMode.equals("Easy")  ) {
                    ((EasyMode) modeGameObject).setPlayerTurn(false);
                } else if (gameMode.equals("Hard")) {
                    ((HardMode) modeGameObject).setPlayerTurn(false);
                }
            }



            HBox topHBox = new HBox(10);
            Label gameModeLabel = new Label(getGameMode() +" Mode");
            //root.setTop(gameModeLabel);
            gameModeLabel.setFont(new Font("Arial", 40));
            gameModeLabel.setTextFill(Color.BLUE);
            topHBox.getChildren().add(gameModeLabel);
            topHBox.setAlignment(Pos.CENTER);
            Label currentRoundLabel = new Label("Current Round "+currentRound);
            currentRoundLabel.setFont(new Font("Arial", 20));
            VBox topVBox = new VBox(10);
            topVBox.getChildren().addAll(topHBox,currentRoundLabel);
            topVBox.setAlignment(Pos.CENTER);


            //player1WinsLabel
            VBox leftVBox = new VBox(10);
            player1WinsLabel.setFont(new Font("Arial", 20));
            leftVBox.getChildren().add(player1WinsLabel);
            leftVBox.setAlignment(Pos.TOP_CENTER);
            VBox rightVBox = new VBox(10);
            player2WinsLabel.setFont(new Font("Arial", 20));
            rightVBox.getChildren().add(player2WinsLabel);
            rightVBox.setAlignment(Pos.TOP_CENTER);
            HBox rightButtonHBox  = new HBox();
            rightButtonHBox.getChildren().addAll(showProb,clearProb);
            VBox rightButtonVBox = new VBox();
            rightButtonVBox.getChildren().addAll(rightVBox,rightButtonHBox);
            rightButtonVBox.setAlignment(Pos.TOP_CENTER);
            createCells();

            borderPane.setTop(topVBox);
            borderPane.setCenter(grid);
            borderPane.setLeft(leftVBox);
            borderPane.setRight(rightButtonVBox);
            borderPane.setBottom(textArea);


            return borderPane;
        }
      static   Button [][] buttons = new Button[3][3];
        void createCells(){
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(10);
            grid.setVgap(10);

            // Create buttons for each cell in the game gridPane
            // grid.getChildren().clear();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    Button button = createButton(i, j);
                    grid.add(button, j, i);
                    buttons[i][j]=button;
                }
            }
        }

        // Create a button for a specific cell in the game gridPane
        private Button createButton(int row , int col) {
            Button button = new Button();
            button.setMinSize(100, 100);


            button.setOnAction(e -> {
                if (gameMode.equals("Easy")  ) {
                    ((EasyMode) modeGameObject).onButtonClick( row, col,button);
                } else if (gameMode.equals("Hard")) {
                    ((HardMode) modeGameObject).onButtonClick( row, col,button);
                }else {
                    ((TwoPlayersMode) modeGameObject).onButtonClick( row, col,button);
                }
            });

            // No event handling code for button clicks
            return button;
        }

        /**
         * those method to get all game setting
         * like(gameMode , numberOfRounds , enterPlayerNames , enterFirstPlayer)*/
    private void getGameSettings() {
        //get game mode to use it on other setting (number of player , way of game progress)
        chooseGameMode();
        // Prompt the user for the number of rounds
        roundsNumber = getNumberOfRounds();
        // Prompt the user to choose the first player and enter player names
        choosePlayerNames();
    }

    private void chooseGameMode() {
        ChoiceDialog<String> gameModeDialog = new ChoiceDialog<>(" ","Two Players", "Easy", "Hard");
        gameModeDialog.setHeaderText("Choose the game mode:");
        gameModeDialog.setContentText("Game Mode:");

        gameModeDialog.showAndWait().ifPresent(choice -> {
            if(!choice.trim().equals("")){
                gameMode = choice;
            }else {
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("You should choose a Mode");
                alert.showAndWait();
                chooseGameMode();
            }
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
       // String input="";
        TextInputDialog player1Dialog = new TextInputDialog("Player 1");
        if(gameMode.equals("Two Players")){
            player1Dialog.setHeaderText("Enter Player 1's name:");
            player1Dialog.setContentText("Player 1 Name:");
            // input = player1Dialog.showAndWait().orElse("Player 1");
             player1Dialog.showAndWait().ifPresent(input -> {
                 if(!input.trim().equals("")){
                     player1_Object.setName(input);
                 }else {
                     alert.setTitle("Error");
                     alert.setHeaderText(null);
                     alert.setContentText("You should Enter a Name");
                     alert.showAndWait();
                     choosePlayerNames();
                 }
             });


            TextInputDialog player2Dialog = new TextInputDialog("Player 2");
            player2Dialog.setHeaderText("Enter Player 2's name:");
            player2Dialog.setContentText("Player 2 Name:");
            //input = player2Dialog.showAndWait().orElse("Player 2");
            player2Dialog.showAndWait().ifPresent(input -> {
                if(!input.trim().equals("")){
                    player2_Object.setName(input);
                }else {
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("You should Enter a Name");
                    alert.showAndWait();
                    choosePlayerNames();
                }
            });


        } else {
            player1Dialog.setHeaderText("Enter Player name:");
            player1Dialog.setContentText("Player  Name:");
            //input = player1Dialog.showAndWait().orElse("Player 1");
            player1Dialog.showAndWait().ifPresent(input -> {
                if(!input.trim().equals("")){
                    player1_Object.setName(input);
                }else {
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("You should Enter a Name");
                    alert.showAndWait();
                    choosePlayerNames();
                }
            });
            player2_Object.setName("Computer");
        }
        player1WinsLabel = new Label(player1_Object.getName() + " Wins: 0");
        player2WinsLabel = new Label( player2_Object.getName() +" Wins: 0");
        chooseFirstPlayer();
    }

    void chooseFirstPlayer(){
        /**
         * choose first player
         * set the char for two players
         * set player turn & is first player  booleans*/
        ChoiceDialog<String> firstPlayerDialog = new ChoiceDialog<>(" ", player1_Object.getName(), player2_Object.getName());
        firstPlayerDialog.setHeaderText("Choose the first player:");
        firstPlayerDialog.setContentText("First Player:");
        Optional<String> result = firstPlayerDialog.showAndWait();
        result.ifPresent(choice -> {
            //currentTurnChar = (choice.equals(player1Name.getName())) ? 'X' : 'O';
            if(!choice.trim().equals("")){
                currentTurnChar='X';
                if (result.get().equals("Computer")) {
                    firstPlayer=result.get();
                    EasyMode.playerTurn=false;
                    HardMode.playerTurn=false;
                    player2_Object.setFirstPlayer(true);
                    player2_Object.setCharacter('X');
                    player1_Object.setCharacter('O');
                    player1_Object.setFirstPlayer(false);
                }
                 else if (result.get().equals(player1_Object.getName())) {
                    player1_Object.setFirstPlayer(true);
                    player1_Object.setCharacter('X');
                    player2_Object.setCharacter('O');
                    player2_Object.setFirstPlayer(false);
                }

//                }else {
//                    player2_Object.setFirstPlayer(true);
//                    player2_Object.setCharacter('X');
//                    player1_Object.setCharacter('O');
//                    player1_Object.setFirstPlayer(false);
//                }
            }else {
                alert.setTitle("Error");
                alert.setContentText("You should choose a First Player");
                alert.showAndWait();
                chooseFirstPlayer();
            }

        });
    }

     void endRound() {

        if (currentRound < roundsNumber) {
            currentRound++;

            player1WinsLabel = new Label(player1_Object.getName() + " Wins: " + player1_Object.getWins());
            player2WinsLabel = new Label(player2_Object.getName() + " Wins: " + player2_Object.getWins());
         /*   this.board = new char[][]{
                    {' ', ' ', ' '},
                    {' ', ' ', ' '},
                    {' ', ' ', ' '}
            };*/
            TwoPlayersMode.isThereWinner=false;
            EasyMode.isThereWinner=false;
            HardMode.isThereWinner=false;
            TwoPlayersMode.firstMove=true;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    board[i][j]=' ';
                }
            }


            //Scene scene =new Scene(createBoard(),800,800);
            grid.getChildren().clear();
            Scene currentScene = grid.getScene();

            currentScene.setRoot(createBoard());
            primaryStage.setScene(currentScene);
            primaryStage.show();
        }else {
            ChoiceDialog<String> firstPlayerDialog = new ChoiceDialog<>("Yes", "No");
            firstPlayerDialog.setHeaderText("Restart Game ?");
            firstPlayerDialog.setContentText("Choose:");
            AtomicBoolean isRestart= new AtomicBoolean(false);
            firstPlayerDialog.showAndWait().ifPresent(choice -> {
                isRestart.set(choice.equals("Yes"));
            });
            if (isRestart.get()) {
                grid.getChildren().clear();
                Scene currentScene = grid.getScene();
                currentScene.setRoot(new BorderPane());
               start();
            }else {
                System.exit(0);
            }
        }

    }
           /*
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

            }else {
                System.exit(0);
            }

        }
    }

    private void updateWinCount() {
        if (currentPlayer == 'X') {
            player1Name.setWins(player1Name.getWins()+1);
        } else {
            player2Name.setWins(player2Name.getWins()+1);
        }
        updateWinsLabels();
    }*/
    private void updateWinsLabels() {
        player1WinsLabel.setText(player1_Object.getName() + " Wins: " + player1_Object.getWins());
        player2WinsLabel.setText(player2_Object.getName() + " Wins: " + player2_Object.getWins());
    }


    public Player getPlayer1_Object() {
        return player1_Object;
    }

    public void setPlayer1_Object(Player player1_Object) {
        this.player1_Object = player1_Object;
    }

    public Player getPlayer2_Object() {
        return player2_Object;
    }

    public void setPlayer2_Object(Player player2_Object) {
        this.player2_Object = player2_Object;
    }



    public int getRoundsNumber() {
        return roundsNumber;
    }

    public void setRoundsNumber(int roundsNumber) {
        this.roundsNumber = roundsNumber;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public char getCurrentTurnChar() {
        return currentTurnChar;
    }

    public void setCurrentTurnChar(char currentTurnChar) {
        this.currentTurnChar = currentTurnChar;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public Object getModeGameObject() {
        return modeGameObject;
    }

    public void setModeGameObject(Object modeGameObject) {
        this.modeGameObject = modeGameObject;
    }

    public char[][] getBoard() {
        return board;
    }

    public void setBoard(char[][] board) {
        this.board = board;
    }



    boolean computerIsFirst(){
        return true;
    }
   /* void turnPlaySetting(){
        if (firstPlayer.equals("Computer")){
            if (gameMode.equals("Easy")  ) {
                ((EasyMode) modeGameObject).setPlayerTurn(false);
            } else if (gameMode.equals("Hard")) {
                ((HardMode) modeGameObject).setPlayerTurn(false);
            }
        }
       *//* if (player2Name.getName().equals("Computer")){
            if (gameMode.equals("Easy")  ) {
                ((EasyMode) modeGameObject).setPlayerTurn(false);
            } else if (gameMode.equals("Hard")) {
                ((HardMode) modeGameObject).setPlayerTurn(false);
            }
        }*//*
    }*/

    // Main method to launch the JavaFX application
        public static void main(String[] args) {
            launch(args);
        }
    }


