/*
 * @(#) ReplayGame.java 0.6 2023/05/04
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.gp02.chesstutor.gui;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import uk.ac.aber.cs221.gp02.chesstutor.game.Game;
import uk.ac.aber.cs221.gp02.chesstutor.game.Square;
import uk.ac.aber.cs221.gp02.chesstutor.gamesave.GameSaveManager;
import uk.ac.aber.cs221.gp02.chesstutor.gui.board.*;
import uk.ac.aber.cs221.gp02.chesstutor.moves.CheckChecker;
import uk.ac.aber.cs221.gp02.chesstutor.util.Type;


/**
 * A class for the replay game's Graphical User Interface (GUI).
 *
 * @author Bilal [biy1]
 * @author Micah [mib60]
 * @author Michael [mjs36]
 * @version 0.1 - Initial development
 * @version 0.2 - Fixed everything
 * @version 0.3 - Added style and fixed display.
 * @version 0.4 - Added prompt for out of bounds clicks and fixed turn box not displaying on first round
 * @version 0.5 - Added more comments
 * @version 0.6 - Added Check/Checkmate highlighting - mjs36
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

      //Get the number of rounds in the replay and set the currentRound to 0
      int numRounds = GameSaveManager.getNumRoundsInReplay(saveDirectory);
      final int[] currentRound = {0}; //An array of 1 integer so that the lambda functions for the buttons can access it

      //Create the board
      BoardGui.createBoard(replayGame);
      GridPane board = BoardGui.getBoard();

      Button quit = new Button("Quit"); //New button fo user/s to be able to quit game
      quit.getStyleClass().add("buttons"); //Assigns style of "buttons" from styles.css fle.

      //Set the action for quitting the replay
      quit.setOnAction(event -> gameGUI.initMainMenu(primaryStage));

      //Create the captured black pieces box
      Label blackCaptures = new Label("Captured Black Pieces:"); //New label to let user/s know whose captured pieces are stored.
      blackCaptures.getStyleClass().add("captureBoxLabels"); //Assigns the label style to "captureBoxLabels" from styles.css file.

      //Create the captured white pieces box
      Label whiteCaptures = new Label("Captured White Pieces:"); //New label to let user/s know whose captured pieces are stored.
      whiteCaptures.getStyleClass().add("captureBoxLabels"); //Assigns the label style to "captureBoxLabels" from styles.css file.

      //Creates a new VBox to occupy the left segment of the BoarderPane and adds content to it
      VBox left = new VBox(25,blackCaptures, RefreshGui.createCapturedWhitePiecesPane());
      left.setAlignment(Pos.CENTER); //Centres the left VBox.
      left.getStyleClass().add("gameVBoxesLeft");

      //Create the left arrow
      ImageView leftArrowImage = new ImageView(new Image("/uk/ac/aber/cs221/gp02/chesstutor/gui/util/left_arrow.png"));
      leftArrowImage.setFitHeight(40);
      leftArrowImage.setFitWidth(50);
      Button leftArrow = new Button("Left");
      leftArrow.setStyle("-fx-background-color: transparent;");
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
      rightArrow.setStyle("-fx-background-color: transparent;");
      rightArrow.setGraphic(rightArrowImage);
      rightArrow.setMinSize(50, 40);
      rightArrow.setMaxSize(50, 40);
      rightArrow.setOnAction(event -> currentRound[0] = goToReplayRound(currentRound[0], 1, numRounds, saveDirectory, replay, left));

      //Creates a new VBox to occupy the right and adds content to it
      VBox right = new VBox(25,quit,whiteCaptures,RefreshGui.createCapturedBlackPiecesPane());
      right.setAlignment(Pos.CENTER); //Centres the right VBox.
      right.getStyleClass().add("gameVBoxes");

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
    * Function to increment the round count and display the new round of the replay
    * @param currentRound The current round
    * @param roundChange The round change (-1 for left button, 1 for right button)
    * @param numRounds The number of rounds in the replay
    * @param saveDirectory The save directory of the replay
    * @param replay The BorderPane for the replay
    * @param left The left VBox of the screen
    * @return Returns the new current round
    */
   private static int goToReplayRound(int currentRound, int roundChange, int numRounds, String saveDirectory, BorderPane replay, VBox left){
      int newRound = currentRound+roundChange; //Variable to store the new round count

      //If the new round count is less than 0 alert the user that they have reached the start of the replay
      if(newRound < 0){
         Alert round = new Alert(Alert.AlertType.WARNING);
         round.setTitle(null);
         round.setHeaderText(null);
         round.setContentText("Reached start of the replay");
         round.showAndWait();

         return currentRound;
      }
      //If the new roudn count is more than the last round alert the user that they have reached the end of the replay
      else if(newRound > numRounds-1){
         Alert round = new Alert(Alert.AlertType.WARNING);
         round.setTitle(null);
         round.setHeaderText(null);
         round.setContentText("Reached the end of the replay");
         round.showAndWait();

         return currentRound;
      }

      //Set the current round to the new round
      currentRound = newRound;

      //Load the replay back-end game
      Game replayGame = GameSaveManager.loadReplayRound(saveDirectory, currentRound);

      //Get generate the board GUI and get it
      BoardGui.createBoard(replayGame);
      GridPane board = BoardGui.getBoard();

      //Show the Check/Checkmate GUI highlight
      Square[][] array = replayGame.getBoard().getBoardArray();
      String kingColor = "";
      outerLoop:
      for (int row = 0; row < 8; row++) {
         for (int col = 0; col < 8; col++) {
            if (array[row][col].hasPiece() && array[row][col].getPiece().getPieceType() == Type.KING) {
               if (array[row][col].getPiece().getPieceColor() != replayGame.getTurn()) {
                  if (CheckChecker.checkChecker(replayGame.getBoard(), row, col, array[row][col].getPiece().getEnemyPieceColor()).isEmpty()) break outerLoop;
                  kingColor = (array[row][col].getPiece().getPieceColor() == uk.ac.aber.cs221.gp02.chesstutor.util.Color.BLACK) ?
                          kingColor.concat("black") : kingColor.concat("white");
                  ObservableList<Node> children = board.getChildren();
                  outerGuiLoop:
                  for(Node node : children) {
                     if(node instanceof StackPane) {
                        ObservableList<Node> stackChildren = ((StackPane) node).getChildren();
                        for(Node stack : stackChildren) {
                           if (stack instanceof ImageView) {
                              String url = ((ImageView) stack).getImage().impl_getUrl();
                              if (url.contains("king") && url.contains(kingColor)) {
                                 StackPane piece = (StackPane) stack.getParent();
                                 piece.setStyle("-fx-background-color: #fcba03");
                                 break outerGuiLoop;
                              }
                           }
                        }
                     }
                  }
                  break outerLoop;
               }
            }
         }
      }
      kingColor = "";
      outerLoop:
      for (int row = 0; row < 8; row++) {
         for (int col = 0; col < 8; col++) {
            if (array[row][col].hasPiece() && array[row][col].getPiece().getPieceType() == Type.KING) {
               if (array[row][col].getPiece().getPieceColor() != replayGame.getTurn()) {
                  if (!CheckChecker.checkMateChecker(replayGame.getBoard(),row,col)) break outerLoop;
                  kingColor = (array[row][col].getPiece().getPieceColor() == uk.ac.aber.cs221.gp02.chesstutor.util.Color.BLACK) ?
                          kingColor.concat("black") : kingColor.concat("white");
                  //find piece on gui
                  ObservableList<Node> children = board.getChildren();
                  outerGuiLoop:
                  for(Node node : children) {
                     if(node instanceof StackPane) {
                        ObservableList<Node> stackChildren = ((StackPane) node).getChildren();
                        for(Node stack : stackChildren) {
                           if (stack instanceof ImageView) {
                              String url = ((ImageView) stack).getImage().impl_getUrl();
                              if (url.contains("king") && url.contains(kingColor)) { //is utility
                                 StackPane piece = (StackPane) stack.getParent();
                                 piece.setStyle("-fx-background-color: #8f0101");
                                 break outerGuiLoop;
                              }
                           }
                        }
                     }
                  }
                  break outerLoop;
               }
            }
         }
      }



      //Set the board to not be clickable
      board.setMouseTransparent(true);

      //Set the nodes of the borderpane
      replay.setCenter(board);
      replay.setLeft(left);

      return newRound;
   }
}
