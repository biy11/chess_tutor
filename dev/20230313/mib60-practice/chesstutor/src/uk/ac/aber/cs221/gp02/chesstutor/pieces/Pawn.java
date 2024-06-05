/*
 * @(#) Knight.java 0.2 2023/03/05
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.gp02.chesstutor.pieces;

import uk.ac.aber.cs221.gp02.chesstutor.game.Square;
import uk.ac.aber.cs221.gp02.chesstutor.util.Color;
import uk.ac.aber.cs221.gp02.chesstutor.util.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents the Pawn piece
 * Inherits the abstract class Piece
 *
 * @author Michael [mjs36]
 * @version 0.1 initial development
 * @version 0.2 condensed code with addValidMove method
 * @see Piece
 */
public class Pawn extends Piece {

   private boolean firstMove;
   private boolean enPassantAvailable;

   /**
    * Constructor
    * Creates new Knight object - calls super, Piece, constructor
    * Sets ID as KNIGHT of the enum Type
    *
    * @param color Color of piece
    */
   public Pawn(Color color) {
      super(color);
      this.ID = Type.PAWN;
      firstMove = true;
      enPassantAvailable = false;
   }

   public void movedPawn() {
      this.firstMove = false;
   }

   public boolean isEnPassantAvailable() {
      return enPassantAvailable;
   }

   public void setEnPassantAvailable() { //if the player moves the piece 2 places, set this to true
      this.enPassantAvailable = true;
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

      boolean noJump = false;

      //finds the direction the pawn can go, down or up
      int direction = (piece.getPieceColor() == Color.WHITE) ? -1 : 1; //ternary operator - learnt in C module - ? TRUE : FALSE
      //adds to the x value with the new direction
      int newX = x + direction;
      if (newX >= 0 && newX < 8 && !boardArray[newX][y].isHasPiece()) { //checks if in range and there is no piece on that square
         int[] move = {newX, y};
         returnArray.add(move); //adds to the arrayList
      } else if (boardArray[newX][y].isHasPiece()) {
         noJump = true;
      }

      if (firstMove) {
         int newNewX = newX + direction; //adds another forward move
         if (newNewX >= 0 && newNewX < 8 && !boardArray[newNewX][y].isHasPiece() && !noJump) { //checks if in range and there is no piece on that square
            int[] move = {newNewX, y};
            returnArray.add(move); //adds to the arrayList
         }
      }

      int attackNeg = y - 1; //coordinate for diagonal left (or right if we are black)
      int attackPos = y + 1; //coordinate for diagonal right (or left if we are black)

      if (newX >= 0 && newX < 8) { //check in range
         if (attackNeg >= 0 && attackNeg < 8) { //check in range
            //if square has piece AND piece is on opposing team - add to valid moves
            if (boardArray[newX][attackNeg].isHasPiece() && boardArray[newX][attackNeg].getPiece().getPieceColor() == piece.getEnemyPieceColor()) {
               int[] move = {newX, attackNeg};
               returnArray.add(move);
            }
            if (boardArray[x][attackNeg].isHasPiece() && boardArray[x][attackNeg].getPiece().getPieceColor() == piece.getEnemyPieceColor()
                    && !boardArray[newX][attackNeg].isHasPiece() && boardArray[x][attackNeg].getPiece().getPieceType() == Type.PAWN) {
               Pawn tempPawn = (Pawn)boardArray[x][attackNeg].getPiece();
               if (tempPawn.isEnPassantAvailable()) {
                  int[] move = {newX, attackNeg};
                  returnArray.add(move);
               }
            }
         }
         if (attackPos >= 0 && attackPos < 8) { //check in range
            //if square has piece AND piece is on opposing team - add to valid moves
            if (boardArray[newX][attackPos].isHasPiece() && boardArray[newX][attackPos].getPiece().getPieceColor() == piece.getEnemyPieceColor()) {
               int[] move = {newX, attackPos};
               returnArray.add(move);
            }
            if (boardArray[x][attackPos].isHasPiece() && boardArray[x][attackPos].getPiece().getPieceColor() == piece.getEnemyPieceColor()
            && !boardArray[newX][attackPos].isHasPiece() && boardArray[x][attackPos].getPiece().getPieceType() == Type.PAWN) {
               Pawn tempPawn = (Pawn)boardArray[x][attackPos].getPiece();
               if (tempPawn.isEnPassantAvailable()) {
                  int[] move = {newX, attackPos};
                  returnArray.add(move);
               }
            }
         }
      }

      return returnArray; //if i take the enPassant move, firstly, how does the program now that was the enPassant?
      //secondly, how does it remove the taken Pawn
   }

}
