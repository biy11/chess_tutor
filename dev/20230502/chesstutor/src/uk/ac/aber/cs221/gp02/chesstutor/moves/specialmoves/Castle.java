/*
 * @(#) Castle.java 0.4 2023/03/06
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.gp02.chesstutor.moves.specialmoves;

import uk.ac.aber.cs221.gp02.chesstutor.game.Board;
import uk.ac.aber.cs221.gp02.chesstutor.game.Square;
import uk.ac.aber.cs221.gp02.chesstutor.moves.CheckChecker;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.*;
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
    * 6 is the y coordinate that the king can move to in order to castle
    * 7 is the y coordinate the rook is at
    * 5 is the y coordinate the rook will be moved to by the castling process
    *
    * @param board Board
    * @param piece Piece
    * @param x int
    * @param y int
    * @return List<int[]>
    */
   public static List<int[]> canCastle(Board board, Piece piece, int x, int y){
      List<int[]> output = new ArrayList<>();

      //check king has not moved and is not in check
      if(piece.hasMoved() || CheckChecker.checkChecker(board, x, y, piece.getEnemyPieceColor()).size() > 0){
         //King can not castle as it has been moved
         return output;
      }

      int[] out;

      //Search the right side of the king for a possible castle
      out = checkKingSide(board, piece, x, y, y+1);
      if(out != null) output.add(out);


      //Search the left side of the king for a possible castle
      out = checkKingLeftSide(board, piece, x, y, y-1);
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
    * @param x int
    * @param y int
    * @param start int where to start search
    * @return int[]
    */
   private static int[] checkKingSide(Board board, Piece piece, int x, int y, int start){
      int[] out = new int[3];

      for(int i = start; i < 8; i++){

         //Get the square and the piece
         Square square = board.getBoardArray()[x][i];
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
            //King at x y can castle rook at x i
            out[0] = y+2;
            if(!CheckChecker.checkChecker(board, x, out[0], piece.getEnemyPieceColor()).isEmpty()) return null;
            out[1] = i;
            out[2] = out[0]-1;
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
    * @param x int
    * @param y int
    * @param start int where to start search
    * @return int[]
    */
   private static int[] checkKingLeftSide(Board board, Piece piece, int x, int y, int start){
      int[] out = new int[3];

      for(int i = start; i > -1; i--){

         //Get the square and the piece
         Square square = board.getBoardArray()[x][i];
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
            //King at x y can castle rook at x i
            out[0] = y-2;
            if(!CheckChecker.checkChecker(board, x, out[0], piece.getEnemyPieceColor()).isEmpty()) return null;
            out[1] = i;
            out[2] = out[0]+1;
            return out;
         }
      }
      return null;
   }
}
