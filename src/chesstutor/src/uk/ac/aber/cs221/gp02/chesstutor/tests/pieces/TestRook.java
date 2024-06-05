/*
 * @(#) TestRook.java 0.5 2023/05/05
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
 * The JUnit Tests for the Rook piece. The tests include the movement, the capture of pieces,
 * and the checking of the king.
 *
 * @author Lance [lvs1]
 * @version 0.1 framework
 * @version 0.2 initial development
 * @version 0.3 coordinate-related bug fixes
 * @version 0.4 removed duplicate tests
 * @version 0.5 removed warnings
 */

public class TestRook {

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
    * @return returns a list of int[] to find all the valid moves for each test
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
    * This method creates an empty board to be used on every unit test
    * @return Board object
    */
   private Board createEmptyBoard(){
      Board obj = new Board();
      obj.clearBoard();

      return obj;
   }

   /**
    * This method creates a board with specific conditions
    * Rooks are placed to have no valid moves
    * @return Board object
    */
   private Board createNoValidMovesBoard(){
      Board board = createEmptyBoard();
      Square[][] obj = board.getBoardArray();

      obj[0][0] = new Square(new Rook(Color.BLACK));
      obj[0][7] = new Square(new Rook(Color.BLACK));
      obj[7][0] = new Square(new Rook(Color.WHITE));
      obj[7][7] = new Square(new Rook(Color.WHITE));

      obj[0][1] = new Square(new Knight(Color.BLACK));
      obj[0][6] = new Square(new Knight(Color.BLACK));
      obj[7][1] = new Square(new Knight(Color.WHITE));
      obj[7][6] = new Square(new Knight(Color.WHITE));

      obj[1][0] = new Square(new Pawn(Color.BLACK));
      obj[1][7] = new Square(new Pawn(Color.BLACK));
      obj[6][0] = new Square(new Pawn(Color.WHITE));
      obj[6][7] = new Square(new Pawn(Color.WHITE));

      return board;
   }

   /**
    * This method creates a board with specific conditions
    * Rooks are placed to capture pieces
    * @return Board object
    */
   private Board createCaptureBoard(){
      Board board = createEmptyBoard();
      Square[][] obj = board.getBoardArray();

      obj[0][0] = new Square(new Rook(Color.BLACK));
      obj[0][7] = new Square(new Rook(Color.BLACK));
      obj[7][0] = new Square(new Rook(Color.WHITE));

      return board;
   }

   /**
    * This method creates a board with specific conditions
    * Rooks and other pieces are placed to test movement
    * @return Board object
    */
   private Board createMoveBoard(){
      Board board = createEmptyBoard();
      Square[][] obj = board.getBoardArray();

      obj[0][7] = new Square(new Rook(Color.BLACK));
      obj[7][0] = new Square(new Rook(Color.WHITE));

      obj[0][1] = new Square(new Pawn(Color.BLACK));
      obj[7][7] = new Square(new Pawn(Color.WHITE));

      return board;
   }

   /**
    * Test rooks for no valid moves at the start of a new
    * game
    */
   @Test
   public void testNoValidMoves(){
      Board board = createNoValidMovesBoard();
      Square[][] obj = board.getBoardArray();

      // All Rooks should not be able to move here
      assertTrue(obj[0][0].getPiece().getPossibleMoves(board, obj[0][0].getPiece(), 0, 0).isEmpty());
      assertTrue(obj[0][7].getPiece().getPossibleMoves(board, obj[0][7].getPiece(), 0, 7).isEmpty());
      assertTrue(obj[7][0].getPiece().getPossibleMoves(board, obj[7][0].getPiece(), 7, 0).isEmpty());
      assertTrue(obj[7][7].getPiece().getPossibleMoves(board, obj[7][0].getPiece(), 7, 7).isEmpty());

   }

   /**
    * Tests black rooks for capturing white pieces by
    * simulating a game with the backend
    */
   @Test
   public void testCapture(){
      Board board = createCaptureBoard();
      Square[][] obj = board.getBoardArray();

      Player p1 = new Player(Color.WHITE, "John");
      Player p2 = new Player(Color.BLACK, "Jack");

      Piece bRook1 = obj[0][0].getPiece();
      Piece bRook2 = obj[0][7].getPiece();
      Piece wRook1 = obj[7][0].getPiece();

      // White Moves first - Check all White moves
      assertTrue( findValidMoves( wRook1.getPossibleMoves(board, wRook1, 7,0), 0,0) );
      assertTrue( findValidMoves( wRook1.getPossibleMoves(board, wRook1, 7,0), 7,7) );
      assertEquals( wRook1.getPossibleMoves(board, wRook1, 7,0).size(), 14 );

      // White Rook Captures Black Rook
      testMovePiece(obj, p1, 7, 0, 0, 0);
      assertTrue(p1.getTakenPieces().contains(bRook1));

      //Check all Black Rook Moves
      assertTrue( findValidMoves( bRook2.getPossibleMoves(board, bRook2, 0,7), 0,0) );
      assertTrue( findValidMoves( bRook2.getPossibleMoves(board, bRook2, 0,7), 7,7) );
      assertEquals( bRook2.getPossibleMoves(board, bRook2, 0,7).size(), 14 );

      // Black Rook Captures White Rook
      testMovePiece(obj, p2, 0,7,0,0);
      assertTrue(p2.getTakenPieces().contains(wRook1));

   }

   /**
    * Tests rooks movements to make sure it has the right
    * number of valid moves for its current position
    */
   @Test
   public void testMove(){
      Board board = createMoveBoard();
      Square[][] obj = board.getBoardArray();

      Piece bRook = obj[0][7].getPiece();
      Piece wRook = obj[7][0].getPiece();

      // Test White Rook
      assertTrue( findValidMoves(wRook.getPossibleMoves(board, wRook, 7,0),0,0) );
      assertTrue( findValidMoves(wRook.getPossibleMoves(board, wRook, 7,0),7,6) );
      assertEquals( wRook.getPossibleMoves(board, wRook, 7,0).size(), 13);

      // Test Black Rook
      assertTrue( findValidMoves(bRook.getPossibleMoves(board, bRook, 0,7),0,2) );
      assertTrue( findValidMoves(bRook.getPossibleMoves(board, bRook, 0,7),7,7) );
      assertEquals( bRook.getPossibleMoves(board, bRook, 0,7).size(), 12);
   }


}
