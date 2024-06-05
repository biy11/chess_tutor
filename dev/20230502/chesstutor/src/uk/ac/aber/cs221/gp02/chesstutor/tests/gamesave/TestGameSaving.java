package uk.ac.aber.cs221.gp02.chesstutor.tests.gamesave;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import uk.ac.aber.cs221.gp02.chesstutor.game.Game;
import uk.ac.aber.cs221.gp02.chesstutor.game.Square;
import uk.ac.aber.cs221.gp02.chesstutor.gamesave.GameSaveManager;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.King;
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
    * Test saving a game replay
    *
    */
   @Test
   void testSavingReplay(){
   }

   @Test
   void testInfoFileCreatedOngoingGame() throws ParserConfigurationException, IOException, SAXException {
      //Create new test game and get info file
      Game savedGame = createTestGame();
      savedGame.setSaveDirectory(savedGame.getSaveDirectory()+"/testInfoFileCreatedOngoingGame");
      savedGame.nextRound();

      File infoFile = new File(savedGame.getSaveDirectory()+"/info.xml");

      //Parse the info file
      DocumentBuilder documentBuilder;
      DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
      documentBuilder = documentBuilderFactory.newDocumentBuilder();
      Document document = documentBuilder.parse(infoFile);

      //Check the info file node 'type' has value 'ongoing'
      Assertions.assertEquals("ongoing", document.getElementsByTagName("type").item(0).getTextContent());
   }


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

   @Test
   void testInfoFileCreatedSavedReplay() throws ParserConfigurationException, IOException, SAXException {
      Game savedGame = createTestGame();
      savedGame.setSaveDirectory(savedGame.getSaveDirectory()+"/testInfoFileCreatedSavedReplay");

      //Skip a round
      savedGame.nextRound();

      GameSaveManager.saveReplay(savedGame);

      File infoFile = new File(savedGame.getSaveDirectory()+"/info.xml");

      //Parse the info file
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
      savedGame.getBoard().setSquare(new Square(new King(Color.BLACK)), 4, 4);
      savedGame.setWhitePlayer("white player name test");
      savedGame.setBlackPlayer("black player name test");
      return savedGame;
   }
}
