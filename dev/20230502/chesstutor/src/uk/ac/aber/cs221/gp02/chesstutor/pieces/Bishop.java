/*
 * @(#) Bishop.java 0.6 2023/03/05
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.gp02.chesstutor.pieces;

import uk.ac.aber.cs221.gp02.chesstutor.game.*;
import uk.ac.aber.cs221.gp02.chesstutor.moves.CheckChecker;
import uk.ac.aber.cs221.gp02.chesstutor.util.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents the Bishop piece
 * Inherits the abstract class Piece
 *
 * @author Michael [mjs36]
 * @version 0.1 initial development
 * @version 0.2 condensed code with addValidMove method
 * @version 0.3 Added XML annotations and zero argument constructor - mib60
 * @version 0.4 changed to use Board instead of Square[][]
 * @version 0.5 updated code variables from x to row and updated i to be index
 * @version 0.6 Added getPossibleMoves - mib60
 * @see Piece
 */
public class Bishop extends Piece {

   /**
    * Constructor
    * Creates new Bishop object - calls super, Piece, constructor
    * Sets ID as BISHOP of the enum Type
    *
    * @param color Color of piece
    */
   public Bishop(Color color) {
      super(color);
      this.ID = Type.BISHOP;
   }

   /**
    * Zero argument constructor used by the XML parser
    */
   public Bishop(){}

   /**
    * A method to return a List of valid move coordinates
    * <p></p>
    * Returned in format of {[6,4],[5,3]}
    *
    * @param board Board
    * @param piece Piece
    * @param row int
    * @param col int
    * @return List<int[]>
    */
   @Override
   public List<int[]> getValidMoves(Board board, Piece piece, int row, int col) {
      Square[][] boardArray = board.getBoardArray();

      returnArray = new ArrayList<int[]>();

      //Check valid moves going up and right
      for (int index = row-1, secondIndex = col+1; index >= 0 && secondIndex < 8; index--, secondIndex++) {
         if (!addValidMove(boardArray, piece, index, secondIndex)) break;
      }

      //Check valid moves going up and left
      for (int index = row-1, secondIndex = col-1; index >= 0 && secondIndex >= 0; index--, secondIndex--) {
         if (!addValidMove(boardArray, piece, index, secondIndex)) break;
      }

      //Check valid moves going down and right
      for (int index = row+1, secondIndex = col+1; index < 8 && secondIndex < 8; index++, secondIndex++) {
         if (!addValidMove(boardArray, piece, index, secondIndex)) break;
      }

      //Check valid moves going down and left
      for (int index = row+1, secondIndex = col-1; index < 8 && secondIndex >= 0; index++, secondIndex--) {
         if (!addValidMove(boardArray, piece, index, secondIndex)) break;
      }

      return returnArray;
   }

   /**
    * A method to return a List of valid move coordinates for when a player
    * clicks on or tries to move a piece. Unlike getValidMoves this function
    * will detect whether any moves put the friendly king in check and remove them
    * <p></p>
    * Returned in format of {[6,4],[5,3]}
    *
    * @param board Board
    * @param piece Piece
    * @param row   int
    * @param col   int
    * @return List<int [ ]>
    */
   @Override
   public List<int[]> getPossibleMoves(Board board, Piece piece, int row, int col) {
      returnArray = getValidMoves(board, piece, row, col);
      CheckChecker.removeMovesThatEndangerKing(board, piece, row, col, returnArray);
      return returnArray;
   }
}
