/*
 * @(#) TestSquare.java 0.4 2023/05/04
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */
package uk.ac.aber.cs221.gp02.chesstutor.tests.game;

import org.junit.jupiter.api.Test;
import uk.ac.aber.cs221.gp02.chesstutor.game.Square;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.*;
import uk.ac.aber.cs221.gp02.chesstutor.util.Color;
import uk.ac.aber.cs221.gp02.chesstutor.util.Type;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for TestSquare.
 *
 * @author Lance [lvs1]
 * @version 0.1 Framework.
 * @version 0.2 Initial Development.
 * @version 0.3 Bug Fixes.
 * @version 0.4 Added comments.
 */
public class TestSquare {
   /**
    * Test to check if an empty square on board is actually empty.
    * */
   @Test
   void constructSquareNoPiece(){
      Square obj = new Square();
      assertFalse(obj.hasPiece());
   }
   /**
    * Test to check if Square object can hold pieces.
    * */
   @Test
   void constructSquareWithPiece(){
      Rook rook = new Rook(Color.BLACK);
      Square obj = new Square(rook);
      assertTrue(obj.hasPiece());
   }

   /**
    * Test to check if piece at square is what it is expected to be. In this instance a Knight.
    * */
   @Test
   void getPieceFromSquare(){
      Square obj = new Square(new Knight(Color.BLACK));
      assertEquals(Type.KNIGHT,obj.getPiece().getPieceType());
   }

}
