package uk.ac.aber.cs221.gp02.chesstutor;


import uk.ac.aber.cs221.gp02.chesstutor.game.Game;
import uk.ac.aber.cs221.gp02.chesstutor.tests.Test;

import java.util.List;

/**
 * @author Michael [mjs36]
 */
public class Main {
   public static void main(String[] args) {

      Game game = new Game();
      game.getBoard().printBoard();

      List<int[]> test = Test.possibleMoves(game.getBoard().getBoardArray(), 1, 1);

      test.forEach((a) -> System.out.println(a[0] + "," + a[1]));

   }
}
