package uk.ac.aber.cs221.gp02.chesstutor.tests.game;

import uk.ac.aber.cs221.gp02.chesstutor.game.Game;
import uk.ac.aber.cs221.gp02.chesstutor.util.Color;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestGame {

   @Test
   public void whitesNameIsJohn(){
      Game obj = new Game();
      obj.setWhitePlayer("John");
      assertEquals("John",obj.getWhitePlayer().getName());
   }

   @Test
   public void blacksNameIsSmith(){
      Game obj = new Game();
      obj.setBlackPlayer("Smith");
      assertEquals("Smith",obj.getBlackPlayer().getName());
   }

   @Test
   public void initialTurnIsWhite(){
      Game obj = new Game();
      assertEquals(1,obj.getRoundCount());
      assertEquals(Color.WHITE, obj.getTurn());
   }

   @Test
   public void startRoundIsOne(){
      Game obj = new Game();
      assertEquals(1,obj.getRoundCount());
   }

   @Test
   public void testNextRoundMethod(){
      Game obj = new Game();
      obj.nextRound();
      assertEquals(2,obj.getRoundCount());
      obj.nextRound();
      obj.nextRound();
      obj.nextRound();
      assertEquals(5,obj.getRoundCount());
   }

   @Test
   public void testSetTurnMethod(){
      Game obj = new Game();
      obj.setTurn(Color.BLACK);
      assertEquals(Color.BLACK, obj.getTurn());
   }

}
