package uk.ac.aber.cs221.gp02.chesstutor;


import javafx.application.Application;
import javafx.stage.Stage;
import uk.ac.aber.cs221.gp02.chesstutor.game.Game;
import uk.ac.aber.cs221.gp02.chesstutor.gui.GUI;

/**
 * @author Michael [mjs36]
 * @author Bilal [biy1]
 */
public class Main {

   
   public static void main(String[] args) {

      Game game = new Game();

      game.setBlackPlayer("Peter");
      game.setWhitePlayer("Pan");

      GUI.main(args, game);

      game.getBoard().printBoard();

   }
}
