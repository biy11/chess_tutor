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
 * @version 0.3 completed initial tests
 * @version 0.4 fixed coordinate-related bugs
 */

public class TestKing {

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
    * Kings are placed to have no valid moves
    * @return Board object
    */
   private Board createNoValidMoveBoard(){
      Board board = createEmptyBoard();
      Square[][] obj = board.getBoardArray();

      obj[0][4] = new Square(new King(Color.BLACK));
      obj[0][3] = new Square(new Pawn(Color.BLACK));
      obj[0][5] = new Square(new Pawn(Color.BLACK));
      obj[1][3] = new Square(new Pawn(Color.BLACK));
      obj[1][4] = new Square(new Pawn(Color.BLACK));
      obj[1][5] = new Square(new Pawn(Color.BLACK));

      obj[7][4] = new Square(new King(Color.WHITE));
      obj[7][3] = new Square(new Pawn(Color.WHITE));
      obj[7][5] = new Square(new Pawn(Color.WHITE));
      obj[6][3] = new Square(new Pawn(Color.WHITE));
      obj[6][4] = new Square(new Pawn(Color.WHITE));
      obj[6][5] = new Square(new Pawn(Color.WHITE));

      return board;

   }

   /**
    * This method creates a board with specific conditions
    * Kings are placed to capture pieces
    * @return Board object
    */
   private Board createCaptureBoard(){
      Board board = createEmptyBoard();
      Square[][] obj = board.getBoardArray();

      obj[3][4] = new Square(new King(Color.WHITE));
      obj[2][5] = new Square(new Rook(Color.BLACK));
      obj[2][3] = new Square(new Pawn(Color.BLACK));

      return board;

   }

   /**
    * This method creates a board with specific conditions
    * Kings and other pieces are placed to test movement
    * @return Board object
    */
   private Board createMoveBoard(){
      Board board = createEmptyBoard();
      Square[][] obj = board.getBoardArray();

      obj[3][4] = new Square(new King(Color.WHITE));

      obj[5][2] = new Square(new King(Color.BLACK));
      obj[2][5] = new Square(new Rook(Color.BLACK));

      return board;

   }

   /**
    * Test kings for no valid movement
    */
   @Test
   public void testNoValidMoves(){
      Board board = createNoValidMoveBoard();
      Square[][] obj = board.getBoardArray();

      Piece bKing = obj[0][4].getPiece();
      Piece wKing = obj[7][4].getPiece();

      // The two kings should not have a possible move
      assertTrue( bKing.getValidMoves(board, bKing, 0, 4).isEmpty() );
      assertTrue( wKing.getValidMoves(board, wKing, 7, 4).isEmpty() );

   }

   /**
    * Test kings for capturing pieces
    */
   @Test
   public void testCapture(){
      Board board = createCaptureBoard();
      Square[][] obj = board.getBoardArray();

      Player p1 = new Player(Color.WHITE, "Lance");
      Player p2 = new Player(Color.BLACK, "Sebastian");

      Piece bPawn = obj[2][3].getPiece();
      Piece bRook = obj[2][5].getPiece();
      Piece wKing = obj[3][4].getPiece();

      // White moves first - King should only have 7 possible moves.
      assertFalse( findValidMoves(wKing.getValidMoves(board, wKing, 3, 4), 2,3) );
      assertTrue( findValidMoves(wKing.getValidMoves(board, wKing, 3,4), 2,5) );
      assertEquals( 7, wKing.getValidMoves(board,wKing,3,4).size() );
      testMovePiece(obj, p1, 3,4,2,5); // king captures rook

      // After Move, White King should now have 8 possible moves + bRook captured.
      assertEquals(8, wKing.getValidMoves(board, wKing, 2,5).size());
      assertTrue( p1.getTakenPieces().contains(bRook) );

   }

   /**
    * Test kings for movement
    */
   @Test
   public void testMove(){
      Board board = createMoveBoard();
      Square[][] obj = board.getBoardArray();

      Piece bRook = obj[2][5].getPiece();
      Piece bKing = obj[5][2].getPiece();
      Piece wKing = obj[3][4].getPiece();

      // Test white moves first - king should only have 3 possible moves
      assertTrue( findValidMoves(wKing.getValidMoves(board, wKing, 3,4),2,5 ));
      assertEquals( 3, wKing.getValidMoves(board, wKing, 3,4).size() );

      // Test black moves - king should only have 7 possible moves
      assertFalse( findValidMoves(bKing.getValidMoves(board,bKing,5,2),4,3) );
      assertEquals( 7, bKing.getValidMoves(board, bKing, 5,2).size() );

   }

}
