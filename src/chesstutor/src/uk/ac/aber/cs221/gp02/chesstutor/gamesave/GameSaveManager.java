/*
 * @(#) GameSaveManager.java 1.0 2023/05/05
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.gp02.chesstutor.gamesave;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import uk.ac.aber.cs221.gp02.chesstutor.game.Game;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Micah [mib60]
 * @author Michael [mjs36]
 * @version 0.1 Initial Development
 * @version 0.2 complete Javadoc added by mjs36
 * @version 0.3 Added clearTempFolder and moveTempFiles
 * @version 0.4 Added info file to save folders
 * @version 0.5 added check for if loaded and actual paths are different
 * @version 0.6 Made getRoundsInReplay static
 * @version 0.7 added a leading 0 to save files when they are below 10 - mjs36
 * @version 0.8 added leading 0 when loading replay round - mjs36
 * @version 0.9 changed to use 000 format instead of 00, allows games of up to 999 moves - mjs36
 * @version 1.0 removed String.valueOf when unnecessary
 */
public class GameSaveManager {

   /**
    * Loads a specific round of a saved replay
    * @param directory The directory of the game
    * @param round The round number - starts at 0
    * @return A game object from the loaded round or null if unsuccessful
    */
   public static Game loadReplayRound(String directory, int round){
      String roundString = String.valueOf(round);
      if (round < 10 && round > 0) {
         roundString = "00"+round;
      } else if (round >= 10 && round < 100) {
         roundString = 0+String.valueOf(round);
      }
      return load(new File(directory+"/"+roundString+".xml"));
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

      //Get the sub files in the directory

      if(file.listFiles() == null){
         System.out.println("Directory contains no files.");
         return null;
      }

      //Get the sub files as a list
      List<File> subFiles = Arrays.asList(Objects.requireNonNull(file.listFiles()));

      File infoFile = null;

      //Find the info.xml file
      for(File currentFile:subFiles){
         if(currentFile.getName().equals("info.xml")){
            infoFile = currentFile;
            break;
         }
      }

      //If there is no info.xml file return null
      if(infoFile == null){
         System.out.println("Directory does not contain info file");
         return null;
      }

      //Check if info file marks the game as ongoing
      DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder documentBuilder;
      Document document;

      //Try to load the info.xml file
      try {
         documentBuilder = documentBuilderFactory.newDocumentBuilder();
      } catch (ParserConfigurationException e) {
         e.printStackTrace();
         return null;
      }
      try {
         document = documentBuilder.parse(infoFile);
      } catch (SAXException | IOException e) {
         e.printStackTrace();
         return null;
      }

      //Get the 'type' tag - defines whether the game is ongoing or replay
      String tag = document.getElementsByTagName("type").item(0).getTextContent();

      //Return null if the game is an ongoing game not a replay
      if(!tag.equals("ongoing")){
         return null;
      }

      //Sort them ascending
      Collections.sort(subFiles);

      //Get the most recent turn
      File lastRound = subFiles.get(subFiles.size()-1);
      if(lastRound.getName().equals("info.xml")) lastRound = subFiles.get(subFiles.size()-2);

      //Load the game and return it
      Game game = load(lastRound);
      if(game == null){
         return null;
      }

      //Check that the directories line up and if not correct it
      if(!game.getSaveDirectory().equals(file.getAbsolutePath())) game.setSaveDirectory(file.getAbsolutePath());

      return game;
   }

   /**
    * Saves an ongoing game, this is called at the end of every turn.
    * @param game The game to save
    */
   public static boolean saveOngoingGame(Game game){
      JAXBContext jaxbContext;
      File directory = new File(game.getSaveDirectory());

      //If the directory doesn't exist, make it, if it could not be made return false
      if(!directory.exists() && !directory.mkdirs()) return false;

      //Save the info file if it is the first round
      if(game.getRoundCount() == 0){
         File infoFile = new File(game.getSaveDirectory()+"/info.xml");

         //create the info file if it does not exist, if this is unsuccessful return false
         if(!infoFile.exists()){
            try {
               Files.createFile(infoFile.toPath());
            } catch (IOException e) {
               return false;
            }
         }

         //Make a new DocumentBuilder and DocumentBuilderFactory
         DocumentBuilder documentBuilder;
         DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
         try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
         } catch (ParserConfigurationException e) { //Return false if creating the document builder caused an error
            System.out.println("Could not create document builder");
            e.printStackTrace();
            return false;
         }

         //Create a new document and set the type
         Document document = documentBuilder.newDocument();
         document.appendChild(document.createElement("type")).appendChild(document.createTextNode("ongoing"));


         //Create a new transformer and try to save the xml file, return false if anything goes wrong
         Transformer tr;
         try {
            tr = TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            tr.setOutputProperty(OutputKeys.METHOD,"xml");
            tr.setOutputProperty(OutputKeys.STANDALONE, "yes");
         } catch (TransformerConfigurationException e) {
            e.printStackTrace();
            return false;
         }
         try {
            tr.transform(new DOMSource(document), new StreamResult(new FileOutputStream(infoFile)));

         } catch (TransformerException | FileNotFoundException e) {
            e.printStackTrace();
            return false;
         }
      }

      try {
         //Create jaxb entry point
         jaxbContext = JAXBContext.newInstance(Game.class);

         //Create jaxb marshaller (converts java objects into persistent xml data)
         Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

         // output pretty printed
         jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

         String roundString = String.valueOf(game.getRoundCount());
         if (game.getRoundCount() < 10 && game.getRoundCount() > 0) {
            roundString = "00"+game.getRoundCount();
         } else if (game.getRoundCount() >= 10 && game.getRoundCount() < 100) {
            roundString = 0+String.valueOf(game.getRoundCount());
         }

         // output to file
         jaxbMarshaller.marshal(game, new File(game.getSaveDirectory()+"/"+roundString+".xml"));

      } catch (JAXBException e) {
         e.printStackTrace();
         return false;
      }

      return true;
   }

   /**
    * A function to get the number of rounds in a replay directory
    * @param directory The directory of the replay
    * @return The number of rounds, or 0 if an error occurred or the directory is not a replay
    */
   public static int getNumRoundsInReplay(String directory){
      //Make a new File object from the directory
      File file = new File(directory);

      //Return null if the directory does not exist
      if(!file.exists()){
         System.out.println("Directory does not exist");
         return 0;
      }

      //If the file has no subfiles return null
      if(file.listFiles() == null){
         System.out.println("Directory contains no files.");
         return 0;
      }

      //Get the sub files in the directory
      File[] subFiles = file.listFiles();
      if(subFiles == null) return 0;
      File infoFile = null;

      //Get the info file
      for(File currentFile:subFiles){
         if(currentFile.getName().equals("info.xml")){
            infoFile = currentFile;
            break;
         }
      }

      //If the info file does not exist return 0
      if(infoFile == null){
         System.out.println("Directory does not contain info file");
         return 0;
      }

      //Check if info file marks the game as ongoing
      DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder documentBuilder;
      Document document;


      //Return 0 if there was an error getting the info file
      try {
         documentBuilder = documentBuilderFactory.newDocumentBuilder();
      } catch (ParserConfigurationException e) {
         e.printStackTrace();
         return 0;
      }
      try {
         document = documentBuilder.parse(infoFile);
      } catch (SAXException | IOException e) {
         e.printStackTrace();
         return 0;
      }

      //Get the type tag
      String tag = document.getElementsByTagName("type").item(0).getTextContent();

      //If the game is a replay return 0
      if(!tag.equals("replay")){
         return 0;
      }

      return subFiles.length-1;
   }

   /**
    * Saves a game replay
    * @param game The game to save
    */
   public static boolean saveReplay(Game game) {

      //Get the info file
      File infoFile = new File(game.getSaveDirectory()+"/info.xml");

      //If the info file could not be found return false
      if(!infoFile.exists()){
         System.out.println("Tried to save replay but could not find info file");
         return false;
      }

      //Create a new documentBuilder and documentBuilderFactory
      DocumentBuilder documentBuilder;
      DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

      //If there was an error making the documentBuilder return false
      try {
         documentBuilder = documentBuilderFactory.newDocumentBuilder();
      } catch (ParserConfigurationException e) {
         e.printStackTrace();
         return false;
      }

      Document document;
      try {
         document = documentBuilder.parse(infoFile);
      } catch (SAXException | IOException e) {
         e.printStackTrace();
         return false;
      }

      //Get the type tag
      document.getElementsByTagName("type").item(0).setTextContent("replay");

      //Create a new transformer and write the xml file to disk, if there is an error return false
      Transformer tr;
      try {
         tr = TransformerFactory.newInstance().newTransformer();
      } catch (TransformerConfigurationException e) {
         e.printStackTrace();
         return false;
      }

      try {
         tr.transform(new DOMSource(document), new StreamResult(Files.newOutputStream(infoFile.toPath())));
      } catch (TransformerException | IOException e) {
         e.printStackTrace();
         return false;
      }

      return true;
   }

   /**
    * Clears the temporary file folder for the start of a new game
    */
   public static void clearTempFolder(){
      //Get the temporary file store and subfiles
      File tempFileStore = new File("temp");
      File[] subFiles = tempFileStore.listFiles();

      //If there are no sub files return
      if(subFiles == null) return;

      //Delete the files
      for(File file:subFiles){
         if(file.getName().endsWith(".xml")){
            file.delete();
         }
      }
   }

   /**
    * Moves files from the temporary file location
    * to a new directory
    * @param directory The directory to move to
    */
   public static boolean moveTempFiles(String directory){
      //Get the temporary file store where the game is held and return if for some reason it does not exist
      File tempFileStore = new File("temp");
      if(!tempFileStore.exists()) return false;

      //Get the rounds of the game
      File[] rounds = tempFileStore.listFiles();

      //If the folder is empty return
      if(rounds == null) return false;

      //Create a File object with the directory where the game will be saved, create the directory if it does not exist
      File saveLoc = new File(directory);
      if(!saveLoc.exists()) {
         boolean success = saveLoc.mkdirs();
         if(!success) return false;
      }

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
    * @param file File
    * @return Game object or null if unsuccessful
    */
   private static Game load(File file){
      Game game;
      JAXBContext jaxbContext;

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
