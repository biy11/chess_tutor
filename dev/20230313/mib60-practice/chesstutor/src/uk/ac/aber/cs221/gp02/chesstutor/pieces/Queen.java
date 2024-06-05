/*
 * @(#) Queen.java 0.2 2023/03/05
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
 * A class that represents the Queen piece
 * Inherits the abstract class Piece
 *
 * @author Michael [mjs36]
 * @version 0.1 initial development
 * @version 0.2 changed to use Rook and Bishop classes
 * @see Piece
 */
public class Queen extends Piece {

   /**
    * Constructor
    * Creates new Queen object - calls super, Piece, constructor
    * Sets ID as QUEEN of the enum Type
    *
    * @param color Color of piece
    */
   public Queen(Color color) {
      super(color);
      this.ID = Type.QUEEN;
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
      //as Queen has same moves as the Rook and Bishop combined, can just use there getValidMoves methods
      Rook tempRook = new Rook(piece.getPieceColor());
      Bishop tempBishop = new Bishop(piece.getPieceColor());

      returnArray.addAll(tempRook.getValidMoves(boardArray,piece,x,y));
      returnArray.addAll(tempBishop.getValidMoves(boardArray,piece,x,y));

      return returnArray;
   }

}
