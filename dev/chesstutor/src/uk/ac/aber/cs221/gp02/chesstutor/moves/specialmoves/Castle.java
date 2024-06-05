/*
 * @(#) Castle.java 0.6 2023/05/04
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.gp02.chesstutor.moves.specialmoves;

import uk.ac.aber.cs221.gp02.chesstutor.game.Board;
import uk.ac.aber.cs221.gp02.chesstutor.game.Square;
import uk.ac.aber.cs221.gp02.chesstutor.moves.CheckChecker;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.*;
import uk.ac.aber.cs221.gp02.chesstutor.util.Color;
import uk.ac.aber.cs221.gp02.chesstutor.util.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for checking if a king can castle
 * Only has one public method: canCastle
 *
 * @author Micah [mib60]
 * @author Michael [mjs36]
 * @version 0.1 initial development
 * @version 0.2 added logic of canCastle and checkKingSide
 * @version 0.3 changed canCastle to return a list with multiple values within
 * @version 0.4 println removed and javadoc reformatted to match other classes by mjs36
 * @version 0.5 Added check to stop king castling into check, removed warnings
 * @version 0.6 can't Castle if transverse square in check - mjs36
 * @see King
 */
public class Castle {

   /**
    * Static method. Checks whether a king can castle
    * <p></p>
    * It is assumed only a KING can be passed to the method
    * <p></p>
    * Returns a list of integer arrays. Each integer array contains: The position the king
    * must move to in order to castle, the position of the rook it will be castling with, and the
    * position the rook will be moved to, in that order.
    * <p></p>
    * Output may be: 6, 7, 5
    * 6 is the col coordinate that the king can move to in order to castle
    * 7 is the col coordinate the rook is at
    * 5 is the col coordinate the rook will be moved to by the castling process
    *
    * @param board Board
    * @param piece Piece
    * @param row int
    * @param col int
    * @return List<int[]>
    */
   public static List<int[]> canCastle(Board board, Piece piece, int row, int col){
      List<int[]> output = new ArrayList<>();

      //check king has not moved and is not in check
      if(piece.hasMoved() || CheckChecker.checkChecker(board, row, col, piece.getEnemyPieceColor()).size() > 0){
         //King can not castle as it has been moved
         return output;
      }

      int[] out;

      //Search the right side of the king for a possible castle
      out = checkKingSide(board, piece, row, col, col+1);
      if(out != null) output.add(out);


      //Search the left side of the king for a possible castle
      out = checkKingLeftSide(board, piece, row, col, col-1);
      if(out != null) output.add(out);

      return output;
   }

   /**
    * Checks one side of the king to see if it can castle
    * <p></p>
    * Returns an array of integers containing: the position the king
    * must move to in order to castle, the position of the rook it will be castling with, and the
    * position the rook will be moved to, in that order. Returns null if a castle move is not found
    *
    * @param board Board
    * @param piece Piece
    * @param row int
    * @param col int
    * @param start int where to start search
    * @return int[]
    */
   private static int[] checkKingSide(Board board, Piece piece, int row, int col, int start){
      int[] out = new int[3];

      for(int index = start; index < 8; index++){

         //Get the square and the piece
         Square square = board.getBoardArray()[row][index];
         Piece p = square.getPiece();

         //Skip the square if the piece is null
         if(p == null) continue;

         //If the piece is not a rook, castling is not possible as a rook would be blocked, so break the loop
         if(!p.getPieceType().equals(Type.ROOK)){
            //piece between king and rook, castling not possible
            return null;
         }

         //The piece is a rook and the loop has not been broken so there are no pieces blocking the castling
         //So if the piece has not moved a castle is possible
         if(!p.hasMoved() && p.getPieceColor().equals(piece.getPieceColor())){
            //King at row col can castle rook at row index
            out[0] = col+2;
            if(!CheckChecker.checkChecker(board, row, out[0], piece.getEnemyPieceColor()).isEmpty()) return null;
            out[1] = index;
            out[2] = out[0]-1;
            if (transverseInCheck(board, row, col, out[2], piece.getEnemyPieceColor())) return null; //if transverse in check, return null
            return out;
         }
      }
      return null;
   }

   /**
    * Checks one side of the king to see if it can castle
    * <p></p>
    * Returns an array of integers containing: the position the king
    * must move to in order to castle, the position of the rook it will be castling with, and the
    * position the rook will be moved to, in that order. Returns null if a castle move is not found
    *
    * @param board Board
    * @param piece Piece
    * @param row int
    * @param col int
    * @param start int where to start search
    * @return int[]
    */
   private static int[] checkKingLeftSide(Board board, Piece piece, int row, int col, int start){
      int[] out = new int[3];

      for(int index = start; index > -1; index--){

         //Get the square and the piece
         Square square = board.getBoardArray()[row][index];
         Piece p = square.getPiece();

         //Skip the square if the piece is null
         if(p == null) continue;

         //If the piece is not a rook, castling is not possible as a rook would be blocked, so break the loop
         if(!p.getPieceType().equals(Type.ROOK)){
            //piece between king and rook, castling not possible
            return null;
         }

         //The piece is a rook and the loop has not been broken so there are no pieces blocking the castling
         //So if the piece has not moved a castle is possible
         if(!p.hasMoved() && p.getPieceColor().equals(piece.getPieceColor())){
            //King at row col can castle rook at row index
            out[0] = col-2;
            if(!CheckChecker.checkChecker(board, row, out[0], piece.getEnemyPieceColor()).isEmpty()) return null;
            out[1] = index;
            out[2] = out[0]+1;
            if (transverseInCheck(board, row, col, out[2], piece.getEnemyPieceColor())) return null; //if transverse in check, return null
            return out;
         }
      }
      return null;
   }

   /**
    * A method to check if the transverse move of the King, during Castling, will put it in check
    *
    * @param board Board
    * @param row row of King/Rook
    * @param kingCol col of King
    * @param moveCol col that Rook will move to/King transverse
    * @param opponentColor color
    * @return true if move puts in Check
    *
    */
   private static boolean transverseInCheck(Board board, int row, int kingCol, int moveCol, Color opponentColor) {
      Board simBoard = board.cloneBoard();
      Square[][] simBoardArray = simBoard.getBoardArray();

      simBoardArray[row][moveCol] = simBoardArray[row][kingCol];
      simBoardArray[row][kingCol] = new Square();

      return !CheckChecker.checkChecker(simBoard, row, moveCol, opponentColor).isEmpty();
   }
}
