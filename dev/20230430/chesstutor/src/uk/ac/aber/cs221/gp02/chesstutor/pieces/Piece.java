/*
 * @(#) Piece.java 0.8 2023/03/04
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.gp02.chesstutor.pieces;

import uk.ac.aber.cs221.gp02.chesstutor.game.*;
import uk.ac.aber.cs221.gp02.chesstutor.util.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

/**
 * An abstract class that represents Pieces
 * All pieces, look Rook and Bishop, inherit this class
 *
 * @author Michael [mjs36]
 * @author Micah [mib60]
 * @version 0.1 initial development
 * @version 0.2 addValidMove method added
 * @version 0.3 changed getEnemyPieceColor to use ternary operator
 * @version 0.4 added comments to code
 * @version 0.5 hasMoved field added by mib60
 * @version 0.6 Added XML annotations and zero argument constructor - mib60
 * @version 0.7 changed to use Board instead of Square[][]
 * @version 0.8 updated code variables from x to row and updated i to be index
 * @version 0.9 Added getPossibleMoves
 * @see Bishop
 * @see King
 * @see Knight
 * @see Pawn
 * @see Queen
 * @see Rook
 */


// Piece is given the XMLTransient tag, this way the right Piece.java subclasses are saved and loaded
@XmlTransient
//Tell the JAXB Parser that it will need to be able to save and load the subclasses
@XmlSeeAlso({Queen.class, Bishop.class, King.class, Knight.class, Rook.class, Pawn.class})
public abstract class Piece { //abstract as nothing should be initialised of this class

   @XmlTransient //Do not save this to the XML save file
   protected List<int[]> returnArray;

   @XmlElement(name = "color") //Save this to the XML save file
   protected Color color; //enum to identify color

   @XmlElement(name = "ID") //Save this to the XML save file
   protected Type ID; //enum to identify piece

   @XmlElement(name = "hasMoved") //Save this to the XML save file
   protected boolean hasMoved; //used by King and Rook for castling

   //Piece.Java is abstract, these annotations will make sure the correct subclass is defined when saving/loading
   @XmlElements({
           @XmlElement(name="pieceInfo",type=Queen.class),
           @XmlElement(name="pieceInfo",type=Bishop.class),
           @XmlElement(name="pieceInfo",type=King.class),
           @XmlElement(name="pieceInfo",type=Knight.class),
           @XmlElement(name="pieceInfo",type=Rook.class),
           @XmlElement(name="pieceInfo",type=Pawn.class)
   })

   public String pieceType; //though IntelliJ Says this is not used, it is necessary to save the piece type


   /**
    * Constructor
    * Creates new Piece object - as abstract, this is used by child classes
    *
    * @param color Color of piece
    */
   public Piece(Color color) {
      this.color = color;
      this.hasMoved = false; //set for all but only used in King and Rook
   }

   /**
    * Zero argument constructor used by the XML parser
    */
   public Piece(){}

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
    * Getter
    * Gets the hasMoved field of the piece
    *
    * @return Type
    */
   public boolean hasMoved() {
      return hasMoved;
   }

   /**
    * Setter
    * Sets the hasMoved field of the piece to true
    *
    */
   public void setHasMoved() {
      hasMoved = true;
   }

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
   public abstract List<int[]> getValidMoves(Board board, Piece piece, int row, int col);

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
   public abstract List<int[]> getPossibleMoves(Board board, Piece piece, int row, int col);


   /**
    * A method to add a move to a List if it is valid
    * A move is valid if the square doesn't have a piece, or is an enemy piece
    * Used by Piece child classes and called in the getValidMoves method
    *
    * @param boardArray Square[][]
    * @param piece Piece
    * @param row int
    * @param col int
    * @return boolean return is not used in some pieces method
    */
   protected boolean addValidMove(Square[][] boardArray, Piece piece, int row, int col) {
      if (!boardArray[row][col].hasPiece()) { //blank squares are valid moves
         int[] move = {row, col};
         returnArray.add(move);
         return true;
      } else if (boardArray[row][col].hasPiece()
              && boardArray[row][col].getPiece().getPieceColor() == piece.getEnemyPieceColor()) {
         //ensures square has a Piece then checks if it is enemy player
         int[] move = {row, col};
         returnArray.add(move);
         return false;
      }
      return false; //break loop so doesn't 'jump' over pieces
   }

}
