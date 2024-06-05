package uk.ac.aber.cs221.gp02.chesstutor.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import uk.ac.aber.cs221.gp02.chesstutor.game.Board;
import uk.ac.aber.cs221.gp02.chesstutor.game.Game;
import uk.ac.aber.cs221.gp02.chesstutor.game.Player;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.Piece;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class BoardGui {

   private static Board array;
   private static int roundCount =1;
   private static int clickCount, x1, y1, x2, y2;
   private static ImageView selectedPiece = null;

   private static List<Node> clickedPieceList = new ArrayList<>();

   private static List<List<Integer>> clicksList = new ArrayList<>();
   private static List<int[]> highlightSquares = new ArrayList<>();

   public static GridPane capturedWhitePieces(Game newGame){
      GridPane whiteCapturedPieces = new GridPane(); //creates a new GridPane for the white captured pieces.
      //Creates a 3x5 grid to in light-gray color, this is where the captured pieces will be stored.
      for(int row = 0; row < 5; row++){
         for(int col = 0; col < 3; col++){
            javafx.scene.shape.Rectangle square = new javafx.scene.shape.Rectangle(50,50); //Creates a new square.
            square.setFill(javafx.scene.paint.Color.LIGHTGRAY); //Sets the color to light-gray
            if(!newGame.getWhitePlayer().getTakenPieces().isEmpty()){
               //code to be added to store captured pieces here
            }
            whiteCapturedPieces.add(square,col,row); //Adds the new squares to the grid.
         }
      }
      return whiteCapturedPieces; //returns the whiteCapturedPieces grid.
   }

   /**
    * Method for storing black's captured pieces
    *<p></p>
    * returns a GridPane
    * */
   public static GridPane capturedBlackPieces(Game newGame){
      GridPane blackCapturedPieces = new GridPane();//creates a new GridPane for the black captured pieces.
      //Creates a 3x5 grid to in light-gray color, this is where the captured pieces will be stored.
      for(int row = 0; row < 5; row++){
         for(int col = 0; col < 3; col++){
            javafx.scene.shape.Rectangle square = new javafx.scene.shape.Rectangle(50,50);  //Creates a new square.
            square.setFill(javafx.scene.paint.Color.LIGHTGRAY); //Sets the color to light-gray
            if(!newGame.getBlackPlayer().getTakenPieces().isEmpty()){
               //code to be added to store captured pieces here
            }
            blackCapturedPieces.add(square,col,row);//Adds the new squares to the grid.
         }
      }
      return blackCapturedPieces; //returns the blackCapturedPieces grid.
   }

   /**
    * Method to create the chess board
    * <p></p>
    * Returns the GridPane board
    * */
   public static GridPane boardFunc(Game newGame) {
      GridPane board = new GridPane(); //Creates a new GridPane for the chess board, called board.
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
            Rectangle square = new Rectangle(50, 50); //Creates a new Rectangle called square and sets its dimensions.
            if ((row + col) % 2 == 0) {
               square.setFill(javafx.scene.paint.Color.SNOW); //Assigns the color of snow to the square.
            } else {
               square.setFill(Color.LIGHTGREEN); //Assign the color light green to the square.
            }
            int finalRow = row;
            int finalCol = col;
            square.setOnMouseClicked(event -> {
               int rows = GridPane.getRowIndex(square);
               int cols = GridPane.getColumnIndex(square);
               System.out.println("Empty");
               System.out.println(cols + "," + rows);
               handleClick(newGame,rows,cols,board);
            });
            square.setPickOnBounds(true);
            board.add(square, col+1, row); //adds the square to the board.
         }
      }
      setStartingPieces(newGame,board); //Puts the pieces on the board
      board.setAlignment(Pos.CENTER); //Center aligns the board.
      return board; //returns the board
   }
   public static void setStartingPieces(Game newGame,GridPane board) {
      String filename = "./pieces/";
      String[] pieces = {"rook","knight","bishop","queen","king","bishop","knight","rook"};
      String pawn = "pawn";
      String colorWhite = "_white.png";
      String colorBlack = "_black.png";

      for (int i = 0; i < pieces.length; i++) {
         ImageView whitePiece = createPiece(pieces[i], colorWhite);
         ImageView blackPiece = createPiece(pieces[i], colorBlack);
         setUpPieceHandlers(newGame,whitePiece, board, pieces[i],colorWhite);
         setUpPieceHandlers(newGame,blackPiece, board, pieces[i],colorBlack);
         board.add(whitePiece, i+1, 7);
         board.add(blackPiece, i+1, 0);
      }

      for (int i = 0; i < 8; i++) {
         ImageView whitePawn = createPiece(pawn, colorWhite);
         ImageView blackPawn = createPiece(pawn, colorBlack);
         setUpPieceHandlers(newGame,whitePawn, board, pieces[i],colorWhite);
         setUpPieceHandlers(newGame,blackPawn, board, pieces[i],colorBlack);
         board.add(whitePawn, i+1, 6);
         board.add(blackPawn, i+1, 1);
      }

   }

   private static ImageView createPiece(String type, String color) {
      ImageView piece = new ImageView(new javafx.scene.image.Image(Objects.requireNonNull(BoardGui.class.getResourceAsStream("./pieces/" + type + color))));
      return piece;
   }

   private static void setUpPieceHandlers(Game newGame,ImageView piece, GridPane board, String pieces,String color) {
      piece.setOnMouseClicked(event -> {
         whoseTurn(newGame,color);
         selectedPiece = piece;
         int rows = GridPane.getRowIndex(piece);
         int cols = GridPane.getColumnIndex(piece);
         handleClick(newGame,rows, cols, board);
         System.out.println(pieces + " " + color);
         System.out.println(cols + ", " + rows);

      });

   }
   public static HBox whoseTurnNameBox(Game newGame) {
         Label player = new Label(whoseTurnName(newGame) + "'s Turn");       //New label to let user know whose turn it is
         player.getStyleClass().add("nameLabel"); //Assigns the style of nameLabel to the label from styles.css file.
         HBox namesAndPromotion = new HBox(25, player); //New HBox to show which player turn it is and pawn promotion window.
         namesAndPromotion.setAlignment(Pos.TOP_CENTER); //Positions the namesAndPromotion to the top center.
         roundCount++;
         return namesAndPromotion;
   }


   /**
    * Function to return the name players whose turn it is.
    * <p></p>
    * Checks weather the round number is odd or even, then assigns the
    * player to the round depending on color section in startGameMenu.
    * */
   public static String whoseTurnName(Game newGame){
      //Statement to check if round count is even
      if(newGame.getRoundCount() % 2 == 0){
         return newGame.getBlackPlayer().getName(); //If true then blackPlayer is returned.
      }else return newGame.getWhitePlayer().getName(); //Else it will be whitePlayers that is returned.
   }

   public static void whoseTurn(Game newGame, String color){
      //Statement to check if round count is even
      if(newGame.getRoundCount() % 2 == 1) {
         if (color.equals("_white.png")) {
            System.out.println("Move piece");
         } else {
            Alert invalidPlayer = new Alert(Alert.AlertType.ERROR); //Initiates a new Alert named noNames.
            invalidPlayer.setTitle("Play Error!"); //Sets the title for the alert.
            invalidPlayer.setHeaderText(null); //Sets it to having no header text.
            invalidPlayer.setContentText("Please make sure you move a white Piece."); //shows the error message.
            invalidPlayer.showAndWait(); //Waits for the user to acknowledge the error message and clicks "Ok".

            Timeline showTime = new Timeline(new KeyFrame(Duration.seconds(2), event ->{
                   invalidPlayer.close();
         }));
            showTime.play();
            clickCount = 0;
         }
      } else if (newGame.getRoundCount() % 2 == 0) {
         if(color.equals("_black.png")){
            System.out.println("move piece");
            newGame.getWhitePlayer().getName();
         }
         else{
            Alert invalidPlayer = new Alert(Alert.AlertType.ERROR); //Initiates a new Alert named noNames.
            invalidPlayer.setTitle("Play Error!"); //Sets the title for the alert.
            invalidPlayer.setHeaderText(null); //Sets it to having no header text.
            invalidPlayer.setContentText("Please make sure you move a black Piece."); //shows the error message.
            invalidPlayer.showAndWait(); //Waits for the user to acknowledge the error message and clicks "Ok".
            Timeline showTime = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
               invalidPlayer.close();
            }));
            showTime.play();
            clickCount = 0;
         }
      }
   }
      public static void movePiece(Game newGame,ImageView selectedPiece, GridPane board, int destRow, int destCol){
      if(!(destCol >8 || destCol<1) && !(destRow < 1 || destRow > 8)){
         board.getChildren().remove(selectedPiece);
         board.add(selectedPiece,destCol,destRow);
         System.out.println("Moved piece to (" + destCol + "," + destRow + ")"); //for dev purposes, will be removed later
         newGame.nextRound();
         System.out.println("ROUND: " + newGame.getRoundCount());
      }
      else{
         System.out.println("move out of bounds");
      }

   }

   public static void handleClick(Game newGame, int row, int col, GridPane board) {
      if (clickCount == 0) {
         x1 = col;
         y1 = row;
         clickCount++;
      }
      else if (clickCount == 1) {
         x2 = col;
         y2 = row;
         clickCount = 0;
         List<Integer> clicks = new ArrayList<>();
         clicks.add(x1);
         clicks.add(y1);
         clicks.add(x2);
         clicks.add(y2);
         clicksList.add(clicks);
         movePiece(newGame,selectedPiece,board,y2,x2);
         System.out.println(clicksList);
         clicksList.clear();
         clickCount = 0;
      }
   }

}
