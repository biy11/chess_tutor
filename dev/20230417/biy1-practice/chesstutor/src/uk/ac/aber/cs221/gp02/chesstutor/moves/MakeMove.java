/*
 * @(#) makeMove.java 0.7 2023/03/06
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
 */
public class MakeMove {

   /**
    * A method to move a piece on the chessboard
    *
    * @param board Board
    * @param pieceX int
    * @param pieceY int
    * @param targetX int
    * @param targetY int
    */
   public static void movePiece(Board board, Player player, int pieceX, int pieceY, int targetX, int targetY) {
      clearEnPassantPiece(board);

      Square[][] boardArray = board.getBoardArray();

      boolean movedDouble = (pieceX == 1 && targetX == 3) || (pieceX == 6 && targetX == 4);
      //if movedDouble, as Pawn can only move double on first move don't need to check !hasMoved,
      if (movedDouble && boardArray[pieceX][pieceY].getPiece().getPieceType() == Type.PAWN) {
         board.setEnPassantPiece(new int[]{pieceX, pieceY});
      }

      if (boardArray[targetX][targetY].isHasPiece()) {
         addTakenPiece(player, boardArray[targetX][targetY].getPiece());
      }

      boardArray[targetX][targetY] = boardArray[pieceX][pieceY];
      boardArray[pieceX][pieceY] = new Square();
      boardArray[targetX][targetY].getPiece().setHasMoved();
   }

   /**
    * A method to move a piece on the chessboard
    * This method works specifically for enPassant
    *
    * @param board Board
    * @param pieceX int
    * @param pieceY int
    * @param targetX int[] 0 is empty Square 1 is captured Pawn
    * @param targetY int[] 0 is empty Square 1 is captured Pawn
    */
   public static void movePiece(Board board, Player player, int pieceX, int pieceY, int[] targetX, int[] targetY) {
      clearEnPassantPiece(board);

      Square[][] boardArray = board.getBoardArray();

      addTakenPiece(player, boardArray[targetX[1]][targetY[1]].getPiece());

      boardArray[targetX[0]][targetY[0]] = boardArray[pieceX][pieceY];
      boardArray[pieceX][pieceY] = new Square();
      boardArray[targetX[1]][targetY[1]] = new Square();
      boardArray[targetX[0]][targetY[0]].getPiece().setHasMoved();
   }

   /**
    * A method to move a piece on the chessboard
    * This method works specifically for Castling
    *
    * @param board Board
    * @param pieceX int[] 0 is King 1 is Rook
    * @param pieceY int[] 0 is King 1 is Rook
    * @param targetX int[] 0 is King 1 is Rook
    * @param targetY int[] 0 is King 1 is Rook
    */
   public static void movePiece(Board board, Player player, int[] pieceX, int[] pieceY, int[] targetX, int[] targetY) {
      clearEnPassantPiece(board);

      Square[][] boardArray = board.getBoardArray();

      //move king
      boardArray[targetX[0]][targetY[0]] = boardArray[pieceX[0]][pieceY[0]];
      boardArray[pieceX[0]][pieceY[0]] = new Square();
      //move rook
      boardArray[targetX[1]][targetY[1]] = boardArray[pieceX[1]][pieceY[1]];
      boardArray[pieceX[1]][pieceY[1]] = new Square();

      //set hasMoved
      boardArray[targetX[0]][targetY[0]].getPiece().setHasMoved();
      boardArray[targetX[1]][targetY[1]].getPiece().setHasMoved();

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
