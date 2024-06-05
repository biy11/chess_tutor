/*
* @(#) GameGui.java 0.4 2023/03/13.
*
* Copyright (c) 2023 Aberystwyth University.
* All rights reserved.
 */

package uk.ac.aber.cs221.gp02.chesstutor.gui;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import uk.ac.aber.cs221.gp02.chesstutor.game.Game;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
* A class for the Graphical User Interface (GUI).
 *
 * @author Bilal [biy1].
 * @version 0.1 - Initial development.
 * @version 0.2 - Added functionality for buttons.
 * @version 0.3 - Created a Start Game menu.
 * @version 0.4 - Comments added.
 * @version 0.5 - Adjustments made for code readability and GUI size.
 * @version 0.6 - Initialized development on starting game.
 * @version 0.7 - Added button functionality and board to game screen.
 * @version 0.8 - Fixed bugs, warnings and added comments to all code written.
*/
public class GameGui {
   private final Stage stage; //Initiates a Stage for the GUI.
   private String player1; //String to hold player 1 name.
   private String player2; //String to hold player 2 name.

   private Game newGame = new Game(); //Initializes a new game to be used for the class.

   //constructor for GameGui.
   public GameGui(Stage stage) {
      this.stage = stage;
      stage.setTitle("Chess Tutor"); //Sets title for the Stage.
      stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("./util/stage_icon_picture.png")))); //Icon picture is free and non-copyrighted. Retrieved from https://icons8.com/icons/set/chess.
   }

   /**
    * A method to initiate the main menu as well as the Start Game Menu.
    * <p></p>
    *
    * @param primaryStage Stage
    */
   public void initMainMenu(Stage primaryStage) {

      Label title = new Label("Main Menu!");      //creates a new label to be used as title for main menu.
      title.getStyleClass().add("titles");       //sets the style for the label according to the styles.css file.

      //Initialises buttons for the first screen(main menu)
      Button btnStartGame = new Button("New Game"); //Button for new game.
      btnStartGame.getStyleClass().add("buttons");       //sets the style for the button according to the styles.css file.
      Button btnLoadGame = new Button("Load Game"); //Button for Load Game.
      btnLoadGame.getStyleClass().add("buttons");        //sets the style for the button according to the styles.css file.
      Button btnReplayGame = new Button("Replay Game"); // Button for Replay Game.
      btnReplayGame.getStyleClass().add("buttons"); //sets the style for the button according to the styles.css file.
      Button btnQuitGame = new Button("Quit Game"); //Button for Quit Game.
      btnQuitGame.getStyleClass().add("buttons"); //sets the style for the button according to the styles.css file.

      VBox root = new VBox(25, title, btnStartGame, btnLoadGame, btnReplayGame, btnQuitGame);  //Initializes a new VBox named root.
      root.getStyleClass().add("mainBackground");   //Sets the background of root to main_menu_background.jpg.
      root.setAlignment((Pos.CENTER));   //sets the position of root in the center of the screen.

      Scene mainMenu = new Scene(root, 889, 500);    //Initializes a new screen by using root and sets height and width.
      mainMenu.getStylesheets().add(Objects.requireNonNull(getClass().getResource("./util/styles.css")).toExternalForm());    //Assigns the file "styles.css" to the screen.

      StackPane stackPane = new StackPane();   //Initiates a new StackPane.
      stackPane.getStyleClass().add("stackPane");       //Assigns the style of "stackPane" to the stackPane.
      Scene gameMenu = new Scene(stackPane, 889, 500);  //Create a new scene named gameMenu and sets the stackPane as it's root node.
      gameMenu.getStylesheets().add(Objects.requireNonNull(getClass().getResource("./util/styles.css")).toExternalForm());    //Assigns the file "styles.css" to the screen.
      stackPane.getStyleClass().add("startGameBackground");       //Assigns the background to the screen.

      //Assignment of button functions
      btnStartGame.setOnAction(event -> primaryStage.setScene(gameMenu));   //Sets action of the Start Game button to go to the start game menu.
      //Sets the action of opening directory where game that the user/s want to replay for button "Load Game" in Main Menu.
      btnLoadGame.setOnAction(event -> {
         try {
            Desktop.getDesktop().open(new File(".")); //Tries to open directory.
         } catch (IOException e) {
            e.printStackTrace(); //Prints Stack trace if there is an error in opening directory.
         }
      });
      //Sets the action of opening the directory where the completed games are store.
      btnReplayGame.setOnAction(event -> {
         try {
            Desktop.getDesktop().open(new File(".")); //Opens Directory for completed games.
         } catch (IOException e) {
            e.printStackTrace(); //Prints Stack Trance if there is an error in opening Directory.
         }
      });
      btnQuitGame.setOnAction(event -> Platform.exit());   //Assigns function of quiting the GUI if the Quit Game button is pressed.

      //initializes buttons for new game menu
      Label player1Txt = new Label("Player 1:");       //New label indicate where Player1 name should be entered.
      player1Txt.getStyleClass().add("playerLabel");      //Assigns the label the style "PlayerLabel" form the styles.css file.
      TextField player1Name = new TextField();      //Creates a new text filed names player1Name.
      player1Name.getStyleClass().add("textBox");      //Adds the style "textBox" to the textField form styles.css file.

      Label player2Txt = new Label("Player 2");       //Create a new label for Player 2.
      player2Txt.getStyleClass().add("playerLabel");       //Sets style of label according to "playerLabel" from styles.css.
      TextField player2Name = new TextField();       //Creates a new text field for "player2Name".
      player2Name.getStyleClass().add("textBox");       //Adds the style "textBox" to the textField form styles.css file.

      Label chooseColor = new Label("Choose color for player 1");       //New Label to tell user/s to choose a color.
      chooseColor.getStyleClass().add("labels");       //Adds the style "labels" to the textField form styles.css file.

      RadioButton radioButtonForWhite = new RadioButton("White");       //Creates new radioButtons for white.
      RadioButton radioButtonForBlack = new RadioButton("Black");      //Creates new radioButtons for black.
      radioButtonForWhite.getStyleClass().add("radioButtons"); //Assigns the style of "radioButtons" to the RadioButton from styles.css.
      radioButtonForBlack.getStyleClass().add("radioButtons"); //Assigns the style of "radioButtons" to the RadioButton from styles.css.

      ToggleGroup colorSelect = new ToggleGroup();       //Initiates a new toggle group to be used to ensure only one color is selected.
      radioButtonForWhite.setToggleGroup(colorSelect);  //Adds radioButtons for white to toggle group.
      radioButtonForBlack.setToggleGroup(colorSelect);   //Adds radioButtons for black to toggle group.

      Button btnStart = new Button("Start");   //Creates a new Button to start the game.
      btnStart.getStyleClass().add("buttons");  //Sets the style of "buttons" from styles.css file.

      //Sets the action of the "Start" button to run the Players function
      btnStart.setOnAction(event -> Players(player1Name, player2Name, radioButtonForWhite, radioButtonForBlack, btnStart));

      Button btnBack = new Button("Back");   //Creates a new button for going back to main menu.
      btnBack.getStyleClass().add("buttons");    //Sets the style of "buttons" from styles.css file.

      btnBack.setOnAction(event -> primaryStage.setScene(mainMenu));   //Sets the action of the "Back" button to go back to Main Menu.

      //creates a new HBox to be able to have the labels "Black", "White" and the checkboxes on the same line.
      HBox colourCheckers = new HBox(chooseColor, radioButtonForWhite, radioButtonForBlack);
      colourCheckers.setAlignment(Pos.CENTER);  //Center aligns the colourCheckers HBox.

      //Creates a VBox for the startGameMenu and adds all the necessary buttons,labels and checkboxes with the spacing of 25 between them.
      VBox startGameMenu = new VBox(25, player1Txt, player1Name, player2Txt, player2Name, chooseColor, colourCheckers, btnStart, btnBack);
      startGameMenu.setAlignment((Pos.CENTER));   //Aligns all the contents for the startGameScreen to the center.

      stackPane.getChildren().add(startGameMenu);  //Adds the startGameMenu to the stackPane.

      primaryStage.setScene(mainMenu);  //Sets primary stage to scene.
      primaryStage.show();  //Displays the scene when program is run.
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
      BorderPane game = new BorderPane(); //Initiates a new borderPane for the game screen.

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
         initGame(stage, game); //Runs the initialiseGame function to start a new game.
      }
      //Statement to assign the player 2 to White due to the black checkbox being ticked and player 1 to black.
      if (colorSelectBlack.isSelected() && (!name1.getText().isEmpty() && !name2.getText().isEmpty())) {
         newGame.setWhitePlayer(player2); //Assigns the player2 to white.
         newGame.setBlackPlayer(player1); //Assign the player1 to black.
         initGame(stage, game); //Runs the initialiseGame function to start a new game.
      }
   }

   /**
    * Method to initiate the Game screen.
    * <p></p>
    * @param primaryStage Stage
    * @param game BorderPane
    *The function sets up the screen and organises the buttons, labels and board
    */

   public void initGame(Stage primaryStage, BorderPane game) {
//      Label player = new Label(BoardGui.whoseTurnName(newGame) + "'s Turn");       //New label to let user know whose turn it is
//      player.getStyleClass().add("nameLabel"); //Assigns the style of nameLabel to the label from styles.css file.
      //the following next 8 lines will be button initialization and style assignation.
      Button btnDraw = new Button("Draw"); //New button for user/s to suggest a draw.
      btnDraw.getStyleClass().add("buttons_gameMenu"); //Sets the style for the button to "buttons_gameMenu" from styles.css file.
      Button btnResign = new Button("Resign"); //New button for user/s to resign.
      btnResign.getStyleClass().add("buttons_gameMenu"); //Sets the style for the button to "buttons_gameMenu" from styles.css file.
      Button btnSave = new Button("Save");  //New button for user/s to save game.
      btnSave.getStyleClass().add("buttons_gameMenu"); //Sets the style for the button to "buttons_gameMenu" from styles.css file.
      Button btnQuit = new Button("Quit");  //New button for user/s to quit game.
      btnQuit.getStyleClass().add("buttons_gameMenu"); //Sets the style for the button to "buttons_gameMenu" from styles.css file.


      btnDraw.setOnAction(event -> {requestDraw(game); });      //When button is pressed, the function requestDraw is run.
      btnResign.setOnAction(event -> resign(game));       //When button is pressed, the function resign is run.
      //When button is pressed, the file directory where you can store the game is opened.
      btnSave.setOnAction(event -> {
         try {
            Desktop.getDesktop().open(new File(".")); //Tries to open directory.
         } catch (IOException e) {
            e.printStackTrace(); //Prints Stack trace if there is an error in opening directory.
         }
      });
      btnQuit.setOnAction(event -> backToMain(game));       //When button is pressed, the function to take you back to the main menu is run.

      Label whiteCaptures = new Label("Captured White Pieces:"); //New label to let user/s know whose captured pieces are stored.
      whiteCaptures.getStyleClass().add("captureBoxLabels"); //Assigns the label style to "captureBoxLabels" from styles.css file.
      Label blackCaptures = new Label("Captured Black Pieces:"); //New label to let user/s know whose captured pieces are stored.
      blackCaptures.getStyleClass().add("captureBoxLabels"); //Assigns the label style to "captureBoxLabels" from styles.css file.

//      HBox namesAndPromotion = new HBox(25, player); //New HBox to show which player turn it is and pawn promotion window.
//      namesAndPromotion.setAlignment(Pos.TOP_CENTER); //Positions the namesAndPromotion to the top center.

      VBox left = new VBox(25, btnDraw, btnResign,whiteCaptures, BoardGui.capturedWhitePieces(newGame )); //New VBox called left to store btnDraw, btnResign and the captured white pieces.
      left.setAlignment(Pos.CENTER); //Center aligns the left VBox to be centered.
      left.getStyleClass().add("game_VBoxes"); // Assigns the style of "game_VBoxes" from styles.css file
      VBox right = new VBox(25, btnSave, btnQuit,blackCaptures, BoardGui.capturedBlackPieces(newGame)); //New VBox called right to store btnSave, btnQuit and the captured black pieces.
      right.setAlignment(Pos.CENTER); //Center aligns the right VBox to be centered.
      right.getStyleClass().add("game_VBoxes"); // Assigns the style of "game_VBoxes" from styles.css file.

      game.setTop(BoardGui.whoseTurnNameBox(newGame)); //Sets the top of  the boarderPane to be the namesAndPromotion HBox.
      game.setLeft(left); //Sets the left of the borderPane to be the left VBox.
      game.setRight(right); //Sets the right of the borderPane to be the right VBox.

      Pane boardPane = new Pane(); //Creates a new Pane called borderPane
      boardPane.getChildren().add(BoardGui.boardFunc(newGame)); //Adds the boardFunc() to show in the boardPane.

      game.setCenter(boardPane); //Sets the center rof the game BoarderPane to be the boardPane.

      Scene gameScene = new Scene(game, 889, 500); //Creates a new scene for the gameScene and sets its dimensions.
      gameScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("./util/styles.css")).toExternalForm());    //Assigns the file "styles.css" to the screen.

      primaryStage.setScene(gameScene); //Sets the scene to be shown to be gameScene.
      primaryStage.show(); //Shows the gameScene
   }

   /**
    * Method to offer the player a draw
    * <p></p>
    * @param game BorderPane
    * Creates a new screen where the board is located and asks if opponent wants to
    * accept a draw.
    */
   public void requestDraw(BorderPane game){
      Label dawOffer = new Label(BoardGui.whoseTurnName(newGame)+" has offered a draw");  //Label to inform about who is offering the draw.
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
         btnQuitGame.setOnAction(event3 -> initMainMenu(stage)); //If "Quit Game" is pressed, user/s are returned to main menu.

         VBox resultDraw = new VBox(25, gameDraw, btnSave, btnQuitGame); //Creates a new VBox to show when a draw has been selected.
         resultDraw.setAlignment(Pos.CENTER); //Center positions the content.
         game.setCenter(resultDraw); //Sets the center of the screen to be the resultDraw VBox.
      });

      Button  btnDecline = new Button("Decline"); //Button for declining the draw.
      btnDecline.getStyleClass().add("buttons"); //Assigns the style for the button to "buttons" from styles.css file.
      btnDecline.setOnAction(event1 -> game.setCenter(BoardGui.boardFunc(newGame))); //When btnDecline is pressed,the board returns to teh screen.

      VBox draw = new VBox(25, dawOffer, btnAccept, btnDecline); //New VBox for the draw content.
      draw.setAlignment(Pos.CENTER); //Center positions the draw VBox.
      game.setCenter(draw); //Sets the center of the screen to be the draw VBox content.
   }

   /**
    * Method for player to decide to resign.
    * <p></p>
    *
    * @param game BorderPane
    *
    * */
   public void resign(BorderPane game){
      Label resign = new Label("Are you sure you want to resign?"); //Label to ensure the user wants to resign.
      resign.getStyleClass().add("labels"); //Sets the style for the label to "labels" from styles.css file.

      Button btnYes = new Button("Yes"); //Button for selecting Yes.
      btnYes.getStyleClass().add("buttons"); //Assigns the style for the button to "buttons" from styles.css file.
      Button btnNo = new Button("No"); //Button for selecting No.
      btnNo.getStyleClass().add("buttons"); //Assigns the style for the button to "buttons" from styles.css file.

      btnYes.setOnAction(event -> winByResignation(stage,game)); //Runs the winByResignation() function when btnYes is chosen.

      VBox choice = new VBox(25, resign, btnYes,btnNo); //Creates a new VBox called choice to hold the resignation screen content.
      choice.setAlignment(Pos.CENTER); //Center aligns the choice VBox.
      game.setCenter(choice); //Sets the center of the screen to be the choice VBox

      btnNo.setOnAction(event -> game.setCenter(BoardGui.boardFunc(newGame))); //When selection "No" is chosen, the chess board returns on the screen.
   }

   /**
    * Method for player to win by resignation.
    * <p></p>
    *
    * @param game BorderPane.
    * @param primaryStage Stage.
    * Lets user resign the game and then save the current game or quit.
    * */
   public void winByResignation(Stage primaryStage, BorderPane game){
      String winningPlayer; //Sting to hold wining player
      //Statement to see who the current player is and who will be wining to resignation.
      if(BoardGui.whoseTurnName(newGame).equals(newGame.getBlackPlayer().getName())){
         winningPlayer = newGame.getWhitePlayer().getName(); //White player wins if black resigns.
      }else {
         winningPlayer = newGame.getBlackPlayer().getName(); //Black player wins as white resigns.
      }
      //Label to inform user/s about who has won.
      Label prompt = new Label(winningPlayer + " wins! "  +  BoardGui.whoseTurnName(newGame) + " has resigned");
      prompt.getStyleClass().add("labels"); //Sets the style for the label to "labels" from styles.css file.

      Button btnSaveGame = new Button("Save Replay");
      btnSaveGame.getStyleClass().add("buttons");
      btnSaveGame.setOnAction(event -> {
         try {
            Desktop.getDesktop().open(new File(".")); //Tries to open directory.
         } catch (IOException e) {
            e.printStackTrace(); //Prints Stack trace if there is an error in opening directory.
         }
      });

      Button btnQuitGame = new Button("Quit Game"); //Button for Quiting game
      btnQuitGame.getStyleClass().add("buttons"); //Assigns the style for the button to "buttons" from styles.css file.
      btnQuitGame.setOnAction(event -> initMainMenu(primaryStage)); //When btnQuitGame is pressed, the user/s are returned to the main menu.

      VBox resignationBox = new VBox(25, prompt, btnSaveGame, btnQuitGame); //Creates a new VBox called resignationBox to store the label and button content.
      resignationBox.setAlignment(Pos.CENTER); //Center aligns the content in resignationBox.
      game.setCenter(resignationBox); //Sets the center of the game screen to the resignationBox.
   }

   /**
    * Method to bring you back to teh main menu
    * <p></p>
    * Prompts the player weather he wants to quit the game or not. If so,
    * the player/s is returned to the main menu
    * */
   public void backToMain(BorderPane quit){
      Label prompt = new Label("Are you sure you want to quit game?");    //new label to prompt the player/s if they want to quit the game
      prompt.getStyleClass().add("labels"); //Assigns the style of "labels" to the label from styles.css file.
      Button btnYes = new Button("Yes"); //New button for section of yes
      btnYes.getStyleClass().add("buttons"); //Assigns the style of "buttons" to the button from styles.css file.

      Button btnNo = new Button("No"); //New button for section of no
      btnNo.getStyleClass().add("buttons"); //Assigns the style of "buttons" to the button from styles.css file

      VBox quitGamePrompt = new VBox(25, prompt,btnYes, btnNo); //Creating a new VBox to hold the label and two buttons
      quitGamePrompt.setAlignment(Pos.CENTER); //Center positions the VBox
      quit.setCenter(quitGamePrompt); //Sets the center of  the screen to the quitGamePrompt, replacing the board.
      btnYes.setOnAction(event -> initMainMenu(stage));//When "Yes" is chosen, user/s get taken back to the main menu
      btnNo.setOnAction(event -> quit.setCenter(BoardGui.boardFunc(newGame))); //When "No" is chosen, user/s get taken back to the board
   }
}

