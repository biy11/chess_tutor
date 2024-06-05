package uk.ac.aber.cs221.gp02.chesstutor.specialmoves;

import uk.ac.aber.cs221.gp02.chesstutor.game.Board;
import uk.ac.aber.cs221.gp02.chesstutor.game.Square;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.Piece;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CastleTest {

   public static void main(String[] args){

      Board board = new Board();
      board.boardResetForCastleTest();
      board.printBoard();

      Square[][] arr = board.getBoardArray();
      Piece king = arr[7][4].getPiece();

      CastleChecker.canCastle(king, board, 7, 4);

      king = arr[0][4].getPiece();

      CastleChecker.canCastle(king, board, 0, 4);
   }
}