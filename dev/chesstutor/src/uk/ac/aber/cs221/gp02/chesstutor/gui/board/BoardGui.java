/*
 * @(#) BoardGui.java 2.2 2023/05/04
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */
package uk.ac.aber.cs221.gp02.chesstutor.gui.board;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import uk.ac.aber.cs221.gp02.chesstutor.game.*;
import uk.ac.aber.cs221.gp02.chesstutor.gui.GameGui;

/**
 * A class that represents the actual Chess board gui
 *
 * @author Michael [mjs36]
 * @author Bilal [biy1]
 * @version 0.1 Initial development.
 * @version 0.2 Changed from using ImageView to StackPane for pieces.
 * @version 0.3 Added Castling and enPassant valid moves gui.
 * @version 0.4 Changed validMoves to use StackPane.
 * @version 0.5 Added Check/CheckMate GUI.
 * @version 0.6 Began on promotion gui, added checker for gui.
 * @version 0.7 Added CapturePieces updater.
 * @version 0.8 Added whoseTurn name updater.
 * @version 0.9 Added promotion box - haven't added click to promote.
 * @version 1.0 Promotion fully implemented - changed to use own HBox.
 * @version 1.1 Fixed warnings and lay-out. - biy1
 * @version 1.2 Added updated capture pieces call to createBoard to display captured pieces when a game is loaded - mib60
 * @version 1.3 fixed so King in check returns to Check colour after being clicked
 * @version 1.4 attempt at CheckMate Quit menu
 * @version 1.5 Refactoring, RefreshGui and RefreshHighlight created
 * @version 1.6 Image's set to fixed size, to accommodate new piece files
 * @version 1.7 Changed clickHandler to accommodate new gold circle for enPassant - should not be a move
 * @version 1.8 Added style for checkmate screen.
 * @version 1.9 Refactored and created endTurn method to remove duplicate code in other classes
 * @version 2.0 Javadoc added
 * @version 2.1 fullstops removed on alerts
 * @version 2.2 Added whoseTurnNotName
 * @see GameGui
 */
public class BoardGui {

   static Game chessGame;
   static GridPane board;
   static String filepath;
   
   static int[] selectedPieceCoordinate;
   static StackPane selectedPiece;

   static GridPane captureBlackPieces;
   static GridPane captureWhitePieces;

   static HBox nameBox;
   static HBox promotionBox;

   /**
    * A method to create the board
    *
    * @param newGame Game
    * @return GridPane
    */
   public static GridPane createBoard(Game newGame) {
      chessGame = newGame;
      board = new GridPane(); //create new GridPane, assign to field variable
      filepath = "/uk/ac/aber/cs221/gp02/chesstutor/gui/"; //set field filepath to equal string

      //Creates a new label, adds style to it and adds the label to each row
      for(int row = 0; row < 8; row++){
         Label numberLabel = new Label(String.valueOf(8-row));
         numberLabel.getStyleClass().add("labels");
         board.add(numberLabel,0, row);
      }
      //Creates and adds a label letter for the column.
      String[] letters = {"a","b","c","d","e","f","g","h"}; //list of strings for the labels.
      for(int col = 0; col < 8; col++){
         Label letterIndex = new Label(letters[col]);
         letterIndex.getStyleClass().add("labels");
         board.add(letterIndex,col+1, 8);
      }

      //Creates the 8x8 chess board.
      for (int row = 0; row < 8; row++) {
         for (int col = 0; col < 8; col++) {
            Rectangle square = new Rectangle(50, 50);
            if ((row + col) % 2 == 0) {
               square.setFill(Color.SNOW); //Assigns the color of snow to the square.
            } else {
               square.setFill(Color.LIGHTSTEELBLUE); //Assign the color light green to the square.
            }
            square.getStyleClass().add("square"); //Adds the style of 'square' to the squarePane from styles.css.
            square.setDisable(true); //disabled - can't be clicked
            board.add(square, col+1, row); //adds the square to the board.
         }
      }

      loadBackendPieces(); //Loads back-end pieces to the chess board.
      RefreshGui.updateTurnName(); //Updates which player's turn it is.
      RefreshGui.updateCapturePiecesPane(); //Updates the captures pieces.
      createClickHandlers(); //Creates click-handlers.

      board.setAlignment(Pos.CENTER); //Center aligns the board.
      return board; //returns the board.
   }

   /**
    * A method to read the backend board, and show the pieces on the GridPane
    */
   static void loadBackendPieces() {
      chessGame.getBoard().printBoard(); //Used for de-bugging.

      String filename;
      String ID = "";

      Square[][] array = chessGame.getBoard().getBoardArray(); //Gets back-end board pieces.
      //Add pieces from back-end board to the chess board.
      for (int row = 0; row < 8; row++) {
         for (int col = 0; col < 8; col++) {
            filename = filepath.concat("pieces/");
            if (array[row][col].hasPiece()) {
               switch (array[row][col].getPiece().getPieceType()) {
                  case KING:
                     ID = "king";
                     break;
                  case KNIGHT:
                     ID = "knight";
                     break;
                  case QUEEN:
                     ID = "queen";
                     break;
                  case BISHOP:
                     ID = "bishop";
                     break;
                  case ROOK:
                     ID = "rook";
                     break;
                  case PAWN:
                     ID = "pawn";
                     break;
               }
               filename = filename.concat(ID);
               filename = (array[row][col].getPiece().getPieceColor() == uk.ac.aber.cs221.gp02.chesstutor.util.Color.BLACK) ?
                       filename.concat("_black.png") : filename.concat("_white.png");
               createImage(filename, col, row);
            }
         }
      }
   }

   /**
    * A method to create an Image and place it on the GridPane
    *
    * @param filename String, exact path
    * @param col backend column
    * @param row backend row
    */
   static void createImage(String filename, int col, int row) {
      ImageView image = new ImageView(new Image(filename));
      image.setPickOnBounds(true); //click entire image
      StackPane imagePane = new StackPane(image);
      imagePane.setAlignment(Pos.CENTER); //centre within StackPane
      imagePane.getStyleClass().add("square"); //Adds the style of 'square' to the pane from styles.css.
      board.add(imagePane, col + 1, row);
   }

   /**
    * A method to check if a Pawn can promote
    *
    * @param targetRow the row the Pawn is moving to
    * @return if the Pawn can promote
    */
   private static boolean shouldPromote(int targetRow) {
      String url = "";

      for (Node child: selectedPiece.getChildren()) {
         if (child instanceof ImageView) {
            url = url.concat(((ImageView) child).getImage().impl_getUrl());
            break;
         }
      }

      return url.contains("pawn") && (targetRow == 0 || targetRow == 7); //is Pawn and on correct row
   }

   /**
    * A method to create the ClickHandlers for the GridPane
    */
   private static void createClickHandlers() {
      EventHandler<MouseEvent> pieceClickHandler = event -> {
         Node selectedNode = event.getPickResult().getIntersectedNode();
         if (selectedNode instanceof ImageView) { //only pieces, ImageView contained in piece StackPane
            String url = ((ImageView) selectedNode).getImage().impl_getUrl();
            if (url.contains("/pieces/")) { //is a piece
               uk.ac.aber.cs221.gp02.chesstutor.util.Color pieceColor;
               pieceColor = (url.endsWith("black.png")) ?
                       uk.ac.aber.cs221.gp02.chesstutor.util.Color.BLACK : uk.ac.aber.cs221.gp02.chesstutor.util.Color.WHITE;

               if (chessGame.getTurn() == pieceColor) {
                  //valid click
                  if (selectedPiece != null) { //if user has already clicked a piece
                     RefreshHighlight.resetHighlight();
                  }

                  int row = GridPane.getRowIndex(selectedNode.getParent());
                  int col = GridPane.getColumnIndex(selectedNode.getParent()) - 1; //-1 to get backend col
                  selectedPieceCoordinate = new int[]{row,col};
                  selectedPiece = (StackPane) selectedNode.getParent();

                  RefreshHighlight.highlightMoves();
               } else { //click on opposite colour piece
                  Alert invalidPlayer = new Alert(Alert.AlertType.ERROR);
                  invalidPlayer.setTitle("Play Error!");
                  invalidPlayer.setHeaderText(null);
                  if (pieceColor == uk.ac.aber.cs221.gp02.chesstutor.util.Color.BLACK) {
                     invalidPlayer.setContentText("Please make sure you move a White Piece");
                  } else {
                     invalidPlayer.setContentText("Please make sure you move a Black Piece");
                  }
                  invalidPlayer.showAndWait();
               }
            }
         }
      };

      EventHandler<MouseEvent> targetClickHandler = event -> {
         Node selectedNode = event.getPickResult().getIntersectedNode();
         if (selectedNode instanceof ImageView) {
            String url = ((ImageView) selectedNode).getImage().impl_getUrl();
            if (url.contains("/util/move") || url.endsWith("gold.png")) { //is valid target
               RefreshHighlight.resetHighlight(); //remove the highlights

               Player player = (chessGame.getTurn() == uk.ac.aber.cs221.gp02.chesstutor.util.Color.BLACK) ?
                       chessGame.getBlackPlayer() : chessGame.getWhitePlayer();

               int[] targetCoordinate = {GridPane.getRowIndex(selectedNode.getParent()),GridPane.getColumnIndex(selectedNode.getParent()) - 1};

               uk.ac.aber.cs221.gp02.chesstutor.moves.MakeMove.movePiece(chessGame.getBoard(),
                       player, selectedPieceCoordinate[0], selectedPieceCoordinate[1], targetCoordinate[0],
                       targetCoordinate[1]);

               selectedPieceCoordinate = null;
               RefreshGui.refreshBoard();
               RefreshGui.updateCapturePiecesPane();

               if (shouldPromote(targetCoordinate[0])) {
                  board.setDisable(true); //only allowed clicking promotion window
                  RefreshGui.updatePromotionBox(targetCoordinate);
                  Alert promote = new Alert(Alert.AlertType.INFORMATION);
                  promote.setTitle(null);
                  promote.setHeaderText(null);
                  promote.setContentText("You must promote the Pawn");
                  promote.showAndWait();
               } else {
                  endTurn();
               }
            }
         }
      };

      board.addEventFilter(MouseEvent.MOUSE_CLICKED,pieceClickHandler);
      board.addEventFilter(MouseEvent.MOUSE_CLICKED,targetClickHandler);
   }

   /**
    * A method to end the users turn
    * <p></p>
    * Duplicate code in RefreshGui, therefore, this method was created
    */
   static void endTurn() {
      selectedPiece = null;
      chessGame.nextRound();
      RefreshGui.updateTurnName();
      RefreshHighlight.isKingInCheck(); //checks if king in check
      if(RefreshHighlight.isKingCheckMate()) { //checks if king in CheckMate - game should end
         board.setDisable(true);
         //Checkmate window
         String playerName = (chessGame.getTurn() == uk.ac.aber.cs221.gp02.chesstutor.util.Color.BLACK) ?
                 chessGame.getWhitePlayer().getName() : chessGame.getBlackPlayer().getName(); //get opposite player
         Label label = new Label("Checkmate! " + playerName + " wins");
         label.getStyleClass().add("promptText");
         GameGui.getCheckmate().getChildren().add(0, label);
         GameGui.getCheckmate().getChildren().remove(1);
         StackPane stack = new StackPane();
         GameGui.getBorderGame().getRight().setDisable(true);
         GameGui.getBorderGame().getLeft().setDisable(true);
         stack.getChildren().add(0, BoardGui.getBoard());
         stack.getChildren().add(1, GameGui.getCheckmate());
         GameGui.getBorderGame().setCenter(stack);
      }
   }

   /**
    * A method to get the name of the current player
    *
    * @return String
    */
   public static String whoseTurnName(){
      return chessGame.getTurn() == uk.ac.aber.cs221.gp02.chesstutor.util.Color.BLACK ?
              chessGame.getBlackPlayer().getName() : chessGame.getWhitePlayer().getName();
   }

   /**
    * A method to get the name of the opposite player
    *
    * @return String
    */
   public static String whoseTurnNotName(){
      return chessGame.getTurn() == uk.ac.aber.cs221.gp02.chesstutor.util.Color.BLACK ?
              chessGame.getWhitePlayer().getName() : chessGame.getBlackPlayer().getName();
   }

   /**
    * A method to return the GridPane of the board
    *
    * @return GridPane
    */
   public static GridPane getBoard(){
      return board;
   }

}

