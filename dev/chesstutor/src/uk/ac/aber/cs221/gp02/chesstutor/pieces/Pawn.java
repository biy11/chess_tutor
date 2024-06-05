/*
 * @(#) Pawn.java 1.1 2023/04/29
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
 * A class that represents the Pawn piece
 * Inherits the abstract class Piece
 *
 * @author Michael [mjs36]
 * @version 0.1 initial development
 * @version 0.2 condensed code with addValidMove method
 * @version 0.3 developed enPassant
 * @version 0.4 moved enPassant to specialmoves package
 * @version 0.5 added javadoc for enPassant setter and getter
 * @version 0.6 removed all enPassant related variables and methods - now uses a static list in Game to store
 * valid enPassant
 * @version 0.7 Added XML annotations and zero argument constructor - mib60
 * @version 0.8 changed to use Board instead of Square[][]
 * @version 0.9 updated code variables from x to row and updated i to be index
 * @version 1.0 Added getPossibleMoves - mib60
 * @version 1.1 added return to getValidMoves if Pawn at end of board
 * @see Piece
 */

//Marks the class as an XML element
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Pawn extends Piece {

   /**
    * Constructor
    * Creates new Pawn object - calls super, Piece, constructor
    * Sets ID as PAWN of the enum Type
    * <p></p>
    * Sets enPassantAvailable as false
    *
    * @param color Color of piece
    */
   public Pawn(Color color) {
      super(color);
      this.ID = Type.PAWN;
   }

   /**
    * Zero argument constructor used by the XML parser
    */
   public Pawn(){}

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

      returnArray = new ArrayList<int[]>();

      if (row == 0 || row == 7) return returnArray; //there are no valid moves;

      boolean noJump = false;

      //finds the direction the pawn can go, down or up
      int direction = (piece.getPieceColor() == Color.WHITE) ? -1 : 1; //ternary operator - learnt in C module - ? TRUE : FALSE
      //adds to the x value with the new direction
      int newRow = row + direction;
      if (newRow >= 0 && newRow < 8 && !boardArray[newRow][col].hasPiece()) { //checks if in range and there is no piece on that square
         int[] move = {newRow, col};
         returnArray.add(move); //adds to the arrayList
      } else if (boardArray[newRow][col].hasPiece()) {
         noJump = true;
      }

      if (!hasMoved) {
         int newNewRow = newRow + direction; //adds another forward move
         if (newNewRow >= 0 && newNewRow < 8 && !boardArray[newNewRow][col].hasPiece() && !noJump) { //checks if in range and there is no piece on that square
            int[] move = {newNewRow, col};
            returnArray.add(move); //adds to the arrayList
         }
      }

      int attackNeg = col - 1; //coordinate for diagonal left (or right if we are black)
      int attackPos = col + 1; //coordinate for diagonal right (or left if we are black)

      if (newRow >= 0 && newRow < 8) { //check in range
         if (attackNeg >= 0 && attackNeg < 8) { //check in range
            //if square has piece AND piece is on opposing team - add to valid moves
            if (boardArray[newRow][attackNeg].hasPiece() && boardArray[newRow][attackNeg].getPiece().getPieceColor() == piece.getEnemyPieceColor()) {
               int[] move = {newRow, attackNeg};
               returnArray.add(move);
            }
         }
         if (attackPos >= 0 && attackPos < 8) { //check in range
            //if square has piece AND piece is on opposing team - add to valid moves
            if (boardArray[newRow][attackPos].hasPiece() && boardArray[newRow][attackPos].getPiece().getPieceColor() == piece.getEnemyPieceColor()) {
               int[] move = {newRow, attackPos};
               returnArray.add(move);
            }
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
