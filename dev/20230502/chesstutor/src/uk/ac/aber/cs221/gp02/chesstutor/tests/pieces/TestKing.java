package uk.ac.aber.cs221.gp02.chesstutor.tests.pieces;

import org.junit.jupiter.api.Test;
import uk.ac.aber.cs221.gp02.chesstutor.game.Board;
import uk.ac.aber.cs221.gp02.chesstutor.game.Player;
import uk.ac.aber.cs221.gp02.chesstutor.game.Square;
import uk.ac.aber.cs221.gp02.chesstutor.moves.CheckChecker;
import uk.ac.aber.cs221.gp02.chesstutor.moves.MakeMove;
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
 * @version 0.5 removed duplicate tests
 * @version 0.6 added testFreePieces test
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
    * Kings are placed to test unobstructed movement
    * @return Board object
    */
   private Board createFreePiecesBoard(){
      Board board = createEmptyBoard();
      Square[][] obj = board.getBoardArray();

      obj[0][7] = new Square(new King(Color.BLACK));
      obj[6][4] = new Square(new King(Color.WHITE));

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

   @Test
   /**
    * Test kings for unobstructed movement.
    */
   public void testFreePieces(){
      Board board = createFreePiecesBoard();
      Square[][] obj = board.getBoardArray();

      Piece bKing = obj[0][7].getPiece();
      Piece wKing = obj[6][4].getPiece();

      // The two kings should have at least 1 move.
      assertTrue( bKing.getPossibleMoves(board, bKing, 0, 7).size() > 0);
      assertTrue( wKing.getPossibleMoves(board, wKing, 6, 4).size() > 0);

      // The black king should have 3 possible moves.
      assertEquals(3, bKing.getPossibleMoves(board, bKing, 0, 7).size());

      // the white king should have 8 possible moves.
      assertEquals(8, wKing.getPossibleMoves(board, wKing, 6, 4).size());

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
      assertTrue( bKing.getPossibleMoves(board, bKing, 0, 4).isEmpty() );
      assertTrue( wKing.getPossibleMoves(board, wKing, 7, 4).isEmpty() );

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

      board.printBoard();

      for(int[] thing:wKing.getPossibleMoves(board, wKing, 3, 4)){
         System.out.println(Arrays.toString(thing));
      }

      // White moves first - King should only have 4 possible moves.
      assertFalse( findValidMoves(wKing.getPossibleMoves(board, wKing, 3, 4), 2,3) );
      assertTrue( findValidMoves(wKing.getPossibleMoves(board, wKing, 3,4), 2,5) );
      assertEquals( 4, wKing.getPossibleMoves(board,wKing,3,4).size() );
      testMovePiece(obj, p1, 3,4,2,5); // king captures rook

      // After Move, White King should now have 8 possible moves + bRook captured.
      //assertEquals(8, wKing.getPossibleMoves(board, wKing, 2,5).size());
      //assertTrue( p1.getTakenPieces().contains(bRook) );
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
      assertTrue( findValidMoves(wKing.getPossibleMoves(board, wKing, 3,4),2,5 ));
      assertEquals( 3, wKing.getPossibleMoves(board, wKing, 3,4).size() );

      // Test black moves - king should only have 7 possible moves
      assertFalse( findValidMoves(bKing.getPossibleMoves(board,bKing,5,2),4,3) );
      assertEquals( 7, bKing.getPossibleMoves(board, bKing, 5,2).size() );

   }

}
