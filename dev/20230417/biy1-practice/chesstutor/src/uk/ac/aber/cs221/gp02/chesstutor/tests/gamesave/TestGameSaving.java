package uk.ac.aber.cs221.gp02.chesstutor.tests.gamesave;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.ac.aber.cs221.gp02.chesstutor.game.Game;
import uk.ac.aber.cs221.gp02.chesstutor.gamesave.GameSaveManager;

//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;

/**
 * @author mib60
 * @version 0.1 initial development
 *
 */
public class TestGameSaving {

   /**
    * Test saving a single round of a game
    */
   @Test
   void testSavingRound(){
      Game savedGame = createTestGame();
      boolean success = GameSaveManager.saveOngoingGame(savedGame);
      Assertions.assertTrue(success);
   }

   /**
    * Test saving a single round of a game
    */
   @Test
   void testSavingFiveRounds(){
   }

   /**
    * Test saving a game replay
    * This has not been implemented yet and will fail the test
    */
   @Test
   void testSavingReplay(){
      Game savedGame = createTestGame();
      boolean success = GameSaveManager.saveReplay(savedGame.getSaveDirectory());
      Assertions.assertTrue(success);
   }

   /**
    * Cerates a test game, used by other tests
    * @return The test game
    */
   public static Game createTestGame(){
      Game savedGame = new Game();
      savedGame.setSaveDirectory("saveLoadTests");
      savedGame.setWhitePlayer("white player name test");
      savedGame.setBlackPlayer("black player name test");
      return savedGame;
   }
}
