package uk.ac.aber.cs221.gp02.filepicker;

import com.sun.istack.internal.Nullable;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;

public class FilePicker implements IFilePicker {

    private Stage mainStage;

    public FilePicker(Stage stage){
        this.mainStage = stage;
    }

    /**
     * Opens the director picker and returns the directory the user picks, or null if a folder is not picked
     *
     * I think this pauses either the javafx thread, the main thread, or both, until a folder is picked
     * Which I don't think will cause issues but may be something to be aware of.
     *
     * @return The directory the user selected or null if they quit the window
     */
    @Override
    public File openFilePicker() {

        //Create a new DirectoryChooser
        DirectoryChooser directoryChooser = new DirectoryChooser();

        //Set the default directory
        File file = new File(".");
        directoryChooser.setInitialDirectory(file);
        directoryChooser.setTitle("pick a file idiot");

        //Show the dialogue and assign selected to the new directory
        File selected = directoryChooser.showDialog(mainStage.getOwner());

        //Do something if the file is not valid
        if(!checkFileValid(selected)){
            System.out.println("Selected file is invalid or null!");
        }

        //Do something else if the file is valid
        else{
            //Return the selected directory
            System.out.println(selected.toString());
        }


        return selected;
    }

    /**
     * Check the directory is valid, i.e. contains a valid
     * main xml file
     * Note: This has not been implemented fully yet and only checks for null file
     *
     * @param file The file to check
     * @return True if valid, otherwise false
     */
    private boolean checkFileValid(File file){
        return file != null;
    }
}