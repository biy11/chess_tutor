package uk.ac.aber.cs221.gp02.chesstutor.gamesave;

import com.sun.istack.internal.Nullable;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;

/**
 * @author mib60
 * A class for opening a directory picker and returning a file
 * @version 0.1 Initial Development
 * @version 0.2 Removed checkFileValid check
 */
public class FilePicker {

    private Stage mainStage;

    /**
     * Initialiser - creates a new file picker
     * @param stage The mainmenu stage
     */
    public FilePicker(Stage stage){
        this.mainStage = stage;
    }

    /**
     * Opens the director picker and returns the directory the user picks, or null if a folder is not picked
     * I think this pauses either the javafx thread, the main thread, or both, until a folder is picked
     * Which I don't think will cause issues but may be something to be aware of.
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

    /**
     * Check the directory is valid, i.e. contains a valid
     * main xml file
     * Note: This has not been implemented fully yet and only checks for null file or an empty directory
     * @param file The file to check
     * @return True if valid, otherwise false
     */
    private boolean checkFileValid(File file){
        return file != null && file.listFiles() != null && file.listFiles().length >= 1;
    }
}