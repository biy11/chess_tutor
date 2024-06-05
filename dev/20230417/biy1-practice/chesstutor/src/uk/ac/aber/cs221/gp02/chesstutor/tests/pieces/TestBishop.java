package uk.ac.aber.cs221.gp02.chesstutor.tests.pieces;

import org.junit.jupiter.api.Test;
import uk.ac.aber.cs221.gp02.chesstutor.game.Board;
import uk.ac.aber.cs221.gp02.chesstutor.game.Player;
import uk.ac.aber.cs221.gp02.chesstutor.game.Square;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.*;
import uk.ac.aber.cs221.gp02.chesstutor.util.Color;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Lance [lvs1]
 * @version 0.1 framework
 * @version 0.2 initial development
 * @version 0.3 fixing coordinate-related bugs
 */
public class TestBishop {

   /**
    * This is a simplistic movement method
    * Used for easy and straightforward testing
    * @param board The board to look through
    * @param p the player the piece is given to
    * @param row the initial row value of the piece
    * @param col the initial column value of the piece
    * @param tarRow the target row value to move the piece
    * @param tarCol the target column value to move the piece
    */
   private void testMovePiece(Square[][] board, Player p, int row, int col, int tarRow, int tarCol){
      p.addTakenPieces(board[tarRow][tarCol].getPiece());

      board[tarRow][tarCol] = board[row][col];
      board[row][col] = new Square();

   }

   /**
    * This method for finding coordinates in a list
    * @param list database to look through
    * @param row the intended row value
    * @param col the intended col value
    * @return
    */
   private boolean findValidMoves(List<int[]> list, int row, int col){
      boolean out = false;
      for (int[] ints : list) {
         out = Arrays.equals(ints, new int[]{row, col});
         if (out) break;
      }
      return out;
   }

   /**
    * This method creates an empty board
    * @return Board object
    */
   private Board createEmptyBoard(){
      Board obj = new Board();
      obj.clearBoard();

      return obj;
   }

   /**
    * This method creates a board with specific conditions
    * Biships are placed to have no valid moves
    * @return Board object
    */
   private Board createNoValidMoveBoard(){
      Board board = createEmptyBoard();
      Square[][] obj = board.getBoardArray();

      obj[0][2] = new Square(new Bishop(Color.BLACK));
      obj[0][5] = new Square(new Bishop(Color.BLACK));
      obj[1][1] = new Square(new Pawn(Color.BLACK));
      obj[1][3] = new Square(new Pawn(Color.BLACK));
      obj[1][4] = new Square(new Pawn(Color.BLACK));
      obj[1][6] = new Square(new Pawn(Color.BLACK));

      obj[7][2] = new Square(new Bishop(Color.WHITE));
      obj[7][5] = new Square(new Bishop(Color.WHITE));
      obj[6][1] = new Square(new Pawn(Color.WHITE));
      obj[6][3] = new Square(new Pawn(Color.WHITE));
      obj[6][4] = new Square(new Pawn(Color.WHITE));
      obj[6][6] = new Square(new Pawn(Color.WHITE));

      return board;
   }

   /**
    * This method creates a board with specific conditions
    * Bishops are placed to capture pieces
    * @return Board object
    */
   private Board createCaptureBoard(){
      Board board = createEmptyBoard();
      Square[][] obj = board.getBoardArray();

      obj[0][1] = new Square(new Bishop(Color.BLACK));
      obj[2][0] = new Square(new Bishop(Color.BLACK));

      obj[7][5] = new Square(new Bishop(Color.WHITE));

      return board;

   }

   /**
    * This method creates a board with specific conditions
    * Bishops and other pieces are placed to test movement
    * @return Board object
    */
   private Board createMoveBoard(){
      Board board = createEmptyBoard();
      Square[][] obj = board.getBoardArray();

      obj[0][0] = new Square(new Bishop(Color.BLACK));
      obj[3][3] = new Square(new Pawn(Color.BLACK));

      obj[7][0] = new Square(new Bishop(Color.WHITE));

      return board;

   }

   /**
    * This method creates a board with specific conditions
    * Bishops are placed to check king
    * @return Board object
    */
   private Board createCheckBoard(){
      Board board = createEmptyBoard();
      Square[][] obj = board.getBoardArray();

      obj[0][3] = new Square(new King(Color.BLACK));
      obj[5][0] = new Square(new Bishop(Color.BLACK));

      obj[7][0] = new Square(new Bishop(Color.WHITE));

      return board;

   }

   /**
    * Test bishops for no valid movement
    */
   @Test
   void testNoValidMoves(){
      Board board = createNoValidMoveBoard();
      Square[][] obj = board.getBoardArray();

      assertTrue(obj[0][2].getPiece().getValidMoves(board, obj[0][2].getPiece(), 0, 2).isEmpty());
      assertTrue(obj[0][5].getPiece().getValidMoves(board, obj[0][5].getPiece(), 0, 5).isEmpty());
      assertTrue(obj[7][2].getPiece().getValidMoves(board, obj[7][2].getPiece(), 7, 2).isEmpty());
      assertTrue(obj[7][5].getPiece().getValidMoves(board, obj[7][5].getPiece(), 7, 5).isEmpty());
   }

   /**
    * Test bishops for capturing pieces
    */
   @Test
   void testCapture(){
      Board board = createCaptureBoard();
      Square[][] obj = board.getBoardArray();

      Player p1 = new Player(Color.WHITE, "John");
      Player p2 = new Player(Color.BLACK, "Jack");

      Piece bBishop1 = obj[0][2].getPiece();
      Piece bBishop2 = obj[2][0].getPiece();
      Piece wBishop1 = obj[7][5].getPiece();

      // White moves first - check and move bishop to capture
      assertTrue( findValidMoves(wBishop1.getValidMoves(board, wBishop1, 7,5), 2,0) );
      assertEquals( 7, wBishop1.getValidMoves(board, wBishop1, 7,5).size() );
      testMovePiece(obj, p1, 7,5,2,0);
      assertTrue( p1.getTakenPieces().contains(bBishop2) );

      // Black bishop captures white
      assertTrue( findValidMoves(bBishop1.getValidMoves(board, bBishop1, 0,2), 2,0) );
      assertEquals( 7, bBishop1.getValidMoves(board, bBishop1, 0,2).size() );
      testMovePiece(obj, p1, 0,2,2,0);
      assertTrue( p2.getTakenPieces().contains(wBishop1) );

   }


   /**
    * Test bishops for movement
    */
   @Test
   void testMove(){
      Board board = createMoveBoard();
      Square[][] obj = board.getBoardArray();

      Piece bBishop = obj[0][0].getPiece();
      Piece wBishop = obj[7][0].getPiece();

      // Test White possible moves
      //assertEquals(7, wBishop.getValidMoves(board, wBishop, 0,7).size());
      assertTrue( findValidMoves(wBishop.getValidMoves(board, wBishop, 7,0),0,7));
      assertFalse( findValidMoves(wBishop.getValidMoves(board, wBishop, 7,0),7,0) );

      // Test Black possible moves
      //assertEquals(2, bBishop.getValidMoves(board, bBishop, 0,0).size());
      assertTrue( findValidMoves(bBishop.getValidMoves(board, bBishop, 0,0),2,2));
      assertFalse( findValidMoves(bBishop.getValidMoves(board, bBishop, 0,0),0,0) );
   }

   /**
    * Test bishops for checking king
    */
   @Test
   void testCheck(){
      Board board = createCheckBoard();
      Square[][] obj = board.getBoardArray();

      Player p1 = new Player(Color.WHITE, "Logan");
      Player p2 = new Player(Color.BLACK, "Victor");

      Piece bKing = obj[0][3].getPiece();
      Piece bBishop = obj[5][0].getPiece();
      Piece wBishop = obj[7][0].getPiece();

      // White move first - check then move
      assertTrue( findValidMoves(wBishop.getValidMoves(board, wBishop, 0,7),2,5) );
      assertEquals( 7, wBishop.getValidMoves(board, wBishop, 0,7).size() );
      testMovePiece(obj, p1, 0,7,5,2);

      // Black king is in check - either bishop blocks or king dodges
      assertEquals( 1,bBishop.getValidMoves(board,bBishop,0,5).size() );
      assertTrue( findValidMoves(bBishop.getValidMoves(board, bBishop, 0,5),1,4) );

      assertEquals( 4,bKing.getValidMoves(board, bKing,3,0).size() );
      assertTrue( findValidMoves(bKing.getValidMoves(board, bKing, 0,3),0,2) );
      assertTrue( findValidMoves(bKing.getValidMoves(board, bKing, 0,3),0,4) );
      assertTrue( findValidMoves(bKing.getValidMoves(board, bKing, 0,3),1,2) );
      assertTrue( findValidMoves(bKing.getValidMoves(board, bKing, 0,3),1,3) );
      assertFalse( findValidMoves(bKing.getValidMoves(board, bKing, 0,3),1,4) );

   }

}
