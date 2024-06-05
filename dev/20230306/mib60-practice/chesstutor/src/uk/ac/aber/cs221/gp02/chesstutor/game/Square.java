package uk.ac.aber.cs221.gp02.chesstutor.game;

import uk.ac.aber.cs221.gp02.chesstutor.pieces.Piece;

/**
 * @author Michael [mjs36]
 */
public class Square { //NOTE: do squares need to know there x & y? we already have boardArray[x][y]??

   private Piece piece;
   private boolean hasPiece;

   public Square() {
      hasPiece = false;
   }

   public Square(Piece piece) {
      this.piece = piece;
      hasPiece = true;
   }

   public Piece getPiece() {
      return piece;
   }

   public void setPiece(Piece piece) {
      this.piece = piece;
   }

   public boolean isHasPiece() {
      return hasPiece;
   }

   public void setHasPiece(boolean hasPiece) {
      this.hasPiece = hasPiece;
   }
}
