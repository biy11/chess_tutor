package uk.ac.aber.cs221.gp02.chesstutor;


import javafx.application.Application;
import javafx.stage.Stage;
import uk.ac.aber.cs221.gp02.chesstutor.gui.GameGui;

/**
 * @author Michael [mjs36]
 * @author Bilal [biy1]
 */
public class Main extends Application {

      @Override
   public void start(Stage primaryStage) throws Exception {
         //Makes the GUI non-resizable
         primaryStage.setResizable(false);
         //Game game = new Game();

         //initiates a new GameGui called gameGUi
         GameGui gameGui = new GameGui(primaryStage);
         //runs the initMainMenu method from GameGui Class
         gameGui.initMainMenu(primaryStage);
   }

   
   public static void main(String[] args) {
      Application.launch(args);
   }
}
