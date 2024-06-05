/*
 * @(#) TestCheck.java 0.3 2023/05/03
 *
 * Copyright (c) 2023 Aberystwyth University
 * All rights reserved
 */

package uk.ac.aber.cs221.gp02.chesstutor.tests.moves.specialmoves;

import org.junit.jupiter.api.Test;
import uk.ac.aber.cs221.gp02.chesstutor.game.Game;
import uk.ac.aber.cs221.gp02.chesstutor.game.Square;
import uk.ac.aber.cs221.gp02.chesstutor.moves.CheckChecker;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.Bishop;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.King;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.Pawn;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.Rook;
import uk.ac.aber.cs221.gp02.chesstutor.util.Color;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * This class contains tests for different Check scenarios
 *
 * @author Micah [mib60]
 * @version 0.1 Initial Development
 * @version 0.2 Added more tests
 * @version 0.3 Added javadoc and comments
 */
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
      List<int[]> attackers = CheckChecker.checkChecker(game.getBoard(), 7, 4, Color.BLACK);

      //If the list is empty the king has no attackers, and passes the test
      assertTrue(attackers.isEmpty());
   }

   /**
    * Test that a piece can not move if it endangers the friendly king
    */
   @Test
   void testPieceCanMoveIfEndangersKing(){
      //Create a new game
      Game game = new Game();
      game.getBoard().clearBoard();

      game.getBoard().setSquare(new Square(new Rook(Color.BLACK)), 3, 4); //Rook attacking the king

      game.getBoard().setSquare(new Square(new Bishop(Color.WHITE)), 4, 4); //Pawn blocking the rook

      game.getBoard().setSquare(new Square(new King(Color.WHITE)), 5, 4); //King


      //Get the moves of the pawn blocking the rook
      List<int[]> moves = game.getBoard().getBoardArray()[4][4].getPiece().getPossibleMoves(game.getBoard(), game.getBoard().getBoardArray()[4][4].getPiece(), 4, 4);

      game.getBoard().printBoard();
      assertTrue(moves.isEmpty());
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
      List<int[]> attackers = CheckChecker.checkChecker(game.getBoard(), 0, 4, Color.WHITE);

      //If the list is empty the king has no attackers, and passes the test
      assertTrue(attackers.isEmpty());
   }

   /**
    * Test that the white king is in check when attacked by a piece
    */
   @Test
   void testSingleCheckWhite(){
      Game game = new Game();

      game.getBoard().clearBoard();
      game.getBoard().setSquare(new Square(new King(Color.WHITE)), 7, 4);
      game.getBoard().setSquare(new Square(new Bishop(Color.BLACK)), 5, 2);
      game.getBoard().setSquare(new Square(new King(Color.BLACK)), 3, 6);
      game.getBoard().printBoard();

      assertEquals(1, CheckChecker.checkChecker(game.getBoard(), 7, 4, Color.BLACK).size());
   }

   /**
    * Test that the white king is in check when attacked by two pieces
    */
   @Test
   void testDoubleCheckWhite(){
      Game game = new Game();

      game.getBoard().clearBoard();
      game.getBoard().setSquare(new Square(new King(Color.WHITE)), 7, 4);
      game.getBoard().setSquare(new Square(new Bishop(Color.BLACK)), 5, 2);
      game.getBoard().setSquare(new Square(new King(Color.BLACK)), 6, 5);
      game.getBoard().printBoard();

      assertEquals(2, CheckChecker.checkChecker(game.getBoard(), 7, 4, Color.BLACK).size());
   }

   /**
    * Test that the white king is not in check when it's attacker is blocked
    */
   @Test
   void testBlockedCheckWhite(){
      Game game = new Game();

      game.getBoard().clearBoard();
      game.getBoard().setSquare(new Square(new King(Color.WHITE)), 7, 4);
      game.getBoard().setSquare(new Square(new Bishop(Color.BLACK)), 5, 2);
      game.getBoard().setSquare(new Square(new King(Color.BLACK)), 3, 6);

      //The white pawn is blocking the attack
      game.getBoard().setSquare(new Square(new Pawn(Color.WHITE)), 6, 3);

      game.getBoard().printBoard();

      assertTrue(CheckChecker.checkChecker(game.getBoard(), 7, 4, Color.BLACK).isEmpty());
   }

   /**
    * Test that the black king is in check when attacked by a piece
    */
   @Test
   void testSingleCheckBlack(){
      Game game = new Game();

      game.getBoard().clearBoard();
      game.getBoard().setSquare(new Square(new King(Color.BLACK)), 0, 4);

      game.getBoard().setSquare(new Square(new King(Color.WHITE)), 7, 4);

      game.getBoard().setSquare(new Square(new Bishop(Color.WHITE)), 2, 6);
      game.getBoard().printBoard();

      assertEquals(1, CheckChecker.checkChecker(game.getBoard(), 0, 4, Color.WHITE).size());
   }

   /**
    * Test that the black king is in double check when attacked by two pieces
    */
   @Test
   void testDoubleCheckBlack(){
      Game game = new Game();

      game.getBoard().clearBoard();
      game.getBoard().setSquare(new Square(new King(Color.BLACK)), 0, 4);

      game.getBoard().setSquare(new Square(new King(Color.WHITE)), 1, 3);

      game.getBoard().setSquare(new Square(new Bishop(Color.WHITE)), 2, 6);
      game.getBoard().printBoard();

      assertEquals(2, CheckChecker.checkChecker(game.getBoard(), 0, 4, Color.WHITE).size());
   }

   /**
    * Test that the black king is in not in check when the attack is blocked
    */
   @Test
   void testBlockedCheckBlack(){
      Game game = new Game();

      game.getBoard().clearBoard();
      game.getBoard().setSquare(new Square(new King(Color.BLACK)), 0, 4);

      game.getBoard().setSquare(new Square(new Bishop(Color.WHITE)), 2, 6);

      game.getBoard().setSquare(new Square(new Pawn(Color.BLACK)), 1, 5);
      game.getBoard().printBoard();

      assertTrue(CheckChecker.checkChecker(game.getBoard(), 0, 4, Color.WHITE).isEmpty());
   }
}
