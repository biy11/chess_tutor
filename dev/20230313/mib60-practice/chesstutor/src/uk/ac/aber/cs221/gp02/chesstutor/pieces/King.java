/*
 * @(#) King.java 0.2 2023/03/05
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
 * A class that represents the King piece
 * Inherits the abstract class Piece
 *
 * @author Michael [mjs36]
 * @version 0.1 initial development
 * @version 0.2 condensed code with addValidMove method
 * @see Piece
 */
public class King extends Piece {

   private boolean castled;

   /**
    * Constructor
    * Creates new King object - calls super, Piece, constructor
    * Sets ID as KING of the enum Type
    *
    * @param color Color of piece
    */
   public King(Color color) {
      super(color);
      this.ID = Type.KING;
      this.castled = false;
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
      returnArray = new ArrayList<>();

      //array of changes in coordinates for x and y
      int[] moveX = {0, 1, 1, 1, 0, -1, -1, -1};
      int[] moveY = {-1, -1, 0, 1, 1, 1, 0, -1};

      //loops through length of change of coordinate array and, if in board and valid move, add to valid move list
      for (int i = 0; i < moveX.length; i++) {
         int newX = x + moveX[i];
         int newY = y + moveY[i];
         if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8) { //check in board
            addValidMove(boardArray, piece, newX, newY); //return is ignored - return is used for loops
         }
      }

      return returnArray;
   }
}
