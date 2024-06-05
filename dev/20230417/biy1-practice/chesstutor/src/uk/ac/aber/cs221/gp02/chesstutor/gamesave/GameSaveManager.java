/*
 * @(#) GameSaveManager.java 0.2 2023/03/15
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.gp02.chesstutor.gamesave;

import com.sun.xml.internal.ws.developer.JAXBContextFactory;
import uk.ac.aber.cs221.gp02.chesstutor.game.Game;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Micah [mib60]
 * @author Michael [mjs36]
 * @version 0.1 Initial Development
 * @version 0.2 complete Javadoc added by mjs36
 */
public class GameSaveManager {

   /**
    * Loads a specific round of a saved replay
    * @param directory The directory of the game
    * @param round The round number - starts at 1
    * @return A game object from the loaded round or null if unsuccessful
    */
   public static Game loadReplayRound(String directory, int round){
      return load(new File(directory+"/"+round+".xml"));
   }

   /**
    * Loads an ongoing game.
    * @param directory The directory of the game
    * @return A new Game object of the saved game, or null if the directory doesn't exist or doesn't contain a game
    */
   public static Game loadOngoingGame(String directory){

      //Make a new File object from the directory
      File file = new File(directory);

      //Return null if the directory does not exist
      if(!file.exists()){
         System.out.println("Directory does not exist");
         return null;
      }

      //Get the subfiles in the directory
      File[] subFiles = file.listFiles();

      if(subFiles == null){
         System.out.println("Directory contains no files.");
         return null;
      }

      //Sort them ascending
      Arrays.sort(subFiles);

      //Get the most recent turn
      File lastRound = subFiles[subFiles.length-1];

      //Load the game and return it
      Game game = load(lastRound);

      return game;
   }

   /**
    * Saves an ongoing game, this should be called at the end of every turn.
    * This requires Game.saveDirectory to be set.
    * @param game The game to save
    */
   public static boolean saveOngoingGame(Game game){
      JAXBContext jaxbContext = null;
      File directory = new File(game.getSaveDirectory());

      //If the directory doesn't exist, make it, if it could not be made return false
      if(!directory.exists() && !directory.mkdirs()) return false;



      try {
         //Create jaxb entry point
         jaxbContext = JAXBContext.newInstance(Game.class);

         //Create jaxb marshaller (converts java objects into persistent xml data)
         Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

         // output pretty printed
         jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

         // output to file
         jaxbMarshaller.marshal(game, new File(game.getSaveDirectory()+"/"+game.getRoundCount()+".xml"));

      } catch (JAXBException e) {
         e.printStackTrace();
         return false;
      }

      return true;
   }


   /**
    * Saves a game replay
    * @param directory The directory to save the game to
    */
   public static boolean saveReplay(String directory){
      //Get the temporary file store where the game is held and return if for some reason it does not exist
      File tempFileStore = new File("temp");
      if(!tempFileStore.exists()) return false;

      //Get the rounds of the game
      File[] rounds = tempFileStore.listFiles();

      //If the folder is empty return
      if(rounds == null) return false;

      //Create a File object with the directory where the game will be saved, create the directory if it does not exist
      File saveLoc = new File(directory);
      if(!saveLoc.exists()) saveLoc.mkdirs();

      //Create a new Path object with the file
      Path saveLocPath = saveLoc.toPath();

      //Iterate through the rounds
      for(File round:rounds){

         //Create a new Path object with the file
         Path tempPathStore = round.toPath();

         //Get the location to move it to
         File roundSaveFile = new File(saveLoc.getAbsolutePath()+"/"+round.getName());
         Path roundSavePath = roundSaveFile.toPath();

         //Copy the files from the temp storage to the save location
         try {
            Files.copy(tempPathStore, roundSavePath, StandardCopyOption.REPLACE_EXISTING);
         } catch (IOException e) {
            System.out.println("Error while saving game replay");
            return false;
         }
      }
      return true;
   }

   /**
    * A private method to load a file
    *
    * @param file File
    * @return Game object or null if unsuccessful
    */
   private static Game load(File file){
      Game game = null;
      JAXBContext jaxbContext = null;

      try {

         //Create jaxb entry point
         jaxbContext = JAXBContext.newInstance(Game.class);

         //Create the unmarshaller object (converts persistent xml data into java object)
         Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
         game = (Game) jaxbUnmarshaller.unmarshal(file);

      } catch (JAXBException e) {
         System.out.println("GameSaveManager.load Tried to unmarshal an invalid file and returned null");
         return null;
      }

      return game;
   }
}
