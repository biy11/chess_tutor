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
 * @version 0.4 coordinate-related bug fixes
 */
public class TestQueen {

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
    * Queens are placed to have no valid moves
    * @return Board object
    */
   private Board createNoValidMoveBoard(){
      Board board = createEmptyBoard();
      Square[][] obj = board.getBoardArray();

      obj[0][2] = new Square(new Bishop(Color.BLACK));
      obj[0][3] = new Square(new Queen(Color.BLACK));
      obj[0][4] = new Square(new King(Color.BLACK));
      obj[1][2] = new Square(new Pawn(Color.BLACK));
      obj[1][3] = new Square(new Pawn(Color.BLACK));
      obj[1][4] = new Square(new Pawn(Color.BLACK));

      obj[7][2] = new Square(new Bishop(Color.WHITE));
      obj[7][3] = new Square(new Queen(Color.WHITE));
      obj[7][4] = new Square(new King(Color.WHITE));
      obj[6][2] = new Square(new Pawn(Color.WHITE));
      obj[6][3] = new Square(new Pawn(Color.WHITE));
      obj[6][4] = new Square(new Pawn(Color.WHITE));

      return board;
   }

   /**
    * This method creates a board with specific conditions
    * Queens are placed to capture pieces
    * @return Board object
    */
   private Board createCaptureBoard(){
      Board board = createEmptyBoard();
      Square[][] obj = board.getBoardArray();

      obj[2][3] = new Square(new Pawn(Color.BLACK));
      obj[3][7] = new Square(new Pawn(Color.BLACK));
      obj[4][0] = new Square(new Pawn(Color.BLACK));

      obj[7][3] = new Square(new Queen(Color.WHITE));

      return board;

   }

   /**
    * This method creates a board with specific conditions
    * Queens and other pieces are placed to test movement
    * @return Board object
    */
   private Board createMoveBoard(){
      Board board = createEmptyBoard();
      Square[][] obj = board.getBoardArray();

      obj[0][0] = new Square(new Queen(Color.BLACK));

      obj[6][5] = new Square(new Pawn(Color.WHITE));
      obj[7][6] = new Square(new Queen(Color.WHITE));

      return board;

   }

   /**
    * This method creates a board with specific conditions
    * Queens are placed to check king
    * @return Board object
    */
   private Board createCheckBoard(){
      Board board = createEmptyBoard();
      Square[][] obj = board.getBoardArray();

      obj[0][3] = new Square(new King(Color.BLACK));
      obj[1][3] = new Square(new Pawn(Color.BLACK));

      obj[7][6] = new Square(new Queen(Color.WHITE));

      return board;

   }

   /**
    * Test queens for no valid movement
    */
   @Test
   void testNoValidMoves(){
      Board board = createNoValidMoveBoard();
      Square[][] obj = board.getBoardArray();

      assertTrue(obj[0][3].getPiece().getValidMoves(board, obj[0][3].getPiece(), 0, 3).isEmpty());
      assertTrue(obj[7][3].getPiece().getValidMoves(board, obj[7][3].getPiece(), 7, 3).isEmpty());
   }

   /**
    * Test queens for capturing pieces
    */
   @Test
   void testCapture(){
      Board board = createCaptureBoard();
      Square[][] obj = board.getBoardArray();

      Player p1 = new Player(Color.WHITE, "Tony");
      Player p2 = new Player(Color.BLACK, "Steve");

      Piece wQueen = obj[7][3].getPiece();
      Piece bPawn = obj[3][7].getPiece();

      // White move first - check then move
      assertTrue( findValidMoves(wQueen.getValidMoves(board, wQueen, 7,3),4,0) );
      assertTrue( findValidMoves(wQueen.getValidMoves(board, wQueen, 7,3),2,3) );
      assertTrue( findValidMoves(wQueen.getValidMoves(board, wQueen, 7,3),3,7) );
      assertEquals( 19, wQueen.getValidMoves(board, wQueen, 7,3).size() );
      testMovePiece(obj, p1,7,3,3,7);
      assertTrue( p1.getTakenPieces().contains(bPawn) );

      // Test Queen after move
      assertTrue( findValidMoves(wQueen.getValidMoves(board, wQueen, 3,7),0,4) );
      assertTrue( findValidMoves(wQueen.getValidMoves(board, wQueen, 3,7),3,0) );
      assertTrue( findValidMoves(wQueen.getValidMoves(board, wQueen, 3,7),7,3) );
      assertEquals( 21, wQueen.getValidMoves(board, wQueen, 3,7).size() );

   }

   /**
    * Test queens for movement
    */
   @Test
   void testMove(){
      Board board = createMoveBoard();
      Square[][] obj = board.getBoardArray();

      Piece bQueen = obj[0][0].getPiece();
      Piece wQueen = obj[7][6].getPiece();

      // Test White possible moves
      assertEquals( 15,wQueen.getValidMoves(board, wQueen,7,6).size() );
      assertTrue( findValidMoves(wQueen.getValidMoves(board, wQueen, 7,6),6,7));
      assertTrue( findValidMoves(wQueen.getValidMoves(board, wQueen, 7,6),0,6));
      assertFalse( findValidMoves(bQueen.getValidMoves(board, wQueen, 7,6),6,5));

      // Test Black possible moves
      assertEquals( 21,bQueen.getValidMoves(board, bQueen,0,0).size() );
      assertTrue( findValidMoves(bQueen.getValidMoves(board, bQueen, 0,0),0,7));
      assertTrue( findValidMoves(bQueen.getValidMoves(board, bQueen, 0,0),7,0));
      assertTrue( findValidMoves(bQueen.getValidMoves(board, bQueen, 0,0),7,7));

   }

   /**
    * Test queens for checking king
    */
   @Test
   void testCheck(){
      Board board = createCheckBoard();
      Square[][] obj = board.getBoardArray();

      Player p1 = new Player(Color.WHITE, "Thor");
      Player p2 = new Player(Color.BLACK, "Thanos");
      
      Piece bKing = obj[0][3].getPiece();
      Piece bPawn = obj[1][3].getPiece();
      Piece wQueen = obj[7][6].getPiece();

      // White move first - check then move
      assertTrue( findValidMoves(wQueen.getValidMoves(board, wQueen,7,6),0,6) );
      assertEquals( 21, wQueen.getValidMoves(board, wQueen, 7,6).size() );
      testMovePiece(obj, p1, 7,6,0,6);

      // Black king is in check - king can only dodge, pawn useless
      assertTrue( bPawn.getValidMoves(board,bPawn,1,3).isEmpty() );

      assertEquals( 2,bKing.getValidMoves(board,bKing,0,3).size() );
      assertTrue( findValidMoves(bKing.getValidMoves(board,bKing,0,3),1,2) );
      assertTrue( findValidMoves(bKing.getValidMoves(board,bKing,0,3),1,4) );
      assertFalse( findValidMoves(bKing.getValidMoves(board,bKing,0,3),0,2) );
      assertFalse( findValidMoves(bKing.getValidMoves(board,bKing,0,3),0,4) );

   }

}
