package uk.ac.aber.cs221.gp02.chesstutor;


import uk.ac.aber.cs221.gp02.chesstutor.game.Game;

/**
 * @author Michael [mjs36]
 */
public class Main {
   public static void main(String[] args) {

      Game game = new Game();
      game.getBoard().boardReset();
      game.getBoard().printBoard();
   }
}
