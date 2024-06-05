/*
 * @(#) GameSaveManager.java 0.2 2023/03/15
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.gp02.chesstutor.gamesave;

import com.sun.org.apache.xerces.internal.parsers.XMLDocumentParser;
import com.sun.org.apache.xerces.internal.parsers.XMLParser;
import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;
import com.sun.xml.internal.ws.developer.JAXBContextFactory;
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
import java.net.URI;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Micah [mib60]
 * @author Michael [mjs36]
 * @version 0.1 Initial Development
 * @version 0.2 complete Javadoc added by mjs36
 * @version 0.3 Added clearTempFolder and moveTempFiles
 * @version 0.4 Added info file to save folders
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

      if(file.listFiles() == null){
         System.out.println("Directory contains no files.");
         return null;
      }

      List<File> subFiles = Arrays.asList(file.listFiles());

      File infoFile = null;

      for(File currentFile:subFiles){
         if(currentFile.getName().equals("info.xml")){
            infoFile = currentFile;
            break;
         }
      }

      if(infoFile == null){
         System.out.println("Directory does not contain info file");
         return null;
      }


      //Check if info file marks the game as ongoing
      DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder documentBuilder = null;
      Document document = null;

      try {
         documentBuilder = documentBuilderFactory.newDocumentBuilder();
      } catch (ParserConfigurationException e) {
         e.printStackTrace();
         return null;
      }
      try {
         document = documentBuilder.parse(infoFile);
      } catch (SAXException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
         return null;
      }

      String tag = document.getElementsByTagName("type").item(0).getTextContent();

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

      if(game.getRoundCount() == 1){
         File infoFile = new File(game.getSaveDirectory()+"/info.xml");
         try {
            Files.createFile(infoFile.toPath());
         } catch (IOException e) {
            return false;
         }
         DocumentBuilder documentBuilder = null;

         DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
         try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
         } catch (ParserConfigurationException e) {
            System.out.println("Could not create document builder");
            e.printStackTrace();
            return false;
         }

         Document document = documentBuilder.newDocument();
         document.appendChild(document.createElement("type")).appendChild(document.createTextNode("ongoing"));


         Transformer tr = null;
         try {
            tr = TransformerFactory.newInstance().newTransformer();

            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            tr.setOutputProperty(OutputKeys.METHOD,"xml");
            tr.setOutputProperty(OutputKeys.STANDALONE, "yes");

         } catch (TransformerConfigurationException e) {
            e.printStackTrace();
         }
         try {

            StreamResult result = new StreamResult("info.xml");
            tr.transform(new DOMSource(document), new StreamResult(new FileOutputStream(infoFile)));

         } catch (TransformerException | FileNotFoundException e) {
            e.printStackTrace();
         }
      }

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
    * @param game The game to save
    */
   public static boolean saveReplay(Game game, String directory) throws ParserConfigurationException, IOException, SAXException, TransformerException {

      if(game.getSaveDirectory().equals("temp")){
         moveTempFiles(directory);
         game.setSaveDirectory(directory);
      }


      File infoFile = new File(game.getSaveDirectory()+"/info.xml");

      if(!infoFile.exists()){
         System.out.println("Tried to save replay but could not find info file");
         return false;
      }

      DocumentBuilder documentBuilder = null;

      DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

      documentBuilder = documentBuilderFactory.newDocumentBuilder();

      Document document = documentBuilder.parse(infoFile);

      document.getElementsByTagName("type").item(0).setTextContent("replay");

      Transformer tr = TransformerFactory.newInstance().newTransformer();

      tr.transform(new DOMSource(document), new StreamResult(new FileOutputStream(infoFile)));

      return true;
   }

   public static void clearTempFolder(){
      File tempFileStore = new File("temp");
      File[] subFiles = tempFileStore.listFiles();
      if(subFiles == null) return;

      for(File file:subFiles){
         if(file.getName().endsWith(".xml")) file.delete();
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
