/*
 * @(#) TestCastle.java 0.4 2023/05/03
 *
 * Copyright (c) 2023 Aberystwyth University
 * All rights reserved
 */

package uk.ac.aber.cs221.gp02.chesstutor.tests.moves.specialmoves;

import org.junit.jupiter.api.Test;
import uk.ac.aber.cs221.gp02.chesstutor.game.Game;
import uk.ac.aber.cs221.gp02.chesstutor.game.Square;
import uk.ac.aber.cs221.gp02.chesstutor.moves.CheckChecker;
import uk.ac.aber.cs221.gp02.chesstutor.moves.MakeMove;
import uk.ac.aber.cs221.gp02.chesstutor.moves.specialmoves.Castle;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.King;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.Pawn;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.Piece;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.Rook;
import uk.ac.aber.cs221.gp02.chesstutor.util.Color;
import uk.ac.aber.cs221.gp02.chesstutor.util.Type;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for castling the king piece
 *
 * @author Thomas [tpr3]
 * @version 0.1 initial development
 * @version 0.2 finished all tests
 * @version 0.3 Fixed some tests/changed some tests
 * @version 0.4 Added test to make sure king cannot castle into check
 */
public class TestCastle {

   /**
    * Test working kingside castle by simulating 7 turns of a regular game
    * and then castling the white king from the king's side.
    */
   @Test
   void testKingsideCastle(){
      //Creates new game instance
      Game game = new Game();
      //Sets player names
      game.setBlackPlayer("Dan");
      game.setWhitePlayer("Angela");

      //Moves white pawn two spaces forward and ends turn
      MakeMove.movePiece(game.getBoard(), game.getWhitePlayer(), 6, 4, 4, 4);
      game.nextRound();

      //Moves black pawn two spaces forward and ends turn
      MakeMove.movePiece(game.getBoard(), game.getBlackPlayer(), 1, 3, 2, 3);
      game.nextRound();

      //Moves white bishop and ends turn
      MakeMove.movePiece(game.getBoard(), game.getWhitePlayer(), 7, 5, 3, 1);
      game.nextRound();

      //Moves black pawn one space forward and ends turn
      MakeMove.movePiece(game.getBoard(), game.getBlackPlayer(), 1, 0, 2, 0);
      game.nextRound();

      //Moves white knight and ends turn
      MakeMove.movePiece(game.getBoard(), game.getWhitePlayer(), 7, 6, 5, 7);
      game.nextRound();

      //Black pawn captures bishop and ends turn
      MakeMove.movePiece(game.getBoard(), game.getBlackPlayer(), 2, 0, 3, 1);
      game.nextRound();

      //Check if canCastle method returns list of potential moves
      List<int[]> castleOutput = Castle.canCastle(game.getBoard(), game.getBoard().getBoardArray()[7][4].getPiece(), 7,4);
      assertFalse(castleOutput.isEmpty());

      game.getBoard().printBoard();

      //Castle white king
      MakeMove.movePiece(game.getBoard(), game.getWhitePlayer(), 7, 4, 7, 6);
      System.out.println("");
      game.getBoard().printBoard();

      //Check if castle has worked correctly
      assertEquals(game.getBoard().getBoardArray()[7][6].getPiece().getPieceType(), Type.KING);
      assertEquals(game.getBoard().getBoardArray()[7][5].getPiece().getPieceType(), Type.ROOK);
      //Check if king and castle are now set to moved
      assertTrue(game.getBoard().getBoardArray()[7][6].getPiece().hasMoved());
      assertTrue(game.getBoard().getBoardArray()[7][5].getPiece().hasMoved());
   }

   /**
    * Test working queenside castle by simulating 8 turns of a regular game
    * and then castling the white king from the queen's side.
    */
   @Test
   void testQueensideCastle(){
      //Creates new game instance
      Game game = new Game();
      //Sets player names
      game.setBlackPlayer("Dan");
      game.setWhitePlayer("Angela");

      //Moves white pawn two spaces forward and ends turn
      MakeMove.movePiece(game.getBoard(), game.getWhitePlayer(), 6, 1, 4, 1);
      game.nextRound();

      //Moves black pawn two spaces forward and ends turn
      MakeMove.movePiece(game.getBoard(), game.getBlackPlayer(), 1, 3, 2, 3);
      game.nextRound();

      //Moves white knight and ends turn
      MakeMove.movePiece(game.getBoard(), game.getWhitePlayer(), 7, 1, 5, 2);
      game.nextRound();

      //Moves black pawn one space forward and ends turn
      MakeMove.movePiece(game.getBoard(), game.getBlackPlayer(), 1, 0, 2, 0);
      game.nextRound();

      //Moves white bishop and ends turn
      MakeMove.movePiece(game.getBoard(), game.getWhitePlayer(), 7, 2, 5, 0);
      game.nextRound();

      //Moves black pawn and ends turn
      MakeMove.movePiece(game.getBoard(), game.getBlackPlayer(), 2, 0, 3, 0);
      game.nextRound();

      //Moves white queen twice
      MakeMove.movePiece(game.getBoard(), game.getWhitePlayer(), 7, 3, 7, 1);
      MakeMove.movePiece(game.getBoard(), game.getWhitePlayer(), 7, 1, 6, 1);

      game.getBoard().printBoard();

      //Check if canCastle method returns list of potential moves
      List<int[]> castleOutput = Castle.canCastle(game.getBoard(), game.getBoard().getBoardArray()[7][4].getPiece(), 7,4);
      assertFalse(castleOutput.isEmpty());

      //Castle white king
      MakeMove.movePiece(game.getBoard(), game.getWhitePlayer(), 7, 4, 7, 2);

      game.getBoard().printBoard();

      //Check if castle has worked correctly
      assertEquals(game.getBoard().getBoardArray()[7][2].getPiece().getPieceType(), Type.KING);
      assertEquals(game.getBoard().getBoardArray()[7][3].getPiece().getPieceType(), Type.ROOK);
      //Check if king and castle are now set to moved
      assertTrue(game.getBoard().getBoardArray()[7][2].getPiece().hasMoved());
      assertTrue(game.getBoard().getBoardArray()[7][3].getPiece().hasMoved());
   }

   /**
    * Test castling if king has been moved by simulating 7 turns of a regular game
    * and then moving the white king one space right before trying the makeMove
    * method.
    */
   @Test
   void testCastleButKingMoved() {
      //Creates new game instance
      Game game = new Game();
      //Sets player names
      game.setBlackPlayer("Dan");
      game.setWhitePlayer("Angela");

      //Moves white pawn two spaces forward and ends turn
      MakeMove.movePiece(game.getBoard(), game.getWhitePlayer(), 6, 4, 4, 4);
      game.nextRound();

      //Moves black pawn two spaces forward and ends turn
      MakeMove.movePiece(game.getBoard(), game.getBlackPlayer(), 1, 3, 2, 3);
      game.nextRound();

      //Moves white bishop and ends turn
      MakeMove.movePiece(game.getBoard(), game.getWhitePlayer(), 7, 5, 3, 1);
      game.nextRound();

      //Moves black pawn one space forward and ends turn
      MakeMove.movePiece(game.getBoard(), game.getBlackPlayer(), 1, 0, 2, 0);
      game.nextRound();

      //Moves white knight and ends turn
      MakeMove.movePiece(game.getBoard(), game.getWhitePlayer(), 7, 6, 5, 7);
      game.nextRound();

      //Black pawn captures bishop and ends turn
      MakeMove.movePiece(game.getBoard(), game.getBlackPlayer(), 2, 0, 3, 1);
      game.nextRound();

      //Moves king
      MakeMove.movePiece(game.getBoard(), game.getWhitePlayer(), 7, 4, 7, 5);

      //Check if canCastle method does not return a list of potential moves
      List<int[]> castleOutput = Castle.canCastle(game.getBoard(), game.getBoard().getBoardArray()[7][5].getPiece(), 7, 5);
      assertTrue(castleOutput.isEmpty());

      game.getBoard().printBoard();

      //Castle white king
      MakeMove.movePiece(game.getBoard(), game.getWhitePlayer(), 7, 5, 7, 6);

      game.getBoard().printBoard();

      //Check if castle has worked correctly
      assertEquals(game.getBoard().getBoardArray()[7][6].getPiece().getPieceType(), Type.KING);
      assertEquals(game.getBoard().getBoardArray()[7][7].getPiece().getPieceType(), Type.ROOK);
   }

   /**
    * Test castling if rook has been moved
    */
   @Test
   void testCastleButRookMoved(){
      //Creates new game instance
      Game game = new Game();
      //Sets player names
      game.setBlackPlayer("Dan");
      game.setWhitePlayer("Angela");

      //Moves white pawn two spaces forward and ends turn
      MakeMove.movePiece(game.getBoard(), game.getWhitePlayer(), 6, 4, 4, 4);
      game.nextRound();

      //Moves black pawn two spaces forward and ends turn
      MakeMove.movePiece(game.getBoard(), game.getBlackPlayer(), 1, 3, 2, 3);
      game.nextRound();

      //Moves white bishop and ends turn
      MakeMove.movePiece(game.getBoard(), game.getWhitePlayer(), 7, 5, 3, 1);
      game.nextRound();

      //Moves black pawn one space forward and ends turn
      MakeMove.movePiece(game.getBoard(), game.getBlackPlayer(), 1, 0, 2, 0);
      game.nextRound();

      //Moves white knight and ends turn
      MakeMove.movePiece(game.getBoard(), game.getWhitePlayer(), 7, 6, 5, 7);
      game.nextRound();

      //Black pawn captures bishop and ends turn
      MakeMove.movePiece(game.getBoard(), game.getBlackPlayer(), 2, 0, 3, 1);
      game.nextRound();

      //Moves rook
      MakeMove.movePiece(game.getBoard(), game.getWhitePlayer(), 7, 7, 7, 5);

      //Check if canCastle method does not return a list of potential moves
      List<int[]> castleOutput = Castle.canCastle(game.getBoard(), game.getBoard().getBoardArray()[7][4].getPiece(), 7, 4);

      for(int[] asd:castleOutput){
         System.out.println(Arrays.toString(asd));
      }

      game.getBoard().printBoard();

      assertTrue(castleOutput.isEmpty());

      //Castle white king
      Piece king = game.getBoard().getBoardArray()[7][4].getPiece();
      List<int[]> kingMoves = king.getPossibleMoves(game.getBoard(), king, 7, 4);

      assertEquals(1, kingMoves.size());
      assertEquals(6, kingMoves.get(0)[0]);
      assertEquals(4, kingMoves.get(0)[1]);

      game.getBoard().printBoard();
   }

   /**
    * Test if castling is allowed if the king is in check
    */
   @Test
   void testCastleButKingChecked(){
      //Creates new game instance
      Game game = new Game();
      //Sets player names
      game.setBlackPlayer("Megan");
      game.setWhitePlayer("Fred");

      //Moves white pawn two spaces forward and ends turn
      MakeMove.movePiece(game.getBoard(), game.getWhitePlayer(), 6, 4, 4, 4);
      game.nextRound();

      //Moves black pawn two spaces forward and ends turn
      MakeMove.movePiece(game.getBoard(), game.getBlackPlayer(), 1, 4, 2, 4);
      game.nextRound();

      //Moves white bishop and ends turn
      MakeMove.movePiece(game.getBoard(), game.getWhitePlayer(), 7, 5, 3, 1);
      game.nextRound();

      //Moves black queen to check position and ends turn
      MakeMove.movePiece(game.getBoard(), game.getBlackPlayer(), 0, 3, 4, 7);
      game.nextRound();

      //Moves white knight and ends turn
      MakeMove.movePiece(game.getBoard(), game.getWhitePlayer(), 7, 6, 5, 7);
      game.nextRound();

      //Black queen captures pawn and ends turn
      MakeMove.movePiece(game.getBoard(), game.getBlackPlayer(), 4, 7, 6, 5);
      game.nextRound();

      //Make sure king is in check
      assertFalse(CheckChecker.checkChecker(game.getBoard(), 7, 4, Color.BLACK).isEmpty());

      game.getBoard().printBoard();

      //Check if canCastle method does not return a list of potential moves
      List<int[]> castleOutput = Castle.canCastle(game.getBoard(), game.getBoard().getBoardArray()[7][4].getPiece(), 7,4);
      assertTrue(castleOutput.isEmpty());

      //Try to castle white king
      MakeMove.movePiece(game.getBoard(), game.getWhitePlayer(), 7, 4, 7, 6);

      game.getBoard().printBoard();

      //Check if illegal castle has was allowed
      assertEquals(game.getBoard().getBoardArray()[7][6].getPiece().getPieceType(), Type.KING);
      assertEquals(game.getBoard().getBoardArray()[7][7].getPiece().getPieceType(), Type.ROOK);
   }

   /**
    * Test if a castle can be made even when it is illegal to do so
    */
   @Test
   void testIllegalCastle(){
      //Creates new game instance
      Game game = new Game();
      //Sets player names
      game.setBlackPlayer("Megan");
      game.setWhitePlayer("Fred");

      //Check if canCastle method does not return a list of potential moves
      List<int[]> castleOutput = Castle.canCastle(game.getBoard(), game.getBoard().getBoardArray()[7][4].getPiece(), 7,4);
      assertTrue(castleOutput.isEmpty());

      game.getBoard().printBoard();
      
      Piece king = game.getBoard().getBoardArray()[7][4].getPiece();

      List<int[]> possibleMoves = king.getPossibleMoves(game.getBoard(), king, 7, 4);

      //Check if illegal castle was allowed
      assertTrue(possibleMoves.isEmpty());
   }

   /**
    * Test that the king can not castle if it puts him in check
    */
   @Test
   void testKingCannotCastleIntoCheck(){
      Game game = new Game();
      game.getBoard().clearBoard();

      game.getBoard().getBoardArray()[7][5] = new Square(new King(Color.WHITE));

      game.getBoard().getBoardArray()[7][7] = new Square(new Rook(Color.WHITE));

      game.getBoard().getBoardArray()[6][6] = new Square(new Pawn(Color.BLACK));

      assertTrue(Castle.canCastle(game.getBoard(), game.getBoard().getBoardArray()[7][5].getPiece(), 7, 5).isEmpty());
   }
}
