package uk.ac.aber.cs221.gp02.board;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.annotation.*;
import uk.ac.aber.cs221.gp02.board.pieces.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Board {

    @XmlTransient
    private File savePath;

    @XmlTransient
    private int turn = 0;

    @XmlTransient
    public Square[][] boardArray; //2d array of squares

    @XmlElement(name = "square")
    public ArrayList<Square> saveArray; //2d array of squares that are occupied

    public Board() {
        this.boardArray = new Square[8][8];
    }


    public void loadBoard(){

        //load empty squares
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boardArray[i][j] = new Square(i, j);
            }
        }

        //populate saved pieces
        for(Square square:saveArray){
            System.out.println(square.getPiece().toString());
            boardArray[square.getX()][square.getY()] = square;
        }

        saveArray = null;
    }

    public void boardReset2() {
        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                boardArray[i][j] = new Square(i, j);
            }
        }

        //boardArray[0][4] = new Square(0, 4, new Castle(Piece.Player.WHITE));
        boardArray[0][0] = new Square(0, 0, new Queen(Color.BLACK));
        boardArray[0][1] = new Square(0, 1, new Queen(Color.BLACK));
        boardArray[1][0] = new Square(1, 0, new Queen(Color.BLACK));
        boardArray[1][1] = new Square(1, 1, new Queen(Color.BLACK));
    }

    public void setSavePath(File savePath){
        this.savePath = savePath;
        if(!this.savePath.exists()) this.savePath.mkdirs();
    }

    public void save(){
        saveArray = new ArrayList<>();

        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {

                Square currentSquare = boardArray[i][j];

                if(currentSquare.getPiece() != null && currentSquare.isHasPiece()) saveArray.add(currentSquare);
            }
        }

        JAXBContext jaxbContext = null;

        try {
            //Create jaxb entry point
            jaxbContext = org.eclipse.persistence.jaxb.JAXBContextFactory
                    .createContext(new Class[]{Board.class}, null);

            //Create jaxb marshaller (converts java objects into persistent xml data)
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // output to file
            jaxbMarshaller.marshal(this, new File(savePath.getAbsolutePath()+"/"+turn+".xml"));

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        saveArray = null;
    }

    public Square getSquare(int x, int y) { //throws Exception {
        /*
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            throw new Exception("Index out of bounds");
        }*/

        return boardArray[x][y];
    }

    public void boardReset() {

        boardArray[0][0] = new Square(0, 0, new Castle(Color.BLACK));
        boardArray[0][7] = new Square(0, 7, new Castle(Color.BLACK));
        boardArray[0][1] = new Square(0, 1, new Knight(Color.BLACK));
        boardArray[0][6] = new Square(0, 6, new Knight(Color.BLACK));
        boardArray[0][2] = new Square(0, 2, new Bishop(Color.BLACK));
        boardArray[0][5] = new Square(0, 5, new Bishop(Color.BLACK));
        boardArray[0][3] = new Square(0, 3, new Queen(Color.BLACK));
        boardArray[0][4] = new Square(0, 4, new King(Color.BLACK));

        boardArray[7][0] = new Square(7, 0, new Castle(Color.WHITE));
        boardArray[7][7] = new Square(7, 7, new Castle(Color.WHITE));
        boardArray[7][1] = new Square(7, 1, new Knight(Color.WHITE));
        boardArray[7][6] = new Square(7, 6, new Knight(Color.WHITE));
        boardArray[7][2] = new Square(7, 2, new Bishop(Color.WHITE));
        boardArray[7][5] = new Square(7, 5, new Bishop(Color.WHITE));
        boardArray[7][3] = new Square(7, 3, new Queen(Color.WHITE));
        boardArray[7][4] = new Square(7, 4, new King(Color.WHITE));

        for (int i = 0; i <= 7; i ++) {
            boardArray[1][i] = new Square(1, i, new Pawn(Color.BLACK));
            boardArray[6][i] = new Square(6, i, new Pawn(Color.WHITE));
        }

        for (int i = 2; i <= 5; i++) {
            for (int j = 0; j <= 7; j++) {
                boardArray[i][j] = new Square(i, j);
            }
        }
    }

    public void printBoard() {
        String[][] array = new String[8][8];

        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                Square square = getSquare(i, j);

                if(square == null) continue;

                Piece piece = square.getPiece();
                if (piece == null) {
                    array[i][j] = " ";
                } else {
                    array[i][j] = piece.getID();
                }
            }
        }
        System.out.println(Arrays.deepToString(array).replace("],", "],\n"));
    }

    public List<int[]> possibleMoves(Square s1) {

        Piece piece = s1.getPiece();
        int x = s1.getX();
        int y = s1.getY();

        List<int[]> returnArray = new ArrayList<int[]>();

        returnArray = piece.getValidMoves(boardArray,piece,x,y);
        if (returnArray.size() == 0) {
            piece.noValidMoves(x,y);
        }

        return returnArray;
    }
}
