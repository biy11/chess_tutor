package uk.ac.aber.cs221.gp02.chesstutor.specialmoves;

import uk.ac.aber.cs221.gp02.chesstutor.game.Board;
import uk.ac.aber.cs221.gp02.chesstutor.game.Square;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.Piece;
import uk.ac.aber.cs221.gp02.chesstutor.util.Type;

public class CastleChecker {



   public static void canCastle(Piece piece, Board board, int x, int y){

      //check king has not moved
      if(piece.getHasMoved()){
         System.out.println("King can not castle as it has been moved");
         return;
      }

      //Search the right side of the king for a possible castle
      checkKingSide(board, piece, x, y, y+1, 8);

      //Search the left side of the king for a possible castle
      checkKingSide(board, piece, x, y, 0, y);
   }

   private static void checkKingSide(Board board, Piece piece, int x, int y, int start, int end){
      for(int i = start; i < end; i++){

         //Get the square and the piece
         Square square = board.getBoardArray()[x][i];
         Piece p = square.getPiece();

         //Skip the square if the piece is null
         if(p == null) continue;

         //If the piece is not a rook, castling is not possible as a rook would be blocked, so break the loop
         if(!p.getPieceType().equals(Type.ROOK)){
            System.out.println("Found a "+p.getPieceType()+" between king and rook, castling not possible");
            break;
         }

         //The piece is a rook and the loop has not been broken so there are no pieces blocking the castling
         //So if the piece has not moved a castle is possible
         if(!p.getHasMoved() && p.getPieceColor().equals(piece.getPieceColor())){
            System.out.println(p.getPieceColor()+" King at "+x+", "+y+" can castle rook at "+x+" "+i);
         }
      }
   }
}
