/*
 * @(#) RefreshGui.java 0.3 2023/04/28
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
 * @author Micah [mib60]
 * @author Bilal [biy1]
 * @author Michael [mjs36]
 * @version 0.1 - Initial development
 * @version 0.2 - Fixed taken pieces displaying the wrong colour
 * @version 0.3 - Changed to updateCapturedPieces after promotion
 */
public class RefreshGui {
   private static GridPane createCapturedPiecesPane() { //REFACTORED
      GridPane capturePiecesPane = new GridPane();
      //Creates a 3x5 grid to in light-gray color, this is where the captured pieces will be stored.
      for(int row = 0; row < 5; row++){
         for(int col = 0; col < 2; col++){
            Rectangle square = new Rectangle(50,50);  //Creates a new square.
            square.setFill(Color.LIGHTGRAY); //Sets the color to light-gray
            square.setDisable(true); //makes pieces un-clickable
            capturePiecesPane.add(square,col,row);//Adds the new squares to the grid.
         }
      }
      return capturePiecesPane;
   }
   /**
    * Method for creating a gird to store black's captured pieces.
    *<p></p>
    * */
   public static GridPane createCapturedBlackPiecesPane() { //REFACTORED
      BoardGui.captureBlackPieces = createCapturedPiecesPane();
      BoardGui.captureBlackPieces.setAlignment(Pos.CENTER); //Centres the capture box in the Vbox
      return BoardGui.captureBlackPieces; //returns the blackCapturedPieces grid.
   }
   /**
    * Method for creating a gird to store white's captured pieces.
    *<p></p>
    * */
   public static GridPane createCapturedWhitePiecesPane() { //REFACTORED
      BoardGui.captureWhitePieces = createCapturedPiecesPane();
      BoardGui.captureWhitePieces.setAlignment(Pos.CENTER); //Centres the capture box in the Vbox
      return BoardGui.captureWhitePieces; //returns the blackCapturedPieces grid.
   }

   /**
    * Method for removing captured pieces from both GridPanes.
    *<p></p>
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

      //Flip the colours so the pieces display correctly
      if(color == uk.ac.aber.cs221.gp02.chesstutor.util.Color.WHITE) color = uk.ac.aber.cs221.gp02.chesstutor.util.Color.BLACK;
      else color = uk.ac.aber.cs221.gp02.chesstutor.util.Color.WHITE;

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

   static void updatedCapturePiecesPane() { //updated both captured pieces list
      removeCapturedPieces(BoardGui.captureBlackPieces);
      removeCapturedPieces(BoardGui.captureWhitePieces);

      addCapturedPieces(BoardGui.captureBlackPieces, uk.ac.aber.cs221.gp02.chesstutor.util.Color.BLACK);
      addCapturedPieces(BoardGui.captureWhitePieces, uk.ac.aber.cs221.gp02.chesstutor.util.Color.WHITE);

   }
   /////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public static HBox whoseTurnNameBox() {
      BoardGui.nameBox = new HBox(25); //New HBox to show which player turn it is and pawn promotion window.
      BoardGui.nameBox.setAlignment(Pos.TOP_CENTER); //Positions the namesAndPromotion to the top center.
      return BoardGui.nameBox;
   }

   public static void updatedTurnName() {
      ObservableList<Node> children = BoardGui.nameBox.getChildren();
      List<Node> removeNodeList = new ArrayList<>();
      for(Node node : children) {
         if(node instanceof Label || node instanceof HBox) {
            removeNodeList.add(node);
         }
      }
      for (Node node : removeNodeList) {
         BoardGui.nameBox.getChildren().remove(node);
      }
      String playerName;
      playerName = (BoardGui.chessGame.getTurn() == uk.ac.aber.cs221.gp02.chesstutor.util.Color.BLACK) ?
              BoardGui.chessGame.getBlackPlayer().getName() : BoardGui.chessGame.getWhitePlayer().getName();

      Label player = new Label(playerName + "'s Turn");       //New label to let user know whose turn it is
      player.getStyleClass().add("nameLabel"); //Assigns the style of nameLabel to the label from styles.css file.
      BoardGui.nameBox.getChildren().add(player);
   }
   /////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public static HBox addPromotionBox() {
      BoardGui.promotionBox = new HBox(2); //New HBox to show which player turn it is and pawn promotion window.
      BoardGui.promotionBox.setAlignment(Pos.TOP_LEFT); //Positions the namesAndPromotion to the top center.
      Rectangle rectangle;
      for (int index = 0; index < 6; index++) {
         rectangle = new Rectangle(40, 41);
         rectangle.setFill(Color.TRANSPARENT);
         rectangle.setDisable(true);
         BoardGui.promotionBox.getChildren().add(rectangle);
      }
      rectangle = new Rectangle(7, 41);
      rectangle.setFill(Color.TRANSPARENT);
      rectangle.setDisable(true);
      BoardGui.promotionBox.getChildren().add(rectangle);
      for (int index = 0; index < 4; index++) {
         rectangle = new Rectangle(40, 41);
         rectangle.setFill(Color.TRANSPARENT);
         rectangle.setDisable(true);
         BoardGui.promotionBox.getChildren().add(rectangle);
      }

      return BoardGui.promotionBox;
   }

   static void removePromotionPiece() {
      BoardGui.promotionBox.getChildren().remove(BoardGui.promotionBox.getChildren().size()-4, BoardGui.promotionBox.getChildren().size());
      for (int index = 0; index < 4; index++) {
         Rectangle rectangle = new Rectangle(40, 41);
         rectangle.setFill(Color.TRANSPARENT);
         rectangle.setDisable(true);
         BoardGui.promotionBox.getChildren().add(rectangle);
      }
   }

   static void updatePromotionBox(int[] targetCoordinate) {
      BoardGui.promotionBox.getChildren().remove(BoardGui.promotionBox.getChildren().size()-4, BoardGui.promotionBox.getChildren().size());

      String filename = BoardGui.filepath.concat("pieces/");

      String color;

      color = (BoardGui.chessGame.getTurn() == uk.ac.aber.cs221.gp02.chesstutor.util.Color.BLACK) ?
              "_black.png" : "_white.png";

      String[] pieces = {"rook","knight","bishop","queen"};

      for (String s : pieces) {
         ImageView piece = new ImageView(new Image(filename.concat(s + color)));
         piece.setPickOnBounds(true);
         piece.setFitHeight(41);
         piece.setFitWidth(40);
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
            RefreshHighlight.refreshBoard();
            updatedCapturePiecesPane();
            removePromotionPiece();
            BoardGui.board.setDisable(false);
            BoardGui.selectedPiece = null;
            BoardGui.chessGame.nextRound();
            updatedTurnName();
            RefreshHighlight.isKingInCheck(); //checks if king in check
            RefreshHighlight.isKingCheckMate(); //checks if king in CheckMate - game should end
         });
         StackPane piecePane = new StackPane(piece);
         piecePane.setAlignment(Pos.CENTER); //Centres the whitePiecePane so it is centre.
         piecePane.getStyleClass().add("square"); //Adds the style of 'square' to the squarePane from styles.css.
         BoardGui.promotionBox.getChildren().add(piecePane);
      }
   }
}
