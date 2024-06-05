/*
 * @(#) Piece.java 0.4 2023/03/04
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.gp02.chesstutor.pieces;

import uk.ac.aber.cs221.gp02.chesstutor.util.Color;
import uk.ac.aber.cs221.gp02.chesstutor.game.Square;
import uk.ac.aber.cs221.gp02.chesstutor.util.Type;

import java.util.List;

/**
 * An abstract class that represents Pieces
 * All pieces, look Rook and Bishop, inherit this class
 *
 * @author Michael [mjs36]
 * @version 0.1 initial development
 * @version 0.2 addValidMove method added
 * @version 0.3 changed getEnemyPieceColor to use ternary operator
 * @version 0.4 added comments to code
 * @see Bishop
 * @see King
 * @see Knight
 * @see Pawn
 * @see Queen
 * @see Rook
 */
public abstract class Piece { //abstract as nothing should be initialised of this class

   protected List<int[]> returnArray;
   private boolean hasMoved = false;
   protected Color color; //enum to identify color
   protected Type ID; //enum to identify piece

   /**
    * Constructor
    * Creates new Piece object - as abstract, this is used by child classes
    *
    * @param color Color of piece
    */
   public Piece(Color color) {
      this.color = color;
   }

   public boolean getHasMoved(){
      return hasMoved;
   }

   /**
    * Getter
    * Gets the piece color
    *
    * @return Color
    */
   public Color getPieceColor() {
      return color;
   }

   /**
    * Getter
    * Gets the color of the opponents piece
    *
    * @return Color
    */
   public Color getEnemyPieceColor() {
      return (color == Color.BLACK) ? Color.WHITE : Color.BLACK; //if TRUE, return WHITE, else, return BLACK
   }

   /**
    * Getter
    * Gets the type of the piece
    *
    * @return Type
    */
   public Type getPieceType() {
      return ID;
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
   public abstract List<int[]> getValidMoves(Square[][] boardArray, Piece piece, int x, int y);

   /**
    * A method to add a move to a List if it is valid
    * A move is valid if the square doesn't have a piece, or is an enemy piece
    * Used by Piece child classes and called in the getValidMoves method
    *
    * @param boardArray Square[][]
    * @param piece Piece
    * @param x int
    * @param y int
    * @return boolean return is not used in some pieces method
    */
   protected boolean addValidMove(Square[][] boardArray, Piece piece, int x, int y) {
      if (!boardArray[x][y].isHasPiece()) { //blank squares are valid moves
         int[] move = {x, y};
         returnArray.add(move);
         return true;
      } else if (boardArray[x][y].isHasPiece()
              && boardArray[x][y].getPiece().getPieceColor() == piece.getEnemyPieceColor()) {
         //ensures square has a Piece then checks if it is enemy player
         int[] move = {x, y};
         returnArray.add(move);
         return false;
      }
      return false; //break loop so doesn't 'jump' over pieces
   }

}
