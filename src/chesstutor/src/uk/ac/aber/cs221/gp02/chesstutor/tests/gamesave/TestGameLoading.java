/*
 * @(#) TestGameLoading.java 0.4 2023/05/03
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.gp02.chesstutor.tests.gamesave;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.ac.aber.cs221.gp02.chesstutor.game.Game;
import uk.ac.aber.cs221.gp02.chesstutor.gamesave.GameSaveManager;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.Piece;
import uk.ac.aber.cs221.gp02.chesstutor.util.Color;

/**
 * @author mib60
 * This class contains tests for loading saved ongoing games and replays.
 * These tests should be run after TestGameSaving tests in order to work
 *
 * @version 0.1 Initial Development
 * @version 0.2 Added more tests
 * @version 0.3 Added comments
 * @version 0.4 Fixed wrong test and fixed warnings
 */
public class TestGameLoading {

   /**
    * Test that a loaded ongoing game is not null
    */
   @Test
   void testLoadingRound(){
      Game loadedGame = GameSaveManager.loadOngoingGame("saveLoadTests/testSavingRound");
      Assertions.assertNotNull(loadedGame);
   }


   /**
    * Check that the white player's name is loaded correctly
    */
   @Test
   void testWhitePlayerNameLoaded(){
      Game loadedGame = GameSaveManager.loadOngoingGame("saveLoadTests/testSavingRound");
      Assertions.assertNotNull(loadedGame);
      Assertions.assertEquals(loadedGame.getWhitePlayer().getName(), TestGameSaving.createTestGame().getWhitePlayer().getName());
   }

   /**
    * Check that the black player's name is loaded correctly
    */
   @Test
   void testBlackPlayerNameLoaded(){
      Game loadedGame = GameSaveManager.loadOngoingGame("saveLoadTests/testSavingRound");
      Assertions.assertNotNull(loadedGame);
      Assertions.assertEquals(loadedGame.getBlackPlayer().getName(), TestGameSaving.createTestGame().getBlackPlayer().getName());
   }

   /**
    * Check that the round count is loaded correctly
    */
   @Test
   void testRoundCountLoaded(){
      Game loadedGame = GameSaveManager.loadOngoingGame("saveLoadTests/testSavingRound");
      Assertions.assertNotNull(loadedGame);
      Assertions.assertEquals(loadedGame.getRoundCount(), 0);
   }

   /**
    * Check that the current turn is loaded correctly
    */
   @Test
   void testTurnLoaded(){
      Game loadedGame = GameSaveManager.loadOngoingGame("saveLoadTests/testSavingRound");
      Assertions.assertNotNull(loadedGame);
      loadedGame.nextRound();
      Assertions.assertEquals(loadedGame.getTurn(), Color.WHITE);
   }


   /**
    * Test that the pieces are loaded from an ongoing game
    */
   @Test
   void testPiecesLoaded(){
      Game loadedGame = GameSaveManager.loadOngoingGame("saveLoadTests/testSavingRound");
      Assertions.assertNotNull(loadedGame);
      loadedGame.getBoard().printBoard();
      Game nonLoadedGame = TestGameSaving.createTestGame();

      //iterate through pieces
      for(int row = 0; row < 8; row++){
         for(int col = 0; col < 8; col++){

            //Get the current piece from the loaded game
            Piece loadedPiece = loadedGame.getBoard().getBoardArray()[row][col].getPiece();

            //Get the current piece from the generated game
            Piece nonLoadedPiece = nonLoadedGame.getBoard().getBoardArray()[row][col].getPiece();

            //If they are both null continue - if one is null and another is not they will throw a nullpointerexception
            if(loadedPiece == null && nonLoadedPiece == null) continue;

            //If the piece types and colors are the same test is good
            Assertions.assertNotNull(loadedPiece);
            Assertions.assertNotNull(nonLoadedPiece);

            Assertions.assertEquals(loadedPiece.getPieceType(), nonLoadedPiece.getPieceType());
            Assertions.assertEquals(loadedPiece.getPieceColor(), nonLoadedPiece.getPieceColor());
         }
      }
   }
}
