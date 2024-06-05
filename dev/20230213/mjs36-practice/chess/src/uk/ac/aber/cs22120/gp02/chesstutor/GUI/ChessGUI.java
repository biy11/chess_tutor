package uk.ac.aber.cs22120.gp02.chesstutor.GUI;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ChessGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        GridPane board = new GridPane();
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
                    square.setFill(((row + col) % 2 == 0) ? Color.WHITE : Color.BLACK); //ternary operator
                    board.add(square, col, row);
                }
            }
        }
        board.setAlignment(Pos.CENTER); //keep in centre of window
        root.setCenter(board);

        Scene scene = new Scene(root, 450, 450);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
