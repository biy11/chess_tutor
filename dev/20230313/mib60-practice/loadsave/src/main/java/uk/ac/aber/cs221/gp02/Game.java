package uk.ac.aber.cs221.gp02;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import javafx.application.Application;
import javafx.stage.Stage;
import uk.ac.aber.cs221.gp02.board.Board;
import uk.ac.aber.cs221.gp02.filepicker.FilePicker;

import java.io.File;

public class Game implements IGame{

    private MainMenu mainMenu;
    private Board board;
    private Player player1;
    private Player player2;
    private Stage mainStage;


    public Game(Stage primaryStage){
        this.mainStage = primaryStage;

        loadGame();
    }

    @Override
    public void saveGame() {

    }

    @Override
    public void loadGame() {
        FilePicker filePicker = new FilePicker(mainStage);

        File file = filePicker.openFilePicker();


        JAXBContext jaxbContext = null;

        try {

            //Create jaxb entry point
            jaxbContext = org.eclipse.persistence.jaxb.JAXBContextFactory
                    .createContext(new Class[]{Board.class}, null);

            //create a File object with the .xml filetype

            //Create the unmarshaller object (converts persistent xml data into java object)
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            board = (Board) jaxbUnmarshaller.unmarshal(new File(file.getAbsolutePath()+"/0.xml"));

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        if(board == null){
            System.out.println("O null");
            return;
        }


        board.loadBoard();
        board.printBoard();


        System.out.println(board.getSquare(2, 1).getPiece());
    }
}
