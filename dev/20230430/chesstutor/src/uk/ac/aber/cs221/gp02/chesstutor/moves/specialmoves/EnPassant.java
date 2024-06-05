/*
 * @(#) enPassant.java 0.4 2023/03/05
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.gp02.chesstutor.moves.specialmoves;

import uk.ac.aber.cs221.gp02.chesstutor.game.*;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.*;
import uk.ac.aber.cs221.gp02.chesstutor.util.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that holds the getMoves method for enPassant
 *
 * @author Michael [mjs36]
 * @version 0.1 initial development
 * @version 0.2 moved to using two List solution
 * @version 0.3 changed to use enPassantList and getRoundCount
 * @version 0.4 changed to use Board instead of Game class for enPassant - parameter now uses Board instead of Square[][]
 * @version 0.5 updated code variables from x to row and updated i to be index
 * @see Pawn
 */
public class EnPassant {

   /**
    * A method to return a List of valid move coordinates
    * <p></p>
    * It is assumed only a PAWN can be passed to the method
    * <p></p>
    * Returns one list which contains two lists
    * First list contains the moves - where the Pawn will move two
    * Second list contains the taking Pawns - what Pawn will be taken
    * <p></p>
    * Returned in format of {{[6,4],[5,3]},{[3,4],[2,3]}}
    *
    * @param board Board
    * @param piece Piece
    * @param row int
    * @param col int
    * @return List<List<int[]>>
    */
   public static List<List<int[]>> getMoves(Board board, Piece piece, int row, int col) {
      Square[][] boardArray = board.getBoardArray();
      List<List<int[]>> returnArray = new ArrayList<>();
      List<int[]> movesArray = new ArrayList<>();
      List<int[]> takingArray = new ArrayList<>();

      //List 0 is move
      //List 1 is taking piece

      int direction = (piece.getPieceColor() == Color.WHITE) ? -1 : 1;
      int newRow = row + direction;

      int attackNeg = col - 1; //coordinate for diagonal left (or right if we are black)
      int attackPos = col + 1; //coordinate for diagonal right (or left if we are black)

      if (newRow >= 0 && newRow < 8) { //check in range
         if (attackNeg >= 0 && attackNeg < 8) { //check in range
            if (boardArray[row][attackNeg].hasPiece() && boardArray[row][attackNeg].getPiece().getPieceColor() == piece.getEnemyPieceColor()
                    && !boardArray[newRow][attackNeg].hasPiece() && boardArray[row][attackNeg].getPiece().getPieceType() == Type.PAWN) {
               int[] availablePiece = board.getEnPassantPiece();
               if (row == availablePiece[0] & attackNeg == availablePiece[1]) {
                  int[] move = {newRow, attackNeg};
                  int[] taking = {row, attackNeg};
                  movesArray.add(move);
                  takingArray.add(taking);
               }
            }
         }
         if (attackPos >= 0 && attackPos < 8) { //check in range
            if (boardArray[row][attackPos].hasPiece() && boardArray[row][attackPos].getPiece().getPieceColor() == piece.getEnemyPieceColor()
                    && !boardArray[newRow][attackPos].hasPiece() && boardArray[row][attackPos].getPiece().getPieceType() == Type.PAWN) {
               int[] availablePiece = board.getEnPassantPiece();
               if (row == availablePiece[0] & attackNeg == availablePiece[1]) {
                  int[] move = {newRow, attackPos};
                  int[] taking = {row, attackPos};
                  movesArray.add(move);
                  takingArray.add(taking);
               }
            }
         }
      }
      returnArray.add(movesArray);
      returnArray.add(takingArray);
      return returnArray;
   }
}
