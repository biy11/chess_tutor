/*
 * @(#) Queen.java 0.4 2023/03/05
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
 * A class that represents the Queen piece
 * Inherits the abstract class Piece
 *
 * @author Michael [mjs36]
 * @version 0.1 initial development
 * @version 0.2 changed to use Rook and Bishop classes
 * @version 0.3 Added XML annotations and zero argument constructor - mib60
 * @version 0.4 changed to use Board instead of Square[][]
 * @see Piece
 */

//Marks the class as an XML element
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
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
    * Zero argument constructor used by the XML parser
    */
   public Queen(){}

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
      //as Queen has same moves as the Rook and Bishop combined, can just use there getValidMoves methods
      Rook tempRook = new Rook(piece.getPieceColor());
      Bishop tempBishop = new Bishop(piece.getPieceColor());

      returnArray.addAll(tempRook.getValidMoves(board,piece,x,y));
      returnArray.addAll(tempBishop.getValidMoves(board,piece,x,y));

      return returnArray;
   }

}
