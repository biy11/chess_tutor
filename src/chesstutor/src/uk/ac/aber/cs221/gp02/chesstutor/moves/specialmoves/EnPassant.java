/*
 * @(#) enPassant.java 0.8 2023/05/04
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.gp02.chesstutor.moves.specialmoves;

import uk.ac.aber.cs221.gp02.chesstutor.game.*;
import uk.ac.aber.cs221.gp02.chesstutor.moves.CheckChecker;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.*;
import uk.ac.aber.cs221.gp02.chesstutor.util.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that holds the getMoves method for enPassant
 *
 * @author Michael [mjs36]
 * @version 0.1 initial development
 * @version 0.2 moved to using two List solution
 * @version 0.3 changed to use enPassantList and getRoundCount
 * @version 0.4 changed to use Board instead of Game class for enPassant - parameter now uses Board instead of Square[][]
 * @version 0.5 updated code variables from x to row and updated i to be index
 * @version 0.6 changed to use List<int[]> instead of List<List<int[]>>
 * @version 0.7 Added check to remove move if it endangers the king
 * @version 0.8 Fixed en passant checkmate problem
 * @see Pawn
 */
public class EnPassant {

   /**
    * A method to return a List of valid move coordinates
    * <p></p>
    * It is assumed only a PAWN can be passed to the method
    * <p></p>
    * Returns one list which contains two int[]
    * First array contains the moves - where the Pawn will move two
    * Second array contains the taking Pawns - what Pawn will be taken
    * <p></p>
    * Returned in format of {[6,4],[5,3]}
    *
    * @param board Board
    * @param piece Piece
    * @param row int
    * @param col int
    * @return List<int[]>
    */
   public static List<int[]> getMoves(Board board, Piece piece, int row, int col) {

      List<int[]> returnArray = getMovesWithoutRemoving(board, piece, row, col);

      //Remove moves that endanger the king
      if(returnArray.isEmpty()) return returnArray;
      List<int[]> checkCheckerReturnArray = new ArrayList<>();
      checkCheckerReturnArray.add(returnArray.get(0));
      removeEnPassantMoves(board, piece, row, col, checkCheckerReturnArray);
      if(checkCheckerReturnArray.isEmpty()) return new ArrayList<>();

      return returnArray;
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
   private static void removeEnPassantMoves(Board board, Piece piece, int row, int col, List<int[]> returnArray){
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
         CheckChecker.inDangerChecker(board, kingX, kingY, piece.getEnemyPieceColor(), returnArray);
      }

      //The piece being moved is not the king
      else{
         for(int[] currentMove:returnArray){

            List<int[]> enPassantMoves = getMovesWithoutRemoving(board, piece, row, col);

            if(enPassantMoves.get(0)[0] == currentMove[0] && enPassantMoves.get(0)[1] == currentMove[1]){

               List<int[]> attackingPieces = CheckChecker.checkChecker(board, row, col, piece.getEnemyPieceColor());

               if(attackingPieces.isEmpty()) continue;

               if(attackingPieces.get(0)[0] == enPassantMoves.get(1)[0] && attackingPieces.get(0)[1] == enPassantMoves.get(1)[1]){
                  continue;
               }
            }

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
    * A method to return a List of valid move coordinates
    * <p></p>
    * It is assumed only a PAWN can be passed to the method
    * <p></p>
    * Returns one list which contains two int[]
    * First array contains the moves - where the Pawn will move two
    * Second array contains the taking Pawns - what Pawn will be taken
    * <p></p>
    * This function does not remove moves that endanger the king
    * Returned in format of {[6,4],[5,3]}
    *
    * @param board Board
    * @param piece Piece
    * @param row int
    * @param col int
    * @return List<int[]>
    */
   private static List<int[]> getMovesWithoutRemoving(Board board, Piece piece, int row, int col) {
      Square[][] boardArray = board.getBoardArray();
      List<int[]> returnArray = new ArrayList<>();
      int[] move = {-1,-1};
      int[] take = {-1,-1};

      //List 0 is move
      //List 1 is taking piece

      int direction = (piece.getPieceColor() == Color.WHITE) ? -1 : 1;
      int newRow = row + direction;

      int attackNeg = col - 1; //coordinate for diagonal left (or right if we are black)
      int attackPos = col + 1; //coordinate for diagonal right (or left if we are black)

      if (newRow >= 0 && newRow < 8) { //check in range
         if (attackNeg >= 0 && attackNeg < 8) { //check in range
            if (boardArray[row][attackNeg].hasPiece() && boardArray[row][attackNeg].getPiece().getPieceColor() == piece.getEnemyPieceColor()
                    && !boardArray[newRow][attackNeg].hasPiece() && boardArray[row][attackNeg].getPiece().getPieceType() == Type.PAWN) {
               int[] availablePiece = board.getEnPassantPiece();
               if (row == availablePiece[0] && attackNeg == availablePiece[1]) {
                  move = new int[]{newRow, attackNeg};
                  take = new int[]{row, attackNeg};
               }
            }
         }
         if (attackPos >= 0 && attackPos < 8) { //check in range
            if (boardArray[row][attackPos].hasPiece() && boardArray[row][attackPos].getPiece().getPieceColor() == piece.getEnemyPieceColor()
                    && !boardArray[newRow][attackPos].hasPiece() && boardArray[row][attackPos].getPiece().getPieceType() == Type.PAWN) {
               int[] availablePiece = board.getEnPassantPiece();
               if (row == availablePiece[0] && attackPos == availablePiece[1]) {
                  move = new int[]{newRow, attackPos};
                  take = new int[]{row, attackPos};
               }
            }
         }
      }
      if (!(move[0] == -1 && move[1] == -1)) { //ignore warning
         returnArray.add(move);
         returnArray.add(take);
      }

      return returnArray;
   }
}
