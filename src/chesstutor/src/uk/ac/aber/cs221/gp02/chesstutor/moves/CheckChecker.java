/*
 * @(#) CheckChecker.java 1.2 2023/05/04
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.gp02.chesstutor.moves;

import uk.ac.aber.cs221.gp02.chesstutor.game.*;
import uk.ac.aber.cs221.gp02.chesstutor.moves.specialmoves.EnPassant;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.Piece;
import uk.ac.aber.cs221.gp02.chesstutor.util.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for checking if a king is in check
 *
 * @author Micah [mib60]
 * @author Michael [mjs36]
 * @version 0.1 initial development
 * @version 0.2 javadoc added and logic corrected by mjs36
 * @version 0.3 new version for check
 * @version 0.4 added CheckMate checker
 * @version 0.5 added to main code by mjs36
 * @version 0.6 changed to use Board instead of Square[][]
 * @version 0.7 as changed from Square[][], board now needed a Clone method
 * @version 0.8 fixed checkMateChecker not accounting for some scenarios
 * @version 0.9 Added removeMovesThatEndangerKing
 * @version 1.0 Fixed warnings
 * @version 1.1 Fixed CheckMate
 * @version 1.2 Added comments.
 * @see Board
 */
public class CheckChecker {

   /**
    * Returns a list of coordinates of pieces that are attacking a square
    *
    * @param board Board
    * @param row X coordinate of the square
    * @param col Y coordinate of the square
    * @return List<int[]> Returns a list of row col coordinates of pieces that are attacking the square
    */
   public static List<int[]> checkChecker(Board board, int row, int col, Color attackingColor){
      Square[][] boardArray = board.getBoardArray();
      List<int[]> coordinateArray = new ArrayList<>();

      //iterate through pieces on the board
      for(int new_row = 0; new_row < 8; new_row++){
         for(int new_col = 0; new_col < 8; new_col++){

            //Get the current piece
            Piece currentPiece = boardArray[new_row][new_col].getPiece();

            //Skip over the piece if it is null or the same colour as the king
            if(currentPiece == null || !(currentPiece.getPieceColor() == attackingColor)) continue;

            //Get the possible moves for the current piece
            List<int[]> currentPieceMoves = currentPiece.getValidMoves(board, currentPiece, new_row, new_col);

            //If any of the moves will place the piece on the king it is in check
            for(int[] currentMove:currentPieceMoves){
               if(currentMove[0] == row && currentMove[1] == col) coordinateArray.add(new int[]{new_row, new_col});
            }
         }
      }

      return coordinateArray;
   }

   /**
    * Checks whether a king is in check mate
    *
    * @param board The board
    * @param row The X position of the king
    * @param col The Y position of the king
    * @return Returns true if in checkmate, false if not. Will return false if the square is not a king
    */
   public static boolean checkMateChecker(Board board, int row, int col){
      //Color is assigned depending on piece at row and col coordinates on the board
      Color attackingColor = board.getBoardArray()[row][col].getPiece().getEnemyPieceColor();

      Piece king = board.getBoardArray()[row][col].getPiece();

      //check if method is called on a square that contains the King, if not, return false
      if(king == null || !(king.getPieceType() == Type.KING)){
         //System.out.println("checkMateChecker was called on a square that is not a king and returned false");
         return false;
      }

      //creates a local copy, not referencing the main board
      //Square[][] simBoardArray = board.getBoardArray().clone();
      Board simBoard = board.cloneBoard();
      Square[][] simBoardArray;

      //Get the pieces attacking the king and the possible moves the king can make
      List<int[]> attackers = checkChecker(simBoard, row, col, attackingColor);
      List<int[]> possibleKingMoves = king.getValidMoves(board, king, row, col);

      //If there is more than one attacker the king is in double check and has to move to escape
      if(attackers.size() >= 2){

         //Iterate through each move the king can make
         for(int[] currentMove:possibleKingMoves){

            //Get the current square and create a new array to simulate the moves in
            simBoard = board.cloneBoard();
            simBoardArray = simBoard.getBoardArray();

            //Get the square of the king in the simulation
            Square kingSquare = simBoardArray[row][col];

            //Get the original state of the square we will simulate the king moving to
            Square oldSquare = simBoardArray[currentMove[0]][currentMove[1]];

            //Move the king in the simulation
            simBoardArray[currentMove[0]][currentMove[1]] = new Square(king);
            simBoardArray[row][col] = new Square();

            //If checkChecker returns an empty list, the current move gets the king out of check, so return false
            if(checkChecker(simBoard, currentMove[0], currentMove[1], attackingColor).isEmpty()){
               System.out.println("Found a move king can make to get out of check");

               //Return the squares to their old state
               simBoardArray[currentMove[0]][currentMove[1]] = oldSquare;
               simBoardArray[row][col] = kingSquare;

               return false;
            }
            //Return the squares to their old state
            simBoardArray[currentMove[0]][currentMove[1]] = oldSquare;
            simBoardArray[row][col] = kingSquare;
         }

         return true; //Otherwise, king is in checkmate so return true
      }


      //Otherwise, if the king is in check and not double-check
      else if(attackers.size() == 1){
         //Check all of king's possible moves to see if any of them take him out of check

         //Iterate through each move the king can make

         //Iterate through each move the king can make
         for(int[] currentMove:possibleKingMoves){

            //Get the current square and create a new array to simulate the moves in
            simBoard = board.cloneBoard();
            simBoardArray = simBoard.getBoardArray();

            //Get the square of the king in the simulation
            Square kingSquare = simBoardArray[row][col];

            //Get the original state of the square we will simulate the king moving to
            Square oldSquare = simBoardArray[currentMove[0]][currentMove[1]];

            //Move the king in the simulation
            simBoardArray[currentMove[0]][currentMove[1]] = new Square(king);
            simBoardArray[row][col] = new Square();

            //If checkChecker returns an empty list, the current move gets the king out of check, so return false
            if(checkChecker(simBoard, currentMove[0], currentMove[1], attackingColor).isEmpty()){
               System.out.println("Found a move king can make to get out of check");

               //Return the squares to their old state
               simBoardArray[currentMove[0]][currentMove[1]] = oldSquare;
               simBoardArray[row][col] = kingSquare;

               return false;
            }
            //Return the squares to their old state
            simBoardArray[currentMove[0]][currentMove[1]] = oldSquare;
            simBoardArray[row][col] = kingSquare;
         }



         //Iterate through pieces
         for(int new_row = 0; new_row < 8; new_row++){
            for(int new_col = 0; new_col < 8; new_col++){

               //Get the current square and create a new array to simulate the moves in
               simBoard = board.cloneBoard();
               simBoardArray = simBoard.getBoardArray();
               Square currentSquare = simBoardArray[new_row][new_col];

               //If current square is empty or occupied by a friendly piece skip over it
               if(!currentSquare.hasPiece()) continue;
               if(currentSquare.getPiece().getPieceColor() == attackingColor) continue;

               //Get the piece's moves and the original square of the piece
               List<int[]> moves = currentSquare.getPiece().getValidMoves(simBoard, currentSquare.getPiece(), new_row, new_col);
               Square oldCurrentSquare = simBoardArray[new_row][new_col];

               for(int[] newMove:moves){

                  //Get the X and Y coordinates of the new move
                  int newMoveX = newMove[0];
                  int newMoveY = newMove[1];

                  //Get the square the piece would be moved to
                  Square oldNewSquare = simBoardArray[newMoveX][newMoveY];

                  //Move the piece to the current move and set the old position to empty
                  simBoardArray[newMoveX][newMoveY] = oldCurrentSquare;
                  simBoardArray[new_row][new_col] = new Square();


                  List<int[]> newAttackers;

                  //If the king has moved, get the attackers of the new square he has moved to
                  if(currentSquare.getPiece().getPieceType() == Type.KING){
                     newAttackers = checkChecker(simBoard, newMoveX, newMoveY, attackingColor);
                  }

                  //Otherwise, get the attackers of the square if the king has not moved
                  else{
                     newAttackers = checkChecker(simBoard, row, col, attackingColor);
                  }

                  //Set the pieces in the simulation board to their original states for the next move
                  simBoardArray[new_row][new_col] = oldCurrentSquare;
                  simBoardArray[newMoveX][newMoveY] = oldNewSquare;

                  //If there are no attackers now, the king is not in checkmate
                  if(newAttackers.isEmpty()){
                     System.out.println("King can escape check by moving piece at "+new_row+","+new_col+" to "+newMoveX+","+newMoveY);
                     return false;
                  }
               }
            }
         }

         System.out.println("king is in checkmate");
         return true;
      }

      else return false;
   }

   /**
    * Function that removes a move from a list of moves if the move
    * endangers the friendly king (places it in check)
    * @param board The board
    * @param piece The piece with the moves
    * @param row The row of the piece
    * @param col The column of the piece
    * @param returnArray The array of moves
    */
   public static void removeMovesThatEndangerKing(Board board, Piece piece, int row, int col, List<int[]> returnArray){
      //Code to remove a valid move if it places the friendly king in check

      int kingX = -1;
      int kingY = -1;

      //Get the location of the friendly king
      outer:
      for(int new_row = 0; new_row < 8; new_row++){
         for(int new_col = 0; new_col < 8; new_col++){

            Square square = board.getBoardArray()[new_col][new_row];
            Piece possibleKing = square.getPiece();

            if(possibleKing != null && possibleKing.getPieceType() == Type.KING && possibleKing.getPieceColor() == piece.getPieceColor()){
               kingX = new_col;
               kingY = new_row;
               break outer;
            }
         }
      }


      if(kingX == -1){
         return;
      }

      //Create a new arraylist with the moves to be removed to avoid concurrent modification exception
      List<int[]> movesToBeRemoved = new ArrayList<>();

      //If the piece being moved is a king call inDangerChecker
      if(piece.getPieceType() == Type.KING){
         inDangerChecker(board, kingX, kingY, piece.getEnemyPieceColor(), returnArray);
      }

      //The piece being moved is not the king
      else{
         for(int[] currentMove:returnArray){


            Board simBoard = board.cloneBoard();
            int newMoveX = currentMove[0];
            int newMoveY = currentMove[1];

            Square newMovePiece = simBoard.getBoardArray()[newMoveX][newMoveY];

            simBoard.getBoardArray()[newMoveX][newMoveY] = simBoard.getBoardArray()[row][col];
            simBoard.getBoardArray()[row][col] = new Square();

            if(!CheckChecker.checkChecker(simBoard, kingX, kingY, board.getBoardArray()[kingX][kingY].getPiece().getEnemyPieceColor()).isEmpty()){
               movesToBeRemoved.add(currentMove);
               //System.out.println("Removing a move because it endangers the king"); //Print statement for debugging purposes
            }

            simBoard.getBoardArray()[row][col] = simBoard.getBoardArray()[newMoveX][newMoveY];
            simBoard.getBoardArray()[newMoveX][newMoveY] = newMovePiece;
         }
      }

      returnArray.removeAll(movesToBeRemoved);
   }


   /**
    * Function to remove possible king moves if they place it in check
    * @param board Board
    * @param attackingColor Attacking colour of the king
    * @param returnArray The array of moves
    */
   public static void inDangerChecker(Board board, int row, int col, Color attackingColor, List<int[]> returnArray) {

      List<int[]> thingsToBeRemoved = new ArrayList<>();

      Square[][] boardArray = board.getBoardArray();

      //Iterate through the moves the king can make and if it places him in check remove it
      for(int[] currentKingMove:returnArray){
         Square oldPiece = boardArray[currentKingMove[0]][currentKingMove[1]];
         boardArray[currentKingMove[0]][currentKingMove[1]] = boardArray[row][col];
         boardArray[row][col] = new Square();

         if(!CheckChecker.checkChecker(board, currentKingMove[0], currentKingMove[1], attackingColor).isEmpty()){
            thingsToBeRemoved.add(currentKingMove);
         }

         boardArray[row][col] = boardArray[currentKingMove[0]][currentKingMove[1]];
         boardArray[currentKingMove[0]][currentKingMove[1]] = oldPiece;
      }

      returnArray.removeAll(thingsToBeRemoved);
   }
}
