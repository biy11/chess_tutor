package uk.ac.aber.cs221.gp02.chesstutor;


import javafx.application.Application;
import javafx.stage.Stage;
import uk.ac.aber.cs221.gp02.chesstutor.game.Game;
import uk.ac.aber.cs221.gp02.chesstutor.gui.GameGui;

/**
 * @author Michael [mjs36]
 * @author Bilal [biy1]
 */
public class Main extends Application {

   private GameGui gameGui;

      @Override
   public void start(Stage primaryStage) throws Exception {
         //Makes the GUI non-resizable
         primaryStage.setResizable(false);
         //initiates a new GameGui called gameGUi
         gameGui = new GameGui(primaryStage);
         //runs the initMainMenu method from GameGui Class
         gameGui.initMainMenu(primaryStage);
   }

   
   public static void main(String[] args) {

      Game game = new Game();
      //uncomment/comment  line 33 to launch GUI/not to launch GUI
      Application.launch(args);

      game.getBoard().printBoard();

   }
}
