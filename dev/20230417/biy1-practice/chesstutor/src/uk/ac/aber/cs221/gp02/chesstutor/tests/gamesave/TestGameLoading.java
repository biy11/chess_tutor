package uk.ac.aber.cs221.gp02.chesstutor.tests.gamesave;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.ac.aber.cs221.gp02.chesstutor.game.Game;
import uk.ac.aber.cs221.gp02.chesstutor.gamesave.GameSaveManager;

/**
 * @author mib60
 * This class contains tests for loading saved ongoing games and replays.
 * These tests should be run after TestGameSaving tests
 */
public class TestGameLoading {

   /**
    * Test loading an ongiong game
    */
   @Test
   void testLoadingRound(){
      Game loadedGame = GameSaveManager.loadOngoingGame("saveLoadTests");
      Assertions.assertNotNull(loadedGame);
   }

   /**
    * Check that the game directory is loaded correctly
    */
   @Test
   void testDirectoryLoaded(){
      Game loadedGame = GameSaveManager.loadOngoingGame("saveLoadTests");
      Assertions.assertEquals(loadedGame.getSaveDirectory(), TestGameSaving.createTestGame().getSaveDirectory());
   }

   /**
    * Check that the white player's name is loaded correctly
    */
   @Test
   void testWhitePlayerNameLoaded(){
      Game loadedGame = GameSaveManager.loadOngoingGame("saveLoadTests");
      Assertions.assertEquals(loadedGame.getWhitePlayer().getName(), TestGameSaving.createTestGame().getWhitePlayer().getName());
   }

   /**
    * Check that the black player's name is loaded correctly
    */
   @Test
   void testBlackPlayerNameLoaded(){
      Game loadedGame = GameSaveManager.loadOngoingGame("saveLoadTests");
      Assertions.assertEquals(loadedGame.getBlackPlayer().getName(), TestGameSaving.createTestGame().getBlackPlayer().getName());
   }

   /**
    * Check that the round count is loaded correctly
    */
   @Test
   void testRoundCountLoaded(){
      Game loadedGame = GameSaveManager.loadOngoingGame("saveLoadTests");
      Assertions.assertEquals(loadedGame.getRoundCount(), TestGameSaving.createTestGame().getRoundCount());
   }

   /**
    * Check that the current turn is loaded correctly
    */
   @Test
   void testTurnLoaded(){
      Game loadedGame = GameSaveManager.loadOngoingGame("saveLoadTests");
      Assertions.assertEquals(loadedGame.getTurn(), TestGameSaving.createTestGame().getTurn());
   }
}
