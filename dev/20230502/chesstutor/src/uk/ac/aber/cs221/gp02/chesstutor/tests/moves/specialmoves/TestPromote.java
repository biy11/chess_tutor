package uk.ac.aber.cs221.gp02.chesstutor.tests.moves.specialmoves;

import org.junit.jupiter.api.Test;
import uk.ac.aber.cs221.gp02.chesstutor.game.Game;
import uk.ac.aber.cs221.gp02.chesstutor.moves.MakeMove;
import uk.ac.aber.cs221.gp02.chesstutor.moves.specialmoves.Promote;
import uk.ac.aber.cs221.gp02.chesstutor.util.Type;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Promote class
 *
 * @author Thomas [tpr3]
 * @version 0.1 initial development
 * @version 0.2 add more comments
 */
public class TestPromote {

   /**
    * Tests if pawn can be promoted when it gets to the other side of
    * the board by moving the white pawn to the other side of the board
    * and then promoting it to a rook. The method then tests if the piece
    * is now the new piece type and if the white pawn has been added to the
    * taken pieces for the black player
    */
   @Test
   void testPromotion(){
      //Creates new game instance
      Game game = new Game();
      //Sets player names
      game.setBlackPlayer("Blag");
      game.setWhitePlayer("Marcgg");

      MakeMove.movePiece(game.getBoard(), game.getWhitePlayer(), 6, 0, 0, 0);
      Promote.promotePawn(game.getBoard(), game.getWhitePlayer(), 0, 0, Type.ROOK);

      assertEquals(game.getBoard().getBoardArray()[0][0].getPiece().getPieceType(), Type.ROOK);
      assertTrue(game.getBlackPlayer().getTakenPieces().isEmpty());
   }

   /**
    * Tests if pawn can be promoted even if it is an illegal promotion by promoting a
    * starting pawn
    */
   @Test
   void testPawnIllegalPromotion(){
      //Creates new game instance
      Game game = new Game();
      //Sets player names
      game.setBlackPlayer("Megatron");
      game.setWhitePlayer("Bumblebee");

      Promote.promotePawn(game.getBoard(), game.getWhitePlayer(), 6, 0, Type.ROOK);
      assertNotEquals(game.getBoard().getBoardArray()[6][0].getPiece().getPieceType(), Type.ROOK);
   }

   /**
    * Tests if any piece on the board can be promoted, including piece that isn't
    * a pawn
    */
   @Test
   void testAnyPromotion(){
      //Creates new game instance
      Game game = new Game();
      //Sets player names
      game.setBlackPlayer("Ellie");
      game.setWhitePlayer("HAL 9000");

      Promote.promotePawn(game.getBoard(), game.getBlackPlayer(), 0, 0, Type.QUEEN);
      assertNotEquals(game.getBoard().getBoardArray()[0][0].getPiece().getPieceType(), Type.QUEEN);
   }
}
