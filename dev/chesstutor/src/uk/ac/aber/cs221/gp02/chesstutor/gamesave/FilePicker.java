/*
 * @(#) FilePicker.java 0.3 2023/05/03
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.gp02.chesstutor.gamesave;

import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * @author mib60
 * A class for opening a directory picker and returning a file
 * @version 0.1 Initial Development
 * @version 0.2 Removed checkFileValid check
 * @version 0.3 Fixed warnings
 */
public class FilePicker {

    private final Stage mainStage;

    /**
     * Initializer - creates a new file picker
     * @param stage The main menu stage
     */
    public FilePicker(Stage stage){
        this.mainStage = stage;
    }

    /**
     * Opens the directory picker and returns the
     * directory the user picks, or null if a folder is not picked
     *
     * @return The directory the user selected or null if they quit the window
     */
    public File openFilePicker() {
        File selected;

        //Create a new DirectoryChooser
        DirectoryChooser directoryChooser = new DirectoryChooser();

        //Set the default directory
        File file = new File(".");
        directoryChooser.setInitialDirectory(file);
        directoryChooser.setTitle("Pick a file to load");

        //Show the dialogue and assign selected to the new directory
        selected = directoryChooser.showDialog(mainStage.getOwner());

        //Return the selected directory
        return selected;
    }
}