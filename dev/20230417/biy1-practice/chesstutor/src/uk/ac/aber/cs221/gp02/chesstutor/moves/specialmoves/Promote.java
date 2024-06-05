/*
 * @(#) Promote.java 0.2 2023/03/15
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
 */
public class Promote {

   /**
    * A method that promotes a Pawn to the desired piece
    * <p></p>
    * It is assumed only a Pawn can be passed to this method
    *
    * @param board Board
    * @param x int
    * @param y int
    * @param newPiece Type
    */
   public static void promotePawn(Board board, int x, int y, Type newPiece) {
      Square[][] boardArray = board.getBoardArray();

      Color color = boardArray[x][y].getPiece().getPieceColor();

      boardArray[x][y] = new Square(); //makes sure the square is empty
      switch (newPiece) {
         case BISHOP:
            boardArray[x][y] = new Square(new Bishop(color));
            break;
         case KNIGHT:
            boardArray[x][y] = new Square(new Knight(color));
            break;
         case QUEEN:
            boardArray[x][y] = new Square(new Queen(color));
            break;
         case ROOK:
            boardArray[x][y] = new Square(new Rook(color));
            break;
      }
      boardArray[x][y].getPiece().setHasMoved();
   }
}
