/*
 * @(#) Rook.java 0.2 2023/03/05
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.gp02.chesstutor.pieces;

import uk.ac.aber.cs221.gp02.chesstutor.util.Color;
import uk.ac.aber.cs221.gp02.chesstutor.game.Square;
import uk.ac.aber.cs221.gp02.chesstutor.util.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents the Rook piece
 * Inherits the abstract class Piece
 *
 * @author Michael [mjs36]
 * @version 0.1 initial development
 * @version 0.2 condensed code with addValidMove method
 * @see Piece
 */
public class Rook extends Piece {

   /**
    * Constructor
    * Creates new Rook object - calls super, Piece, constructor
    * Sets ID as ROOK of the enum Type
    *
    * @param color Color of piece
    */
   public Rook(Color color) {
      super(color);
      this.ID = Type.ROOK;
   }

   /**
    * A method to return a List of valid move coordinates
    *
    * @param boardArray Square[][]
    * @param piece Piece
    * @param x int
    * @param y int
    * @return List<int[]>
    */
   @Override
   public List<int[]> getValidMoves(Square[][] boardArray, Piece piece, int x, int y) {
      returnArray = new ArrayList<int[]>();

      //Check valid moves going right
      for (int i = y + 1; i < 8; i++) {
         if (!addValidMove(boardArray, piece, x, i)) break;
      }

      //Check valid moves going left
      for (int i = y - 1; i >= 0; i--) {
         if (!addValidMove(boardArray, piece, x, i)) break;
      }

      //Check valid moves going up
      for (int i = x - 1; i >= 0; i--) {
         if (!addValidMove(boardArray, piece, i, y)) break;
      }

      //Check valid moves going down
      for (int i = x + 1; i < 8; i++) {
         if (!addValidMove(boardArray, piece, i, y)) break;
      }

      return returnArray;
   }

}
