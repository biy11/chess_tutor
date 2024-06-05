package uk.ac.aber.cs221.gp02.chesstutor.specialmoves;

import uk.ac.aber.cs221.gp02.chesstutor.game.Board;
import uk.ac.aber.cs221.gp02.chesstutor.game.Square;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.Piece;
import uk.ac.aber.cs221.gp02.chesstutor.util.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for checking if a king can castle
 * Only has one public method: canCastle
 *
 * @author mib60
 * @version 0.1
 */

public class CastleChecker {

   /**
    *
    * Static method. Checks whether a king can castle
    *
    * @param board The main board
    * @param piece The king to check for castling
    * @param x The king's x position
    * @param y The king's y position
    * @return Returns a list of integer arrays. Each integer array contains: The position the king
    * must move to in order to castle, the position of the rook it will be castling with, and the
    * position the rook will be moved to, in that order.
    */
   public static List<int[]> canCastle(Board board, Piece piece, int x, int y){
      List<int[]> output = new ArrayList<>();

      //check king has not moved
      if(piece.getHasMoved()){
         System.out.println("King can not castle as it has been moved");
         return output;
      }

      int[] out = null;

      //Search the right side of the king for a possible castle
      out = checkKingSide(board, piece, x, y, y+1, 8, -1);
      if(out != null) output.add(out);


      //Search the left side of the king for a possible castle
      out = checkKingSide(board, piece, x, y, 0, y, +1);
      if(out != null) output.add(out);

      return output;
   }

   /**
    * Checks one side of the king to see if it can castle
    * @param board The main board
    * @param piece The king to check
    * @param x The x position of the king
    * @param y THe y position of the king
    * @param start What x position to start the search
    * @param end What x position to end the search
    * @param direction The direction (-1 for left, 1 for right)
    * @return Returns an array of integers containing: the position the king
    * must move to in order to castle, the position of the rook it will be castling with, and the
    * position the rook will be moved to, in that order. Returns null if a castle move is not found
    */
   private static int[] checkKingSide(Board board, Piece piece, int x, int y, int start, int end, int direction){
      int[] out = new int[3];

      for(int i = start; i < end; i++){

         //Get the square and the piece
         Square square = board.getBoardArray()[x][i];
         Piece p = square.getPiece();

         //Skip the square if the piece is null
         if(p == null) continue;

         //If the piece is not a rook, castling is not possible as a rook would be blocked, so break the loop
         if(!p.getPieceType().equals(Type.ROOK)){
            System.out.println("Found a "+p.getPieceType()+" between king and rook, castling not possible");
            return null;
         }

         //The piece is a rook and the loop has not been broken so there are no pieces blocking the castling
         //So if the piece has not moved a castle is possible
         if(!p.getHasMoved() && p.getPieceColor().equals(piece.getPieceColor())){
            System.out.println(p.getPieceColor()+" King at "+x+", "+y+" can castle rook at "+x+" "+i);
            out[0] = y-direction*2;
            out[1] = i;
            out[2] = out[0]+direction;
            return out;
         }
      }
      return null;
   }
}
