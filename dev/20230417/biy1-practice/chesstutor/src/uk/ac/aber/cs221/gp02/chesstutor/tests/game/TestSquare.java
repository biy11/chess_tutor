package uk.ac.aber.cs221.gp02.chesstutor.tests.game;

import org.junit.jupiter.api.Test;
import uk.ac.aber.cs221.gp02.chesstutor.game.Square;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.*;
import uk.ac.aber.cs221.gp02.chesstutor.util.Color;
import uk.ac.aber.cs221.gp02.chesstutor.util.Type;

import static org.junit.jupiter.api.Assertions.*;

public class TestSquare {

   @Test
   void constructSquareNoPiece(){
      Square obj = new Square();
      assertFalse(obj.isHasPiece());
   }

   @Test
   void constructSquareWithPiece(){
      Rook rook = new Rook(Color.BLACK);
      Square obj = new Square(rook);
      assertTrue(obj.isHasPiece());
   }

   @Test
   void getPieceFromSquare(){
      Square obj = new Square(new Knight(Color.BLACK));
      assertEquals(Type.KNIGHT,obj.getPiece().getPieceType());
   }

}
