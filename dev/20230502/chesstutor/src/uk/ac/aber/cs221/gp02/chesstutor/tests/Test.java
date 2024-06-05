package uk.ac.aber.cs221.gp02.chesstutor.tests;

import uk.ac.aber.cs221.gp02.chesstutor.game.*;
import uk.ac.aber.cs221.gp02.chesstutor.moves.CheckChecker;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.*;
import uk.ac.aber.cs221.gp02.chesstutor.util.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {

   //JUnit can use this or delete it
   //it takes in a boardArray and the x and y coordinates of a piece
   //remember x and y are inverted due to arrays counting from top left
   //it gets the Piece object you specified
   //then prints the valid moves for it
   //it doesn't work for Castling or enPassant as they return non-standard output (formatted differently)
   public static void possibleMoves(Board board, int x, int y) {
      Square[][] boardArray = board.getBoardArray();

      Piece piece = boardArray[x][y].getPiece();

      List<int[]> returnArray = new ArrayList<int[]>();

      returnArray = piece.getValidMoves(board,piece,x,y);
      if (returnArray.size() == 0) {
         System.err.println("No valid moves for " + piece.getPieceType().name() + " at " + x + ", " + y);
      }

      returnArray.forEach((a) -> System.out.println(a[0] + "," + a[1]));

   }

}
