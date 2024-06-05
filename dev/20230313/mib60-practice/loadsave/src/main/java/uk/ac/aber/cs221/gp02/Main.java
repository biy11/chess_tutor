package uk.ac.aber.cs221.gp02;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * TODO add hasMoved and any additional info to saving
     *
     * @param args
     */

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Game game = new Game(primaryStage);

    }
}
