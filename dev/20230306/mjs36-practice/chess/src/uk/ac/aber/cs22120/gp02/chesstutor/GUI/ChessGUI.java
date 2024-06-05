package uk.ac.aber.cs22120.gp02.chesstutor.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import uk.ac.aber.cs22120.gp02.chesstutor.Board;
import uk.ac.aber.cs22120.gp02.chesstutor.Pieces.*;
import uk.ac.aber.cs22120.gp02.chesstutor.Square;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ChessGUI extends Application {

    private GridPane board;
    private static Board array;
    private List<Node> clickedPieceList;

    public static void main(String[] args, Board board) {
        array = board;
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        board = new GridPane();
        clickedPieceList = new ArrayList<Node>();

        board.setAlignment(Pos.CENTER); //keep in centre of window
        root.setCenter(board);

        setupBoard(primaryStage);

        EventHandler<MouseEvent> squareClickHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Node clickedPiece = event.getPickResult().getIntersectedNode();
                if (clickedPiece instanceof ImageView) {
                    clickedPieceList.add(clickedPiece);
                    ImageView clickedImageView = (ImageView) clickedPiece;
                    String filename = clickedImageView.getImage().impl_getUrl();
                    String pieceName = filename.substring(filename.length() - 9); //to get colour
                    GridPane gridPane = (GridPane) clickedPiece.getParent();
                    System.out.println(filename);
                    System.out.println(pieceName);

                }
            }
        };
        EventHandler<MouseEvent> pieceClickHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Node targetSquare = event.getPickResult().getIntersectedNode();
                if (targetSquare instanceof Rectangle) {
                    array.boardArray[GridPane.getRowIndex(targetSquare)-1][GridPane.getColumnIndex(targetSquare)-1] = array.boardArray[GridPane.getRowIndex(clickedPieceList.get(clickedPieceList.size()-1))-1][GridPane.getColumnIndex(clickedPieceList.get(clickedPieceList.size()-1))-1];
                    array.boardArray[GridPane.getRowIndex(clickedPieceList.get(clickedPieceList.size()-1))-1][GridPane.getColumnIndex(clickedPieceList.get(clickedPieceList.size()-1))-1] = new Square(GridPane.getRowIndex(clickedPieceList.get(clickedPieceList.size()-1))-1, GridPane.getColumnIndex(clickedPieceList.get(clickedPieceList.size()-1))-1);
                    array.printBoard();
                    clickedPieceList.clear();
                    setupBoard(primaryStage);
                }
            }
        };
        board.addEventFilter(MouseEvent.MOUSE_CLICKED, squareClickHandler);
        board.addEventFilter(MouseEvent.MOUSE_CLICKED, pieceClickHandler);

        Scene scene = new Scene(root, 450, 450);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setupBoard(Stage primaryStage) {
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
        String filename = "/uk/ac/aber/cs22120/gp02/chesstutor/GUI/Pieces/";

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (array.getSquare(i, j).isHasPiece()) {
                    String ID = "";
                    switch (array.getSquare(i, j).getPiece().getID()) {
                        case "K":
                            ID = "king";
                            break;
                        case "Kt":
                            ID = "knight";
                            break;
                        case "Q":
                            ID = "queen";
                            break;
                        case "B":
                            ID = "bishop";
                            break;
                        case "C":
                            ID = "castle";
                            break;
                        case "P":
                            ID = "pawn";
                            break;
                        default:
                            ID = "skip";
                            break;
                    }
                    if (!ID.equals("skip")) {
                        uk.ac.aber.cs22120.gp02.chesstutor.Color color = array.getSquare(i, j).getPiece().getPlayer();
                        String col = "";
                        if (color == uk.ac.aber.cs22120.gp02.chesstutor.Color.BLACK) {
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
}
