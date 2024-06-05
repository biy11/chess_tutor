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
 * @version 0.4 fixed test coordinates (testCheck)
 * @version 0.5 fixed coordinate-related bugs
 * @version 0.6 removed duplicate tests
 */
public class TestKnight {

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
    * This method prints out the board in a comprehensible form
    * @param board the board to output
    */
   private void printBoard(Square[][] board) { //for debugging only
      String[][] strar = new String[8][8];
      for (int i = 0; i <= 7; i++) {
         for (int j = 0; j <= 7; j++) {
            if (!board[i][j].hasPiece()) {
               strar[i][j] = "     ";
            } else {
               strar[i][j] = board[i][j].getPiece().getPieceType().name();
            }
         }
      }
      System.out.println(Arrays.deepToString(strar).replace("],", "]\n").replace("[[", " [").replace("]]","]"));
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
    * Knights are placed to have no valid moves
    * @return Board object
    */
   private Board createNoValidMoveBoard(){
      Board board = createEmptyBoard();
      Square[][] obj = board.getBoardArray();

      obj[0][1] = new Square(new Knight(Color.BLACK));
      obj[0][6] = new Square(new Knight(Color.BLACK));
      obj[1][3] = new Square(new Pawn(Color.BLACK));
      obj[1][4] = new Square(new Pawn(Color.BLACK));
      obj[2][0] = new Square(new Pawn(Color.BLACK));
      obj[2][2] = new Square(new Pawn(Color.BLACK));
      obj[2][5] = new Square(new Pawn(Color.BLACK));
      obj[2][7] = new Square(new Pawn(Color.BLACK));

      obj[7][1] = new Square(new Knight(Color.WHITE));
      obj[7][6] = new Square(new Knight(Color.WHITE));
      obj[6][3] = new Square(new Pawn(Color.WHITE));
      obj[6][4] = new Square(new Pawn(Color.WHITE));
      obj[5][0] = new Square(new Pawn(Color.WHITE));
      obj[5][2] = new Square(new Pawn(Color.WHITE));
      obj[5][5] = new Square(new Pawn(Color.WHITE));
      obj[5][7] = new Square(new Pawn(Color.WHITE));

      return board;
   }

   /**
    * This method creates a board with specific conditions
    * Knights are placed to capture pieces
    * @return Board object
    */
   private Board createCaptureBoard(){
      Board board = createEmptyBoard();
      Square[][] obj = board.getBoardArray();

      obj[2][5] = new Square(new Knight(Color.BLACK));
      obj[3][3] = new Square(new Knight(Color.BLACK));

      obj[5][4] = new Square(new Knight(Color.WHITE));

      return board;

   }

   /**
    * This method creates a board with specific conditions
    * Knights and other pieces are placed to test movement
    * @return Board object
    */
   private Board createMoveBoard(){
      Board board = createEmptyBoard();
      Square[][] obj = board.getBoardArray();

      obj[0][4] = new Square(new Pawn(Color.BLACK));
      obj[2][5] = new Square(new Knight(Color.BLACK));

      obj[5][4] = new Square(new Knight(Color.WHITE));

      return board;

   }

   /**
    * This method creates a board with specific conditions
    * Knights are placed to check king
    * @return Board object
    */
   private Board createCheckBoard(){
      Board board = createEmptyBoard();
      Square[][] obj = board.getBoardArray();

      obj[0][2] = new Square(new Knight(Color.BLACK));
      obj[0][4] = new Square(new King(Color.BLACK));

      obj[3][5] = new Square(new Knight(Color.WHITE));

      return board;

   }

   /**
    * Test knights with no valid movement
    */
   @Test
   public void testNoValidMoves(){
      Board board = createNoValidMoveBoard();
      Square[][] obj = board.getBoardArray();

      // All knights should have 0 possible moves
      assertTrue(obj[0][1].getPiece().getPossibleMoves(board, obj[0][1].getPiece(), 0, 1).isEmpty());
      assertTrue(obj[0][6].getPiece().getPossibleMoves(board, obj[0][6].getPiece(), 0, 6).isEmpty());
      assertTrue(obj[7][1].getPiece().getPossibleMoves(board, obj[7][1].getPiece(), 7, 1).isEmpty());
      assertTrue(obj[7][6].getPiece().getPossibleMoves(board, obj[7][6].getPiece(), 7, 6).isEmpty());

   }

   /**
    * Test knights for capturing pieces
    */
   @Test
   public void testCapture(){
      Board board = createCaptureBoard();
      Square[][] obj = board.getBoardArray();

      Player p1 = new Player(Color.WHITE, "John");
      Player p2 = new Player(Color.BLACK, "Jack");

      Piece bKnight1 = obj[2][5].getPiece();
      Piece bKnight2 = obj[3][3].getPiece();
      Piece wKnight1 = obj[5][4].getPiece();

      // White moves first - check and move white knight to capture
      assertTrue( findValidMoves(wKnight1.getPossibleMoves(board, wKnight1, 4,5),3,3) );
      assertEquals( 8, wKnight1.getPossibleMoves(board, wKnight1, 4,5).size());
      testMovePiece(obj, p1, 5,4,3,3);
      assertTrue(p1.getTakenPieces().contains(bKnight2));

      // Black's turn - check and move black knight to capture
      assertTrue( findValidMoves(bKnight1.getPossibleMoves(board, bKnight1, 5,2),3,3) );
      assertEquals( 8, bKnight1.getPossibleMoves(board, bKnight1, 5,2).size());
      testMovePiece(obj, p2, 2,5,3,3);
      assertTrue(p2.getTakenPieces().contains(wKnight1));

   }

   /**
    * Test knights for movement
    */
   @Test
   public void testMove(){
      Board board = createMoveBoard();
      Square[][] obj = board.getBoardArray();

      Piece bKnight = obj[2][5].getPiece();
      Piece wKnight = obj[5][4].getPiece();

      // Test White possible moves
      assertEquals( 8, wKnight.getPossibleMoves(board, wKnight, 5,4).size());
      assertTrue( findValidMoves(wKnight.getPossibleMoves(board, wKnight, 5,4),3,3) );
      assertTrue( findValidMoves(wKnight.getPossibleMoves(board, wKnight, 5,4),4,2) );
      assertTrue( findValidMoves(wKnight.getPossibleMoves(board, wKnight, 5,4),6,2) );
      assertTrue( findValidMoves(wKnight.getPossibleMoves(board, wKnight, 5,4),7,3) );
      assertTrue( findValidMoves(wKnight.getPossibleMoves(board, wKnight, 5,4),7,5) );
      assertTrue( findValidMoves(wKnight.getPossibleMoves(board, wKnight, 5,4),6,6) );
      assertTrue( findValidMoves(wKnight.getPossibleMoves(board, wKnight, 5,4),4,6) );
      assertTrue( findValidMoves(wKnight.getPossibleMoves(board, wKnight, 5,4),3,5) );

      // Test Black possible moves
      assertEquals( 7, bKnight.getPossibleMoves(board, bKnight, 2,5).size());
      assertFalse( findValidMoves(bKnight.getPossibleMoves(board, bKnight, 2,5),0,4) );
      assertTrue( findValidMoves(bKnight.getPossibleMoves(board, bKnight, 2,5),1,3) );
      assertTrue( findValidMoves(bKnight.getPossibleMoves(board, bKnight, 2,5),3,3) );
      assertTrue( findValidMoves(bKnight.getPossibleMoves(board, bKnight, 2,5),4,4) );
      assertTrue( findValidMoves(bKnight.getPossibleMoves(board, bKnight, 2,5),4,6) );
      assertTrue( findValidMoves(bKnight.getPossibleMoves(board, bKnight, 2,5),3,7) );
      assertTrue( findValidMoves(bKnight.getPossibleMoves(board, bKnight, 2,5),1,7) );
      assertTrue( findValidMoves(bKnight.getPossibleMoves(board, bKnight, 2,5),0,6) );


   }


}
