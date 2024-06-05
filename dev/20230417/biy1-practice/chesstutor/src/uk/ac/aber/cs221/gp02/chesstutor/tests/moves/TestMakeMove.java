package uk.ac.aber.cs221.gp02.chesstutor.tests.moves;

import org.junit.jupiter.api.Test;

import uk.ac.aber.cs221.gp02.chesstutor.game.Game;
import uk.ac.aber.cs221.gp02.chesstutor.game.Square;
import uk.ac.aber.cs221.gp02.chesstutor.moves.MakeMove;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.Piece;
import uk.ac.aber.cs221.gp02.chesstutor.util.Color;
import uk.ac.aber.cs221.gp02.chesstutor.util.Type;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the MakeMove class
 *
 * @author Lance [lvs1]
 * @author Thomas [tpr3]
 * @version 0.1 initial development
 * @version 0.2 implemented initial tests
 * @version 0.3 added more tests
 * @version 0.4 implemented takeOpponentsPiece & makeIllegalMove
 * @version 0.5 fixed bug and added test for taken pieces, removed makeIllegalMove
 */
public class TestMakeMove {

   /**
    * Moves the black player's pawn to an empty space then checks if the pawn has moved to the
    * correct space by seeing if the previous space is empty and making sure the correct piece
    * has moved to the new location.
    */
   @Test
   void movePieceToEmptySpace() {
      Game game = new Game();
      MakeMove.movePiece(game.getBoard(), game.getWhitePlayer(), 6, 1, 5, 5);
      Square[][] movedPiece = game.getBoard().getBoardArray();

      assertFalse(movedPiece[6][1].isHasPiece()); //Checks if previous space is empty
      assertEquals(movedPiece[5][5].getPiece().getPieceType(), Type.PAWN); //Checks if new position is right type
      assertEquals(movedPiece[5][5].getPiece().getPieceColor(), Color.WHITE); //Checks if new position is right color
   }

   /**
    * Checks if a player tries to move a piece out of bounds of the game board by throwing an
    * index out of bounds exception.
    */
   @Test
   void movePieceOffBoard(){
      boolean thrown = false;
      Game game = new Game();
      try {
         MakeMove.movePiece(game.getBoard(), game.getWhitePlayer(), 6, 1, 9, 9);
      } catch (IndexOutOfBoundsException e) {
         thrown = true;
      }
      assertTrue(thrown);
   }

   /**
    * Checks if a player captures an enemies piece correctly by simulating a few rounds of a game,
    * moving two opposing pawns so that one captures the other.
    */
   @Test
   void takeOpponentsPiece(){
      //Creates new game instance
      Game game = new Game();
      //Sets player names
      game.setBlackPlayer("Fred");
      game.setWhitePlayer("Mo");

      //Creates local instance of black pawn for testing
      Piece blackPawn = game.getBoard().getBoardArray()[1][2].getPiece();

      //Moves white pawn two spaces forward and ends turn
      MakeMove.movePiece(game.getBoard(), game.getWhitePlayer(), 6, 1, 4, 1);
      game.nextRound();
      //Moves black pawn two spaces forward so white can capture it and ends turn
      MakeMove.movePiece(game.getBoard(), game.getBlackPlayer(), 1, 2, 3, 2);
      game.nextRound();
      //White pawn captures black pawn and ends turn
      MakeMove.movePiece(game.getBoard(), game.getWhitePlayer(), 4, 1, 3, 2);
      game.getBoard().printBoard();
      Square[][] movedPiece = game.getBoard().getBoardArray();

      //Checks if previous space is empty
      assertFalse(movedPiece[4][1].isHasPiece());
      //Checks if new piece is right type
      assertEquals(movedPiece[3][2].getPiece().getPieceType(), Type.PAWN);
      //Checks if new position is right color
      assertEquals(movedPiece[3][2].getPiece().getPieceColor(), Color.WHITE);
      //Checks if black pawn has been added to white player's taken pieces
      assertTrue(game.getWhitePlayer().getTakenPieces().contains(blackPawn));
   }
}
