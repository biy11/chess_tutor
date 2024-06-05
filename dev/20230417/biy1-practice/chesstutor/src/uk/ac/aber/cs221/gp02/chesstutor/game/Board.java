/*
 * @(#) Board.java 0.4 2023/03/05
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.gp02.chesstutor.game;

import uk.ac.aber.cs221.gp02.chesstutor.pieces.*;
import uk.ac.aber.cs221.gp02.chesstutor.util.Color;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;

/**
 * @author Michael [mjs36]
 * @author Micah [mib60]
 * @version 0.1 Initial Development
 * @version 0.2 Added XML annotations - mib60
 * @version 0.3 complete Javadoc added
 * @version 0.4 implements Cloneable and Clone method - used for CheckChecker
 * @version 0.5 added setSquare method for testing - mib60
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Board implements Cloneable { //Marks the class as an XML element

   @XmlElement(name = "boardArray") //Save this to the XML save file
   private final Square[][] boardArray; //contents themselves are not final

   @XmlElement(name = "enPassantPiece") //Save this to the XML save file
   private int[] enPassantPiece; //stores coordinates of available enPassant Pawn

   /**
    * Constructor
    * Creates new Board object
    * <p></p>
    * Sets the boardArray as a 2D Square array
    */
   public Board() {
      boardArray = new Square[8][8];
      enPassantPiece = new int[]{-1,-1}; //-1 will ensure no erroneous data is passed
      boardReset();
   }

   /**
    * Added setsquare method so that
    * the testing looks neater
    * @param square The new square
    * @param x The x coordinate
    * @param y The y coordinate
    */
   public void setSquare(Square square, int x, int y){
      boardArray[x][y] = square;
   }

   /**
    * Getter
    * Gets the boardArray field
    *
    * @return Square[][]
    */
   public Square[][] getBoardArray() {
      return boardArray;
   }

   /**
    * Getter
    * Gets the enPassantPiece field
    *
    * @return int[]
    */
   public int[] getEnPassantPiece() {
      return enPassantPiece;
   }

   /**
    * Setter
    * Sets the enPassantPiece field as passed coordinates
    */
   public void setEnPassantPiece(int[] enPassantPiece) {
      this.enPassantPiece = enPassantPiece;
   }

   /**
    * A method to reset the board
    * This sets the board according to the Rules of Chess
    */
   public void boardReset() {

      boardArray[0][0] = new Square(new Rook(Color.BLACK));
      boardArray[0][7] = new Square(new Rook(Color.BLACK));
      boardArray[0][1] = new Square(new Knight(Color.BLACK));
      boardArray[0][6] = new Square(new Knight(Color.BLACK));
      boardArray[0][2] = new Square(new Bishop(Color.BLACK));
      boardArray[0][5] = new Square(new Bishop(Color.BLACK));
      boardArray[0][3] = new Square(new Queen(Color.BLACK));
      boardArray[0][4] = new Square(new King(Color.BLACK));

      boardArray[7][0] = new Square(new Rook(Color.WHITE));
      boardArray[7][7] = new Square(new Rook(Color.WHITE));
      boardArray[7][1] = new Square(new Knight(Color.WHITE));
      boardArray[7][6] = new Square(new Knight(Color.WHITE));
      boardArray[7][2] = new Square(new Bishop(Color.WHITE));
      boardArray[7][5] = new Square(new Bishop(Color.WHITE));
      boardArray[7][3] = new Square(new Queen(Color.WHITE));
      boardArray[7][4] = new Square(new King(Color.WHITE));

      for (int i = 0; i <= 7; i ++) {
         boardArray[1][i] = new Square(new Pawn(Color.BLACK));
         boardArray[6][i] = new Square(new Pawn(Color.WHITE));
      }

      for (int i = 2; i <= 5; i++) {
         for (int j = 0; j <= 7; j++) {
            boardArray[i][j] = new Square();
         }
      }
   }

   /**
    * A method to clear the board
    */
   public void clearBoard() {
      for (int i = 0; i <= 7; i++) {
         for (int j =0; j <= 7; j++) {
            boardArray[i][j] = new Square();
         }
      }
   }

   /**
    * A method to print the board of the command line
    * <p></p>
    * Only used for testing and debugging
    */
   public void printBoard() { //for debugging only
      String[][] array = new String[8][8];
      for (int i = 0; i <= 7; i++) {
         for (int j = 0; j <= 7; j++) {
            if (!boardArray[i][j].isHasPiece()) {
               array[i][j] = "     ";
            } else {
               array[i][j] = boardArray[i][j].getPiece().getPieceType().name();
            }
         }
      }
      System.out.println(Arrays.deepToString(array).replace("],", "]\n").replace("[[", " [").replace("]]","]"));
   }

   /**
    * Clone method
    * from Cloneable interface
    * @return Board exact clone of original
    */
   @Override
   public Board clone() {
      try {
         Board clone = (Board) super.clone();
         // TODO: copy mutable state here, so the clone can't change the internals of the original
         return clone;
      } catch (CloneNotSupportedException e) {
         throw new AssertionError();
      }
   }
}