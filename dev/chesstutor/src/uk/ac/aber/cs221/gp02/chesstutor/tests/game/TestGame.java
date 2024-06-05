/*
 * @(#) TestGame.java 0.5 2023/05/04
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */
package uk.ac.aber.cs221.gp02.chesstutor.tests.game;

import uk.ac.aber.cs221.gp02.chesstutor.game.Game;
import uk.ac.aber.cs221.gp02.chesstutor.util.Color;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for TestGame.
 *
 * @author Lance [lvs1]
 * @version 0.1 Framework.
 * @version 0.2 Initial Development.
 * @version 0.3 Bug Fixes.
 * @version 0.4 Added comments.
 * @version 0.5 Adjusted Tests.
 */

public class TestGame {
   /**
    * Test to check if setName function works for white player.
    * */
   @Test
   public void whitesNameIsJohn(){
      Game obj = new Game();
      obj.setWhitePlayer("John");
      assertEquals("John",obj.getWhitePlayer().getName());
   }
   /**
    * Test to check if setName function works for black player.
    * */
   @Test
   public void blacksNameIsSmith(){
      Game obj = new Game();
      obj.setBlackPlayer("Smith");
      assertEquals("Smith",obj.getBlackPlayer().getName());
   }
   /**
    * Test to check if first person to move on the board is the white player.
    * */
   @Test
   public void initialTurnIsBlack(){
      Game obj = new Game();
      assertEquals(0,obj.getRoundCount());
      assertEquals(Color.BLACK, obj.getTurn());
   }
   /**
    * Test to check if first round is set to 0.
    * */
   @Test
   public void startRoundIsZero(){
      Game obj = new Game();
      assertEquals(0,obj.getRoundCount());
   }
   /**
    * Test to check if nextRound method works.
    * */
   @Test
   public void testNextRoundMethod(){
      Game obj = new Game();
      obj.nextRound();
      assertEquals(1,obj.getRoundCount());
      obj.nextRound();
      obj.nextRound();
      obj.nextRound();
      assertEquals(4,obj.getRoundCount());
   }
   /**
    * Test to check if setTurn is functioning.
    * */
   @Test
   public void testSetTurnMethod(){
      Game obj = new Game();
      obj.setTurn(Color.BLACK);
      assertEquals(Color.BLACK, obj.getTurn());
   }

}
