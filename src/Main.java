import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

     UI ui = new UI();
     Stage stage_ = new Stage();
    static EasyMode easyMode = new EasyMode();
    static HardMode hardMode = new HardMode();
    static TwoPlayersMode twoPlayersMode = new TwoPlayersMode();
    static NewUi newUi = new NewUi();
    public static void main(String[] args) {launch(args);}

    @Override
    public void start(Stage stage) throws Exception {

        newUi.start();// to collect data
        hardMode.setNewUi(newUi);
        easyMode.setNewUi(newUi);
        twoPlayersMode.setNewUi(newUi);
   /*     if (newUi.getGameMode().equals("Easy")  ) {

        } else if (newUi.getGameMode().equals("Hard")) {

        }else {

        }*/
       // ui.setModeGameObject(easyMode);
       // ui.start();
       // easyMode.setUi(ui);

    }
    static  void enterGameModeObject (){
        if (newUi.getGameMode().equals("Easy")  ) {
            newUi.setModeGameObject(easyMode);
        } else if (newUi.getGameMode().equals("Hard")) {
            newUi.setModeGameObject(hardMode);
        }else {
            newUi.setModeGameObject(twoPlayersMode);
        }
    }
}