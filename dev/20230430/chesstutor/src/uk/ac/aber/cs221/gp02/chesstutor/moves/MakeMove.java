/*
 * @(#) makeMove.java 0.8 2023/03/06
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.gp02.chesstutor.moves;

import uk.ac.aber.cs221.gp02.chesstutor.game.*;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.Piece;
import uk.ac.aber.cs221.gp02.chesstutor.util.Type;

/**
 * A class that holds the movePiece method
 *
 * @author Michael [mjs36]
 * @version 0.1 initial development
 * @version 0.2 added Pawn specific checks
 * @version 0.3 changed to use enPassantList and getRoundCount
 * @version 0.4 changed to use Board instead of Square[][] & uses Board to hold enPassant instead of Game
 * @version 0.5 added methods specific to enPassant and Castling
 * @version 0.6 now adds taken Piece to players list
 * @version 0.7 fixed bug when moving onto square with no piece - was alawys trying to add to takenPiece list
 * @version 0.8 updated code variables from x to row and updated i to be index
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
      clearEnPassantPiece(board);

      Square[][] boardArray = board.getBoardArray();

      boolean movedDouble = (pieceRow == 1 && targetRow == 3) || (pieceRow == 6 && targetRow == 4);
      //if movedDouble, as Pawn can only move double on first move don't need to check !hasMoved,
      if (movedDouble && boardArray[pieceRow][pieceCol].getPiece().getPieceType() == Type.PAWN) {
         board.setEnPassantPiece(new int[]{pieceRow, pieceCol});
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
   public static void movePiece(Board board, Player player, int pieceRow, int pieceCol, int[] targetRow, int[] targetCol) {
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
   public static void movePiece(Board board, Player player, int[] pieceRow, int[] pieceCol, int[] targetRow, int[] targetCol) {
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
