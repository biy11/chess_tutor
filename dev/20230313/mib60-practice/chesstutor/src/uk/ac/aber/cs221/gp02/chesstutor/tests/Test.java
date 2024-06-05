package uk.ac.aber.cs221.gp02.chesstutor.tests;

import uk.ac.aber.cs221.gp02.chesstutor.game.Square;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class Test {
   public static List<int[]> possibleMoves(Square[][] boardArray, int x, int y) {

      Piece piece = boardArray[x][y].getPiece();

      List<int[]> returnArray = new ArrayList<int[]>();

      returnArray = piece.getValidMoves(boardArray,piece,x,y);
      if (returnArray.size() == 0) {
         System.err.println("No valid moves for " + piece.getPieceType().name() + " at " + x + ", " + y);
      }

      return returnArray;
   }
}
