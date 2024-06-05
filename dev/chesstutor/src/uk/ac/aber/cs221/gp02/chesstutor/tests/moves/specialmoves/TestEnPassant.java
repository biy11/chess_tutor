/*
 * @(#) TestEnPassant.java 0.4 2023/05/05
 *
 * Copyright (c) 2023 Aberystwyth University
 * All rights reserved
 */

package uk.ac.aber.cs221.gp02.chesstutor.tests.moves.specialmoves;

import org.junit.jupiter.api.Test;
import uk.ac.aber.cs221.gp02.chesstutor.game.Game;
import uk.ac.aber.cs221.gp02.chesstutor.moves.MakeMove;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.Piece;

import static org.junit.jupiter.api.Assertions.*;
import static uk.ac.aber.cs221.gp02.chesstutor.moves.specialmoves.EnPassant.getMoves;

/**
 * Unit tests for EnPassant, this will run through several EnPassant scenarios to make sure
 * this move works correctly with the current rules of chess
 *
 * @author Thomas [tpr3]
 * @version 0.1 initial development
 * @version 0.2 Fix problems with unit tests
 * @version 0.3 Updated using new enPassant method - mjs36
 * @version 0.4 Fixed issues mentioned
 */
public class TestEnPassant {

   /**
    * Global vars to help avoid declaring for every unit test method
    */
   Game game = new Game();

   /**
    * Tests every pawn at the start of the game to make sure it's not able EnPassant by checking if
    * both int[] in either list has any potential moves.
    */
   @Test
   void testStartingPawnsCanNotEnPassant(){
      game.setBlackPlayer("Ali"); //Sets player names (needed to avoid an error)
      game.setWhitePlayer("Glados");

      //Test every white pawn
      assertTrue(getMoves(game.getBoard(), game.getBoard().getBoardArray()[6][4].getPiece(), 6, 0).isEmpty());
      assertTrue(getMoves(game.getBoard(), game.getBoard().getBoardArray()[6][4].getPiece(), 6, 1).isEmpty());
      assertTrue(getMoves(game.getBoard(), game.getBoard().getBoardArray()[6][4].getPiece(), 6, 2).isEmpty());
      assertTrue(getMoves(game.getBoard(), game.getBoard().getBoardArray()[6][4].getPiece(), 6, 3).isEmpty());
      assertTrue(getMoves(game.getBoard(), game.getBoard().getBoardArray()[6][4].getPiece(), 6, 4).isEmpty());
      assertTrue(getMoves(game.getBoard(), game.getBoard().getBoardArray()[6][4].getPiece(), 6, 5).isEmpty());
      assertTrue(getMoves(game.getBoard(), game.getBoard().getBoardArray()[6][4].getPiece(), 6, 6).isEmpty());
      assertTrue(getMoves(game.getBoard(), game.getBoard().getBoardArray()[6][4].getPiece(), 6, 7).isEmpty());

      //Test every black pawn
      assertTrue(getMoves(game.getBoard(), game.getBoard().getBoardArray()[6][4].getPiece(), 1, 0).isEmpty());
      assertTrue(getMoves(game.getBoard(), game.getBoard().getBoardArray()[6][4].getPiece(), 1, 1).isEmpty());
      assertTrue(getMoves(game.getBoard(), game.getBoard().getBoardArray()[6][4].getPiece(), 1, 2).isEmpty());
      assertTrue(getMoves(game.getBoard(), game.getBoard().getBoardArray()[6][4].getPiece(), 1, 3).isEmpty());
      assertTrue(getMoves(game.getBoard(), game.getBoard().getBoardArray()[6][4].getPiece(), 1, 4).isEmpty());
      assertTrue(getMoves(game.getBoard(), game.getBoard().getBoardArray()[6][4].getPiece(), 1, 5).isEmpty());
      assertTrue(getMoves(game.getBoard(), game.getBoard().getBoardArray()[6][4].getPiece(), 1, 6).isEmpty());
      assertTrue(getMoves(game.getBoard(), game.getBoard().getBoardArray()[6][4].getPiece(), 1, 7).isEmpty());

   }

   /**
    * This test will run a simulated game using the backend that will allow the white pawn to EnPassant once
    * the black piece makes its initial two-step movement. It will only check to make sure it can move, not
    * that it will move.
    */
   @Test
   void testWorkingEnPassant(){
      game.setBlackPlayer("Ali"); //Sets player names (needed to avoid an error)
      game.setWhitePlayer("Glados");

      //Moves white pawn two spaces forward and ends turn
      MakeMove.movePiece(game.getBoard(), game.getWhitePlayer(), 6, 4, 4, 4);
      game.nextRound();
      //Moves black pawn two spaces forward and ends turn
      MakeMove.movePiece(game.getBoard(), game.getBlackPlayer(), 1, 1, 3, 1);
      game.nextRound();
      //Moves white pawn two spaces forward and ends turn
      MakeMove.movePiece(game.getBoard(), game.getWhitePlayer(), 4, 4, 3, 4);
      game.nextRound();
      //Moves black pawn two spaces forward and ends turn
      MakeMove.movePiece(game.getBoard(), game.getBlackPlayer(), 1, 3, 3, 3);
      game.nextRound();

      //Check if EnPassant returns the correct EnPassant list
      assertFalse(getMoves(game.getBoard(), game.getBoard().getBoardArray()[3][4].getPiece(), 3, 4).isEmpty());
   }

   /**
    * Tests the EnPassant move will work using the makeMove class, it will check that the piece has been
    * moved correctly as well as if the black pawn was captured correctly
    */
   @Test
   void testEnPassantMakeMove(){
      //Creates new game instance
      Game game = new Game();
      //Sets player names
      game.setBlackPlayer("Ellie");
      game.setWhitePlayer("HAL 9000");

      //Local black pawn variable
      Piece blackPawn = game.getBoard().getBoardArray()[1][3].getPiece();

      //Moves white pawn two spaces forward and ends turn
      MakeMove.movePiece(game.getBoard(), game.getWhitePlayer(), 6, 4, 4, 4);
      game.nextRound();
      //Moves black pawn two spaces forward and ends turn
      MakeMove.movePiece(game.getBoard(), game.getBlackPlayer(), 1, 1, 3, 1);
      game.nextRound();
      //Moves white pawn two spaces forward and ends turn
      MakeMove.movePiece(game.getBoard(), game.getWhitePlayer(), 4, 4, 3, 4);
      game.nextRound();
      //Moves black pawn two spaces forward and ends turn
      MakeMove.movePiece(game.getBoard(), game.getBlackPlayer(), 1, 3, 3, 3);
      game.nextRound();

      //Check if EnPassant works
      MakeMove.movePiece(game.getBoard(), game.getWhitePlayer(), 3, 4, 2, 3);
      //Checks if pawn was moved to correct position and captured piece added to taken pieces
      assertTrue(game.getBoard().getBoardArray()[2][3].hasPiece());
      assertTrue(game.getWhitePlayer().getTakenPieces().contains(blackPawn));
   }

   /**
    * Tests an illegal EnPassant where the opposing pawn has not moved two squares on its inital moves,
    * this should then return an empty list since the white pawn should not be allowed to EnPassant
    */
   @Test
   void testEnPassantWithoutTwoSquare(){
      //Creates new game instance
      Game game = new Game();
      //Sets player names
      game.setBlackPlayer("Chloe");
      game.setWhitePlayer("Adam");

      //Moves white pawn two spaces forward and ends turn
      MakeMove.movePiece(game.getBoard(), game.getWhitePlayer(), 6, 4, 4, 4);
      game.nextRound();
      //Moves black pawn two spaces forward and ends turn
      MakeMove.movePiece(game.getBoard(), game.getBlackPlayer(), 1, 3, 3, 3);
      game.nextRound();
      //Moves white pawn two spaces forward and ends turn
      MakeMove.movePiece(game.getBoard(), game.getWhitePlayer(), 4, 4, 3, 4);
      game.nextRound();
      //Moves black pawn two spaces forward and ends turn
      MakeMove.movePiece(game.getBoard(), game.getBlackPlayer(), 1, 1, 3, 1);
      game.nextRound();

      //Check if EnPassant returns the correct EnPassant list
      assertTrue(getMoves(game.getBoard(), game.getBoard().getBoardArray()[3][4].getPiece(), 3, 4).isEmpty());
   }
}
