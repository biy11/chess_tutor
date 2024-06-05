package uk.ac.aber.cs221.gp02.chesstutor.tests.systemtests;

import javafx.collections.ObservableList;
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
import uk.ac.aber.cs221.gp02.chesstutor.game.Game;
import uk.ac.aber.cs221.gp02.chesstutor.game.Player;
import uk.ac.aber.cs221.gp02.chesstutor.game.Square;
import uk.ac.aber.cs221.gp02.chesstutor.gui.GameGui;
import uk.ac.aber.cs221.gp02.chesstutor.moves.specialmoves.Promote;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.Piece;
import uk.ac.aber.cs221.gp02.chesstutor.util.Type;

import java.util.ArrayList;
import java.util.List;

public class TestBoardGUI {
   static Game chessGame;
   static GridPane board; //package-private
   static String filepath;

   static int[] selectedPieceCoordinate;
   static StackPane selectedPiece;

   static GridPane captureBlackPieces;
   static GridPane captureWhitePieces;

   static HBox nameBox;
   static HBox promotionBox;

   /**public static GridPane createBoard(Game newGame) { //REFACTORED
      chessGame = newGame;
      board = new GridPane(); //Creates a new GridPane for the chess board, called board.
      filepath = "/uk/ac/aber/cs221/gp02/chesstutor/gui/";

      //Adds a label number for each row
      for(int row = 0; row < 8; row++){
         Label numberLabel = new Label(String.valueOf(8-row));
         numberLabel.getStyleClass().add("labels");
         board.add(numberLabel,0, row);
      }
      //Adds a label letter for the column.
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
               square.setFill(Color.LIGHTGREEN); //Assign the color light green to the square.
            }
            square.getStyleClass().add("square"); //Adds the style of 'square' to the squarePane from styles.css.
            square.setDisable(true); //disabled - can't be clicked
            board.add(square, col+1, row); //adds the square to the board.
         }
      }

      loadBackendPieces();
      RefreshGui.updatedTurnName();
      RefreshGui.updatedCapturePiecesPane();
      createClickHandlers();

      board.setAlignment(Pos.CENTER); //Center aligns the board.
      return board; //returns the board
   }

    static void loadBackendPieces() { //REFACTORED
       chessGame.getBoard().printBoard();

       String filename;
       String ID = "";

       Square[][] array = chessGame.getBoard().getBoardArray();

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
             filename = (array[row][col].getPiece().getPieceColor() == uk.ac.aber.cs221.gp02.chesstutor.util.Color.BLACK) ? filename.concat("_black.png") : filename.concat("_white.png");
             createImage(filename, col, row, false);
             }
          }
       }
    }

    static void createImage(String filename, int col, int row, boolean disable) {
       ImageView image = new ImageView(new Image(filename));
       image.setPickOnBounds(true); //click entire square
       image.setFitHeight(41);
       image.setFitWidth(40);
       StackPane imagePane = new StackPane(image);
       imagePane.setDisable(disable);
       imagePane.setAlignment(Pos.CENTER); //centre within StackPane
       imagePane.getStyleClass().add("square"); //Adds the style of 'square' to the pane from styles.css.
       board.add(imagePane, col + 1, row);
    }

    private static void createClickHandlers() {
       EventHandler<MouseEvent> pieceClickHandler = event -> {
       Node selectedNode = event.getPickResult().getIntersectedNode();
       if (selectedNode instanceof ImageView) { //only pieces, ImageView contained in piece StackPane
       String url = ((ImageView) selectedNode).getImage().impl_getUrl();
       if (url.contains("/pieces/")) { //is a piece
       uk.ac.aber.cs221.gp02.chesstutor.util.Color pieceColor;
       if (url.endsWith("black.png")) {
       pieceColor = uk.ac.aber.cs221.gp02.chesstutor.util.Color.BLACK;
       } else {
       pieceColor = uk.ac.aber.cs221.gp02.chesstutor.util.Color.WHITE;
       }
       if (chessGame.getTurn() == pieceColor) {
       if (selectedPiece != null) {
       RefreshHighlight.resetHighlight();
       }
       //valid click
       int row = GridPane.getRowIndex(selectedNode.getParent());
       int col = GridPane.getColumnIndex(selectedNode.getParent()) - 1;
       selectedPieceCoordinate = new int[]{row,col};
       selectedPiece = (StackPane) selectedNode.getParent();

       RefreshHighlight.highlightMoves();
       } else {
       Alert invalidPlayer = new Alert(Alert.AlertType.ERROR);
       invalidPlayer.setTitle("Play Error!");
       invalidPlayer.setHeaderText(null);
       if (pieceColor == uk.ac.aber.cs221.gp02.chesstutor.util.Color.BLACK) {
       invalidPlayer.setContentText("Please make sure you move a white Piece.");
       } else {
       invalidPlayer.setContentText("Please make sure you move a black Piece.");
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
             if (url.contains("/util/move") || url.contains("gold")) { //is valid target
             RefreshHighlight.resetHighlight(); //remove the highlights
             Player player;
             if (chessGame.getTurn() == uk.ac.aber.cs221.gp02.chesstutor.util.Color.BLACK) {
             player = chessGame.getBlackPlayer();
             } else {
             player = chessGame.getWhitePlayer();
             }
             int[] targetCoordinate = {GridPane.getRowIndex(selectedNode.getParent()),GridPane.getColumnIndex(selectedNode.getParent()) - 1};
             uk.ac.aber.cs221.gp02.chesstutor.moves.MakeMove.movePiece(chessGame.getBoard(),
             player, selectedPieceCoordinate[0], selectedPieceCoordinate[1], targetCoordinate[0],
             targetCoordinate[1]);
             selectedPieceCoordinate = null;
             RefreshHighlight.refreshBoard();
             RefreshGui.updatedCapturePiecesPane();
             if (shouldPromote(targetCoordinate[0])) {
                board.setDisable(true); //only allowed to click promotion window
                RefreshGui.updatePromotionBox(targetCoordinate);
             } else {
                selectedPiece = null;
                chessGame.nextRound();
                RefreshGui.updatedTurnName();
                RefreshHighlight.isKingInCheck(); //checks if king in check
                if(RefreshHighlight.isKingCheckMate()) { //checks if king in CheckMate - game should end
                board.setDisable(true);
                board.setVisible(false);
                String playerName;
                if (chessGame.getTurn() == uk.ac.aber.cs221.gp02.chesstutor.util.Color.BLACK) { //get opposite player
                playerName = chessGame.getWhitePlayer().getName();
                } else {
                playerName = chessGame.getBlackPlayer().getName();
                }
                Label label = new Label("Checkmate! " + playerName + " wins");
                label.getStyleClass().add("labels");
                GameGui.getCheckmate().getChildren().add(0, label);
                GameGui.getCheckmate().getChildren().remove(1);
                GameGui.getBorderMenu().setCenter(GameGui.getCheckmate());
                }
                }
             }
          }
      };

       board.addEventFilter(MouseEvent.MOUSE_CLICKED,pieceClickHandler);
       board.addEventFilter(MouseEvent.MOUSE_CLICKED,targetClickHandler);
    }

    public static String whoseTurnName(){
       return chessGame.getTurn() == uk.ac.aber.cs221.gp02.chesstutor.util.Color.BLACK ?
       chessGame.getBlackPlayer().getName() : chessGame.getWhitePlayer().getName();
       }

       public static GridPane getBoard(){
       return board;
    }
    **/
}
