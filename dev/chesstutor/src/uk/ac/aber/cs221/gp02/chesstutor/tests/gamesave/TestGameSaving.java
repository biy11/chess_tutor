/*
 * @(#) TestGameSaving.java 0.3 2023/05/03
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.gp02.chesstutor.tests.gamesave;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import uk.ac.aber.cs221.gp02.chesstutor.game.Game;
import uk.ac.aber.cs221.gp02.chesstutor.game.Square;
import uk.ac.aber.cs221.gp02.chesstutor.gamesave.GameSaveManager;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.*;
import uk.ac.aber.cs221.gp02.chesstutor.util.Color;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * @author mib60
 * @version 0.1 initial development
 * @version 0.2 Added more tests
 * @version 0.3 Added comments
 */
public class TestGameSaving {

   /**
    * Test saving a single round of a game
    */
   @Test
   void testSavingRound(){
      Game savedGame = createTestGame();
      savedGame.setSaveDirectory(savedGame.getSaveDirectory()+"/testSavingRound");
      boolean success = GameSaveManager.saveOngoingGame(savedGame);
      Assertions.assertTrue(success);
   }

   /**
    * Test the info file is created when saving an ongoing game
    */
   @Test
   void testInfoFileCreatedOngoingGame() throws ParserConfigurationException, IOException, SAXException {
      //Create new test game and get info file
      Game savedGame = createTestGame();
      savedGame.setSaveDirectory(savedGame.getSaveDirectory()+"/testInfoFileCreatedOngoingGame");
      savedGame.nextRound();

      //Get the info file directory
      File infoFile = new File(savedGame.getSaveDirectory()+"/info.xml");

      //Parse the info file
      DocumentBuilder documentBuilder;
      DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
      documentBuilder = documentBuilderFactory.newDocumentBuilder();
      Document document = documentBuilder.parse(infoFile);

      //Check the info file node 'type' has value 'ongoing'
      Assertions.assertEquals("ongoing", document.getElementsByTagName("type").item(0).getTextContent());
   }


   /**
    * Test that the info file is created for a replay that has not been saved during play
    */
   @Test
   void testInfoFileCreatedUnsavedReplay() throws ParserConfigurationException, IOException, SAXException {
      //Create new test game and get info file
      Game savedGame = createTestGameWithNoSaveDirectory();

      //Skip a round
      savedGame.nextRound();
      savedGame.nextRound();

      //The directory the game replay will be saved to
      String directory = "saveLoadTests/testInfoFileCreatedUnsavedReplay";

      savedGame.setSaveDirectory(directory);
      GameSaveManager.moveTempFiles(directory);
      GameSaveManager.saveReplay(savedGame);
      File infoFile = new File(directory+"/info.xml");

      //Parse the info file
      DocumentBuilder documentBuilder;
      DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
      documentBuilder = documentBuilderFactory.newDocumentBuilder();
      Document document = documentBuilder.parse(infoFile);

      //Check the info file node 'type' has value 'replay'
      Assertions.assertEquals("replay", document.getElementsByTagName("type").item(0).getTextContent());
   }

   /**
    * Test that the info file is saved when a repay is saved
    */
   @Test
   void testInfoFileCreatedSavedReplay() throws ParserConfigurationException, IOException, SAXException {
      //Create a new game and set the save directory
      Game savedGame = createTestGame();
      savedGame.setSaveDirectory(savedGame.getSaveDirectory()+"/testInfoFileCreatedSavedReplay");

      //Skip a round
      savedGame.nextRound();
      GameSaveManager.saveReplay(savedGame);

      //Get the info file path
      File infoFile = new File(savedGame.getSaveDirectory()+"/info.xml");

      //Try to parse the info file
      DocumentBuilder documentBuilder;
      DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
      documentBuilder = documentBuilderFactory.newDocumentBuilder();
      Document document = documentBuilder.parse(infoFile);

      //Check the info file node 'type' has value 'replay'
      System.out.println(document.getElementsByTagName("type").item(0).getTextContent());
      Assertions.assertEquals("replay", document.getElementsByTagName("type").item(0).getTextContent());
   }

   /**
    * Creates a test game, used by other tests
    * @return The test game
    */
   public static Game createTestGame(){
      GameSaveManager.clearTempFolder();
      Game savedGame = createTestGameWithNoSaveDirectory();
      savedGame.setSaveDirectory("saveLoadTests");
      return savedGame;
   }

   /**
    * Creates a test game, used by other tests
    * @return The test game
    */
   public static Game createTestGameWithNoSaveDirectory(){
      Game savedGame = new Game();
      savedGame.getBoard().clearBoard();
      savedGame.getBoard().setSquare(new Square(new King(Color.BLACK)), 4, 4);
      savedGame.getBoard().setSquare(new Square(new Queen(Color.BLACK)), 5, 6);
      savedGame.getBoard().setSquare(new Square(new Bishop(Color.BLACK)), 2, 7);
      savedGame.getBoard().setSquare(new Square(new King(Color.WHITE)), 1, 3);
      savedGame.getBoard().setSquare(new Square(new Pawn(Color.WHITE)), 7, 4);
      savedGame.getBoard().setSquare(new Square(new Rook(Color.WHITE)), 6, 7);

      savedGame.setWhitePlayer("white player name test");
      savedGame.setBlackPlayer("black player name test");
      return savedGame;
   }
}
