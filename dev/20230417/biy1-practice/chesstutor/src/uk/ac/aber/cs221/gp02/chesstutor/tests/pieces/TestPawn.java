package uk.ac.aber.cs221.gp02.chesstutor.tests.pieces;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.ac.aber.cs221.gp02.chesstutor.game.Board;
import uk.ac.aber.cs221.gp02.chesstutor.game.Player;
import uk.ac.aber.cs221.gp02.chesstutor.game.Square;
import uk.ac.aber.cs221.gp02.chesstutor.moves.CheckChecker;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.*;
import uk.ac.aber.cs221.gp02.chesstutor.util.Color;
import uk.ac.aber.cs221.gp02.chesstutor.moves.CheckChecker.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.List;

/**
 * @author Lance [lvs1]
 * @version 0.1 framework
 * @version 0.2 initial development
 * @version 0.3 completed initial tests
 * @version 0.4 Coordinate-related bug fixes
 */
public class TestPawn {

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
    * This method prints out the valid moves of a piece in a comprehensible form
    * @param board the board to look through
    * @param x the x value of the piece
    * @param y the y value of the piece
    */
   private void printValidMoves(Board board, int x, int y){ //for debugging only
      Square[][] obj = board.getBoardArray();

      for ( int i=0 ; i<obj[y][x].getPiece().getValidMoves(board, obj[y][x].getPiece(), x, y).size();i++) {
         for(int j=0 ; j < 2;j++) {
            System.out.println(j + ":" + obj[y][x].getPiece().getValidMoves(board, obj[y][x].getPiece(), x, y).get(i)[j]);
         }
      }
   }

   /**
    * This method prints out the board in a comprehensible form
    * @param board the board to output
    */
   private void printBoard(Square[][] board) { //for debugging only
      String[][] strar = new String[8][8];
      for (int i = 0; i <= 7; i++) {
         for (int j = 0; j <= 7; j++) {
            if (!board[i][j].isHasPiece()) {
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
    * Pawns are placed to have no valid moves
    * @return Board object
    */
   private Board createNoValidMoveBoard(){
      Board board = createEmptyBoard();
      Square[][] obj = board.getBoardArray();

      obj[6][1] = new Square(new Pawn(Color.WHITE));
      obj[5][1] = new Square(new Pawn(Color.WHITE));
      obj[5][4] = new Square(new Pawn(Color.WHITE));
      obj[4][4] = new Square(new Pawn(Color.WHITE));

      obj[2][2] = new Square(new Pawn(Color.BLACK));
      obj[3][2] = new Square(new Pawn(Color.BLACK));
      obj[1][6] = new Square(new Pawn(Color.BLACK));
      obj[2][6] = new Square(new Pawn(Color.BLACK));

      return board;
   }

   /**
    * This method creates a board with specific conditions
    * Pawns are placed to be promoted in various ways.
    * @return Board object
    */
   private Board createPromotionBoard(){
      Board board = createEmptyBoard();
      Square[][] obj = board.getBoardArray();

      obj[0][2] = new Square(new Pawn(Color.BLACK));
      obj[1][3] = new Square(new Pawn(Color.WHITE));
      obj[1][5] = new Square(new Pawn(Color.WHITE));
      obj[6][2] = new Square(new Pawn(Color.BLACK));
      obj[7][1] = new Square(new Pawn(Color.WHITE));
      obj[6][5] = new Square(new Pawn(Color.BLACK));

      return board;
   }

   /**
    * This method creates a board with specific conditions
    * Pawns are placed to capture pieces
    * @return Board object
    */
   private Board createCaptureBoard(){
      Board board = createEmptyBoard();
      Square[][] obj = board.getBoardArray();

      obj[2][4] = new Square(new Pawn(Color.BLACK));
      obj[3][3] = new Square(new Rook(Color.BLACK));
      obj[4][2] = new Square(new Pawn(Color.WHITE));
      obj[4][4] = new Square(new Pawn(Color.WHITE));
      return board;
   }

   /**
    * This method creates a board with specific conditions
    * Pawns are placed to test movement
    * @return Board object
    */
   private Board createInitalMoveBoard(){
      Board board = createEmptyBoard();
      Square[][] obj = board.getBoardArray();

      obj[6][2] = new Square(new Pawn(Color.WHITE));
      obj[5][5] = new Square(new Pawn(Color.WHITE));
      obj[1][0] = new Square(new Pawn(Color.BLACK));
      obj[2][7] = new Square(new Pawn(Color.BLACK));
      return board;
   }

   /**
    * This method creates a board with specific conditions
    * Pawns are placed to check black king
    * @return Board object
    */
   private Board createCheckWhiteBoard(){
      Board board = createEmptyBoard();
      Square[][] obj = board.getBoardArray();

      obj[3][4] = new Square(new Pawn(Color.WHITE));
      obj[0][0] = new Square(new Rook(Color.BLACK));
      obj[1][3] = new Square(new King(Color.BLACK));
      return board;
   }

   /**
    * This method creates a board with specific conditions
    * Pawns are placed to check white king
    * @return Board object
    */
   private Board createCheckBlackBoard(){
      Board board = createEmptyBoard();
      Square[][] obj = board.getBoardArray();

      obj[5][3] = new Square(new Pawn(Color.BLACK));
      obj[7][7] = new Square(new Rook(Color.WHITE));
      obj[7][4] = new Square(new King(Color.WHITE));
      return board;
   }

   /**
    * Test pawns for no valid moves
    */
   @Test
   public void testNoValidMoves(){
      Board board = createNoValidMoveBoard();
      Square[][] obj = board.getBoardArray();

      assertTrue(obj[6][1].getPiece().getValidMoves(board, obj[6][1].getPiece(), 6, 1).isEmpty());
      assertTrue(obj[5][4].getPiece().getValidMoves(board, obj[5][4].getPiece(), 5, 4).isEmpty());
      assertTrue(obj[2][2].getPiece().getValidMoves(board, obj[2][2].getPiece(), 2, 2).isEmpty());
      assertTrue(obj[1][6].getPiece().getValidMoves(board, obj[1][6].getPiece(), 1, 6).isEmpty());
   }

   /**
    * Test pawns for promotion
    * (currently unfinished)
    */
   @Test
   public void testPromotion(){
      Board board = createPromotionBoard();
      Square[][] obj = board.getBoardArray();

      // Test Round One Valid Moves
      assertTrue( findValidMoves( obj[1][3].getPiece().getValidMoves(board, obj[1][3].getPiece(), 1, 3) , 2,0) );
      assertTrue( findValidMoves( obj[1][3].getPiece().getValidMoves(board, obj[1][3].getPiece(), 1, 3) , 3,0) );
      assertTrue( findValidMoves( obj[1][3].getPiece().getValidMoves(board, obj[1][5].getPiece(), 1, 5) , 5,0) );

      assertTrue( findValidMoves( obj[1][3].getPiece().getValidMoves(board, obj[1][3].getPiece(), 6, 2) , 2,7) );
      assertTrue( findValidMoves( obj[1][3].getPiece().getValidMoves(board, obj[1][3].getPiece(), 6, 2) , 3,7) );
      assertTrue( findValidMoves( obj[1][3].getPiece().getValidMoves(board, obj[1][5].getPiece(), 6, 5) , 5,7) );

      // Test Promotion (I don't know if it's been implemented yet, can't find it)
   }

   /**
    * Test pawns to capture other pieces
    */
   @Test
   public void testCapture(){
      Board board = createCaptureBoard();
      Square[][] obj = board.getBoardArray();

      Player p1 = new Player(Color.WHITE, "John");
      Player p2 = new Player(Color.BLACK, "Jack");

      Piece rook = obj[3][3].getPiece();
      Piece bPawn = obj[2][4].getPiece();
      Piece wPawn1 = obj[4][2].getPiece();
      Piece wPawn2 = obj[4][4].getPiece();

      // White Moves first - Check all White moves
      assertTrue( findValidMoves( wPawn2.getValidMoves(board, wPawn2, 4, 4) , 3,4) );
      assertTrue( findValidMoves( wPawn2.getValidMoves(board, wPawn2, 4, 4) , 3,3) );

      assertTrue( findValidMoves( wPawn1.getValidMoves(board, wPawn1, 4, 2) , 3,2) );
      assertTrue( findValidMoves( wPawn1.getValidMoves(board, wPawn1, 4, 2) , 3,3) );

      // White Pawn Captures Black Rook
      testMovePiece(obj, p1, 4, 4, 3, 3);
      assertTrue(p1.getTakenPieces().contains(rook));

      // Black Pawn Captures White Pawn
      assertTrue( findValidMoves( bPawn.getValidMoves(board, bPawn, 2, 4) , 3,3) );
      testMovePiece(obj, p2, 2, 4, 3, 3);
      assertTrue(p2.getTakenPieces().contains(wPawn2));

      // White Pawn Captures Black Pawn
      assertTrue( findValidMoves( wPawn1.getValidMoves(board, wPawn1, 4, 2) , 3,3) );
      testMovePiece(obj, p1, 4, 2, 3, 3);
      assertTrue(p1.getTakenPieces().contains(bPawn));

   }

   /**
    * Test pawns for initial and non-initial moves
    */
   @Test
   public void testInitialMove(){
      Board board = createInitalMoveBoard();
      Square[][] obj = board.getBoardArray();

      Piece bPawn1 = obj[1][0].getPiece();
      Piece bPawn2 = obj[2][7].getPiece();
      Piece wPawn1 = obj[6][2].getPiece();
      Piece wPawn2 = obj[5][5].getPiece();

      bPawn2.setHasMoved();
      wPawn2.setHasMoved();

      // First Black Pawn
      assertTrue( findValidMoves(bPawn1.getValidMoves(board, bPawn1, 1, 0), 3,0 ));
      assertTrue( findValidMoves(bPawn1.getValidMoves(board, bPawn1, 1, 0), 2,0 ));
      assertEquals(2, bPawn1.getValidMoves(board, bPawn1, 1, 0).size());

      // Second Black Pawn
      assertTrue( findValidMoves(bPawn2.getValidMoves(board, bPawn2, 2, 7), 3,7 ));
      assertEquals(1, bPawn2.getValidMoves(board, bPawn2, 2, 7).size());

      // First White Pawn
      assertTrue( findValidMoves(wPawn1.getValidMoves(board, wPawn1, 6, 2), 4,2 ));
      assertTrue( findValidMoves(wPawn1.getValidMoves(board, wPawn1, 6, 2), 5,2 ));
      assertEquals(2, wPawn1.getValidMoves(board, wPawn1, 6, 2).size());

      // Second White Pawn
      assertTrue( findValidMoves(wPawn2.getValidMoves(board, wPawn2, 5, 5), 4,5 ));
      assertEquals(1, wPawn2.getValidMoves(board, wPawn2, 5, 5).size());

   }

   /**
    * Test pawns for checking black king
    */
   @Test
   public void testCheckWhite(){
      Board board = createCheckWhiteBoard();
      Square[][] obj = board.getBoardArray();

      Player p1 = new Player(Color.WHITE, "Arthur");
      Player p2 = new Player(Color.BLACK, "John");

      Piece whitePawn = obj[3][4].getPiece();
      Piece blackKing = obj[1][3].getPiece();
      Piece blackRook = obj[0][0].getPiece();

      whitePawn.setHasMoved();

      // White moves first
      assertTrue( findValidMoves(whitePawn.getValidMoves(board, whitePawn, 3, 4), 2, 4) );
      testMovePiece(obj, p1, 3, 4, 2, 4);

      assertTrue( CheckChecker.checkChecker(board, 1, 3).size() > 0);
      assertTrue( blackRook.getValidMoves(board, blackRook, 0,0).isEmpty() );
      assertEquals( 8,blackKing.getValidMoves(board, blackKing, 1,3).size() );

   }

   /**
    * Test pawns for checking white king
    */
   @Test
   public void testCheckBlack(){
      Board board = createCheckBlackBoard();
      Square[][] obj = board.getBoardArray();

      Player p1 = new Player(Color.WHITE, "Micah");
      Player p2 = new Player(Color.BLACK, "Dutch");

      Piece blackPawn = obj[5][3].getPiece();
      Piece whiteKing = obj[7][4].getPiece();
      Piece whiteRook = obj[7][7].getPiece();

      blackPawn.setHasMoved();

      assertTrue( findValidMoves(blackPawn.getValidMoves(board, blackPawn, 5, 3), 6, 3) );
      testMovePiece(obj, p2, 5, 3, 6, 3);

      assertTrue( CheckChecker.checkChecker(board, 7, 4).size() > 0 );
      assertTrue( whiteRook.getValidMoves(board, whiteRook, 7,7).isEmpty() );
      assertEquals( 5,whiteKing.getValidMoves(board, whiteKing, 4,7).size() );
   }

}
