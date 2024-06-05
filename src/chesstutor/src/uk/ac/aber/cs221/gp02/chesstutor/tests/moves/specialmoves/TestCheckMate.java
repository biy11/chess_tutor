/*
 * @(#) TestCheckMate.java 0.4 2023/05/03
 *
 * Copyright (c) 2023 Aberystwyth University
 * All rights reserved
 */

package uk.ac.aber.cs221.gp02.chesstutor.tests.moves.specialmoves;

import org.junit.jupiter.api.Test;

import uk.ac.aber.cs221.gp02.chesstutor.game.Game;
import uk.ac.aber.cs221.gp02.chesstutor.game.Square;
import uk.ac.aber.cs221.gp02.chesstutor.moves.CheckChecker;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.King;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.Pawn;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.Queen;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.Rook;
import uk.ac.aber.cs221.gp02.chesstutor.util.Color;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * These unit tests check checkmate by running through several scenarios and making
 * sure that they work with the current chess rules
 *
 * @author Micah [mib60]
 * @version 0.1 Initial Development
 * @version 0.2 Added more tests
 * @version 0.3 Added comments and javadoc
 * @version 0.4 Removed unused imports
 */
public class TestCheckMate {

   /**
    * Create a new game with pieces in the default positions and
    * check if the square with the white king is in Check Mate
    */
   @Test
   void testStartingCheckMateWhiteKing(){
      //Create a new game
      Game game = new Game();

      //If checkMateChecker returns false the test is passed
      assertFalse(CheckChecker.checkMateChecker(game.getBoard(), 7, 4));
   }

   /**
    * Create a new game with pieces in the default positions and
    * check if the square with the black king is in Check Mate
    */
   @Test
   void testStartingCheckMateBlackKing(){
      //Create a new game
      Game game = new Game();

      //If checkMateChecker returns false the test is passed
      assertFalse(CheckChecker.checkMateChecker(game.getBoard(), 0, 4));
   }

   /**
    *  The black king is in checkmate by the white queen and rook
    */
   @Test
   void testCheckMateSituation1(){
      //Create a new game
      Game game = new Game();

      //Set pieces
      game.getBoard().clearBoard();
      game.getBoard().setSquare(new Square(new Rook(Color.WHITE)), 7, 7);
      game.getBoard().setSquare(new Square(new Queen(Color.WHITE)), 3, 5);
      game.getBoard().setSquare(new Square(new King(Color.BLACK)), 3, 7);
      game.getBoard().printBoard();

      assertTrue(CheckChecker.checkMateChecker(game.getBoard(), 3, 7));
   }

   /**
    *  The black king is in checkmate by the white queen and king
    */
   @Test
   void testCheckMateSituation2(){
      //Create a new game
      Game game = new Game();

      //Set pieces
      game.getBoard().clearBoard();
      game.getBoard().setSquare(new Square(new King(Color.WHITE)),2, 2);
      game.getBoard().setSquare(new Square(new Queen(Color.WHITE)), 2, 1);
      game.getBoard().setSquare(new Square(new King(Color.BLACK)), 2, 0);
      game.getBoard().printBoard();

      assertTrue(CheckChecker.checkMateChecker(game.getBoard(), 2, 0));
   }

   /**
    *  The black king is in checkmate by the white queen and king
    */
   @Test
   void testCheckMateSituation3(){
      //Create a new game
      Game game = new Game();

      //Set pieces
      game.getBoard().clearBoard();
      game.getBoard().setSquare(new Square(new King(Color.WHITE)),2, 5);
      game.getBoard().setSquare(new Square(new Queen(Color.WHITE)), 7, 7);
      game.getBoard().setSquare(new Square(new King(Color.BLACK)), 2, 7);
      game.getBoard().printBoard();

      assertTrue(CheckChecker.checkMateChecker(game.getBoard(), 2, 7));
   }

   /**
    *  The black king is in checkmate by the white queen and king
    */
   @Test
   void testCheckMateSituation4(){
      //Create a new game
      Game game = new Game();

      //Set pieces
      game.getBoard().clearBoard();
      game.getBoard().setSquare(new Square(new King(Color.WHITE)),5, 6);
      game.getBoard().setSquare(new Square(new Queen(Color.WHITE)), 7, 4);
      game.getBoard().setSquare(new Square(new King(Color.BLACK)), 7, 7);
      game.getBoard().printBoard();

      assertTrue(CheckChecker.checkMateChecker(game.getBoard(), 7, 7));
   }

   /**
    *  The black king is in checkmate by the white queen and king
    */
   @Test
   void testCheckMateSituation5(){
      //Create a new game
      Game game = new Game();

      //Set pieces
      game.getBoard().clearBoard();
      game.getBoard().setSquare(new Square(new King(Color.WHITE)),6, 5);
      game.getBoard().setSquare(new Square(new Queen(Color.WHITE)), 3, 7);
      game.getBoard().setSquare(new Square(new King(Color.BLACK)), 5, 7);
      game.getBoard().printBoard();

      assertTrue(CheckChecker.checkMateChecker(game.getBoard(), 5, 7));
   }

   /**
    * Test that a complex checkmate situation is calculated in under a second
    */
   @Test
   void testComplexCheckmateTime(){
      Game game = new Game();

      //Get the start time
      long startTime = System.nanoTime();

      //Check for checkmate
      CheckChecker.checkMateChecker(game.getBoard(), 1, 1);

      //Get the end time
      long endTime = System.nanoTime();

      //Get the time taken for checkMateChecker to run
      long timeTaken = endTime-startTime;

      assertTrue(timeTaken < 1000000000); //apparently that is a second in nanoseconds ¯\_(ツ)_/¯
   }

   @Test
   void testQuickCheckmate(){
      Game game = new Game();
      game.getBoard().boardReset();

      //Set pieces
      game.getBoard().getBoardArray()[6][2] = new Square();
      game.getBoard().getBoardArray()[5][2] = new Square(new Pawn(Color.WHITE));
      game.getBoard().getBoardArray()[1][3] = new Square();
      game.getBoard().getBoardArray()[3][2] = new Square(new Pawn(Color.BLACK));
      game.getBoard().getBoardArray()[7][3] = new Square();
      game.getBoard().getBoardArray()[4][0] = new Square(new Queen(Color.WHITE));

      game.getBoard().printBoard();

      assertFalse(CheckChecker.checkMateChecker(game.getBoard(), 0, 4));
   }
}
