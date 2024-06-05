/*
 * @(#) King.java 0.7 2023/03/05
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.gp02.chesstutor.pieces;

import uk.ac.aber.cs221.gp02.chesstutor.game.*;
import uk.ac.aber.cs221.gp02.chesstutor.moves.CheckChecker;
import uk.ac.aber.cs221.gp02.chesstutor.util.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents the King piece
 * Inherits the abstract class Piece
 *
 * @author Michael [mjs36]
 * @author Micah [mib60]
 * @version 0.1 initial development
 * @version 0.2 condensed code with addValidMove method
 * @version 0.3 removed castled field - using hasMoved field instead
 * @version 0.4 Added XML annotations and zero argument constructor - mib60
 * @version 0.5 changed to use Board instead of Square[][]
 * @version 0.6 updated code variables from x to row and updated i to be index
 * @version 0.7 Added getPossibleMoves - mib60
 * @see Piece
 */

//Marks the class as an XML element
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class King extends Piece {

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
   }

   /**
    * Zero argument constructor used by the XML parser
    */
   public King(){}

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

      returnArray = new ArrayList<>();

      //array of changes in coordinates for row and col
      int[] moveRow = {0, 1, 1, 1, 0, -1, -1, -1};
      int[] moveCol = {-1, -1, 0, 1, 1, 1, 0, -1};

      //loops through length of change of coordinate array and, if in board and valid move, add to valid move list
      for (int i = 0; i < moveRow.length; i++) {
         int newRow = row + moveRow[i];
         int newCol = col + moveCol[i];
         if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) { //check in board
            addValidMove(boardArray, piece, newRow, newCol); //return is ignored - return is used for loops
         }
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
