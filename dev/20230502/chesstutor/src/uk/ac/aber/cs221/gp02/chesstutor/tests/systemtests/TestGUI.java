/*
 * @(#) GameGui.java 1.5 2023/03/13.
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.gp02.chesstutor.tests.systemtests;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import uk.ac.aber.cs221.gp02.chesstutor.game.Game;
import uk.ac.aber.cs221.gp02.chesstutor.gamesave.FilePicker;
import uk.ac.aber.cs221.gp02.chesstutor.gamesave.GameSaveManager;
import uk.ac.aber.cs221.gp02.chesstutor.gui.board.*;
import uk.ac.aber.cs221.gp02.chesstutor.util.Color;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * A GUI class for demonstrating system tests
 *
 * @author Lance [lvs1]
 * @version 0.1 - Initial development.
*/
public class TestGUI {
   private final Stage stage; //Initiates a Stage for the GUI.
   private static StackPane mainStack;
   private static BorderPane borderMenu;
   private static BorderPane borderGame;
   private static VBox checkmate;

   private Game newGame;
   private String player1; //String to hold player 1 name.
   private String player2; //String to hold player 2 name.

   private Game previousGame = new Game(); //Initializes a new game to be used for the class.

   //constructor for GameGui.
   public TestGUI(Stage stage) {
      this.stage = stage;
      stage.setTitle("Chess Tutor"); //Sets title for the Stage.
      stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("./util/stage_icon_picture.png")))); //Icon picture is free and non-copyrighted. Retrieved from https://icons8.com/icons/set/chess.
   }

   /**
    * Method for setting functionality for the user input in the startGameMenu.
    * Has prevention for empty text fields and check boxes and provides error messages if
    * eiter or both are not correct.
    * <p></p>
    *
    * @param name1            TextField
    * @param name2            TextField
    * @param colorSelectWhite CheckBox
    * @param colorSelectBlack CheckBox
    * @param start            Button
    */
   public void Players(TextField name1, TextField name2, RadioButton colorSelectWhite, RadioButton colorSelectBlack, Button start) {
      borderGame = new BorderPane(); //Initiates a new borderPane for the game screen.

      //Statement to get the names form the text-fields if they are not empty.
      if (name1.getText().isEmpty() || name2.getText().isEmpty()) {
         Alert noNames = new Alert(Alert.AlertType.ERROR); //Initiates a new Alert named noNames.
         noNames.setTitle("Naming Error!"); //Sets the title for the alert.
         noNames.setHeaderText(null); //Sets it to having no header text.
         noNames.setContentText("Please make sure you enter a name in each text box."); //shows the error message.
         noNames.showAndWait(); //Waits for the user to acknowledge the error message and clicks "Ok".
      }
      //Statement to check if no checkbox or if both checkboxes have been ticket. Prompts the user/s with an error message.
      if (!colorSelectWhite.isSelected() && !colorSelectBlack.isSelected()) {
         Alert noColorSelected = new Alert(Alert.AlertType.ERROR); //Initiates a new Alert named noColorSelected.
         noColorSelected.setTitle("Color Selection Error!"); //Sets the title for the alert.
         noColorSelected.setHeaderText(null); //Sets it to not have a header text.
         noColorSelected.setContentText("Please make sure you tick one box to choose starting color."); //shows the error message.
         noColorSelected.showAndWait(); //Waits for the user to acknowledge the error message and clicks "Ok".
      }

      player1 = name1.getText().trim(); //Stores text-box input from name1 as player1.
      player2 = name2.getText().trim(); //Stores text-box input from name2 as player2.
      //Statement to assign the player 1 to White due to the white checkbox being ticked and player 2 to black.
      if (colorSelectWhite.isSelected() && (!name1.getText().isEmpty() && !name2.getText().isEmpty())) {
         newGame.setWhitePlayer(player1); //Assigns the player1 to white.
         newGame.setBlackPlayer(player2); //Assign the player2 to black.
         initGame(stage); //Runs the initialiseGame function to start a new game.
      }
      //Statement to assign the player 2 to White due to the black checkbox being ticked and player 1 to black.
      if (colorSelectBlack.isSelected() && (!name1.getText().isEmpty() && !name2.getText().isEmpty())) {
         newGame.setWhitePlayer(player2); //Assigns the player2 to white.
         newGame.setBlackPlayer(player1); //Assign the player1 to black.
         initGame(stage); //Runs the initialiseGame function to start a new game.
      }
   }

   /**
    * Method to initiate the Game screen.
    * <p></p>
    * @param primaryStage Stage
    *The function sets up the screen and organises the buttons, labels and board
    */

   public void initGame(Stage primaryStage) {
      //newGame.getBoard().boardReset();
      //the following next 8 lines will be button initialization and style assignation.
      newGame = new Game(); //ensures fresh copy of game when at main menu
      Button btnDraw = new Button("Draw"); //New button for user/s to suggest a draw.
      btnDraw.getStyleClass().add("buttons_gameMenu"); //Sets the style for the button to "buttons_gameMenu" from styles.css file.
      Button btnResign = new Button("Resign"); //New button for user/s to resign.
      btnResign.getStyleClass().add("buttons_gameMenu"); //Sets the style for the button to "buttons_gameMenu" from styles.css file.
      Button btnSave = new Button("Save");  //New button for user/s to save game.
      btnSave.getStyleClass().add("buttons_gameMenu"); //Sets the style for the button to "buttons_gameMenu" from styles.css file.
      Button btnQuit = new Button("Quit");  //New button for user/s to quit game.
      btnQuit.getStyleClass().add("buttons_gameMenu"); //Sets the style for the button to "buttons_gameMenu" from styles.css file.

      gameWinner();

      btnDraw.setOnAction(event -> requestDraw());      //When button is pressed, the function requestDraw is run.
      btnResign.setOnAction(event -> resign());       //When button is pressed, the function resign is run.

      //When button is pressed, the file directory where you can store the game is opened.
      btnSave.setOnAction(event -> {
         //Open the file picker and get the selected file
         FilePicker filePicker = new FilePicker(primaryStage);
         File file = filePicker.openFilePicker();

         if(file != null){
            //Set the game's savings directory to the new file
            newGame.setSaveDirectory(file.getAbsolutePath());
         }
      });

      btnQuit.setOnAction(event -> backToMain());       //When button is pressed, the function to take you back to the main menu is run.

      Label whiteCaptures = new Label("Captured White Pieces:"); //New label to let user/s know whose captured pieces are stored.
      whiteCaptures.getStyleClass().add("captureBoxLabels"); //Assigns the label style to "captureBoxLabels" from styles.css file.
      Label blackCaptures = new Label("Captured Black Pieces:"); //New label to let user/s know whose captured pieces are stored.
      blackCaptures.getStyleClass().add("captureBoxLabels"); //Assigns the label style to "captureBoxLabels" from styles.css file.

      VBox left = new VBox(25, btnDraw, btnResign,whiteCaptures, RefreshGui.createCapturedWhitePiecesPane()); //New VBox called left to store btnDraw, btnResign and the captured white pieces.
      left.setAlignment(Pos.CENTER); //Center aligns the left VBox to be centered.
      left.getStyleClass().add("game_VBoxes"); // Assigns the style of "game_VBoxes" from styles.css file
      VBox right = new VBox(25, btnSave, btnQuit,blackCaptures, RefreshGui.createCapturedBlackPiecesPane()); //New VBox called right to store btnSave, btnQuit and the captured black pieces.
      right.setAlignment(Pos.CENTER); //Center aligns the right VBox to be centered.
      right.getStyleClass().add("game_VBoxes"); // Assigns the style of "game_VBoxes" from styles.css file.

      HBox top = new HBox(80,RefreshGui.addPromotionBox(), RefreshGui.whoseTurnNameBox());

      borderGame.setTop(top); //Sets the top of  the boarderPane to be the namesAndPromotion HBox.
      borderGame.setLeft(left); //Sets the left of the borderPane to be the left VBox.
      borderGame.setRight(right); //Sets the right of the borderPane to be the right VBox.

      Pane boardPane = new Pane(); //Creates a new Pane called borderPane
      boardPane.getChildren().add(BoardGui.createBoard(newGame)); //Adds the boardFunc() to show in the boardPane.

      borderGame.setCenter(boardPane); //Sets the center rof the game BoarderPane to be the boardPane.

      Scene gameScene = new Scene(borderGame, 889, 500); //Creates a new scene for the gameScene and sets its dimensions.
      gameScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("./util/styles.css")).toExternalForm());    //Assigns the file "styles.css" to the screen.

      primaryStage.setScene(gameScene); //Sets the scene to be shown to be gameScene.
      primaryStage.show(); //Shows the gameScene

      //Clears the temporary game store folder
      GameSaveManager.clearTempFolder();
   }

   /**
    * Method to offer the player a draw
    * <p></p>
    * Creates a new screen where the board is located and asks if opponent wants to
    * accept a draw.
    */
   public void requestDraw(){
      Label dawOffer = new Label(BoardGui.whoseTurnName()+" has offered a draw");  //Label to inform about who is offering the draw.
      dawOffer.getStyleClass().add("labels"); //Sets the style for the label to "labels" from styles.css file.

      Button btnAccept = new Button("Accept"); //Button for accepting the draw.
      btnAccept.getStyleClass().add("buttons"); //Assigns the style for the button to "buttons" from styles.css file.
      //If button "Accept" is pressed game ends in a draw
      btnAccept.setOnAction(event1 -> {
         Label gameDraw = new Label("Game ended in a Draw"); //New label to inform about the draw.
         gameDraw.getStyleClass().add("labels"); //Sets the style for the button to "labels" from styles.css file.

         Button btnSave = new Button("Save Game"); //Button for saving game the draw.
         btnSave.getStyleClass().add("buttons"); //Assigns the style for the button to "buttons" from styles.css file.
         btnSave.setOnAction(event2 -> {
            try {
               Desktop.getDesktop().open(new File(".")); //Tries to open directory.
            } catch (IOException e) {
               e.printStackTrace(); //Prints Stack trace if there is an error in opening directory.
            }
         });
         Button btnQuitGame = new Button("Quit Game"); //Button for quiting the draw.
         btnQuitGame.getStyleClass().add("buttons"); //Assigns the style for the button to "buttons" from styles.css file.
         //btnQuitGame.setOnAction(event3 -> initMainMenu(stage)); //If "Quit Game" is pressed, user/s are returned to main menu.

         VBox resultDraw = new VBox(25, gameDraw, btnSave, btnQuitGame); //Creates a new VBox to show when a draw has been selected.
         resultDraw.setAlignment(Pos.CENTER); //Center positions the content.
         borderMenu.setCenter(resultDraw); //Sets the center of the screen to be the resultDraw VBox.
      });

      Button btnDecline = new Button("Decline"); //Button for declining the draw.
      btnDecline.getStyleClass().add("buttons"); //Assigns the style for the button to "buttons" from styles.css file.
      btnDecline.setOnAction(event1 -> borderGame.setCenter(BoardGui.createBoard(newGame))); //When btnDecline is pressed,the board returns to teh screen.

      VBox draw = new VBox(25, dawOffer, btnAccept, btnDecline); //New VBox for the draw content.
      draw.setAlignment(Pos.CENTER); //Center positions the draw VBox.
      borderMenu.setCenter(draw); //Sets the center of the screen to be the draw VBox content.
   }

   /**
    * Method for player to decide to resign.
    * <p></p>
    *
    *
    * */
   public void resign(){
      Label resign = new Label("Are you sure you want to resign?"); //Label to ensure the user wants to resign.
      resign.getStyleClass().add("labels"); //Sets the style for the label to "labels" from styles.css file.

      Button btnYes = new Button("Yes"); //Button for selecting Yes.
      btnYes.getStyleClass().add("buttons"); //Assigns the style for the button to "buttons" from styles.css file.
      Button btnNo = new Button("No"); //Button for selecting No.
      btnNo.getStyleClass().add("buttons"); //Assigns the style for the button to "buttons" from styles.css file.

      btnYes.setOnAction(event -> winByResignation(stage)); //Runs the winByResignation() function when btnYes is chosen.

      VBox choice = new VBox(25, resign, btnYes,btnNo); //Creates a new VBox called choice to hold the resignation screen content.
      choice.setAlignment(Pos.CENTER); //Center aligns the choice VBox.
      borderMenu.setCenter(choice); //Sets the center of the screen to be the choice VBox

      btnNo.setOnAction(event -> borderGame.setCenter(BoardGui.createBoard(newGame))); //When selection "No" is chosen, the chess board returns on the screen.
   }

   /**
    * Method for player to win by resignation.
    * <p></p>
    * @param primaryStage Stage.
    * Lets user resign the game and then save the current game or quit.
    * */
   public void winByResignation(Stage primaryStage){
      String winningPlayer; //Sting to hold wining player
      //Statement to see who the current player is and who will be wining to resignation.
      if(BoardGui.whoseTurnName().equals(newGame.getBlackPlayer().getName())){
         winningPlayer = newGame.getWhitePlayer().getName(); //White player wins if black resigns.
      }else {
         winningPlayer = newGame.getBlackPlayer().getName(); //Black player wins as white resigns.
      }
      //Label to inform user/s about who has won.
      Label prompt = new Label(winningPlayer + " wins! "  +  BoardGui.whoseTurnName() + " has resigned");
      prompt.getStyleClass().add("labels"); //Sets the style for the label to "labels" from styles.css file.

      Button btnSaveGame = new Button("Save Replay");
      btnSaveGame.getStyleClass().add("buttons");
      btnSaveGame.setOnAction(event -> {

         FilePicker filePicker = new FilePicker(primaryStage);
         File file = filePicker.openFilePicker();

         if(file != null){
            if(newGame.getSaveDirectory().equals("temp")){
               GameSaveManager.moveTempFiles(file.getAbsolutePath());
            }

         }
      });

      Button btnQuitGame = new Button("Quit Game"); //Button for Quiting game
      btnQuitGame.getStyleClass().add("buttons"); //Assigns the style for the button to "buttons" from styles.css file.
      //btnQuitGame.setOnAction(event -> initMainMenu(primaryStage)); //When btnQuitGame is pressed, the user/s are returned to the main menu.

      VBox resignationBox = new VBox(25, prompt, btnSaveGame, btnQuitGame); //Creates a new VBox called resignationBox to store the label and button content.
      resignationBox.setAlignment(Pos.CENTER); //Center aligns the content in resignationBox.
      borderMenu.setCenter(resignationBox); //Sets the center of the game screen to the resignationBox.
   }

   /**
    * Method to bring you back to the main menu
    * <p></p>
    * Prompts the player weather he wants to quit the game or not. If so,
    * the player/s is/are returned to the main menu
    * */
   public void backToMain(){
      Label prompt = new Label("Are you sure you want to quit game?");    //new label to prompt the player/s if they want to quit the game
      prompt.getStyleClass().add("labels"); //Assigns the style of "labels" to the label from styles.css file.
      Button btnYes = new Button("Yes"); //New button for section of yes
      btnYes.getStyleClass().add("buttons"); //Assigns the style of "buttons" to the button from styles.css file.

      Button btnNo = new Button("No"); //New button for section of no
      btnNo.getStyleClass().add("buttons"); //Assigns the style of "buttons" to the button from styles.css file

      VBox quitGamePrompt = new VBox(25, prompt,btnYes, btnNo); //Creating a new VBox to hold the label and two buttons
      quitGamePrompt.setAlignment(Pos.CENTER); //Center positions the VBox
      borderMenu.setCenter(quitGamePrompt); //Sets the center of  the screen to the quitGamePrompt, replacing the board.
      //btnYes.setOnAction(event -> initMainMenu(stage));//When "Yes" is chosen, user/s get taken back to the main menu
      btnNo.setOnAction(event -> borderGame.setCenter(BoardGui.createBoard(newGame))); //When "No" is chosen, user/s get taken back to the board
   }

   /**
    * Method to bring you back to the main menu/save game at checkmate
    * <p></p>
    * Prompts the player weather he wants to save game and quit or quit the game.
    * The player/s is/are returned to the main menu.
    * */
   public void gameWinner() {
      Label winner = new Label("Checkmate! " + RefreshGui.whoseTurnNameBox() + " wins"); //Label to display winner of game.
      winner.getStyleClass().add("playerLabel;"); //Assigns the style of "labels" to the label from styles.css file.
      Button saveGame = new Button("Save Replay"); //New button for saving game
      saveGame.getStyleClass().add("buttons"); //Assigns the style of "labels" to the label from styles.css file.
      Button quitGame = new Button("Quit Game"); //New button for quiting game/ going back to main menu.
      quitGame.getStyleClass().add("buttons"); //Assigns the style of "labels" to the label from styles.css file.

      //saveGame.setOnAction(event -> initMainMenu(stage)); //When "saveGame" is selected, game is saved and user/s get taken back to the main menu.
      //quitGame.setOnAction(event -> initMainMenu(stage)); //When "quitGame" is selected, user/s get taken back to the main menu.

      checkmate = new VBox(25, winner, saveGame, quitGame);
      checkmate.setAlignment(Pos.CENTER);
      //game.setCenter(checkmate);
   }

   public static BorderPane getBorderGame() {
      return borderGame;
   }

   public static VBox getCheckmate() {
      return checkmate;
   }
}

