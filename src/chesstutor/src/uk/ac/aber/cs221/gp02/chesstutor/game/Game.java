/*
 * @(#) Game.java 1.2 2023/05/03
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.gp02.chesstutor.game;

import uk.ac.aber.cs221.gp02.chesstutor.gamesave.GameSaveManager;
import uk.ac.aber.cs221.gp02.chesstutor.util.Color;
import javax.xml.bind.annotation.*;

/**
 * A class that represents the Game
 * <p></p>
 * This stores all the game data. Such as the Board, both Players, who's turn it is, and roundCount
 *
 * @author Michael [mjs36]
 * @author Micah [mib60]
 * @version 0.1 initial development
 * @version 0.2 changed to use static variables for enPassantList and roundCount
 * @version 0.3 basic javadoc added
 * @version 0.4 removed static variables and enPassant - moved to use Board class (roundCount no longer needed for
 * enPassant solution)
 * @version 0.5 Added XML annotations, saveDirectory variable and saveDirectory getter/setter - mib60
 * @version 0.6 complete Javadoc added
 * @version 0.7 added changing color to nextRound
 * @version 0.8 Added temp folder as default save directory, made nextRound call the game saving function, made setSaveDirectory move files from before the game was saved
 * @version 0.9 Game now saves the first round
 * @version 1.0 Made board final
 * @version 1.1 Added save in the constructor to save initial board state
 * @version 1.2 changed to be BLACK and 0 at start - updated to white before playing - due to Save/Load
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Game { //Marks the class as an XML element

   @XmlElement(name = "board") //Save this to the XML save file
   private final Board board;

   @XmlElement(name = "whitePlayer") //Save this to the XML save file
   private Player whitePlayer;

   @XmlElement(name = "blackPlayer") //Save this to the XML save file
   private Player blackPlayer;

   @XmlElement(name = "turn") //Save this to the XML save file
   private Color turn;

   @XmlElement(name = "roundCount") //Save this to the XML save file
   private int roundCount;

   @XmlElement(name = "saveDirectory") //Save this to the XML save file
   private String saveDirectory;

   /**
    * Constructor
    * Creates new Game object
    * <p></p>
    * Sets turn to BLACK and roundCount to 0
    */
   public Game() {
      board = new Board();
      turn = Color.BLACK; //initial round used to save board, moved to WHITE before user takes turn
      roundCount = 0;
      if(saveDirectory == null) saveDirectory = "temp";
   }

   /**
    * Getter
    * Gets the board field
    *
    * @return Board
    */
   public Board getBoard() {
      return board;
   }

   /**
    * Setter
    * Sets the White Player
    * <p></p>
    * Pass in name then creates new Player object
    */
   public void setWhitePlayer(String name) {
      whitePlayer = new Player(Color.WHITE, name);
   }

   /**
    * Setter
    * Sets the Black Player
    * <p></p>
    * Pass in name then creates new Player object
    */
   public void setBlackPlayer(String name) {
      blackPlayer = new Player(Color.BLACK, name);
   }

   /**
    * Getter
    * Gets the whitePlayer
    *
    * @return Player
    */
   public Player getWhitePlayer() {
      return whitePlayer;
   }

   /**
    * Getter
    * Gets the blackPlayer
    *
    * @return Player
    */
   public Player getBlackPlayer() {
      return blackPlayer;
   }

   /**
    * Getter
    * Gets the turn field
    *
    * @return Color
    */
   public Color getTurn() {
      return turn;
   }

   /**
    * Setter
    * Sets the turn field
    */
   public void setTurn(Color turn) {
      this.turn = turn;
   }

   /**
    * Getter
    * Gets the roundCount field
    *
    * @return Color
    */
   public int getRoundCount() {
      return roundCount;
   }

   /**
    * Setter
    * Sets the roundCount to the next round
    * Also sets the turn to the opposite colour
    */
   public void nextRound() {

      GameSaveManager.saveOngoingGame(this);

      roundCount++;
      this.turn = (getTurn() == Color.WHITE) ?  Color.BLACK : Color.WHITE;
   }

   /**
    * Getter
    * Gets the saveDirectory field
    *
    * @return String
    */
   public String getSaveDirectory() {
      return saveDirectory;
   }

   /**
    * Setter
    * Sets the saveDirectory field to passed String
    */
   public void setSaveDirectory(String saveDirectory) {
      this.saveDirectory = saveDirectory;

      //Move any existing game files into the new directory
      if(roundCount > 0) GameSaveManager.moveTempFiles(saveDirectory);
   }
}
