package uk.ac.aber.cs221.gp02;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;


public class Queen extends Piece {

    public Queen(Color colour) {
        super(colour);
        this.ID = "Q";
    }

    public Queen(){}

    @Override
    public List<int[]> getValidMoves(Square[][] boardArray, Piece piece, int x, int y) {
        List<int[]> returnArray = new ArrayList<>();
        Castle tempCastle = new Castle(piece.getPlayer());
        Bishop tempBishop = new Bishop(piece.getPlayer());
        returnArray.addAll(tempCastle.getValidMoves(boardArray,piece,x,y));
        returnArray.addAll(tempBishop.getValidMoves(boardArray,piece,x,y));
        return returnArray;
    }

    @Override
    public void noValidMoves(int x, int y) {
        System.err.println("No Valid Moves for Queen at " + x + "," + y);
    }
}
