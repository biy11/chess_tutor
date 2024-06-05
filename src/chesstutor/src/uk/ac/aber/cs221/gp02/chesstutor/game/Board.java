/*
 * @(#) Board.java 0.6 2023/05/02
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
 * @version 0.6 updated code variables from x to row and updated i to be index
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Board { //Marks the class as an XML element

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
    * @param row The x coordinate
    * @param col The y coordinate
    */
   public void setSquare(Square square, int row, int col){
      boardArray[row][col] = square;
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

      for (int index = 0; index <= 7; index++) {
         boardArray[1][index] = new Square(new Pawn(Color.BLACK));
         boardArray[6][index] = new Square(new Pawn(Color.WHITE));
      }

      for (int index = 2; index <= 5; index++) {
         for (int secondIndex = 0; secondIndex <= 7; secondIndex++) {
            boardArray[index][secondIndex] = new Square();
         }
      }
   }

   /**
    * A method to clear the board
    */
   public void clearBoard() {
      for (int index = 0; index <= 7; index++) {
         for (int secondIndex =0; secondIndex <= 7; secondIndex++) {
            boardArray[index][secondIndex] = new Square();
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
      for (int index = 0; index <= 7; index++) {
         for (int secondIndex = 0; secondIndex <= 7; secondIndex++) {
            if (!boardArray[index][secondIndex].hasPiece()) {
               array[index][secondIndex] = "     ";
            } else {
               array[index][secondIndex] = boardArray[index][secondIndex].getPiece().getPieceType().name();
            }
         }
      }
      System.out.println(Arrays.deepToString(array).replace("],", "]\n").replace("[[", " [").replace("]]","]"));
   }

   /**
    * Clone method
    * @return Board exact clone of original
    */
   public Board cloneBoard() {
      Board newBoard = new Board();
      newBoard.clearBoard();
      for (int index = 0; index <= 7; index++) {
         for (int secondIndex = 0; secondIndex <= 7; secondIndex++) {
            newBoard.getBoardArray()[index][secondIndex] = boardArray[index][secondIndex];
         }
      }
      return newBoard;
   }
}