/*
 * @(#) TestQueen.java 0.6 2023/05/05
 *
 * Copyright (c) 2023 Aberystwyth University
 * All rights reserved
 */

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
 * The JUnit Tests for the Queen piece. The tests include the movement, the capture of other
 * pieces, and checking the king.
 *
 * @author Lance [lvs1]
 * @version 0.1 framework
 * @version 0.2 initial development
 * @version 0.3 completed initial tests
 * @version 0.4 coordinate-related bug fixes
 * @version 0.5 removed duplicate tests
 * @version 0.6 removed warnings & commented - tpr3
 */
public class TestQueen {

   /**
    * This is a simplistic movement method
    * Used for easy and straightforward testing
    *
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
    * This method for finding all the valid moves so the unit tests
    * can call all the valid moves multiple times
    *
    * @param list database to look through
    * @param row the intended row value
    * @param col the intended col value
    * @return returns a list of valid moves as int[] to be used by all the tests
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
    * This method creates an empty board to be used for each of the unit
    * tests
    *
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
    *
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
    *
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
    *
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
    * Test queens for no valid movement when at the
    * start of a new game
    */
   @Test
   void testNoValidMoves(){
      Board board = createNoValidMoveBoard();
      Square[][] obj = board.getBoardArray();

      assertTrue(obj[0][3].getPiece().getPossibleMoves(board, obj[0][3].getPiece(), 0, 3).isEmpty());
      assertTrue(obj[7][3].getPiece().getPossibleMoves(board, obj[7][3].getPiece(), 7, 3).isEmpty());
   }

   /**
    * Tests queens ability to capture different types of
    * pieces on the chess board
    */
   @Test
   void testCapture(){
      Board board = createCaptureBoard();
      Square[][] obj = board.getBoardArray();

      Player p1 = new Player(Color.WHITE, "Tony");

      Piece wQueen = obj[7][3].getPiece();
      Piece bPawn = obj[3][7].getPiece();

      // White move first - check then move
      assertTrue( findValidMoves(wQueen.getPossibleMoves(board, wQueen, 7,3),4,0) );
      assertTrue( findValidMoves(wQueen.getPossibleMoves(board, wQueen, 7,3),2,3) );
      assertTrue( findValidMoves(wQueen.getPossibleMoves(board, wQueen, 7,3),3,7) );
      assertEquals( 19, wQueen.getPossibleMoves(board, wQueen, 7,3).size() );
      testMovePiece(obj, p1,7,3,3,7);
      assertTrue( p1.getTakenPieces().contains(bPawn) );

      // Test Queen after move
      assertTrue( findValidMoves(wQueen.getPossibleMoves(board, wQueen, 3,7),0,4) );
      assertTrue( findValidMoves(wQueen.getPossibleMoves(board, wQueen, 3,7),3,0) );
      assertTrue( findValidMoves(wQueen.getPossibleMoves(board, wQueen, 3,7),7,3) );
      assertEquals( 21, wQueen.getPossibleMoves(board, wQueen, 3,7).size() );

   }

   /**
    * Test queens for movement across the board, comparing
    * their valid moves with a move number that is known to
    * be true
    */
   @Test
   void testMove(){
      Board board = createMoveBoard();
      Square[][] obj = board.getBoardArray();

      Piece bQueen = obj[0][0].getPiece();
      Piece wQueen = obj[7][6].getPiece();

      // Test White possible moves
      assertEquals( 15,wQueen.getPossibleMoves(board, wQueen,7,6).size() );
      assertTrue( findValidMoves(wQueen.getPossibleMoves(board, wQueen, 7,6),6,7));
      assertTrue( findValidMoves(wQueen.getPossibleMoves(board, wQueen, 7,6),0,6));
      assertFalse( findValidMoves(bQueen.getPossibleMoves(board, wQueen, 7,6),6,5));

      // Test Black possible moves
      assertEquals( 21,bQueen.getPossibleMoves(board, bQueen,0,0).size() );
      assertTrue( findValidMoves(bQueen.getPossibleMoves(board, bQueen, 0,0),0,7));
      assertTrue( findValidMoves(bQueen.getPossibleMoves(board, bQueen, 0,0),7,0));
      assertTrue( findValidMoves(bQueen.getPossibleMoves(board, bQueen, 0,0),7,7));

   }

}
