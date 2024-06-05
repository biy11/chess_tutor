/*
 * @(#) Bishop.java 0.4 2023/03/05
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.gp02.chesstutor.pieces;

import uk.ac.aber.cs221.gp02.chesstutor.game.*;
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
    * @param x int
    * @param y int
    * @return List<int[]>
    */
   @Override
   public List<int[]> getValidMoves(Board board, Piece piece, int x, int y) {
      Square[][] boardArray = board.getBoardArray();

      returnArray = new ArrayList<int[]>();

      //Check valid moves going up and right
      for (int i = x-1, j = y+1; i >= 0 && j < 8; i--, j++) {
         if (!addValidMove(boardArray, piece, i, j)) break;
      }

      //Check valid moves going up and left
      for (int i = x-1, j = y-1; i >= 0 && j >= 0; i--, j--) {
         if (!addValidMove(boardArray, piece, i, j)) break;
      }

      //Check valid moves going down and right
      for (int i = x+1, j = y+1; i < 8 && j < 8; i++, j++) {
         if (!addValidMove(boardArray, piece, i, j)) break;
      }

      //Check valid moves going down and left
      for (int i = x+1, j = y-1; i < 8 && j >= 0; i++, j--) {
         if (!addValidMove(boardArray, piece, i, j)) break;
      }

      return returnArray;
   }

}
