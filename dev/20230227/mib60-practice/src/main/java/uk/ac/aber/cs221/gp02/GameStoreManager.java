package uk.ac.aber.cs221.gp02;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;

public class GameStoreManager {

    public static void main(String[] args){
        GameStoreManager gameStoreManager = new GameStoreManager();

        //gameStoreManager.saveTest();
        gameStoreManager.load(); //load it
    }

    private void saveTest(){
        Board board = new Board();
        board.boardReset();
        board.setSavePath("test/foo/bar");
        board.save();
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

        o.loadBoard();
        o.printBoard();

        System.out.println(o.getSquare(7, 1).getPiece());
    }
}
