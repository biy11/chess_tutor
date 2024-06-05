package uk.ac.aber.cs221.gp02.chesstutor.specialmoves;

import uk.ac.aber.cs221.gp02.chesstutor.game.Board;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.Piece;

import java.util.Arrays;
import java.util.List;

public class CheckTest {

   public static void main(String[] args){

      Board board = new Board();
      board.boardResetForCheckTest();
      board.printBoard();
      Piece king = board.getBoardArray()[7][4].getPiece();
      System.out.println(king.getPieceType());


      List<int[]> pieces = CheckChecker.isInCheck(board, 7, 4);


      System.out.println("Pieces checking the king are:");
      for(int[] piece:pieces){
         System.out.println(Arrays.toString(piece));
      }
   }
}
