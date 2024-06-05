package uk.ac.aber.cs22120.gp02.chesstutor;

import uk.ac.aber.cs22120.gp02.chesstutor.Pieces.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    public Square[][] boardArray; //2d array of squares

    public Board() {
        this.boardArray = new Square[8][8];
        this.boardReset(); //constructor can use same code as the reset
    }

    public void boardReset2() {
        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                boardArray[i][j] = new Square(i, j);
            }
        }

        //boardArray[0][4] = new Square(0, 4, new Castle(Piece.Player.WHITE));
        boardArray[0][0] = new Square(0, 0, new Queen(Piece.Player.BLACK));
        boardArray[0][1] = new Square(0, 1, new Queen(Piece.Player.BLACK));
        boardArray[1][0] = new Square(1, 0, new Queen(Piece.Player.BLACK));
        boardArray[1][1] = new Square(1, 1, new Queen(Piece.Player.BLACK));
    }

    public Square getSquare(int x, int y) { //throws Exception {
        /*
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            throw new Exception("Index out of bounds");
        }*/

        return boardArray[x][y];
    }

    public void boardReset() {

        boardArray[0][0] = new Square(0, 0, new Castle(Piece.Player.WHITE));
        boardArray[0][7] = new Square(0, 7, new Castle(Piece.Player.WHITE));
        boardArray[0][1] = new Square(0, 1, new Knight(Piece.Player.WHITE));
        boardArray[0][6] = new Square(0, 6, new Knight(Piece.Player.WHITE));
        boardArray[0][2] = new Square(0, 2, new Bishop(Piece.Player.WHITE));
        boardArray[0][5] = new Square(0, 5, new Bishop(Piece.Player.WHITE));
        boardArray[0][3] = new Square(0, 3, new Queen(Piece.Player.WHITE));
        boardArray[0][4] = new Square(0, 4, new King(Piece.Player.WHITE));

        boardArray[7][0] = new Square(7, 0, new Castle(Piece.Player.BLACK));
        boardArray[7][7] = new Square(7, 7, new Castle(Piece.Player.BLACK));
        boardArray[7][1] = new Square(7, 1, new Knight(Piece.Player.BLACK));
        boardArray[7][6] = new Square(7, 6, new Knight(Piece.Player.BLACK));
        boardArray[7][2] = new Square(7, 2, new Bishop(Piece.Player.BLACK));
        boardArray[7][5] = new Square(7, 5, new Bishop(Piece.Player.BLACK));
        boardArray[7][3] = new Square(7, 3, new Queen(Piece.Player.BLACK));
        boardArray[7][4] = new Square(7, 4, new King(Piece.Player.BLACK));

        for (int i = 0; i <= 7; i ++) {
            boardArray[1][i] = new Square(1, i, new Pawn(Piece.Player.WHITE));
            boardArray[6][i] = new Square(1, i, new Pawn(Piece.Player.BLACK));
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
                Piece piece = getSquare(i,j).getPiece();
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
