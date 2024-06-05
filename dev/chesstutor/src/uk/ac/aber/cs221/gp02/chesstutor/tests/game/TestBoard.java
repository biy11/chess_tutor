/*
 * @(#) TestBoard.java 0.4 2023/05/04
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */
package uk.ac.aber.cs221.gp02.chesstutor.tests.game;

import uk.ac.aber.cs221.gp02.chesstutor.game.Board;
import uk.ac.aber.cs221.gp02.chesstutor.game.Square;

import org.junit.jupiter.api.Test;
import uk.ac.aber.cs221.gp02.chesstutor.util.Color;
import uk.ac.aber.cs221.gp02.chesstutor.util.Type;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Board
 *
 * @author  Lance [lvs1]
 * @version 0.1 Framework
 * @version 0.2 Initial Development
 * @version 0.3 Bug Fixes
 * @version 0.4 Added comments
 */

public class TestBoard {

   /**
    * Test to check if Rooks are correctly located on the chess board.
    * */
   @Test
   public void rookLocationsAllCorrect(){
      Board board = new Board();
      board.boardReset();
      Square[][] array ;
      array = board.getBoardArray();
      //Checks if piece on given x and y coordinates is a Rook.
      assertEquals(Type.ROOK, array[0][0].getPiece().getPieceType());
      assertEquals(Type.ROOK, array[0][7].getPiece().getPieceType());
      assertEquals(Type.ROOK, array[7][0].getPiece().getPieceType());
      assertEquals(Type.ROOK, array[7][7].getPiece().getPieceType());
   }

   /**
    * Test to check if the Knights are located correctly on the chess board.
    * */
   @Test
   public void knightLocationsAllCorrect(){
      Board board = new Board();
      board.boardReset();
      Square[][] array ;
      array = board.getBoardArray();
      //Checks if piece on given x and y coordinates is a Knight.
      assertEquals(Type.KNIGHT, array[0][1].getPiece().getPieceType());
      assertEquals(Type.KNIGHT, array[0][6].getPiece().getPieceType());
      assertEquals(Type.KNIGHT, array[7][1].getPiece().getPieceType());
      assertEquals(Type.KNIGHT, array[7][6].getPiece().getPieceType());
   }

   /**
    * Test to check if the Bishops are located correctly on the chess board.
    * */
   @Test
   public void bishopLocationsAllCorrect(){
      Board board = new Board();
      board.boardReset();
      Square[][] array ;
      array = board.getBoardArray();
      //Checks if piece on given x and y coordinates is a Bishop.
      assertEquals(Type.BISHOP, array[0][2].getPiece().getPieceType());
      assertEquals(Type.BISHOP, array[0][5].getPiece().getPieceType());
      assertEquals(Type.BISHOP, array[7][2].getPiece().getPieceType());
      assertEquals(Type.BISHOP, array[7][5].getPiece().getPieceType());
   }

   /**
    * Test to check if the Queen are located correctly on the chess board.
    * */
   @Test
   public void queenLocationsAllCorrect(){
      Board board = new Board();
      board.boardReset();
      Square[][] array ;
      array = board.getBoardArray();
      //Checks if piece on given x and y coordinates is a Queen.
      assertEquals(Type.QUEEN, array[0][3].getPiece().getPieceType());
      assertEquals(Type.QUEEN, array[0][3].getPiece().getPieceType());
   }
   /**
    * Test to check if the King are located correctly on the chess board.
    * */
   @Test
   public void kingLocationsAllCorrect(){
      Board board = new Board();
      board.boardReset();
      Square[][] array ;
      array = board.getBoardArray();
      //Checks if piece on given x and y coordinates is a King.
      assertEquals(Type.KING, array[0][4].getPiece().getPieceType());
      assertEquals(Type.KING, array[0][4].getPiece().getPieceType());
   }
   /**
    * Test to check if the Pawn are located correctly on the chess board.
    * */
   @Test
   public void pawnLocationsAllCorrect(){
      Board board = new Board();
      board.boardReset();
      Square[][] array ;
      array = board.getBoardArray();
      //Checks if piece on given x and y coordinates is a Pawn.
      for (int i=0 ; i < 8; i++) {
         assertEquals(Type.PAWN, array[1][i].getPiece().getPieceType());
         assertEquals(Type.PAWN, array[6][i].getPiece().getPieceType());
      }
   }

   /**
    * Test to check if all white pieces are located correctly on the chess board.
    * */
   @Test
   public void whiteLocationsAllCorrect(){
      Board board = new Board();
      board.boardReset();
      Square[][] array ;
      array = board.getBoardArray();
      //Checks if piece on given x and y coordinates is a white piece.
      for (int i=0 ; i < 8; i++) {
         assertEquals(Color.WHITE, array[6][i].getPiece().getPieceColor());
         assertEquals(Color.WHITE, array[7][i].getPiece().getPieceColor());
      }
   }
   /**
    * Test to check if all black pieces are located correctly on the chess board.
    * */
   @Test
   public void blackLocationsAllCorrect(){
      Board board = new Board();
      board.boardReset();
      Square[][] array ;
      array = board.getBoardArray();
      //Checks if piece on given x and y coordinates is a black piece.
      for (int i=0 ; i < 8; i++) {
         assertEquals(Color.BLACK, array[0][i].getPiece().getPieceColor());
         assertEquals(Color.BLACK, array[1][i].getPiece().getPieceColor());
      }
   }

   /**
    * Test to check if correct number of pieces are located on the chess board.
    * */
   @Test
   public void noExtraPiecesOnBoard(){
      Board board = new Board();
      board.boardReset();
      Square[][] array ;
      array = board.getBoardArray();

      int pieceNmb = 0;
      int x;
      int y;
      for (int i=0;i<64;i++){
         x = i%8;
         y = i/8;
         if (array[y][x].hasPiece())pieceNmb++;
      }
      assertEquals(32, pieceNmb);
   }

}
