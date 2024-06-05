/*
 * @(#) Rook.java 0.4 2023/03/05
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.gp02.chesstutor.pieces;

import uk.ac.aber.cs221.gp02.chesstutor.game.*;
import uk.ac.aber.cs221.gp02.chesstutor.util.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents the Rook piece
 * Inherits the abstract class Piece
 *
 * @author Michael [mjs36]
 * @version 0.1 initial development
 * @version 0.2 condensed code with addValidMove method
 * @version 0.3 Added XML annotations and zero argument constructor - mib60
 * @version 0.4 changed to use Board instead of Square[][]
 * @see Piece
 */

//Marks the class as an XML element
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
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
    * Zero argument constructor used by the XML parser
    */
   public Rook(){}

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
