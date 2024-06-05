/*
 * @(#) ReplayGame.java 1.5 2023/03/13.
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.gp02.chesstutor.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import uk.ac.aber.cs221.gp02.chesstutor.game.Game;
import uk.ac.aber.cs221.gp02.chesstutor.gamesave.GameSaveManager;
import uk.ac.aber.cs221.gp02.chesstutor.gui.board.*;


/**
 * A class for the replay game's Graphical User Interface (GUI).
 *
 * @author Bilal [biy1]
 * @author Micah [mib60]
 * @version 0.1 - Initial development
 * @version 0.2 - Fixed everything
 * @version 0.3 - Added style and fixed display.
 * @version 0.4 - Added prompt for out of bounds clicks and fixed turn box not displaying on first round
 */


public class ReplayGame{


   /**
    * Method to initiate the Replay Game screen.
    * <p></p>
    * @param gameGUI GameGui
    * @param replay BorderPane
    * @param primaryStage primaryStage
    * @param replayGame Game
    *The function sets up the screen and organises the buttons, labels and board from previous game
    */

   public static void gameReplay(GameGui gameGUI, BorderPane replay, Stage primaryStage, Game replayGame, String saveDirectory){

      int numRounds = GameSaveManager.getNumRoundsInReplay(saveDirectory);
      final int[] currentRound = {0};

      //Create the board
      BoardGui.createBoard(replayGame);
      GridPane board = BoardGui.getBoard();

      Button quit = new Button("Quit"); //New button fo user/s to be able to quit game
      quit.getStyleClass().add("buttons"); //Assigns style of "buttons" from styles.css fle.

      //Set the action for quitting the replay
      quit.setOnAction(event -> gameGUI.initMainMenu(primaryStage));

      Label blackCaptures = new Label("Captured Black Pieces:"); //New label to let user/s know whose captured pieces are stored.
      blackCaptures.getStyleClass().add("captureBoxLabels"); //Assigns the label style to "captureBoxLabels" from styles.css file.
      //Creates a new VBox to occupy the left segment of the BoarderPane and adds content to it
      VBox left = new VBox(25,blackCaptures, RefreshGui.createCapturedWhitePiecesPane());
      left.setAlignment(Pos.CENTER); //Centres the left VBox.
      left.getStyleClass().add("game_VBoxes_left");

      //Create the left arrow
      ImageView leftArrowImage = new ImageView(new Image("/uk/ac/aber/cs221/gp02/chesstutor/gui/util/left_arrow.png"));
      leftArrowImage.setFitHeight(40);
      leftArrowImage.setFitWidth(50);
      Button leftArrow = new Button("Left");
      leftArrow.setGraphic(leftArrowImage);
      leftArrow.setMinSize(50, 40);
      leftArrow.setMaxSize(50, 40);
      leftArrow.setOnAction(event -> currentRound[0] = goToReplayRound(currentRound[0], -1, numRounds, saveDirectory, replay, left));

      //Create the right arrow
      ImageView rightArrowImage = new ImageView(new Image("/uk/ac/aber/cs221/gp02/chesstutor/gui/util/right_arrow.png"));
      rightArrowImage.setFitHeight(40);
      rightArrowImage.setFitWidth(50);
      rightArrowImage.resize(50, 40);
      Button rightArrow = new Button("Right");
      rightArrow.setGraphic(rightArrowImage);
      rightArrow.setMinSize(50, 40);
      rightArrow.setMaxSize(50, 40);
      rightArrow.setOnAction(event -> currentRound[0] = goToReplayRound(currentRound[0], 1, numRounds, saveDirectory, replay, left));

      Label whiteCaptures = new Label("Captured White Pieces:"); //New label to let user/s know whose captured pieces are stored.
      whiteCaptures.getStyleClass().add("captureBoxLabels"); //Assigns the label style to "captureBoxLabels" from styles.css file.


      //Creates a new VBox to occupy the right and adds content to it
      VBox right = new VBox(25,quit,whiteCaptures,RefreshGui.createCapturedBlackPiecesPane());
      right.setAlignment(Pos.CENTER); //Centres the right VBox.
      right.getStyleClass().add("game_VBoxes");
      //Creates a new VBox to occupy the top BoarderPane and adds to content to it
      HBox top = new HBox(25,leftArrow,rightArrow,RefreshGui.whoseTurnNameBox());
      top.setAlignment(Pos.CENTER); //Centres the top HBox
      //Sets padding of 10px at the top of BoarderPane
      replay.setPadding(new Insets(10,0,0,0));
      replay.setTop(top); //Sets the replay game screen top to top HBox
      replay.setLeft(left); //Sets the replay game screen left to left VBox
      replay.setRight(right); //Sets the replay game screen right to right VBox
      replay.setCenter(board); //Sets the replay game screen centre to board

      goToReplayRound(currentRound[0], 0, numRounds, saveDirectory, replay, left);
   }

   /**
    * Method to increment or decrement round count
    * <p></p>
    * **/
   private static int goToReplayRound(int currentRound, int roundChange, int numRounds, String saveDirectory, BorderPane replay, VBox left){
      int newRound = currentRound+roundChange; //Variable to store the new round count

      //If the round count is
      if(newRound < 0){
         Alert round = new Alert(Alert.AlertType.ERROR);
         round.setTitle(null);
         round.setHeaderText(null);
         round.setContentText("Reached start of the replay");
         round.showAndWait();

         return currentRound;
      }
      else if(newRound > numRounds-1){
         Alert round = new Alert(Alert.AlertType.ERROR);
         round.setTitle(null);
         round.setHeaderText(null);
         round.setContentText("Reached the end of the replay");
         round.showAndWait();

         return currentRound;
      }

      replay.setLeft(left);
      currentRound = newRound;
      Game replayGame = GameSaveManager.loadReplayRound(saveDirectory, currentRound);
      BoardGui.createBoard(replayGame);
      GridPane board = BoardGui.getBoard();
      replay.setCenter(board);
      return newRound;
   }
}
