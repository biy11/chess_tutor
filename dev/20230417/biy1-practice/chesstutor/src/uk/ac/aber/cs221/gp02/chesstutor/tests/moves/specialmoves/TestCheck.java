package uk.ac.aber.cs221.gp02.chesstutor.tests.moves.specialmoves;

import org.junit.jupiter.api.Test;
import uk.ac.aber.cs221.gp02.chesstutor.game.Game;
import uk.ac.aber.cs221.gp02.chesstutor.game.Square;
import uk.ac.aber.cs221.gp02.chesstutor.moves.CheckChecker;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class TestCheck {


   /**
    * Create a new game with pieces in the default positions and
    * check if the square with the white king is in check
    */
   @Test
   void testStartingCheckWhiteKing(){
      //Create a new game
      Game game = new Game();

      System.out.println(game.getBoard().getBoardArray()[7][4].getPiece().getPieceType());

      //Get the number of attackers of the white king
      List<int[]> attackers = CheckChecker.checkChecker(game.getBoard(), 7, 4);

      //If the list is empty the king has no attackers, and passes the test
      assertTrue(attackers.isEmpty());
   }

   /**
    * Create a new game with pieces in the default positions and
    * check if the square with the black king is in check
    */
   @Test
   void testStartingCheckBlackKing(){
      //Create a new game
      Game game = new Game();

      System.out.println(game.getBoard().getBoardArray()[0][4].getPiece().getPieceType());

      //Get the number of attackers of the white king
      List<int[]> attackers = CheckChecker.checkChecker(game.getBoard(), 0, 4);

      //If the list is empty the king has no attackers, and passes the test
      assertTrue(attackers.isEmpty());
   }
}
