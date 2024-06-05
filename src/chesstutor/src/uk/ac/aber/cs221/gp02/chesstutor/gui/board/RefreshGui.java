/*
 * @(#) RefreshGui.java 0.6 2023/05/04
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */
package uk.ac.aber.cs221.gp02.chesstutor.gui.board;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import uk.ac.aber.cs221.gp02.chesstutor.game.Player;
import uk.ac.aber.cs221.gp02.chesstutor.moves.specialmoves.Promote;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.Piece;
import uk.ac.aber.cs221.gp02.chesstutor.util.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for refreshing the GUI
 *
 * @author Michael [mjs36]
 * @author Micah [mib60]
 * @author Bilal [biy1]
 * @version 0.1 - Initial development
 * @version 0.2 - Fixed taken pieces displaying the wrong colour
 * @version 0.3 - Changed to updateCapturedPieces after promotion
 * @version 0.4 - Removed changing Image size for promotion, and alignment of name box
 * @version 0.5 - Refactored and added Javadoc
 * @version 0.6 - moved refreshBoard to refreshGui class
 */
public class RefreshGui {

   /**
    * A method to refresh the board to show updated pieces
    */
   static void refreshBoard() {
      ObservableList<Node> children = BoardGui.board.getChildren();
      List<StackPane> removeNodeList = new ArrayList<>();
      for(Node node : children) {
         if(node instanceof StackPane) {
            ObservableList<Node> stackChildren = ((StackPane) node).getChildren();
            for(Node image : stackChildren) {
               if (image instanceof ImageView) {
                  StackPane piece = (StackPane) node; // use what you want to remove
                  removeNodeList.add(piece);
               }
            }
         }
      }
      for (StackPane node : removeNodeList) {
         BoardGui.board.getChildren().remove(node);
      }
      BoardGui.loadBackendPieces();
   }

   /**
    * A method to create a GridPane for CapturedPieces
    *
    * @return GridPane
    */
   private static GridPane createCapturedPiecesPane() {
      GridPane capturePiecesPane = new GridPane();
      //Creates a 3x5 grid in light-gray color, this is where the captured pieces will be stored.
      for(int row = 0; row < 5; row++){
         for(int col = 0; col < 2; col++){
            Rectangle square = new Rectangle(45,50);  //Creates a new square.
            square.setFill(Color.LIGHTGRAY); //Sets the color to light-gray
            square.setDisable(true); //makes pieces un-clickable
            capturePiecesPane.add(square,col,row);//Adds the new squares to the grid.
         }
      }
      return capturePiecesPane;
   }

   /**
    * A method for creating a grid to store black's captured pieces.
    * */
   public static GridPane createCapturedBlackPiecesPane() {
      BoardGui.captureBlackPieces = createCapturedPiecesPane();
      BoardGui.captureBlackPieces.getStyleClass().add("captureBox");
      BoardGui.captureBlackPieces.setAlignment(Pos.CENTER); //Centres the capture box in the Vbox
      return BoardGui.captureBlackPieces; //returns the blackCapturedPieces grid.
   }
   /**
    * A method for creating a grid to store white's captured pieces.
    * */
   public static GridPane createCapturedWhitePiecesPane() {
      BoardGui.captureWhitePieces = createCapturedPiecesPane();
      BoardGui.captureWhitePieces.getStyleClass().add("captureBox");
      BoardGui.captureWhitePieces.setAlignment(Pos.CENTER); //Centres the capture box in the Vbox
      return BoardGui.captureWhitePieces; //returns the blackCapturedPieces grid.
   }

   /**
    * A method for removing captured pieces from both GridPanes.
    * */
   static void removeCapturedPieces(GridPane capturedPieces) {
      ObservableList<Node> children = capturedPieces.getChildren();
      List<Node> removeNodeList = new ArrayList<>();
      for(Node node : children) {
         if(node instanceof StackPane || node instanceof Label) {
            removeNodeList.add(node);
         }
      }
      for (Node node : removeNodeList) {
         capturedPieces.getChildren().remove(node);
      }
   }

   /**
    * A method for creating the CapturedPieces image and count
    *
    * @param capturedPieces GridPane
    * @param filename filename of piece
    * @param pieceCount how many pieces of type have been captured
    * @param index where to appear on GridPane
    */
   private static void createCapturedImage(GridPane capturedPieces, String filename, int pieceCount, int index) {
      ImageView capturedPiece = new ImageView(new Image(filename));
      capturedPiece.setPickOnBounds(true);
      StackPane capturedPiecePane = new StackPane(capturedPiece);
      capturedPiecePane.setAlignment(Pos.CENTER);
      capturedPiecePane.getStyleClass().add("square");
      capturedPiecePane.setDisable(true);
      capturedPieces.add(capturedPiecePane, 0, index);
      Label label = new Label("x" + pieceCount);
      label.getStyleClass().add("captureBoxLabel"); //sets the style for the label according to the styles.css file.
      capturedPieces.add(label, 1, index);
   }

   /**
    * A method to add captured pieces to the CapturedPieces GridPane
    *
    * @param capturedPieces GridPane
    * @param color color
    */
   static void addCapturedPieces(GridPane capturedPieces, uk.ac.aber.cs221.gp02.chesstutor.util.Color color) {
      List<Piece> takenPieces;
      takenPieces = (color == uk.ac.aber.cs221.gp02.chesstutor.util.Color.BLACK) ?
              BoardGui.chessGame.getBlackPlayer().getTakenPieces() : BoardGui.chessGame.getWhitePlayer().getTakenPieces();

      int rookCount, knightCount, bishopCount, queenCount, pawnCount;
      rookCount = knightCount = bishopCount = queenCount = pawnCount = 0;
      for (Piece piece:takenPieces) {
         switch(piece.getPieceType()) {
            case ROOK:
               rookCount++;
               break;
            case KNIGHT:
               knightCount++;
               break;
            case BISHOP:
               bishopCount++;
               break;
            case QUEEN:
               queenCount++;
               break;
            case PAWN:
               pawnCount++;
               break;
         }
      }

      //Flip the colours to get correct img file
      color = (color == uk.ac.aber.cs221.gp02.chesstutor.util.Color.BLACK) ?
              uk.ac.aber.cs221.gp02.chesstutor.util.Color.WHITE : uk.ac.aber.cs221.gp02.chesstutor.util.Color.BLACK;

      String filename = BoardGui.filepath.concat("pieces/");
      if (rookCount != 0) {
         filename = (color == uk.ac.aber.cs221.gp02.chesstutor.util.Color.BLACK) ?
                 filename.concat("rook_black.png") : filename.concat("rook_white.png");

         createCapturedImage(capturedPieces, filename, rookCount, 0);
      }
      filename = BoardGui.filepath.concat("pieces/");
      if (knightCount != 0) {
         filename = (color == uk.ac.aber.cs221.gp02.chesstutor.util.Color.BLACK) ?
                 filename.concat("knight_black.png") : filename.concat("knight_white.png");

         createCapturedImage(capturedPieces, filename, knightCount, 1);
      }
      filename = BoardGui.filepath.concat("pieces/");
      if (bishopCount != 0) {
         filename = (color == uk.ac.aber.cs221.gp02.chesstutor.util.Color.BLACK) ?
                 filename.concat("bishop_black.png") : filename.concat("bishop_white.png");

         createCapturedImage(capturedPieces, filename, bishopCount, 2);
      }
      filename = BoardGui.filepath.concat("pieces/");
      if (queenCount != 0) {
         filename = (color == uk.ac.aber.cs221.gp02.chesstutor.util.Color.BLACK) ?
                 filename.concat("queen_black.png") : filename.concat("queen_white.png");

         createCapturedImage(capturedPieces, filename, queenCount, 3);
      }
      filename = BoardGui.filepath.concat("pieces/");
      if (pawnCount != 0) {
         filename = (color == uk.ac.aber.cs221.gp02.chesstutor.util.Color.BLACK) ?
                 filename.concat("pawn_black.png") : filename.concat("pawn_white.png");

         createCapturedImage(capturedPieces, filename, pawnCount, 4);
      }
   }

   /**
    * A method to update the CapturedPiece GridPane
    */
   static void updateCapturePiecesPane() { //updated both captured pieces list
      removeCapturedPieces(BoardGui.captureBlackPieces);
      removeCapturedPieces(BoardGui.captureWhitePieces);

      addCapturedPieces(BoardGui.captureBlackPieces, uk.ac.aber.cs221.gp02.chesstutor.util.Color.BLACK);
      addCapturedPieces(BoardGui.captureWhitePieces, uk.ac.aber.cs221.gp02.chesstutor.util.Color.WHITE);

   }
   /////////////////////////////////////////////////////////////////////////////////////////////////////////////

   /**
    * A method to create the whoseTurnNameBox
    *
    * @return HBox
    */
   public static HBox whoseTurnNameBox() {
      BoardGui.nameBox = new HBox(25); //New HBox to show which player turn it is and pawn promotion window.
      BoardGui.nameBox.setAlignment(Pos.TOP_CENTER); //Positions the namesAndPromotion to the top center.
      return BoardGui.nameBox;
   }

   /**
    * A method to update the whoseTurnNameBox
    */
   public static void updateTurnName() {
      ObservableList<Node> children = BoardGui.nameBox.getChildren();
      List<Node> removeNodeList = new ArrayList<>();
      for(Node node : children) {
         if(node instanceof Label) {
            removeNodeList.add(node);
         }
      }
      for (Node node : removeNodeList) {
         BoardGui.nameBox.getChildren().remove(node);
      }

      String playerName = (BoardGui.chessGame.getTurn() == uk.ac.aber.cs221.gp02.chesstutor.util.Color.BLACK) ?
              BoardGui.chessGame.getBlackPlayer().getName() : BoardGui.chessGame.getWhitePlayer().getName();

      Label player = new Label(playerName + "'s Turn");       //New label to let user know whose turn it is
      player.getStyleClass().add("nameLabel"); //Assigns the style of nameLabel to the label from styles.css file.
      BoardGui.nameBox.getChildren().add(player);
   }
   /////////////////////////////////////////////////////////////////////////////////////////////////////////////

   /**
    * A method to create the promotion box
    * <p></p>
    * Creates a HBox containing transparent squares, to be manipulated later
    *
    * @return HBox
    */
   public static HBox addPromotionBox() {
      BoardGui.promotionBox = new HBox(2); //HBox to show pawn promotion window.
      BoardGui.promotionBox.setAlignment(Pos.TOP_LEFT); //Position to the top center.
      Rectangle rectangle;
      for (int index = 0; index < 6; index++) { //add spacing to the left
         rectangle = new Rectangle(40, 41);
         rectangle.setFill(Color.TRANSPARENT);
         rectangle.setDisable(true);
         BoardGui.promotionBox.getChildren().add(rectangle);
      }
      rectangle = new Rectangle(7, 41); //add spacing to the left
      rectangle.setFill(Color.TRANSPARENT);
      rectangle.setDisable(true);
      BoardGui.promotionBox.getChildren().add(rectangle);
      for (int index = 0; index < 4; index++) { //add holding squares to be replaced by pieces
         rectangle = new Rectangle(40, 41);
         rectangle.setFill(Color.TRANSPARENT);
         rectangle.setDisable(true);
         BoardGui.promotionBox.getChildren().add(rectangle);
      }
      rectangle = new Rectangle(2, 41); //add spacing to the right
      rectangle.setFill(Color.TRANSPARENT);
      rectangle.setDisable(true);
      BoardGui.promotionBox.getChildren().add(rectangle);

      return BoardGui.promotionBox;
   }

   /**
    * A method to remove the promotion pieces after a Pawn has been promoted
    */
   static void removePromotionPiece() {
      BoardGui.promotionBox.getChildren().remove(BoardGui.promotionBox.getChildren().size()-4, BoardGui.promotionBox.getChildren().size());
      Rectangle rectangle;
      for (int index = 0; index < 4; index++) { //add holding squares to be replaced by pieces
         rectangle = new Rectangle(40, 41);
         rectangle.setFill(Color.TRANSPARENT);
         rectangle.setDisable(true);
         BoardGui.promotionBox.getChildren().add(rectangle);
      }
      rectangle = new Rectangle(2, 41); //add spacing to the right
      rectangle.setFill(Color.TRANSPARENT);
      rectangle.setDisable(true);
      BoardGui.promotionBox.getChildren().add(rectangle);
   }

   /**
    * A method to update the promotion box
    *
    * @param targetCoordinate coordinate Pawn is moving to
    */
   static void updatePromotionBox(int[] targetCoordinate) {
      BoardGui.promotionBox.getChildren().remove(BoardGui.promotionBox.getChildren().size()-5, BoardGui.promotionBox.getChildren().size());

      String filename = BoardGui.filepath.concat("pieces/");

      String color = (BoardGui.chessGame.getTurn() == uk.ac.aber.cs221.gp02.chesstutor.util.Color.BLACK) ?
              "_black.png" : "_white.png";

      String[] pieces = {"rook","knight","bishop","queen"};

      for (String s : pieces) {
         ImageView piece = new ImageView(new Image(filename.concat(s + color)));
         piece.setPickOnBounds(true);
         piece.setOnMouseClicked(event -> {
            String url = piece.getImage().impl_getUrl();
            Type promoteType = Type.PAWN;

            if (url.contains("rook")) {
               promoteType = Type.ROOK;
            } else if (url.contains("knight")) {
               promoteType = Type.KNIGHT;
            } else if (url.contains("bishop")) {
               promoteType = Type.BISHOP;
            } else if (url.contains("queen")) {
               promoteType = Type.QUEEN;
            }

            Player oppositePlayer = (BoardGui.chessGame.getTurn() == uk.ac.aber.cs221.gp02.chesstutor.util.Color.BLACK) ?
                    BoardGui.chessGame.getWhitePlayer() : BoardGui.chessGame.getBlackPlayer();

            Promote.promotePawn(BoardGui.chessGame.getBoard(), oppositePlayer, targetCoordinate[0], targetCoordinate[1], promoteType);

            refreshBoard();
            updateCapturePiecesPane();
            removePromotionPiece();
            BoardGui.board.setDisable(false);
            BoardGui.endTurn();
         });
         StackPane piecePane = new StackPane(piece);
         piecePane.setAlignment(Pos.CENTER); //Centres the whitePiecePane so it is centre.
         piecePane.getStyleClass().add("square"); //Adds the style of 'square' to the squarePane from styles.css.
         BoardGui.promotionBox.getChildren().add(piecePane);
      }
   }
}
