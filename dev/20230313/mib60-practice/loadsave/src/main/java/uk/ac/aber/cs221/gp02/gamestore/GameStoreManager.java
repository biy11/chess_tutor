package uk.ac.aber.cs221.gp02.gamestore;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import javafx.application.Application;
import javafx.stage.Stage;
import uk.ac.aber.cs221.gp02.board.Board;

import java.io.File;

public class GameStoreManager extends Application {

    public static void main(String[] args){
        GameStoreManager gameStoreManager = new GameStoreManager();

        //gameStoreManager.saveTest();
        //gameStoreManager.load(); //load it
        try {
            gameStoreManager.start(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void test(){

    }



    private void load(){
        Board o = null;
        JAXBContext jaxbContext = null;

        try {

            //Create jaxb entry point
            jaxbContext = org.eclipse.persistence.jaxb.JAXBContextFactory
                    .createContext(new Class[]{Board.class}, null);

            //create a File object with the .xml filetype
            File file = new File("test/foo/bar/0.xml");

            //Create the unmarshaller object (converts persistent xml data into java object)
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            o = (Board) jaxbUnmarshaller.unmarshal(file);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        if(o == null){
            System.out.println("O null");
            return;
        }


        o.loadBoard();
        o.printBoard();

        System.out.println(o.getSquare(2, 1).getPiece());
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        test();
    }
}
