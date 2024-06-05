package uk.ac.aber.cs221.gp02.chesstutor.tests.systemtests;

/**
 * @author Lance [lvs1]
 * version 0.1 framework
 *
 * This class is for testing FR11 - Storing and restoring the game state
 */
public class ManipulatingGameState {
   /**
    * Test if a replay game button opens file explorer
    */
   public void testReplayGame(){}

   /**
    * Test if user can navigate through the folders
    */
   public void testNavigateFolders(){}

   /**
    * Test that user cannot climber higher than the
    * parent folder
    */
   public void testLimitToParentFolder(){}

   /**
    * Test that user cannot access irrelevant folders
    * (folders that do not contain saved games files)
    */
   public void testLimitToRelevantDirectories(){}

   /**
    * Test if the program avoids invalid XML files
    */
   public void testInvalidXMLFiles(){}

   /**
    * Test if the program avoids corrupted save files
    * (files that don't follow the format)
    */
   public void testCorruptedSaveFiles(){}

   /**
    * Test loading a valid replay
    */
   public void testLoadValidReplay(){}

   /**
    * Test loading names from file
    */
   public void testNamesLoadFromFile(){}

   /**
    * Test loading a valid ongoing game
    */
   public void testLoadOngoingGame(){}

   /**
    * Test saving an ongoing game
    */
   public void testSaveOngoingGame(){}

   /**
    * Test loading a newly saved ongoing game
    */
   public void testLoadNewlySavedGame(){}

   /**
    * Test for autosaving after each round
    */
   public void testSaveAutomatically(){}

   /**
    * Test that saving a replay works correctly
    */
   public void testSaveGameReplay(){}

   /**
    * Test that program can load a newly saved replay
    */
   public void testLoadNewlySavedReplay(){}

}
