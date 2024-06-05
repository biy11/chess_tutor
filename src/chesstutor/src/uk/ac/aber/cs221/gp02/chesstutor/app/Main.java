/*
 * @(#) Main.java 0.1 2023/05/04
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.gp02.chesstutor.app;

import javafx.application.Application;
import javafx.stage.Stage;
import uk.ac.aber.cs221.gp02.chesstutor.gui.GameGui;

/**
 * @author Michael [mjs36]
 * @author Bilal [biy1]
 */
public class Main extends Application {

      @Override
   public void start(Stage primaryStage)  {
         //Makes the GUI non-resizable
         primaryStage.setResizable(false);
         //initiates a new GameGui called gameGUi
         GameGui gameGui = new GameGui(primaryStage);
         //runs the initMainMenu method from GameGui Class
         gameGui.initMainMenu(primaryStage);
   }

   
   public static void main(String[] args) {
      Application.launch(args);
   }
}
