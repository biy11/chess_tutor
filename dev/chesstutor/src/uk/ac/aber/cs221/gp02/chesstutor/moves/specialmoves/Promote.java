/*
 * @(#) Promote.java 0.6 2023/05/03
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.gp02.chesstutor.moves.specialmoves;

import uk.ac.aber.cs221.gp02.chesstutor.game.*;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.*;
import uk.ac.aber.cs221.gp02.chesstutor.util.*;

/**
 * A class that promotes the passed Pawn to the desired piece
 *
 * @author Michael [mjs36]
 * @version 0.1 initial development
 * @version 0.2 added javadocs
 * @version 0.3 updated code variables from x to row and updated i to be
 * @version 0.4 Added pawn check, added null check, added position check
 * @version 0.5 Changed to pass opponent player, adding to the captured list
 * @version 0.6 Added comments.
 * @see Pawn
 */
public class Promote {

   /**
    * A method that promotes a Pawn to the desired piece
    * <p></p>
    * It is assumed only a Pawn can be passed to this method
    *
    * @param board Board
    * @param row int
    * @param col int
    * @param newPiece Type
    */
   public static void promotePawn(Board board, Player player, int row, int col, Type newPiece) {
      Square[][] boardArray = board.getBoardArray();

      Piece piece = board.getBoardArray()[row][col].getPiece();

      if(piece == null) return;

      //If the piece is white and not on the top rank return
      if(piece.getPieceColor() == Color.WHITE && row != 0) return;

      //If the piece is black and not the bottom rank return
      if(piece.getPieceColor() == Color.BLACK && row != 7) return;


      if(!board.getBoardArray()[row][col].getPiece().getPieceType().equals(Type.PAWN)) return;

      Color color = boardArray[row][col].getPiece().getPieceColor();

      player.addTakenPieces(piece);

      boardArray[row][col] = new Square(); //makes sure the square is empty
      //Adds a new piece to the board based on player's choice
      switch (newPiece) {
         case BISHOP:
            boardArray[row][col] = new Square(new Bishop(color));
            break;
         case KNIGHT:
            boardArray[row][col] = new Square(new Knight(color));
            break;
         case QUEEN:
            boardArray[row][col] = new Square(new Queen(color));
            break;
         case ROOK:
            boardArray[row][col] = new Square(new Rook(color));
            break;
      }
      boardArray[row][col].getPiece().setHasMoved();
   }
}
