
package uk.ac.aber.cs221.gp02.chesstutor.gui;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import uk.ac.aber.cs221.gp02.chesstutor.game.*;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.Piece;
import uk.ac.aber.cs221.gp02.chesstutor.util.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;


public class GUI extends Application {
   private GridPane board;
   private BorderPane root;
   private static Board array;
   private static Game chess;
   private List<Node> clickedPieceList;

   private EventHandler<MouseEvent> whitePieceClickHandler;
   private EventHandler<MouseEvent> blackPieceClickHandler;
   private EventHandler<MouseEvent> enemyWhitePieceClickHandler;
   private EventHandler<MouseEvent> enemyBlackPieceClickHandler;
   private EventHandler<MouseEvent> squareClickHandler;

   public static void main(String[] args, Game game) {
      chess = game;
      array = game.getBoard();
      launch(args);
   }

   @Override
   public void start(Stage primaryStage) {
      root = new BorderPane();
      clickedPieceList = new ArrayList<Node>();


      squareClickHandler = new EventHandler<MouseEvent>() {
         @Override
         public void handle(MouseEvent event) {
            Node targetSquare = event.getPickResult().getIntersectedNode();
            if (targetSquare instanceof Rectangle) {
               if (!clickedPieceList.isEmpty() && ((Rectangle) targetSquare).getFill() == Color.GRAY) {
                  uk.ac.aber.cs221.gp02.chesstutor.moves.MakeMove.movePiece(array, chess.getBlackPlayer(),
                          GridPane.getRowIndex(clickedPieceList.get(clickedPieceList.size() - 1)) - 1,
                          GridPane.getColumnIndex(clickedPieceList.get(clickedPieceList.size() - 1)) - 1,
                          GridPane.getRowIndex(targetSquare) - 1,
                          GridPane.getColumnIndex(targetSquare) - 1);
                  array.printBoard();
                  clickedPieceList.clear();
                  setupBoard(primaryStage);

                  chess.nextRound();
                  System.out.println(chess.getTurn().toString());

               }
            }
         }
      };
      enemyWhitePieceClickHandler = new EventHandler<MouseEvent>() {
         @Override
         public void handle(MouseEvent event) {
            Node targetSquare = event.getPickResult().getIntersectedNode();
            if (targetSquare instanceof ImageView && !clickedPieceList.isEmpty()) {
               ImageView clickedImageView = (ImageView) targetSquare;
               String filename = clickedImageView.getImage().impl_getUrl();
               String pieceName = filename.substring(filename.length() - 9); //to get colour
               if (pieceName.equals("black.png")) {
                  uk.ac.aber.cs221.gp02.chesstutor.moves.MakeMove.movePiece(array, chess.getBlackPlayer(),
                          GridPane.getRowIndex(clickedPieceList.get(clickedPieceList.size() - 1)) - 1,
                          GridPane.getColumnIndex(clickedPieceList.get(clickedPieceList.size() - 1)) - 1,
                          GridPane.getRowIndex(targetSquare) - 1,
                          GridPane.getColumnIndex(targetSquare) - 1);
                  array.printBoard();
                  clickedPieceList.clear();
                  setupBoard(primaryStage);

                  chess.nextRound();
                  System.out.println(chess.getTurn().toString());

               }
            }
         }
      };
      enemyBlackPieceClickHandler = new EventHandler<MouseEvent>() {
         @Override
         public void handle(MouseEvent event) {
            Node targetSquare = event.getPickResult().getIntersectedNode();
            if (targetSquare instanceof ImageView && !clickedPieceList.isEmpty()) {
               ImageView clickedImageView = (ImageView) targetSquare;
               String filename = clickedImageView.getImage().impl_getUrl();
               String pieceName = filename.substring(filename.length() - 9); //to get colour
               if (pieceName.equals("white.png")) {
                  uk.ac.aber.cs221.gp02.chesstutor.moves.MakeMove.movePiece(array, chess.getBlackPlayer(),
                          GridPane.getRowIndex(clickedPieceList.get(clickedPieceList.size() - 1)) - 1,
                          GridPane.getColumnIndex(clickedPieceList.get(clickedPieceList.size() - 1)) - 1,
                          GridPane.getRowIndex(targetSquare) - 1,
                          GridPane.getColumnIndex(targetSquare) - 1);
                  array.printBoard();
                  clickedPieceList.clear();
                  setupBoard(primaryStage);

                  chess.nextRound();
                  System.out.println(chess.getTurn().toString());

               }
            }
         }
      };

      blackPieceClickHandler = new EventHandler<MouseEvent>() {
         @Override
         public void handle(MouseEvent event) {
            Node clickedPiece = event.getPickResult().getIntersectedNode();
            if (clickedPiece instanceof ImageView) {
               removeGrey();
               //setupBoard(primaryStage); //cleans board of any yellow
               //that doesn't work well
               //and another Grid over the old one - then just delete when no longer needed
               ImageView clickedImageView = (ImageView) clickedPiece;
               String filename = clickedImageView.getImage().impl_getUrl();
               String pieceName = filename.substring(filename.length() - 9); //to get colour
               if (pieceName.equals("black.png")) {
                  clickedPieceList.add(clickedPiece);

                  int row = GridPane.getRowIndex(clickedPiece) - 1;
                  int col = GridPane.getColumnIndex(clickedPiece) - 1;

                  //instead of making new, is it possible to change?

                  Rectangle square = new Rectangle(50, 50);
                  square.setFill(Color.YELLOW);
                  board.add(square, col+1, row+1);

                  ImageView peice = new ImageView(new Image(filename));
                  peice.setFitWidth(50);
                  peice.setFitHeight(50);
                  peice.setPickOnBounds(true);
                  board.add(peice, col + 1, row + 1);

                  Piece tempPiece = array.getBoardArray()[row][col].getPiece();
                  List<int[]> validMoves = tempPiece.getPossibleMoves(array, tempPiece, row, col);
                  for (int[] move:validMoves) {
                     Rectangle greySquare = new Rectangle(50,50);
                     greySquare.setFill(Color.GRAY);
                     board.add(greySquare, move[1]+1, move[0]+1);
                     if (array.getBoardArray()[move[0]][move[1]].hasPiece()) {
                        tempPiece = array.getBoardArray()[move[0]][move[1]].getPiece();
                        uk.ac.aber.cs221.gp02.chesstutor.util.Color pieceColor = tempPiece.getPieceColor();
                        Type pieceType = tempPiece.getPieceType();
                        filename = "/uk/ac/aber/cs221/gp02/chesstutor/gui/pieces/";
                        String ID = "";
                        switch (pieceType) {
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
                        String color = "";
                        if (pieceColor == uk.ac.aber.cs221.gp02.chesstutor.util.Color.BLACK) {
                           color = "_black.png";
                        } else {
                           color = "_white.png";
                        }
                        peice = new ImageView(new Image(filename + ID + color));
                        peice.setFitWidth(50);
                        peice.setFitHeight(50);
                        peice.setPickOnBounds(true);
                        board.add(peice, move[1]+1, move[0]+1);
                     }
                  }
               }
               System.out.println(filename);
               System.out.println(pieceName);
            }
         }
      };

      whitePieceClickHandler = new EventHandler<MouseEvent>() {
         @Override
         public void handle(MouseEvent event) {
            Node clickedPiece = event.getPickResult().getIntersectedNode();
            if (clickedPiece instanceof ImageView) {
               removeGrey();
               //setupBoard(primaryStage); //cleans board of any yellow
               //that doesn't work well
               //and another Grid over the old one - then just delete when no longer needed
               ImageView clickedImageView = (ImageView) clickedPiece;
               String filename = clickedImageView.getImage().impl_getUrl();
               String pieceName = filename.substring(filename.length() - 9); //to get colour
               if (pieceName.equals("white.png")) {
                  clickedPieceList.add(clickedPiece);

                  int row = GridPane.getRowIndex(clickedPiece) - 1;
                  int col = GridPane.getColumnIndex(clickedPiece) - 1;

                  //instead of making new, is it possible to change?

                  Rectangle square = new Rectangle(50, 50);
                  square.setFill(Color.YELLOW);
                  board.add(square, col+1, row+1);

                  ImageView peice = new ImageView(new Image(filename));
                  peice.setFitWidth(50);
                  peice.setFitHeight(50);
                  peice.setPickOnBounds(true);
                  board.add(peice, col + 1, row + 1);

                  Piece tempPiece = array.getBoardArray()[row][col].getPiece();
                  List<int[]> validMoves = tempPiece.getPossibleMoves(array, tempPiece, row, col);
                  for (int[] move:validMoves) {
                     Rectangle greySquare = new Rectangle(50,50);
                     greySquare.setFill(Color.GRAY);
                     board.add(greySquare, move[1]+1, move[0]+1);
                     if (array.getBoardArray()[move[0]][move[1]].hasPiece()) {
                        tempPiece = array.getBoardArray()[move[0]][move[1]].getPiece();
                        uk.ac.aber.cs221.gp02.chesstutor.util.Color pieceColor = tempPiece.getPieceColor();
                        Type pieceType = tempPiece.getPieceType();
                        filename = "/uk/ac/aber/cs221/gp02/chesstutor/gui/pieces/";
                        String ID = "";
                        switch (pieceType) {
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
                        String color = "";
                        if (pieceColor == uk.ac.aber.cs221.gp02.chesstutor.util.Color.BLACK) {
                           color = "_black.png";
                        } else {
                           color = "_white.png";
                        }
                        peice = new ImageView(new Image(filename + ID + color));
                        peice.setFitWidth(50);
                        peice.setFitHeight(50);
                        peice.setPickOnBounds(true);
                        board.add(peice, move[1]+1, move[0]+1);
                     }
                  }
               }
               System.out.println(filename);
               System.out.println(pieceName);
            }
         }
      };

      setupBoard(primaryStage);

      Scene scene = new Scene(root, 450, 450);
      primaryStage.setScene(scene);
      primaryStage.show();
   }

   private void removeGrey() {
      ObservableList<Node> childrens = board.getChildren();
      List<Rectangle> removeNodeList = new ArrayList<>();
      for(Node node : childrens) {
         if(node instanceof Rectangle && (((Rectangle) node).getFill() == Color.GRAY || ((Rectangle) node).getFill() == Color.YELLOW)) {
            Rectangle square= (Rectangle) node; // use what you want to remove
            removeNodeList.add(square);
         }
      }
      for (Rectangle node : removeNodeList) {
         board.getChildren().remove(node);
      }
   }

   private void removeBlack() {
      board.removeEventFilter(MouseEvent.MOUSE_CLICKED, blackPieceClickHandler);
      board.removeEventFilter(MouseEvent.MOUSE_CLICKED, enemyBlackPieceClickHandler);
   }
   private void removeWhite() {
      board.removeEventFilter(MouseEvent.MOUSE_CLICKED, whitePieceClickHandler);
      board.removeEventFilter(MouseEvent.MOUSE_CLICKED, enemyWhitePieceClickHandler);
   }
   private void addBlack() {
      board.addEventFilter(MouseEvent.MOUSE_CLICKED, blackPieceClickHandler);
      board.addEventFilter(MouseEvent.MOUSE_CLICKED, enemyBlackPieceClickHandler);
   }
   private void addWhite() {
      board.addEventFilter(MouseEvent.MOUSE_CLICKED, whitePieceClickHandler);
      board.addEventFilter(MouseEvent.MOUSE_CLICKED, enemyWhitePieceClickHandler);
   }

   public void setupBoard(Stage primaryStage) {
      board = new GridPane(); //create new each time as even though all was destroyed, old files remained underneath
      board.setAlignment(Pos.CENTER); //keep in centre of window
      root.setCenter(board);

      board.getChildren().clear();
      removeBlack();
      removeWhite();

      board.addEventFilter(MouseEvent.MOUSE_CLICKED, squareClickHandler);
//doesn't seem to change round until moved WHITE twice
      if (chess.getTurn() == uk.ac.aber.cs221.gp02.chesstutor.util.Color.WHITE) {
         addWhite();
         removeBlack();
      } else {
         addBlack();
         removeWhite();
      }

      for (int row = 0; row < 9; row++) {
         for (int col = 0; col < 9; col++) {
            Rectangle square = new Rectangle(50, 50);
            if (row == 0 || col == 0) {
               square.setFill(Color.LIGHTGRAY);
               Text label = new Text();
               if (row == 0 && col != 0) {
                  label.setText("    " + String.valueOf((char) (col + 64)));
               } else if (col == 0 && row != 0) {
                  label.setText(String.valueOf(9-row) + " ");
               }
               label.setStyle("-fx-font-size: 20");
               board.add(label, col, row);
            } else {
               square.setFill(((row + col) % 2 == 0) ? Color.WHITE : Color.LIGHTGREEN); //ternary operator
               square.setPickOnBounds(true);
               board.add(square, col, row);
            }
         }
      }
      setupPieces(primaryStage);
   }

   public void setupPieces(Stage primaryStage) {
      String filename = "/uk/ac/aber/cs221/gp02/chesstutor/gui/pieces/";

      for (int i = 0; i < 8; i++) {
         for (int j = 0; j < 8; j++) {
            if (array.getBoardArray()[i][j].hasPiece()) {
               String ID = "";
               switch (array.getBoardArray()[i][j].getPiece().getPieceType()) {
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
               uk.ac.aber.cs221.gp02.chesstutor.util.Color color = array.getBoardArray()[i][j].getPiece().getPieceColor();
               String col = "";
               if (color == uk.ac.aber.cs221.gp02.chesstutor.util.Color.BLACK) {
                  col = "_black.png";
               } else {
                  col = "_white.png";
               }
               ImageView peice = new ImageView(new Image(filename + ID + col));
               peice.setFitWidth(50);
               peice.setFitHeight(50);
               peice.setPickOnBounds(true);
               board.add(peice, j + 1, i + 1);

            }
         }
      }
   }
}


