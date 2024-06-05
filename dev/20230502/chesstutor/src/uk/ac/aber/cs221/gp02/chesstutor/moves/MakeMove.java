/*
 * @(#) makeMove.java 1.2 2023/03/06
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.gp02.chesstutor.moves;

import uk.ac.aber.cs221.gp02.chesstutor.game.*;
import uk.ac.aber.cs221.gp02.chesstutor.moves.specialmoves.Castle;
import uk.ac.aber.cs221.gp02.chesstutor.moves.specialmoves.EnPassant;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.Piece;
import uk.ac.aber.cs221.gp02.chesstutor.util.Type;
import java.util.List;

/**
 * A class that holds the movePiece method
 *
 * @author Michael [mjs36]
 * @author Micah [mib60]
 * @version 0.1 initial development
 * @version 0.2 added Pawn specific checks
 * @version 0.3 changed to use enPassantList and getRoundCount
 * @version 0.4 changed to use Board instead of Square[][] & uses Board to hold enPassant instead of Game
 * @version 0.5 added methods specific to enPassant and Castling
 * @version 0.6 now adds taken Piece to players list
 * @version 0.7 fixed bug when moving onto square with no piece - was always trying to add to takenPiece list
 * @version 0.8 updated code variables from x to row and updated i to be index
 * @version 0.9 Updated regular movePiece to call castling movePiece when necessary
 * @version 1.0 Updated regular movePiece to call enPassant movePiece when necessary
 * @version 1.1 fix enPassant, adding wrong coordinates to enPassantPiece
 * @version 1.2 changed enPassant to use List<int[]>
 */
public class MakeMove {

   /**
    * A method to move a piece on the chessboard
    *
    * @param board Board
    * @param pieceRow int
    * @param pieceCol int
    * @param targetRow int
    * @param targetCol int
    */
   public static void movePiece(Board board, Player player, int pieceRow, int pieceCol, int targetRow, int targetCol) {

      Square[][] boardArray = board.getBoardArray();

      Piece piece = board.getBoardArray()[pieceRow][pieceCol].getPiece();

      //Castling
      if(piece != null && piece.getPieceType() == Type.KING){
         List<int[]> possibleCastles = Castle.canCastle(board, piece, pieceRow, pieceCol);

         //Current castle contains:
         //0: new king location
         //1: current rook location
         //2: new rook location
         for(int[] currentCastle:possibleCastles){
            int moveX = currentCastle[0];

            if(targetCol == moveX && targetRow == pieceRow){
               int[] pcrow = {pieceRow, pieceRow};
               int[] targetrow = {pieceRow, pieceRow};

               int[] pccol = {pieceCol, currentCastle[1]};
               int[] targetcol = {targetCol, currentCastle[2]};

               movePiece(board, pcrow, pccol, targetrow, targetcol);
               return;
            }
         }
      }

      if (piece != null && piece.getPieceType() == Type.PAWN) {
         List<int[]> moves = EnPassant.getMoves(board, piece, pieceRow, pieceCol);

         int[] targetMove = {targetRow, targetCol};

         if (!moves.isEmpty() && (targetMove[0] == moves.get(0)[0] & targetMove[1] == moves.get(0)[1])) {

            int[] targetOne = {targetRow, moves.get(1)[0]};
            int[] targetTwo = {targetCol, moves.get(1)[1]};
            movePiece(board, player, pieceRow, pieceCol, targetOne, targetTwo);
            return;
         }
      }

      clearEnPassantPiece(board);

      boolean movedDouble = (pieceRow == 1 && targetRow == 3) || (pieceRow == 6 && targetRow == 4);
      //if movedDouble, as Pawn can only move double on first move don't need to check !hasMoved,
      if (movedDouble && boardArray[pieceRow][pieceCol].getPiece().getPieceType() == Type.PAWN) {
         board.setEnPassantPiece(new int[]{targetRow, targetCol});
      }

      if (boardArray[targetRow][targetCol].hasPiece()) {
         addTakenPiece(player, boardArray[targetRow][targetCol].getPiece());
      }

      boardArray[targetRow][targetCol] = boardArray[pieceRow][pieceCol];
      boardArray[pieceRow][pieceCol] = new Square();
      boardArray[targetRow][targetCol].getPiece().setHasMoved();
   }

   /**
    * A method to move a piece on the chessboard
    * This method works specifically for enPassant
    *
    * @param board Board
    * @param pieceRow int
    * @param pieceCol int
    * @param targetRow int[] 0 is empty Square 1 is captured Pawn
    * @param targetCol int[] 0 is empty Square 1 is captured Pawn
    */
   private static void movePiece(Board board, Player player, int pieceRow, int pieceCol, int[] targetRow, int[] targetCol) {
      clearEnPassantPiece(board);

      Square[][] boardArray = board.getBoardArray();

      addTakenPiece(player, boardArray[targetRow[1]][targetCol[1]].getPiece());

      boardArray[targetRow[0]][targetCol[0]] = boardArray[pieceRow][pieceCol];
      boardArray[pieceRow][pieceCol] = new Square();
      boardArray[targetRow[1]][targetCol[1]] = new Square();
      boardArray[targetRow[0]][targetCol[0]].getPiece().setHasMoved();
   }

   /**
    * A method to move a piece on the chessboard
    * This method works specifically for Castling
    *
    * @param board Board
    * @param pieceRow int[] 0 is King 1 is Rook
    * @param pieceCol int[] 0 is King 1 is Rook
    * @param targetRow int[] 0 is King 1 is Rook
    * @param targetCol int[] 0 is King 1 is Rook
    */
   private static void movePiece(Board board, int[] pieceRow, int[] pieceCol, int[] targetRow, int[] targetCol) {
      clearEnPassantPiece(board);

      Square[][] boardArray = board.getBoardArray();

      //move king
      boardArray[targetRow[0]][targetCol[0]] = boardArray[pieceRow[0]][pieceCol[0]];
      boardArray[pieceRow[0]][pieceCol[0]] = new Square();

      //move rook
      boardArray[targetRow[1]][targetCol[1]] = boardArray[pieceRow[1]][pieceCol[1]];
      boardArray[pieceRow[1]][pieceCol[1]] = new Square();

      //set hasMoved
      boardArray[targetRow[0]][targetCol[0]].getPiece().setHasMoved();
      boardArray[targetRow[1]][targetCol[1]].getPiece().setHasMoved();

   }

   /**
    * A method to reset the enPassantPiece variable
    *
    * @param board Board
    */
   private static void clearEnPassantPiece(Board board) {
      board.setEnPassantPiece(new int[]{-1, -1});
   }

   /**
    * A method to add the taken enemy piece to the players list
    *
    * @param player Player
    * @param piece Piece
    */
   private static void addTakenPiece(Player player, Piece piece) {
      player.addTakenPieces(piece);
   }

}
