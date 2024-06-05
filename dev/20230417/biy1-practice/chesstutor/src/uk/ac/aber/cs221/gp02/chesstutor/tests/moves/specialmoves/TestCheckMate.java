package uk.ac.aber.cs221.gp02.chesstutor.tests.moves.specialmoves;

import org.junit.jupiter.api.Test;
import uk.ac.aber.cs221.gp02.chesstutor.game.Board;
import uk.ac.aber.cs221.gp02.chesstutor.game.Game;
import uk.ac.aber.cs221.gp02.chesstutor.game.Square;
import uk.ac.aber.cs221.gp02.chesstutor.moves.CheckChecker;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.King;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.Pawn;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.Queen;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.Rook;
import uk.ac.aber.cs221.gp02.chesstutor.util.Color;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

      game.getBoard().clearBoard();
      game.getBoard().setSquare(new Square(new King(Color.WHITE)),6, 5);
      game.getBoard().setSquare(new Square(new Queen(Color.WHITE)), 3, 7);
      game.getBoard().setSquare(new Square(new King(Color.BLACK)), 5, 7);
      game.getBoard().printBoard();

      assertTrue(CheckChecker.checkMateChecker(game.getBoard(), 5, 7));
   }

   /**
    *  The black king is in checkmate by the white queen and king
    *  TODO finish this
    */
   @Test
   void testCheckMateSituation6(){
      //Create a new game
      Game game = new Game();

      game.getBoard().clearBoard();
      game.getBoard().setSquare(new Square(new Queen(Color.WHITE)),0, 1);
      game.getBoard().setSquare(new Square(new Pawn(Color.BLACK)),1, 5);
      game.getBoard().setSquare(new Square(new King(Color.BLACK)),1, 6);




      game.getBoard().printBoard();

      assertTrue(CheckChecker.checkMateChecker(game.getBoard(), 5, 7));
   }

   /**
    * Test that a complex checkmate situation is calculated in under a second
    * TODO this is not finished
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
}
